package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    static final String hostName = "localhost";
    static final String dbName = "denik_db";
    static final String userName = "root";
    static final String password = "12345";
    static final String connectionURL = "jdbc:mysql://" + hostName + ":3306/" + dbName;

    public static Connection connectionDB() {
        Connection connection = null;
        try {
            System.out.println("Get connection...");
            connection = DriverManager.getConnection(connectionURL, userName, password);
            System.out.println("Get: " + connection);
            System.out.println("Done\n");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            return connection;
        }
    }

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;

        try {
            Configuration configuration = new Configuration();
            Properties settings = new Properties();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL, connectionURL + "?useSSL=false");
            settings.put(Environment.USER, userName);
            settings.put(Environment.PASS, password);
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            //settings.put(Environment.SHOW_SQL, "true");
            settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            settings.put(Environment.HBM2DDL_AUTO, "create-drop");
            configuration.setProperties(settings);
            configuration.addAnnotatedClass(User.class);
            StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());

            sessionFactory = configuration.buildSessionFactory(builder.build());

        } catch (Exception e) {
            System.out.println("Исключение!" + e);
        }
        return sessionFactory;
    }
}
