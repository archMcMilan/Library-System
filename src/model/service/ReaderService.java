package model.service;

import java.util.List;

import model.dao.DaoFactory;
import model.dao.ReaderDao;
import model.entities.Book;
import model.entities.Copy;
import model.entities.Reader;

/**
 * @author Artem Pryzhkov
 * Class provides layer between database and IDE
 */
public class ReaderService {
	private static ReaderService instance = new ReaderService();

	public static ReaderService getInstance() {
		return instance;
	}
	
	private ReaderDao createReaderDao(){
		return DaoFactory.getFactory().createReaderDao();
	}
	
	public List<Copy> readerBooks(long id) {
		return createReaderDao().findReaderCopies(id);
	}
	
	public Reader checkLogin(String login, int pass){
		return createReaderDao().findReaderByLoginAndPass(login, pass);
	}
	
	public List<Book> readerOrders(long id) {
		return createReaderDao().findOrderBookList(id);
	}
	
}
