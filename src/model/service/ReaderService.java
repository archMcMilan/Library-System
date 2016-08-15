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
	
	private ReaderDao createDaoReader(){
		return DaoFactory.getFactory().createReaderDao();
	}
	
	/**
	 * Method return list of Copy's objects that reader read
	 * @param id - reader
	 * @return list of Copy's objects
	 */
	public List<Copy> readerBooks(long id) {
		return createDaoReader().findReaderCopies(id);
	}
	

	/**
	 * Method find reader in database by login and pass
	 * @param login - String value of the input value
	 * @param pass - hashCode() of the input  value
	 * @return Object if find, otherwise null 
	 */
	public Reader checkLogin(String login, int pass){
		return createDaoReader().findReaderByLoginAndPass(login, pass);
	}
	
	/**
	 * Method show which books reader ordered
	 * @param id - reader id
	 * @return list of Book's objects
	 */
	public List<Book> readerOrders(long id) {
		return createDaoReader().findOrderBookList(id);
	}
	
	/**
	 * Method provides operation distributing copy of the Book to reader
	 * @param copy
	 * @param readerId - id of the reader
	 * @return object Reader with new Copy in the filed "copies"
	 */
	public Reader distribute(Copy copy,long readerId){
		Reader reader=createDaoReader().find(readerId);
		
		DaoFactory.getFactory().createCopyDao().update(copy);
		reader.addCopy(copy);
		createDaoReader().updateCopyList(reader);
		return reader;
	}
}
