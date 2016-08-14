package model.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import model.dao.ReaderDao;
import model.entities.Book;
import model.entities.Copy;
import model.entities.Reader;
import view.LoggerMessage;
import view.View;

/**
 * @author Artem Pryzhkov
 * Class that contains methods that work with database table Reader - send request and get response.
 * Implements class ReaderDao that contains "instruction" methods that show which functions must have
 *
 */
public class JdbcReaderDao extends JdbcAbstractDao<Reader> implements ReaderDao {

	public static final String SQL_SELECT_ALL_READERS = "SELECT * FROM Reader";
	public static final String SQL_SELECT_READER_BY_ID = "SELECT * FROM Reader where id_reader=?";

	public static final String SQL_SELECT_ALL_COPIES = "SELECT * FROM Reader_copy_list WHERE id_reader = ?";
	public static final String SQL_SELECT_ALL_ORDER_BOOKS = "SELECT * FROM Order_readers WHERE id_reader = ?";
	public static final String SQL_DELETE_READER_BY_ID = "DELETE FROM Reader WHERE id_reader = ?; ";
	public static final String SQL_DELETE_COPIES_LIST_BY_ID = "DELETE FROM Reader_copy_list WHERE id_reader=?";
	public static final String SQL_DELETE_ORDER_BOOKS_BY_ID = "DELETE FROM Order_readers WHERE id_reader=?";
	public static final String SQL_INSERT_NEW_READER = "INSERT Reader SET name=?, address=?, phone=? "
			+ "birthday=?, password=?, id_reader=?";
	public static final String SQL_INSERT_NEW_COPIES_LIST = "INSERT INTO Reader_copy_list VALUES (?, ?)";
	public static final String SQL_INSERT_NEW_ORDER_BOOK_LIST = "INSERT INTO Order_readers VALUES (?, ?)";

	public static final String SQL_UPDATE_READER = "UPDATE Reader SET name=?, address=?, phone=?, "
			+ "birthday=?, password=? WHERE id_reader=?";

	public static final String SQL_SELECT_READER_COPIES = "SELECT * FROM Copy WHERE id_copy IN "+
				"(SELECT id_copy FROM Reader_copy_list WHERE id_reader=?)";
	
	public static final String SQL_SELECT_READER_BY_LOGIN_AND_PASS = "SELECT * FROM Reader WHERE"+
			" id_reader=? and password=?";

	public static final String SQL_SELECT_READER_ORDERS = "SELECT * FROM Order_readers WHERE id_reader=?";
	
	public static final String SQL_SELECT_READER_BY_COPY = "SELECT * FROM Reader WHERE id_reader IN"
			+ "(SELECT id_reader FROM Reader_copy_list WHERE id_copy=?)";
	
	//final static Logger logger=Logger.getLogger(getClass());

