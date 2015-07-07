package com.comresource.lunchapp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PersistenceManager implements ServletRequestListener, ServletContextListener {

    private static String persistenceUnit = null;
    public static String vendor ="";

    // Entity Manager Factory is thread safe and can be shared across all threads.
    private static EntityManagerFactory emFactory = null;
    // Entity Managers are not thread safe and must be thread safe
    private static final ThreadLocal<EntityManager> entityManager = new ThreadLocal<EntityManager>();

    /**
     * Helper method which returns the persistence unit for the current module.
     * If the persistence unit is not explicitly set before this is called it
     * will be looked up in JNDI.
     *
     * @return
     * @throws javax.naming.NamingException
     */
    public static String getPersistenceUnit() throws NamingException {
        if (persistenceUnit == null) {
            persistenceUnit = "lunchApp";
        }
        return persistenceUnit;
    }

    /**
     * Setter for test construction allowing specific persistence unit to be
     * used. Must be called before getEntityManager in test case setup.
     *
     * @param persistenceUnit
     */
    public static void setPersistenceUnit(String persistenceUnit) {
        PersistenceManager.persistenceUnit = persistenceUnit;
    }

    private static synchronized EntityManagerFactory getEntityManagerFactory() throws NamingException, FileNotFoundException {
        if (emFactory == null) {
            String pu = getPersistenceUnit();

            Properties jdbc_cust = new Properties();
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream stream = loader.getResourceAsStream("jdbc_customer.properties");
            if (stream == null) {
                throw new FileNotFoundException("property file jdbc_customer.properties not found in the classpath");
            }
            try {
                jdbc_cust.load(stream);
            } catch (IOException e) {
//                logger.error(org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e));
            }

            vendor = jdbc_cust.getProperty("lunchApp.dbvendor").toLowerCase().trim();
            String dialect = "";

            if ("oracle".equals(vendor)) {
                dialect = "org.hibernate.dialect.Oracle10gDialect";
            } else if ("db2".equals(vendor)) {
                dialect = "org.hibernate.dialect.DB2Dialect";
            } else if ("mssql".equals(vendor)) {
                dialect = "org.hibernate.dialect.SQLServer2008Dialect";
            } else {
                throw new IllegalArgumentException("SQL dialect not found. " + dialect + "Vendor = " + vendor);
            }

            Map<String, String> properties = new HashMap<String, String>();
            properties.put("javax.persistence.jdbc.driver", jdbc_cust.getProperty("lunchApp.driver"));
            properties.put("javax.persistence.jdbc.url", jdbc_cust.getProperty("lunchApp.url"));
            properties.put("javax.persistence.jdbc.user", jdbc_cust.getProperty("lunchApp.user"));
            properties.put("javax.persistence.jdbc.password", jdbc_cust.getProperty("lunchApp.password"));
            properties.put("hibernate.dialect", dialect);
            properties.put("hibernate.default_schema", jdbc_cust.getProperty("lunchApp.schema"));

            emFactory = Persistence.createEntityManagerFactory(pu, properties);
        }
        return emFactory;
    }

    /**
     * Gets a entity manager to be used with this resource.
     *
     * @return
     * @throws NamingException
     */
    public static EntityManager getEntityManager() throws PersistenceException, NamingException {

        if (entityManager.get() == null) {
            try {
                entityManager.set(getEntityManagerFactory().createEntityManager());
            } catch (FileNotFoundException e) {
//                logger.error(org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace(e));
            }
        }

        return entityManager.get();
    }

    @Override
    public void requestDestroyed(ServletRequestEvent arg0) {
        // If we have an entity manager on this request thread we need to close it.
        // If we do not we will leak database connections and possibly leave transactions hanging.
        if (entityManager.get() != null) {
            entityManager.get().close();
            entityManager.set(null);
        }
    }

    @Override
    public void requestInitialized(ServletRequestEvent arg0) {
        // Set the logging path so that our custom logs end up in the correct folder across all platforms
        Properties props = new Properties();
        InputStream stream = this.getClass().getClassLoader().getResourceAsStream("centralops.properties");
        String logPath = "./logs";
        try {
            props.load(stream);
            logPath = props.getProperty("log_directory");
        } catch (Exception e) {
            // Do nothing and accpet the default
        }
        System.setProperty("log_path", logPath);

        // Hibernate uses JBOSS logging and by default it will log "ALL" to the console which will be directed to the 
        // Jetty log in SI.  To change this at all the JBOSS logging provider needs to be set before any Hibernate classes 
        // get called, otherwise it's too late.  This method gets called by the initilzation of the servlet that Hibernate
        // is running inside of.
        // The "slf4j" is a logging frame work that relies on logging interface to configure.  We are using "logback" and the 
        // default configuration is setup in the ./WEB-INF/classes/logback.xml file.
        // tldr; This one line saves about 22MB of disk space per user login
        System.setProperty("org.jboss.logging.provider", "slf4j");
    }

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // When the application shuts down we need to clean up the factory
        if (emFactory != null) {
            emFactory.close();
            emFactory = null;
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        // No-Op
    }
}
