package model.jdbc;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import view.LoggerMessage;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Artem on 20.07.16.
 */
public class ConnectionPool {
    private static final String DATA_SOURCE_NAME="jdbc/myresources";
    private static DataSource dataSource;

    public static Connection getConnection() {
        Connection connection=null;
		try {
			Context initContext=null;
			try {
				initContext = new InitialContext();
				dataSource = (DataSource) initContext.lookup(DATA_SOURCE_NAME);
			} catch (NamingException e) {
				Logger.getLogger(ConnectionPool.class).error(LoggerMessage.UNSUCCESS_CONNECTION_TO_RESOURCE);
			}
			connection = dataSource.getConnection();
		} catch (SQLException e) {
			Logger.getLogger(ConnectionPool.class).error(LoggerMessage.UNSUCCESS_CONNECTION_TO_DATABASE);
		}
        return connection;
    }

}
