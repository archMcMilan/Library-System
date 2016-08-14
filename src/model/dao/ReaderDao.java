package model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import model.entities.Book;
import model.entities.Copy;
import model.entities.Reader;

/**
 * @author Artem Pryzhkov
 * Class that contains "instruction" methods that show which special functions 
 * to work with Reader  must have inheritance class.
 * Implements class AbstractDao that contains general "instruction" methods for all entities
 *
 */
public interface ReaderDao extends AbstractDao<Reader> {
	/**
	 * Method finds Reader's object by login and pass - mean that finds such Reader's object,
	 * which field "id" equals with login and field "password" equals with pass
	 * @param login String value of the input login
	 * @param pass int - hashCode of the input password value
	 * @return Reader object if such object exist, otherwise return null 
	 */
	public abstract Reader findReaderByLoginAndPass(String login, int pass);
	
	/**
	 * Method finds List of Copy's objects by Readers's object id - mean that method return
	 * value of field copies from the Reader's object with such id
	 * @param id - Reader's object field "id" value
	 * @return List of Copy's objects if method finds Reader's object with such id
	 * (might return null if Reader's object field "copies" is empty), otherwise return null 
	 */
	public abstract List<Copy> findReaderCopies(long id);
	
	/**
	 * Method update Reader's object copies - mean that this Reader's object field "copies" value deleted
	 * and input value of the entity Reader's object. Operation is a transaction so if during the process appears
	 * an exception it will try to rollback through calling method rollbackTransaction() and write in logger about error 
	 * @param Reader entity - object with new "copies" list
	 */
	public abstract void updateCopyList(Reader entity);
	
	/**
	 * Method update Reader's object orderBooks - mean that this Reader's object field "orderBooks" value deleted
	 * and input value of the entity Reader's object. Operation is a transaction so if during the process appears
	 * an exception it will try to rollback through calling method rollbackTransaction() and write in logger about error 
	 * @param Reader entity - object with new "orderBooks" list
	 */
	public abstract void updateOrderBooks(Reader entity);
	
	/**
	 * Method finds List of Book's objects by Readers's object id - mean that method return
	 * value of field orderBooks from the Reader's object with such id
	 * @param id - Reader's object field "id" value
	 * @return List of Book's objects if method finds Reader's object with such id
	 * (might return null if Reader's object field "orderBooks" is empty), otherwise return null 
	 */
	public abstract List<Book> findOrderBookList(long id);
	
	/**
	 * Method finds Reader's object by Copy's object id - mean that finds such Reader's object,
	 * which field "copies" contains Copy's object with such id
	 * @param id - Copy's object field "id" value
	 * @return Reader object if such object exist, otherwise return null 
	 */
	public abstract Reader findReaderByCopy(long id);
}
