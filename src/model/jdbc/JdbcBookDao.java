package model.jdbc;

import model.dao.BookDao;
import model.entities.Author;
import model.entities.Book;
import model.entities.Catalog;
import view.LoggerMessage;
import view.View;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


/**
 * @author Artem Pryzhkov
 * Class that contains methods that work with database table Book - send request and get response.
 * Implements class BooksDao that contains "instruction" methods that show which functions must have
 *
 */
public class JdbcBookDao extends JdbcAbstractDao<Book> implements BookDao {

	public static final String SQL_SELECT_ALL_BOOKS = "SELECT * FROM Book";
	public static final String SQL_SELECT_BOOK_BY_ID = "SELECT * FROM Book where id_book=?";

	public static final String SQL_SELECT_ALL_AUTHORS = "SELECT * FROM Author_book_list WHERE id_book = ?";
	public static final String SQL_DELETE_BOOK_BY_ID = "DELETE FROM Book WHERE id_book = ?; ";
	public static final String SQL_DELETE_AUTHOR_LIST_BY_ID = "DELETE FROM Author_book_list WHERE id_book=?";
	public static final String SQL_INSERT_NEW_BOOK = "INSERT Book SET name=?, publication_year=?, pages_amount=? "+
			"catalog=?, id_book=?";
	public static final String SQL_INSERT_NEW_AUTHOR_LIST = "INSERT INTO Author_book_list VALUES (?, ?)";

	public static final String SQL_UPDATE_BOOK = "UPDATE Book SET name=?, publication_year=?, pages_amount=? "+
			"catalog=? WHERE id_book=?";
	
	public static final String SQL_SELECT_BOOK_BY_CATALOG_ID = "SELECT * FROM Book WHERE catalog = ?";
	public static final String SQL_SELECT_BOOK_BY_NAME = "SELECT * FROM Book WHERE name = ?";
	public static final String SQL_SELECT_BOOK_BY_AUTHOR = "SELECT * FROM Book WHERE id_book IN"+ 
			"(SELECT id_book FROM Author_book_list WHERE id_author IN"+
				"(SELECT id_author FROM Author WHERE surname=?));";
	
