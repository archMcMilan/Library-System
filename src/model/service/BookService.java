package model.service;

import model.dao.BookDao;
import model.dao.DaoFactory;
import model.entities.Book;

import java.util.List;


/**
 * @author Artem Pryzhkov
 * Class provides layer between database and IDE
 */
public class BookService {
	private static BookService instance = new BookService();

	public static BookService getInstance() {
		return instance;
	}
	
	private BookDao createDaoBook(){
		return DaoFactory.getFactory().createBookDao();
	}

	public List<Book> findAll() {
		return createDaoBook().findAll();
	}

	public List<Book> findBookByCatalog(long id) {
		return createDaoBook().findBookByCatalogId(id);
	}

	public List<Book> findBookByName(String name) {
		return createDaoBook().findBookByName(name);
	}
	
	public List<Book> findBookByAuthor(String surname) {
		return createDaoBook().findBookByAuthor(surname);
	}
	
	public Book findBookByCopy(long id){
		return createDaoBook().findBookByCopy(id);
	}

	public List<Book> findAvailableBooksByCatalog(Long id) {
		return createDaoBook().findAvailableBooksByCatalog(id);
	}
	
	public List<Book> findUnAvailableBooksByCatalog(Long id) {
		return createDaoBook().findUnAvailableBooksByCatalog(id);
	}
}
