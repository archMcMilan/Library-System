package model.dao;

import model.entities.Book;

import java.sql.Connection;
import java.util.List;

/**
 * @author Artem Pryzhkov
 * Class that contains "instruction" methods that show which special functions 
 * to work with Book must have inheritance class.
 * Implements class AbstractDao that contains general "instruction" methods for all entities
 *
 */
public interface BookDao extends AbstractDao<Book>{
    
    /**
	 * Method finds List of Book's objects by Catalog's object id - mean that method return
	 * Book's objects where catalog id equals with input id 
	 * @param id - Catalog's object field "id" value
	 * @return List of Book's objects if method finds Book's objects with such catalog id
	 * (might return null if haven't found Book's object with field "catalog" that equals with input id),
	 * otherwise return null 
	 */
    List<Book> findBookByCatalogId(long id);
    
    /**
	 * Method finds List of Book's objects by Book's object name - mean that method return
	 * Book's objects where name equals with input name
	 * @param name - Catalog's object field "name" value
	 * @return List of Book's objects if method finds Book's objects with such name
	 * (might return null if haven't found Book's object with field "name" that equals with input name),
	 * otherwise return null 
	 */
    List<Book> findBookByName(String name);
    
    /**
	 * Method finds List of Book's objects by Author's object - mean that method return
	 * Book's objects where Author's object field "surname" equals with input surname 
	 * @param surname - Author's object field "surname" value
	 * @return List of Book's objects if method finds Book's objects with such Author's object surname
	 * (might return null if haven't found Book's object with Author's object field "surname"),
	 * otherwise return null 
	 */
    List<Book> findBookByAuthor(String surname);
    
    /**
	 * Method finds Book's object by Copy's object id - mean that method return
	 * Copy's object field "book" value where Copy's object field "id" equals with input id 
	 * @param id - Copy's object field "id" value
	 * @return Book's objects if method finds Copy's objects with such id
	 * (might return null if haven't found Copy's object with field "id" that equals with input id),
	 * otherwise return null 
	 */
     Book findBookByCopy(long id);
     
     List<Book> findAvailableBooksByCatalog(long id);
     List<Book> findUnAvailableBooksByCatalog(long id);
}