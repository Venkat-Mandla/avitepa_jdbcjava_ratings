/**
 * 
 */
package avitepa.jdbcjava.ratings;

import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import avitepa.jdbcjava.ratings.exception.JdbcConnectionException;

/**
 * @author Vijay M
 *
 */
public class Configuration {

	private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);

	private Properties properties;
	private static Configuration configuration=new Configuration();
	
	private Configuration() {
		properties=getProperties();
		
	}
	
	public static Configuration getInstance() {
		return configuration;
	}
	
	public Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(properties.getProperty("app.db.driver.class"));

			LOGGER.info("Trying to get the JDBC connection..");
			conn = DriverManager.getConnection(properties.getProperty("app.db.url"), properties.getProperty("app.db.userName"), properties.getProperty("app.db.password"));
			LOGGER.info("JDBC connection successful..");
			return conn;
		} catch (Exception exception) {
			throw new JdbcConnectionException("Unable to get the connection", exception);
		}
	}

	public Properties getProperties() {
		if(Objects.nonNull(properties)) {
			return properties;
		}
		try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
			Properties prop = new Properties();
			prop.load(input);
			return prop;
		} catch (Exception e) {
			throw new RuntimeException("Unable to load the properties file..",e);
		}
	}

}
