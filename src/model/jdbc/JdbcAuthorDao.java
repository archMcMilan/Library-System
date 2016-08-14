package model.jdbc;

import model.dao.AuthorDao;
import model.entities.Author;
import model.entities.Book;
import model.entities.Reader;
import view.LoggerMessage;
import view.View;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * @author Artem Pryzhkov
 * Class that contains methods that work with database table Author - send request and get response.
 * Implements class AuthorDao that contains "instruction" methods that show which functions must have
 *
 */
public class JdbcAuthorDao extends JdbcAbstractDao<Author> implements AuthorDao{


	public static final String SQL_SELECT_ALL_AUTHORS = "SELECT * FROM Author";
    public static final String SQL_SELECT_AUTHOR_BY_ID = "SELECT * FROM Author WHERE id_author=?";

    public static final String SQL_DELETE_AUTHOR_BY_ID = "DELETE FROM Author WHERE id_author = ?; ";
	public static final String SQL_INSERT_NEW_AUTHOR = "INSERT Author SET name=?, surname=?, patromynic=? "
			+ "id_author=?";

	public static final String SQL_UPDATE_AUTHOR = "UPDATE Author SET name=?, surname=?, patromynic=? "
			+ "where id_author=?";

    public JdbcAuthorDao(Connection connection) {
		super(connection);
	}
	
    /* (non-Javadoc)
     * @see model.dao.AbstractDao#create(model.entities.Entity)
     */
    @Override
    public boolean create(Author entity) {
    	return createWrapper(SQL_INSERT_NEW_AUTHOR,entity);
    }

    /* (non-Javadoc)
     * @see model.dao.AbstractDao#update(model.entities.Entity)
     */
    @Override
    public boolean update(Author entity) {
    	return updateWrapper(SQL_UPDATE_AUTHOR, entity);
    }

    /* (non-Javadoc)
     * @see model.dao.AbstractDao#delete(long)
     */
    @Override
    public boolean delete(long id) {
    	return deleteWrapper(SQL_DELETE_AUTHOR_BY_ID,id);
    }
    
	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#delete(model.entities.Entity)
	 */
	@Override
	public boolean delete(Author entity) {
		return delete(entity.getId());
	}

    /* (non-Javadoc)
     * @see model.dao.AbstractDao#find(long)
     */
    @Override
    public Author find(long id) {
    	return findWrapper(SQL_SELECT_AUTHOR_BY_ID, id);
    }

    
    /* (non-Javadoc)
     * @see model.dao.AbstractDao#findAll()
     */
    @Override
    public List<Author> findAll() {
        return findAllWrapper(SQL_SELECT_ALL_AUTHORS);
    }

    /* (non-Javadoc)
     * @see model.dao.AbstractDao#init(java.sql.ResultSet)
     */
    @Override
    protected Author init(ResultSet resultSet) {
        Author author= null;
        try {
            author = new Author(resultSet.getInt(View.ID_AUTHOR));
            author.setName(resultSet.getString(View.NAME));
            author.setSurname(resultSet.getString(View.SURNAME));
            author.setPatromynic(resultSet.getString(View.PATRONYMIC)!= null ? resultSet.getString(View.PATRONYMIC) : "");

        } catch (SQLException e) {
        	Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_COPIED);
        	throw new RuntimeException(LoggerMessage.UNSUCCESS_COPIED+this.getClass().getName());
        }
        Logger.getLogger(getClass()).
			info(LoggerMessage.RETURN_OBJECT_INIT+author);
        
        return  author;
    }

	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#setter(java.sql.PreparedStatement, model.entities.Entity)
	 */
	@Override
	protected void setter(PreparedStatement ps, Author entity) {
		try {
			ps.setString(1, entity.getName());
			ps.setString(2, entity.getSurname());
			ps.setString(3, entity.getPatromynic()!= null ? entity.getPatromynic() : "");
			ps.setLong(4,entity.getId());
			
			Logger.getLogger(getClass()).
				info(LoggerMessage.SUCCESS_SET+this.getClass().getName());

		} catch (SQLException e) {
			Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_SET);
			throw new RuntimeException(LoggerMessage.UNSUCCESS_SET+this.getClass().getName());
		}
	}
}
