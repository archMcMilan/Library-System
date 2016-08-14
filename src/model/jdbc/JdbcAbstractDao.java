package model.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import model.entities.Entity;
import view.LoggerMessage;


/**
 * @author Artem Pryzhkov
 *
 * Class contains methods that provide base functions with database.
 * Class used in template method pattern - all inheritance classes must realized methods:
 * T init(ResultSet resultSet) and void setter(PreparedStatement ps,T entity) which used in 
 * base function methods
 * @param <T extends Entity> 
 */
public abstract class JdbcAbstractDao<T extends Entity> {
	
	protected Connection connection;

	public JdbcAbstractDao(Connection connection) {
		this.connection = connection;
	}

	protected void finalize(){
		try {
			connection.close();
		} catch (SQLException e) {
			Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_CLOSE_CONNECTION);
		}
	}
	/**
     * Method create for template pattern.In method use method "init" that implemented in inheritance classes.
     * @param <T>
     * @param sql request 
     * @return List of found inheritance classes' object
     */
    protected List<T> findAllWrapper(String sql){
        List<T> objects=new ArrayList<>();
        try (PreparedStatement statement=connection.prepareStatement(sql)){
            ResultSet resultSet=statement.executeQuery();
            while (resultSet.next()){
                objects.add(init(resultSet));
            }
            Logger.getLogger(getClass()).info(LoggerMessage.RETURN_OBJECTS+objects.size());
            
        } catch (SQLException e) {
        	Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_FIND_ALL);
        }
        return objects;
    }


	/**
     * Method create for template pattern.In method use method "init" that implemented in inheritance classes.
     * @param sql request 
     * @param id of the inheritance classes' object
     * @return inheritance classes' object
     */
    public T findWrapper(String sql,long id){
        T object=null;
        try (PreparedStatement statement=connection.prepareStatement(sql);){
            statement.setLong(1, id);
            ResultSet resultSet =statement.executeQuery();
            if (resultSet.next()){
                object=init(resultSet);
            }
            Logger.getLogger(getClass()).info(LoggerMessage.RETURN_OBJECT_BY_ID+id);
            
        } catch (SQLException e) {
        	Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_FIND_BY_ID+id);
        }
        return object;
    }
    
    /**
     * Method create for template pattern.In method use method "setter" that implemented in inheritance classes 
     * Operation is a transaction so if during the process appears an exception it will try to rollback through calling method
	 * rollbackTransaction() and write in logger about error.
     * @param sql request 
     * @param entity inheritance class object
     * @return true if creation execute successfully, otherwise false
     */
    protected boolean createWrapper(String sql,T entity){
    	try (PreparedStatement statement = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS)){
    		connection.setAutoCommit(false);

			setter(statement, entity);
			statement.executeUpdate();
			
			ResultSet key = statement.getGeneratedKeys();

			if (key.next()) {
				entity.setId(key.getLong(1));
			}
			

			Logger.getLogger(getClass()).info(LoggerMessage.SUCCESS_CREATION);
			
			connection.commit();
			connection.setAutoCommit(true);
			return true;

		} catch (SQLException e) {
			rollbackTransaction(getClass().getName(),LoggerMessage.UNSUCCESS_CREATION);
		}
		return false;
    }
    
    /**
     * Method create for template pattern.In method use method "setter" that implemented in inheritance classes 
     * Operation is a transaction so if during the process appears an exception it will try to rollback through calling method
	 * rollbackTransaction() and write in logger about error.
     * @param sql request 
     * @param entity inheritance class object
     * @return true if updating execute successfully, otherwise false
     */
    protected boolean updateWrapper(String sql,T entity){
    	try (PreparedStatement statement = connection.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS)){
    		connection.setAutoCommit(false);
			
			setter(statement, entity);
			statement.executeUpdate();
			
			Logger.getLogger(this.getClass().getName()).
				info(LoggerMessage.SUCCESS_UPDATING+" "+ this.getClass().getName());
			
			connection.commit();
			connection.setAutoCommit(true);
			return true;

		} catch (SQLException e) {
			rollbackTransaction(getClass().getName(),LoggerMessage.UNSUCCESS_UPDATING);
		}
		return false;
    }
    
    /**
     * Method create for template pattern.In method use method "setter" that implemented in inheritance classes 
     * Operation is a transaction so if during the process appears an exception it will try to rollback through calling method
	 * rollbackTransaction() and write in logger about error.
     * @param sql request 
     * @param entity inheritance class object
     * @return true if deleting execute successfully, otherwise false
     */
    protected boolean deleteWrapper(String sql,long id){
    	try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setLong(1, id);
			statement.executeUpdate();
			
			Logger.getLogger(getClass()).
				info(LoggerMessage.SUCCESS_DELETING+" "+id);
			return true;
		} catch (SQLException e) {
			rollbackTransaction(getClass().getName(),LoggerMessage.UNSUCCESS_DELETING+" "+id);
		}
		return false;
    }
    
    /**
	 * Method initialize inheritance object's fields 
	 * @param resultSet - ResultSet that will be executed in AbstractDao class' methods
	 * @return inheritance class object
	 */
    protected abstract T init(ResultSet resultSet);
    
    /**
     * Method set values to the PreparedStatement due to the Entity object inheritor.
	 * @param statement - PreparedStatement that will be executed in AbstractDao class' methods
	 * @param entity - object of inheritance class
	 * @return PreparedStatement object with set values
     */
    protected abstract void setter(PreparedStatement ps,T entity);
    
    /**
     * Method execute rollback transaction if appears an exception
     * @param logger - in it write description of the exception
     * @param className - name of the class where exception appears
     * @param loggerMessage - message thar write in the logger
     */
    protected void rollbackTransaction(String className,String loggerMessage){
    	Logger.getLogger(className).warn(loggerMessage);
    	try {
    		connection.rollback();
    		connection.setAutoCommit(true);
			Logger.getLogger(getClass()).info(LoggerMessage.SUCCESS_TRANSACTION_ROLLBACK+className);
		} catch (SQLException ex1) {
			Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCESS_TRANSACTION_ROLLBACK, ex1);
			throw new RuntimeException(loggerMessage+className);
		}
    }
    
//    /**
//     * Method close Statement
//     * @param statement
//     */
//    protected void close(PreparedStatement statement){
//        try {
//            if(statement!=null){
//            	Logger.getLogger(getClass()).error(LoggerMessage.SUCCES_CLOSING);
//                statement.close();
//            }
//        } catch (SQLException e) {
//        	Logger.getLogger(getClass()).error(LoggerMessage.UNSUCCES_CLOSING);
//        }
//    }
}
