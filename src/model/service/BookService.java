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
	
	private BookDao createBookDao(){
		return DaoFactory.getFactory().createBookDao();
	}

	public List<Book> findAll() {
		return createBookDao().findAll();
	}

	public List<Book> findBookByCatalog(long id) {
		return createBookDao().findBookByCatalogId(id);
	}

	public List<Book> findBookByName(String name) {
		return createBookDao().findBookByName(name);
	}
	
	public List<Book> findBookByAuthor(String surname) {
		return createBookDao().findBookByAuthor(surname);
	}
	
	public Book findBookByCopy(long id){
		return createBookDao().findBookByCopy(id);
	}
}
