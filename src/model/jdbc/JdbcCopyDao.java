package model.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import model.dao.CopyDao;
import model.entities.Book;
import model.entities.Copy;
import model.entities.PresenceEnum;
import model.logic.DateImplementor;
import view.LoggerMessage;
import view.View;

/**
 * @author Artem Pryzhkov
 * Class that contains methods that work with database table Copy - send request and get response.
 * Implements class CopyDao that contains "instruction" methods that show which functions must have
 *
 */
public class JdbcCopyDao extends JdbcAbstractDao<Copy> implements CopyDao{

	public static final String SQL_SELECT_ALL_COPIES = "SELECT * FROM Copy";
    public static final String SQL_SELECT_COPY_BY_ID = "SELECT * FROM COPY WHERE id_copy=?";

    public static final String SQL_DELETE_COPY_BY_ID = "DELETE FROM COPY WHERE id_author = ?; ";
	public static final String SQL_INSERT_NEW_COPY = "INSERT Copy SET presence=?, data_taking=?, data_bring_back=?, "+
			"data_fact_bring_back=?, book=?, id_copy=?";

	public static final String SQL_UPDATE_COPY = "UPDATE Copy SET presence=?, data_taking=?, data_bring_back=?, "+
			"data_fact_bring_back=?, book=? WHERE id_copy=?";
	
	public static final String SQL_SELECT_COPY_BY_BOOK = "SELECT * FROM Copy where book = ?";
	public static final String SQL_SELECT_COPY_WITHOUT_FACT_DATE = "SELECT * FROM Copy WHERE data_fact_bring_back IS null"
			+ " and data_bring_back IS NOT null";

    public JdbcCopyDao(Connection connection) {
		super(connection);
	}
    
    /* (non-Javadoc)
     * @see model.dao.AbstractDao#create(model.entities.Entity)
     */
    @Override
    public boolean create(Copy entity) {
        return createWrapper(SQL_INSERT_NEW_COPY, entity);
    }

    /* (non-Javadoc)
     * @see model.dao.AbstractDao#update(model.entities.Entity)
     */
    @Override
    public boolean update(Copy entity) {
        return updateWrapper(SQL_UPDATE_COPY, entity);
    }

    /* (non-Javadoc)
     * @see model.dao.AbstractDao#delete(long)
     */
    @Override
    public boolean delete(long id) {
        return deleteWrapper(SQL_DELETE_COPY_BY_ID, id);
    }
    

    /* (non-Javadoc)
     * @see model.dao.AbstractDao#delete(model.entities.Entity)
     */
    @Override
	public boolean delete(Copy entity) {
		return delete(entity.getId());
	}
    
    /* (non-Javadoc)
     * @see model.dao.AbstractDao#find(long)
     */
    @Override
    public Copy find(long id) {
        return findWrapper(SQL_SELECT_COPY_BY_ID, id);
    }

    /* (non-Javadoc)
     * @see model.dao.AbstractDao#findAll()
     */
    @Override
    public List<Copy> findAll() {
        return findAllWrapper(SQL_SELECT_ALL_COPIES);
    }
    
    /* (non-Javadoc)
     * @see model.dao.AbstractDao#init(java.sql.ResultSet)
     */
    @Override
    protected Copy init(ResultSet resultSet) {
        Copy copy= null;
        try {
            copy = new Copy(resultSet.getInt(View.ID_COPY));
            copy.setPresence(resultSet.getInt(View.PRESENCE));
            copy.setDateTaking(resultSet.getDate(View.DATA_TAKING));
            copy.setDateBringBack(resultSet.getDate(View.DATA_BRING_BACK));
            copy.setDateFactBringBack(resultSet.getDate(View.DATA_FACT_BRING_BACK));
            copy.setBook(new JdbcBookDao(connection).find(resultSet.getLong(View.BOOK)));
            
        } catch (SQLException e) {
        	Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_COPIED);
        	throw new RuntimeException(LoggerMessage.UNSUCCESS_COPIED+getClass().getName());
        }
        Logger.getLogger(getClass()).
			info(LoggerMessage.RETURN_OBJECT_INIT+copy);

        return copy;
    }

	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#setter(java.sql.PreparedStatement, model.entities.Entity)
	 */
	@Override
	protected void setter(PreparedStatement ps, Copy entity) {
		try {
			ps.setInt(1, entity.getPresence().ordinal()+1);
			ps.setDate(2, entity.getDateTaking()!= null ? entity.getDateTaking() : null);
			ps.setDate(3, entity.getDateBringBack()!= null ? entity.getDateBringBack() : null);
			ps.setDate(4, entity.getDateFactBringBack()!= null ? entity.getDateFactBringBack() : null);
			ps.setLong(5, entity.getBook().getId());
			ps.setLong(6, entity.getId());
			
			Logger.getLogger(getClass()).info(LoggerMessage.SUCCESS_SET);
		} catch (SQLException e) {
			Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_SET);
			throw new RuntimeException(LoggerMessage.UNSUCCESS_SET+getClass().getName());
		}
	}

	/* (non-Javadoc)
	 * @see model.dao.CopyDao#findCopyByBook(long)
	 */
	@Override
	public List<Copy> findCopyByBook(long id) {
		List<Copy> copies=new ArrayList<>();
		try (Connection cn=connection;PreparedStatement statement = cn.prepareStatement(SQL_SELECT_COPY_BY_BOOK)){
			statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				copies.add(init(rs));
			}
			Logger.getLogger(getClass()).info(LoggerMessage.RETURN_OBJECT_FIND_COPIES_BY_BOOK);
			
			return copies;
		} catch (SQLException e) {
			Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_FIND_COPY_BY_BOOK+id);
		}
		Logger.getLogger(getClass()).info(LoggerMessage.RETURN_COPIES_NULL+id);
		return null;
	}
	

	/* (non-Javadoc)
	 * @see model.dao.CopyDao#findCopyThatOverdue()
	 */
	@Override
	public List<Copy> findCopyThatOverdue(){
		List<Copy> copies=new ArrayList<>();
		try (PreparedStatement statement =connection.prepareStatement(SQL_SELECT_COPY_WITHOUT_FACT_DATE)){
            ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				Copy copyTemp=init(rs);
				if(copyTemp.getDateBringBack().getTime()<DateImplementor.currentDate().getTime()){
					copies.add(copyTemp);
				}
			}
			Logger.getLogger(getClass()).info(LoggerMessage.RETURN_OBJECT_FIND_COPIES_WITHOUT_FACT_DATE);
			
			return copies;
		} catch (SQLException e) {
			Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_FIND_WITHOUT_FACT_DATE);
		}
		return null;
	}
	
}
