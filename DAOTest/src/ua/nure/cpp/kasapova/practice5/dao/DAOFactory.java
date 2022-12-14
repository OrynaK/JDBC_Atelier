package ua.nure.cpp.kasapova.practice5.dao;

import ua.nure.cpp.kasapova.practice5.dao.impl.*;
import ua.nure.cpp.kasapova.practice5.dao.interfaces.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.ConfigurationException;

public class DAOFactory {
    private static ClientDAO daoCl = null;
    private static CutterDAO daoCut = null;
    private static FabricDAO daoFab = null;
    private static ModelDAO daoMod = null;
    private static ReceiptDAO daoRec = null;

    public static ClientDAO getClientDAO(TypeDAO type) {
        if (type == TypeDAO.MySQL) {
            if (daoCl == null) {
                daoCl = MySQLClientDAO.getInstance();
            } else
                return daoCl;

        }
        return null;
    }

    public static CutterDAO getCutterDAO(TypeDAO type) {
        if (type == TypeDAO.MySQL) {
            if (daoCut == null) {
                daoCut = MySQLCutterDAO.getInstance();
            } else
                return daoCut;

        }
        return null;
    }

    public static FabricDAO getFabricDAO(TypeDAO type) {
        if (type == TypeDAO.MySQL) {
            if (daoFab == null) {
                daoFab = MySQLFabricDAO.getInstance();
            } else
                return daoFab;

        }
        return null;
    }

    public static ModelDAO getModelDAO(TypeDAO type) {
        if (type == TypeDAO.MySQL) {
            if (daoMod == null) {
                daoMod = MySQLModelDAO.getInstance();
            } else
                return daoMod;

        }
        return null;
    }

    public static ReceiptDAO getReceiptDAO(TypeDAO type) {
        if (type == TypeDAO.MySQL) {
            if (daoRec == null) {
                daoRec = MySQLReceiptDAO.getInstance();
            } else
                return daoRec;

        }
        return null;
    }

    public static ClientDAO getClientDAO() {
        Properties config = new Properties();
        try {
            config.load(new FileInputStream("application.properties"));
            String daoClass = config.getProperty("ClientDAOClass");
            if (daoClass == null) {
                throw new ConfigurationException("ClientDAOClass property has not been found: " + daoClass);
            }
            return (ClientDAO) Class.forName(daoClass).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static CutterDAO getCutterDAO() {
        Properties config = new Properties();
        try {
            config.load(new FileInputStream("application.properties"));
            String daoClass = config.getProperty("CutterDAOClass");
            if (daoClass == null) {
                throw new ConfigurationException("CutterDAOClass property has not been found: " + daoClass);
            }
            return (CutterDAO) Class.forName(daoClass).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static FabricDAO getFabricDAO() {
        Properties config = new Properties();
        try {
            config.load(new FileInputStream("application.properties"));
            String daoClass = config.getProperty("FabricDAOClass");
            if (daoClass == null) {
                throw new ConfigurationException("FabricDAOClass property has not been found: " + daoClass);
            }
            return (FabricDAO) Class.forName(daoClass).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static ModelDAO getModelDAO() {
        Properties config = new Properties();
        try {
            config.load(new FileInputStream("application.properties"));
            String daoClass = config.getProperty("ModelDAOClass");
            if (daoClass == null) {
                throw new ConfigurationException("ModelDAOClass property has not been found: " + daoClass);
            }
            return (ModelDAO) Class.forName(daoClass).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static ReceiptDAO getReceiptDAO() {
        Properties config = new Properties();
        try {
            config.load(new FileInputStream("application.properties"));
            String daoClass = config.getProperty("ReceiptDAOClass");
            if (daoClass == null) {
                throw new ConfigurationException("ReceiptDAOClass property has not been found: " + daoClass);
            }
            return (ReceiptDAO) Class.forName(daoClass).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    private static String getUrl() throws IOException {
        String url = null;
        Properties properties = new Properties();

        properties.load(new FileInputStream("application.properties"));
        url = properties.getProperty("URL");

        return url;
    }

    static Connection getConnection() throws SQLException, IOException {
        return getConnection(false);
    }

    public static Connection getConnection(boolean autoCommit) throws SQLException, IOException {
        Connection con = DriverManager.getConnection(getUrl());
        if (autoCommit) {
            con.setAutoCommit(autoCommit);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        }
        return con;
    }
}