	public JdbcReaderDao(Connection connection) {
		super(connection);
	}
	
	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#create(model.entities.Entity)
	 */
	@Override
	public boolean create(Reader entity) {
		try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_READER,
						Statement.RETURN_GENERATED_KEYS)){
			
			connection.setAutoCommit(false);

			setter(statement, entity);
			statement.executeUpdate();

			for (Copy c : entity.getCopies()) {
				insertCopiesList(entity, c);
			}
			for (Book b : entity.getOrderBooks()) {
				insertOrderBooks(entity, b);
			}
			ResultSet key = statement.getGeneratedKeys();
			if (key.next()) {
				entity.setId(key.getLong(1));
			}
			connection.commit();
			connection.setAutoCommit(true);
			Logger.getLogger(getClass()).info(LoggerMessage.SUCCESS_CREATION);
			
			return true;

		} catch (SQLException e) {
			rollbackTransaction(getClass().getName(),LoggerMessage.UNSUCCESS_CREATION);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#update(model.entities.Entity)
	 */
	@Override
	public boolean update(Reader entity) {
		try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_READER,
				Statement.RETURN_GENERATED_KEYS)){
			
			connection.setAutoCommit(false);

			setter(statement, entity);
			statement.executeUpdate();
			updateCopyList(entity);
			connection.commit();
			connection.setAutoCommit(true);
			Logger.getLogger(getClass()).info(LoggerMessage.SUCCESS_CREATION);
			return true;
		} catch (SQLException e) {
			rollbackTransaction(getClass().getName(),LoggerMessage.UNSUCCESS_UPDATING);
		}
		return false;
	}

	
	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#delete(long)
	 */
	@Override
	public boolean delete(long id) {
		try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_READER_BY_ID + SQL_DELETE_COPIES_LIST_BY_ID)){
			
			connection.setAutoCommit(false);
			statement.setLong(1, id);
			statement.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
			Logger.getLogger(getClass()).
				info(LoggerMessage.SUCCESS_DELETING+" "+id);
			return true;
		} catch (SQLException e) {
			rollbackTransaction(getClass().getName(),LoggerMessage.UNSUCCESS_DELETING+" "+id);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#delete(model.entities.Entity)
	 */
	@Override
	public boolean delete(Reader entity) {
		return delete(entity.getId());
	}
    
	
	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#find(long)
	 */
	@Override
	public Reader find(long id) {
		return findWrapper(SQL_SELECT_READER_BY_ID, id);
	}

	
	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#findAll()
	 */
	@Override
	public List<Reader> findAll() {
		return findAllWrapper(SQL_SELECT_ALL_READERS);
	}

	
	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#init(java.sql.ResultSet)
	 */
	@Override
	protected Reader init(ResultSet resultSet) {
		Reader reader = null;
		try {
			reader = new Reader(resultSet.getInt(View.ID_READER));
			reader.setName(resultSet.getString(View.NAME));
			reader.setAddress(resultSet.getString(View.ADDRESS));
			reader.setPhone(resultSet.getString(View.PHONE));
			reader.setBirthday(resultSet.getDate(View.BIRTHDAY));
			reader.setPassword(resultSet.getInt(View.PASSWORD));
			List<Copy> copies = new ArrayList<>();
			List<Book> books = new ArrayList<>();
			try (PreparedStatement statement =connection.prepareStatement(SQL_SELECT_ALL_COPIES)){
				statement.setLong(1, reader.getId());
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					Copy copy = new JdbcCopyDao(connection).find(rs.getLong(View.ID_COPY));
					copies.add(copy);
				}
				
				Logger.getLogger(getClass()).
					info(LoggerMessage.SUCCESS_COPIED_COPY+" "+copies.size());
			} catch (SQLException e) {
				Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_COPIED_COPY, e);
				throw new RuntimeException(LoggerMessage.UNSUCCESS_COPIED_BOOK+getClass().getName());
			}
			try (PreparedStatement statement =connection.prepareStatement(SQL_SELECT_ALL_ORDER_BOOKS)){
				statement.setLong(1, reader.getId());
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					Book book = new JdbcBookDao(connection).find(rs.getLong(View.ID_BOOK));
					books.add(book);
				}
				
				Logger.getLogger(getClass()).
					info(LoggerMessage.SUCCESS_COPIED_BOOK+" "+books.size());
			} catch (SQLException e) {
				Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_COPIED_BOOK, e);
				throw new RuntimeException(LoggerMessage.UNSUCCESS_COPIED_BOOK+getClass().getName());
			}
			reader.setCopies(copies);
			
			Logger.getLogger(getClass()).info(LoggerMessage.SUCCESS_INIT);
		} catch (SQLException e) {
			Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_COPIED);
		}
		Logger.getLogger(getClass()).
			info(LoggerMessage.RETURN_OBJECT_INIT+" "+reader);
		
		return reader;
	}

	
	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#setter(java.sql.PreparedStatement, model.entities.Entity)
	 */
	@Override
	protected void setter(PreparedStatement statement, Reader entity) {
		try {
			statement.setString(1, entity.getName());
			statement.setString(2, entity.getAddress());
			statement.setString(3, entity.getPhone());
			statement.setDate(4, entity.getBirthday());
			statement.setInt(5, entity.getPassword());
			statement.setLong(6, entity.getId());
			
			Logger.getLogger(getClass()).
				info(LoggerMessage.SUCCESS_SET);
		} catch (SQLException e) {
			Logger.getLogger(getClass()).
				error(LoggerMessage.UNSUCCESS_SET);
			throw new RuntimeException(LoggerMessage.UNSUCCESS_SET+getClass().getName());
		}
	}

	/**
	 * Method insert new pare of values reader.id and copy.id into the table Reader_copy_list in database. Operation is
	 * a transaction so if during the process appears an exception it will try to rollback through calling method
	 * rollbackTransaction() and write in logger about error.
	 * @param reader
	 * @param book
	 * @throws SQLException 
	 */
	private void insertCopiesList(Reader reader, Copy copy) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_COPIES_LIST);
		statement.setLong(1, reader.getId());
		statement.setLong(2, copy.getId());
		statement.executeUpdate();
		statement.close();
	}
	
	
	/**
	 * Method insert new pare of values reader.id and book.id into the table Order_readers in database. Operation is
	 * a transaction so if during the process appears an exception it will try to rollback through calling method
	 * rollbackTransaction() and write in logger about error.
	 * @param reader
	 * @param book
	 * @throws SQLException 
	 */
	private void insertOrderBooks(Reader reader, Book book) throws SQLException {
		PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_ORDER_BOOK_LIST);
		statement.setLong(1, reader.getId());
		statement.setLong(2, book.getId());
		statement.executeUpdate();
		statement.close();
	}
	
	/* (non-Javadoc)
	 * @see model.dao.ReaderDao#findReaderCopies(long)
	 */
	@Override
	public List<Copy> findReaderCopies(long id) {
		List<Copy> readerCopies = new ArrayList<>();
		try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_READER_COPIES)){
			statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				readerCopies.add(new JdbcCopyDao(connection).init(resultSet));
			}
			
			Logger.getLogger(getClass()).
				info(LoggerMessage.RETURN_OBJECT_FIND_READER_COPIES+" "+readerCopies.size());
			
			return readerCopies;
		} catch (SQLException e) {
			Logger.getLogger(getClass()).
				error(LoggerMessage.UNSUCCESS_FIND_READER_COPIES+" "+id);
		}
		return null;
	}
	
	
	/* (non-Javadoc)
	 * @see model.dao.ReaderDao#updateCopyList(model.entities.Reader)
	 */
	@Override
	public void updateCopyList(Reader entity){
		try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_COPIES_LIST_BY_ID)){
			connection.setAutoCommit(false);
			statement.setLong(1, entity.getId());
			statement.executeUpdate();
			
			for (Copy c : entity.getCopies()) {
				insertCopiesList(entity, c);
			}
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			rollbackTransaction(getClass().getName(),LoggerMessage.UNSUCCESS_UPDATE_COPY_LIST);
		}	
	}
	
	
	/* (non-Javadoc)
	 * @see model.dao.ReaderDao#updateOrderBooks(model.entities.Reader)
	 */
	@Override
	public void updateOrderBooks(Reader entity){
		try (PreparedStatement statement = connection.prepareStatement(SQL_DELETE_ORDER_BOOKS_BY_ID)){
			connection.setAutoCommit(false);
			statement.setLong(1, entity.getId());
			statement.executeUpdate();
			for (Book b : entity.getOrderBooks()) {
				insertOrderBooks(entity, b);
			}		
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			rollbackTransaction(getClass().getName(),LoggerMessage.UNSUCCESS_UPDATE_ORDER_BOOK);
		}
		
	}

	/* (non-Javadoc)
	 * @see model.dao.ReaderDao#findReaderByLoginAndPass(java.lang.String, int)
	 */
	@Override
	public Reader findReaderByLoginAndPass(String login, int pass) {
		try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_READER_BY_LOGIN_AND_PASS)){
			statement.setLong(1, Long.parseLong(login));
			statement.setInt(2, pass);
			ResultSet resultSet=statement.executeQuery();
			if(resultSet.next()){
				return init(resultSet);
			}
		} catch (SQLException e) {
			Logger.getLogger(getClass()).
				error(LoggerMessage.UNSUCCESS_FIND_READER_BY_LOGIN_AND_PASS);
		}
		Logger.getLogger(getClass()).
			info(LoggerMessage.RETURN_NULL_DUE_TO_NO_LOGIN_OR_PASS);
		return null;
	}

	/* (non-Javadoc)
	 * @see model.dao.ReaderDao#findOrderBookList(long)
	 */
	@Override
	public List<Book> findOrderBookList(long id) {
		List<Book> readerOrders = new ArrayList<>();
		try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_READER_ORDERS)){
			statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				readerOrders.add(new JdbcBookDao(connection).find(resultSet.getLong(View.ID_BOOK)));
			}
			
			Logger.getLogger(this.getClass()).
				info(LoggerMessage.RETURN_OBJECT_FIND_READER_ORDERS+" "+readerOrders.size());
			return readerOrders;
		} catch (SQLException e) {
			Logger.getLogger(this.getClass()).
				error(LoggerMessage.UNSUCCESS_FIND_READER_ORDERS+" "+id);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see model.dao.ReaderDao#findReaderByCopy(long)
	 */
	@Override
	public Reader findReaderByCopy(long id){
		try (PreparedStatement statement = connection.prepareStatement(SQL_SELECT_READER_BY_COPY)){
			statement.setLong(1, id);
			ResultSet resultSet=statement.executeQuery();
			if(resultSet.next()){
				return init(resultSet);
			}
		} catch (SQLException e) {
			Logger.getLogger(getClass()).
				error(LoggerMessage.UNSUCCESS_FIND_READER_BY_COPY+" "+id);
		}
		Logger.getLogger(getClass()).
			info(LoggerMessage.RETURN_NULL_READER_BY_COPY_ID+" "+id);
		return null;
	}
	
}
