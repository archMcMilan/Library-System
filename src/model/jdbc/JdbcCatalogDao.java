package model.jdbc;

import model.dao.CatalogDao;
import model.entities.Catalog;
import view.LoggerMessage;
import view.View;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author Artem Pryzhkov
 * Class that contains methods that work with database table Catalog - send request and get response.
 * Implements class CatalogDao that contains "instruction" methods that show which functions must have
 *
 */
public class JdbcCatalogDao extends JdbcAbstractDao<Catalog> implements CatalogDao{

	public static final String SQL_SELECT_ALL_CATALOGS = "SELECT * FROM Catalog";
    public static final String SQL_SELECT_CATALOG_BY_ID = "SELECT * FROM Catalog WHERE id_catalog=?";

    public static final String SQL_DELETE_CATALOG_BY_ID = "DELETE FROM Catalog WHERE id_catalog = ?; ";
	public static final String SQL_INSERT_NEW_CATALOG = "INSERT Catalog SET name=?, id_catalog=?";

	public static final String SQL_UPDATE_CATALOG = "UPDATE Catalog SET name=? WHERE id_catalog=?";

    public JdbcCatalogDao(Connection connection) {
		super(connection);
	}
    /* (non-Javadoc)
     * @see model.dao.AbstractDao#create(model.entities.Entity)
     */
    @Override
    public boolean create(Catalog entity) {
        return createWrapper(SQL_INSERT_NEW_CATALOG, entity);
    }

    /* (non-Javadoc)
     * @see model.dao.AbstractDao#update(model.entities.Entity)
     */
    @Override
    public boolean update(Catalog entity) {
        return updateWrapper(SQL_UPDATE_CATALOG, entity);
    }

    /* (non-Javadoc)
     * @see model.dao.AbstractDao#delete(long)
     */
    @Override
    public boolean delete(long id) {
    	return deleteWrapper(SQL_DELETE_CATALOG_BY_ID,id);
    }

    /* (non-Javadoc)
     * @see model.dao.AbstractDao#delete(model.entities.Entity)
     */
    @Override
	public boolean delete(Catalog entity) {
		return delete(entity.getId());
	}


    /* (non-Javadoc)
     * @see model.dao.AbstractDao#find(long)
     */
    @Override
    public Catalog find(long id) {
        return findWrapper(SQL_SELECT_CATALOG_BY_ID, id);
    }

    /* (non-Javadoc)
     * @see model.dao.AbstractDao#findAll()
     */
    @Override
    public List<Catalog> findAll() {
        return findAllWrapper(SQL_SELECT_ALL_CATALOGS);
    }
    

    /* (non-Javadoc)
     * @see model.dao.AbstractDao#init(java.sql.ResultSet)
     */
    @Override
    protected Catalog init(ResultSet resultSet) {
        Catalog catalog= null;
        try {
            catalog = new Catalog(resultSet.getInt(View.ID_CATALOG));
            catalog.setName(resultSet.getString(View.NAME));
        } catch (SQLException e) {
        	Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_COPIED);
        	throw new RuntimeException(LoggerMessage.UNSUCCESS_COPIED+getClass().getName());
        }
        Logger.getLogger(getClass()).info(LoggerMessage.RETURN_OBJECT_INIT+catalog);
    
        
        return catalog;
    }

	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#setter(java.sql.PreparedStatement, model.entities.Entity)
	 */
	@Override
	protected void setter(PreparedStatement ps, Catalog entity) {
		try {
			ps.setString(1, entity.getName());
			ps.setLong(2, entity.getId());
			
			Logger.getLogger(getClass()).info(LoggerMessage.SUCCESS_SET);
		} catch (SQLException e) {
			Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_SET);
			throw new RuntimeException(LoggerMessage.UNSUCCESS_SET+getClass().getName());
		}
	}


}
