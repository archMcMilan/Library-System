package model.dao;

import java.util.List;

import model.entities.Entity;

/**
 * @author Artem Pryzhkov
 * Class that contains general "instruction" methods for all entities.
 *
 */
public interface AbstractDao<T extends Entity> {

	/**
	 * Method create new record in database. Method play roll of the instruction to inheritance classes
	 * @param entity inheritance class object
	 * @return true if creation execute successfully, otherwise false
	 */
	public boolean create(T entity);
	
	/**
	 * Method update record in database. Method play roll of the instruction to inheritance classes
	 * @param entity inheritance class object
	 * @return true if updating execute successfully, otherwise false
	 */
    public boolean update(T entity);
    
    /**
	 * Method delete record in database. Method play roll of the instruction to inheritance classes
	 * @param id inheritance class object
	 * @return true if deleting execute successfully, otherwise false
	 */
    public boolean delete (long id);
    
    /**
	 * Method delete record in database. Method play roll of the instruction to inheritance classes
	 * @param entity inheritance class object
	 * @return true if deleting execute successfully, otherwise false
	 */
    public boolean delete (T entity);
    
    /**
	 * Method find record in database by id. Method play roll of the instruction to inheritance classes
	 * @param entity inheritance class object
	 * @return object of the found inheritance class if search execute successfully, otherwise null
	 */
    public T find(long id);
    
    /**
	 * Method find all record in database. Method play roll of the instruction to inheritance classes
	 * @param entity inheritance class object
	 * @return list of objects of the found inheritance class if search execute successfully, otherwise null
	 */
    public List<T> findAll();
    

}