	public static final String SQL_SELECT_BOOK_BY_COPY = "SELECT * FROM Book WHERE id_book IN"+
			" (SELECT book FROM Copy WHERE id_copy=?)";

	
	public JdbcBookDao(Connection connection) {
		super(connection);
	}

	
	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#create(model.entities.Entity)
	 */
	@Override
	public boolean create(Book entity) {
		try (PreparedStatement statement = connection.prepareStatement(SQL_INSERT_NEW_BOOK,
				Statement.RETURN_GENERATED_KEYS)){
			
			connection.setAutoCommit(false);

			setter(statement, entity);
			statement.executeUpdate();

			for (Author a : entity.getAuthors()) {
				insertAuthorList(entity, a);
			}

			ResultSet key = statement.getGeneratedKeys();

			if (key.next()) {
				entity.setId(key.getLong(1));
			}

			Logger.getLogger(getClass()).info(LoggerMessage.SUCCESS_CREATION);
			
			connection.commit();
			connection.setAutoCommit(true);
			statement.close();
			return true;

		} catch (SQLException e) {
			rollbackTransaction(this.getClass().getName(),LoggerMessage.UNSUCCESS_CREATION);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#update(model.entities.Entity)
	 */
	@Override
	public boolean update(Book entity) {
		try (PreparedStatement statement = connection.prepareStatement(SQL_UPDATE_BOOK,
				Statement.RETURN_GENERATED_KEYS)){
			connection.setAutoCommit(false);

			setter(statement, entity);
			statement.executeUpdate();
			try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE_AUTHOR_LIST_BY_ID)) {
				ps.setLong(1, entity.getId());
				ps.executeUpdate();
			} catch (SQLException ex) {
				Logger.getLogger(getClass()).
					error(LoggerMessage.UNSUCCESS_DELETE_AUTHOR_LIST_BY_ID+
							getClass()+" "+entity.getId());
			}
			for (Author a : entity.getAuthors()) {
				insertAuthorList(entity, a);
			}
			connection.commit();
			connection.setAutoCommit(true);
			statement.close();
			Logger.getLogger(getClass()).
				info(LoggerMessage.SUCCESS_UPDATING);
			return true;
		} catch (SQLException e) {
			rollbackTransaction(this.getClass().getName(),LoggerMessage.UNSUCCESS_UPDATING);
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#delete(long)
	 */
	@Override
	public boolean delete(long id) {
		try (PreparedStatement statement =connection
				.prepareStatement(SQL_DELETE_BOOK_BY_ID + SQL_DELETE_AUTHOR_LIST_BY_ID)){
			statement.setLong(1, id);
			statement.setLong(2, id);
			statement.executeUpdate();
			statement.close();
			Logger.getLogger(getClass()).
				info(LoggerMessage.SUCCESS_DELETING+id);
			return true;
		} catch (SQLException e) {
			rollbackTransaction(this.getClass().getName(),LoggerMessage.UNSUCCESS_DELETING+id);
		}
		return false;
	}
	


	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#delete(model.entities.Entity)
	 */
	@Override
	public boolean delete(Book entity) {
		return delete(entity.getId());
	}

	
	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#find(long)
	 */
	@Override
	public Book find(long id) {
		return findWrapper(SQL_SELECT_BOOK_BY_ID, id);
	}

	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#findAll()
	 */
	@Override
	public List<Book> findAll() {
		return findAllWrapper(SQL_SELECT_ALL_BOOKS);
	}

	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#init(java.sql.ResultSet)
	 */
	@Override
	protected Book init(ResultSet resultSet) {
		Book book = null;
		try {
			book = new Book(resultSet.getInt(View.ID_BOOK));
			book.setName(resultSet.getString(View.NAME));
			book.setPublicationYear(resultSet.getInt(View.PUBLICATION_YEAR));
			book.setPagesAmount(resultSet.getInt(View.PAGES_AMOUNT));
			book.setCatalog(new JdbcCatalogDao(connection).find(resultSet.getLong(View.CATALOG)));
			List<Author> authors = new ArrayList<>();
			try (PreparedStatement statement =connection.prepareStatement(SQL_SELECT_ALL_AUTHORS)){
				statement.setLong(1, book.getId());
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					Author author = new JdbcAuthorDao(connection).find(rs.getLong(View.ID_AUTHOR));
					authors.add(author);
				}
				
				Logger.getLogger(getClass()).info(LoggerMessage.SUCCESS_COPIED_AUTHOR+authors.size());
			} catch (SQLException e) {
				Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_COPIED_AUTHOR);
				throw new RuntimeException(LoggerMessage.UNSUCCESS_COPIED_AUTHOR);
			}
			Logger.getLogger(getClass()).info(LoggerMessage.SUCCESS_INIT);
			book.setAuthors(authors);
		} catch (SQLException e) {
			Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_COPIED);
			throw new RuntimeException(LoggerMessage.UNSUCCESS_COPIED+getClass().getName());
		}

