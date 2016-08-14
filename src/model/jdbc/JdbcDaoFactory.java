package model.jdbc;

import java.sql.Connection;

import model.dao.*;

/**
 * @author Artem Pryzhkov
 * Class that contains methods to connect with database.
 * Implements class DaoFactory that contains "instruction" methods that show which functions must have
 *
 */
public class JdbcDaoFactory extends DaoFactory{

    /* (non-Javadoc)
     * @see model.dao.DaoFactory#createAuthorDao()
     */
    @Override
    public AuthorDao createAuthorDao() {
        return new JdbcAuthorDao(ConnectionPool.getConnection());
    }

    /* (non-Javadoc)
     * @see model.dao.DaoFactory#createBookDao()
     */
    @Override
    public BookDao createBookDao() {
        return new JdbcBookDao(ConnectionPool.getConnection());
    }

    /* (non-Javadoc)
     * @see model.dao.DaoFactory#createCatalogDao()
     */
    @Override
    public CatalogDao createCatalogDao() {
        return new JdbcCatalogDao(ConnectionPool.getConnection());
    }

    /* (non-Javadoc)
     * @see model.dao.DaoFactory#createCopyDao()
     */
    @Override
    public CopyDao createCopyDao() {
        return new JdbcCopyDao(ConnectionPool.getConnection());
    }
    
    /* (non-Javadoc)
     * @see model.dao.DaoFactory#createReaderDao()
     */
    @Override
    public ReaderDao createReaderDao() {
        return new JdbcReaderDao(ConnectionPool.getConnection());
    }
}
