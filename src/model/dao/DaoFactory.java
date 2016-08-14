package model.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Artem Pryzhkov
 * Class that contains "instruction" methods to connect with database, which must be realized in inheritance class.
 */
public abstract class DaoFactory {
	
    /**
     * @return AuthorDao object that created by JdbcAuthorDao.class
     */
    public abstract AuthorDao createAuthorDao();
    
    /**
     * @return BookDao object that created by JdbcBookDao.class
     */
    public abstract BookDao createBookDao();
    
    
    /**
     * @return CatalogDao object that created by JdbcCatalogDao.class
     */
    public abstract CatalogDao createCatalogDao();
    
    /**
     * @return CopyDao object that created by JdbcCopyDao.class
     */
    public abstract CopyDao createCopyDao();
    
    /**
     * @return ReaderDao object that created by JdbcReaderDao.class
     */
    public abstract ReaderDao createReaderDao();

    
    /**
     * Method realized pattern singleton for JdbcDaoFactory.class
     * @return DaoFactory object
     */
    public static DaoFactory getFactory() {
        try {
            return (DaoFactory) Class.forName(
                    "model.jdbc.JdbcDaoFactory").newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
   
    /**
     *  Method close Connection
     * @param connection
     */
    public static void close(Connection connection){
        if(connection!=null){
            try {
                connection.close();
            } catch (SQLException e) {
                //TODO: log about closing impossibility
            }
        }
    }
}
