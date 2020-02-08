package ua.training.restaurant.dao.impl;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import ua.training.restaurant.dao.GenericDao;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Created by Student on 26.01.2020
 */
public class ConnectionPoolHolder {
    final static Logger log = Logger.getLogger(ConnectionPoolHolder.class);
    private static volatile DataSource dataSource;
    private static Properties prop;

    static {
        try {
            FileInputStream fis = new FileInputStream(GenericDao.CONNECTION_PROPERTIES_FILE_PATH);
            prop = new Properties();
            prop.load(fis);
        } catch (IOException e) {
            log.error(e);
        }
    }


    public static DataSource getDataSource() {

        if (dataSource == null) {
            synchronized (ConnectionPoolHolder.class) {
                if (dataSource == null) {
                    BasicDataSource ds = new BasicDataSource();
                    ds.setDriverClassName(prop.getProperty("db.driver.class"));
                    ds.setUrl(prop.getProperty("db.conn.url"));
                    ds.setUsername(prop.getProperty("db.username"));
                    ds.setPassword(prop.getProperty("db.password"));
                    ds.setMinIdle(5);
                    ds.setMaxIdle(10);
                    ds.setMaxOpenPreparedStatements(100);
                    dataSource = ds;
                }
            }
        }
        return dataSource;

    }
}