		return book;
	}

	/* (non-Javadoc)
	 * @see model.dao.AbstractDao#setter(java.sql.PreparedStatement, model.entities.Entity)
	 */
	@Override
	protected void setter(PreparedStatement ps, Book entity) {
		try {
			ps.setString(1, entity.getName());
			ps.setInt(2, entity.getPublicationYear());
			ps.setInt(3, entity.getPagesAmount());
			ps.setLong(4, entity.getCatalog().getId());
			ps.setLong(5, entity.getId());
			Logger.getLogger(getClass()).info(LoggerMessage.SUCCESS_SET);
		} catch (SQLException e) {
			Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_SET);
			throw new RuntimeException(LoggerMessage.UNSUCCESS_SET+getClass().getName());
		}
	}
	
	/**
	 * Method insert new pare of values reader.id and copy.id into the table Reader_copy_list in database. Operation is
	 * a transaction so if during the process appears an exception it will try to rollback through calling method
	 * rollbackTransaction() and write in logger about error.
	 * @param book
	 * @param author
	 */
	private void insertAuthorList(Book book, Author author) {
		try (PreparedStatement ps =connection.prepareStatement(SQL_INSERT_NEW_AUTHOR_LIST)){
			connection.setAutoCommit(false);
			ps.setLong(1, author.getId());
			ps.setLong(2, book.getId());
			ps.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
		} catch (SQLException ex) {
			rollbackTransaction(getClass().getName(),LoggerMessage.UNSUCCESS_CHANGE_AUTHOR_LIST);
		}
	}

	/* (non-Javadoc)
	 * @see model.dao.BookDao#findBookByCatalogId(long)
	 */
	@Override
	public List<Book> findBookByCatalogId(long id) {
		List<Book> books=new ArrayList<>();
		try (PreparedStatement statement =connection.prepareStatement(SQL_SELECT_BOOK_BY_CATALOG_ID)){
			statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				books.add(init(rs));
			}
			Logger.getLogger(getClass()).info(LoggerMessage.RETURN_OBJECT_FIND_BOOK_BY_CATALOG+
					books.size()+" "+id);
			return books;
		} catch (SQLException e) {
			Logger.getLogger(getClass()).
				error(LoggerMessage.UNSUCCESS_FIND_BOOK_BY_CATALOG+id);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see model.dao.BookDao#findBookByName(java.lang.String)
	 */
	@Override
	public List<Book> findBookByName(String name) {
		List<Book> books=new ArrayList<>();
		try (PreparedStatement statement =connection.prepareStatement(SQL_SELECT_BOOK_BY_NAME)){
			statement.setString(1, name);
            ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				books.add(init(rs));
			}
			Logger.getLogger(getClass()).
				info(LoggerMessage.RETURN_OBJECT_FIND_BOOK_BY_NAME+
						books.size()+" "+name);
			
			return books;
		} catch (SQLException e) {
			Logger.getLogger(getClass()).
				error(LoggerMessage.UNSUCCESS_FIND_BOOK_BY_NAME+name);
		}
		return null;
	}
	

	/* (non-Javadoc)
	 * @see model.dao.BookDao#findBookByAuthor(java.lang.String)
	 */
	@Override
	public List<Book> findBookByAuthor(String surname) {
		List<Book> books=new ArrayList<>();
		try (PreparedStatement statement =connection.prepareStatement(SQL_SELECT_BOOK_BY_AUTHOR)){
			statement.setString(1, surname);
            ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				books.add(init(rs));
			}
			Logger.getLogger(getClass()).
				info(LoggerMessage.RETURN_OBJECT_FIND_BOOK_BY_AUTHOR+
						books.size()+" "+surname);
			
			return books;
		} catch (SQLException e) {
			Logger.getLogger(getClass()).
				error(LoggerMessage.UNSUCCESS_FIND_BOOK_BY_AUTHOR+surname);
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see model.dao.BookDao#findBookByCopy(long)
	 */
	@Override
	public Book findBookByCopy(long id) {
		try (PreparedStatement statement =connection.prepareStatement(SQL_SELECT_BOOK_BY_COPY)){
			statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				Logger.getLogger(getClass()).
					info(LoggerMessage.RETURN_OBJECT_FIND_BOOK_BY_COPY+getClass());
				return init(rs);
			}
		} catch (SQLException e) {
			Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_FIND_BOOK_BY_COPY+id);
		}
		Logger.getLogger(getClass()).
			info(LoggerMessage.RETURN_OBJECT_FIND_BOOK_BY_COPY_NULL);
		return null;
	}

	@Override
	public List<Book> findAvailableBooksByCatalog(long id) {
		List<Book> books=new ArrayList<>();
		try (PreparedStatement statement =connection.prepareStatement("SELECT * from Book WHERE id_book in "
				+ "(select book from copy where data_fact_bring_back IS null group by book) and catalog=?")){
			statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				books.add(init(rs));
			}
            
			Logger.getLogger(getClass()).info(LoggerMessage.RETURN_OBJECT_FIND_BOOK_BY_CATALOG+
					books.size()+" "+id);
			
			return books;
		} catch (SQLException e) {
			Logger.getLogger(getClass()).
				error(LoggerMessage.UNSUCCESS_FIND_BOOK_BY_CATALOG+id);
		}
		return null;

	}

	@Override
	public List<Book> findUnAvailableBooksByCatalog(long id) {
		List<Book> books=new ArrayList<>();
		try (PreparedStatement statement =connection.prepareStatement("SELECT * from Book WHERE catalog=? and id_book in "
				+ "(select book from copy where data_fact_bring_back IS not null group by book)")){
			statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				books.add(init(rs));
			}
			Logger.getLogger(getClass()).info(LoggerMessage.RETURN_OBJECT_FIND_BOOK_BY_CATALOG+
					books.size()+" "+id);
			return books;
		} catch (SQLException e) {
			Logger.getLogger(getClass()).
				error(LoggerMessage.UNSUCCESS_FIND_BOOK_BY_CATALOG+id);
		}
		return null;

	}

}
