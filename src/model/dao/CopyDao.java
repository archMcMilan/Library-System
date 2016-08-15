package model.dao;

import model.entities.Copy;
import view.View;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Artem Pryzhkov
 * Class that contains "instruction" methods that show which special functions 
 * to work with Copy  must have inheritance class.
 * Implements class AbstractDao that contains general "instruction" methods for all entities
 *
 */
public interface CopyDao extends AbstractDao<Copy>{
    
    /**
	 * Method finds List of Copy's objects by Book's object id - mean that method return
	 * all Copy's objects where value of field book equals id
	 * @param id - Books's object field "id" value
	 * @return List of Copy's objects if method finds Copy's object with such Book id
	 * (might return null if haven't found any Copy's object with field "book" equals with input id),
	 * otherwise return null 
	 */
    List<Copy> findCopyByBook(long id);
    
    /**
	 * Method finds List of Copy's objects that overdue bring back date - mean that method return
	 * all Copy's objects where dateFactBringBack is null and dateBringBack Date is early than current time  
	 * @return List of Copy's objects if method finds Copy's object 
	 * (might return null if haven't found any Copy's object with dateBringBack Date than current time),
	 * otherwise return null 
	 */
    List<Copy> findCopyThatOverdue();
    
}
