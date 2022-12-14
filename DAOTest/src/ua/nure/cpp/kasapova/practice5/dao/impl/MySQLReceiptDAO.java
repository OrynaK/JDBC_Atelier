package ua.nure.cpp.kasapova.practice5.dao.impl;

import ua.nure.cpp.kasapova.practice5.dao.DAOFactory;
import ua.nure.cpp.kasapova.practice5.dao.DBException;
import ua.nure.cpp.kasapova.practice5.dao.interfaces.ReceiptDAO;
import ua.nure.cpp.kasapova.practice5.entity.Receipt;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class MySQLReceiptDAO implements ReceiptDAO {
    private static final String GET_ALL_RECEIPTS = "SELECT * FROM receipt";
    private static final String ADD_RECEIPT = "INSERT INTO receipt (client_id, model_id, fabric_id, cutter_id, date_acceptance, date_fitting, due_date, status) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE_RECEIPT = "UPDATE receipt SET client_id=?, model_id=?, fabric_id=?, cutter_id=?, date_acceptance=?, date_fitting=?, due_date=?, status=? WHERE o_id=?";
    private static final String DELETE_RECEIPT_BY_ID = "DELETE FROM receipt WHERE o_id = ?";
    private static final String UPDATE_FITTING_DATE = "UPDATE receipt SET date_fitting= ? WHERE o_id= ?";
    private static final String GET_RECEIPT_BY_ID = "SELECT * FROM receipt WHERE o_id = ?";

    private static MySQLReceiptDAO instance;

    public MySQLReceiptDAO() {
    }


    public static synchronized MySQLReceiptDAO getInstance() {
        if (instance == null) {
            instance = new MySQLReceiptDAO();
        }
        return instance;
    }

    @Override
    public List<Receipt> loadAll() throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (Statement st = con.createStatement()) {
                try (ResultSet resultSet = st.executeQuery(GET_ALL_RECEIPTS)) {
                    List<Receipt> receipts = new ArrayList<>();
                    while (resultSet.next()) {
                        receipts.add(mapReceipt(resultSet));
                    }
                    return receipts;
                }
            }
        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }


    private Receipt mapReceipt(ResultSet resultSet) throws SQLException {
        Receipt receipt = new Receipt();
        int k = 0;
        receipt.setId(resultSet.getInt(++k));
        receipt.setClientId(resultSet.getInt(++k));
        receipt.setModelId(resultSet.getInt(++k));
        receipt.setFabricId(resultSet.getInt(++k));
        receipt.setCutterId(resultSet.getInt(++k));
        receipt.setDateAcceptance(resultSet.getDate(++k));
        receipt.setDateFitting(resultSet.getDate(++k));
        receipt.setDueDate(resultSet.getDate(++k));
        receipt.setStatus(resultSet.getBoolean(++k));
        return receipt;
    }

    @Override
    public void add(Receipt receipt) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement st = con.prepareStatement(ADD_RECEIPT, Statement.RETURN_GENERATED_KEYS)) {
                int k = 0;
                st.setInt(++k, receipt.getClientId());
                st.setInt(++k, receipt.getModelId());
                st.setInt(++k, receipt.getFabricId());
                st.setInt(++k, receipt.getCutterId());
                st.setDate(++k, (java.sql.Date) receipt.getDateAcceptance());
                st.setDate(++k, (java.sql.Date) receipt.getDateFitting());
                st.setDate(++k, (java.sql.Date) receipt.getDueDate());
                st.setBoolean(++k, receipt.getStatus());
                st.executeUpdate();
                try (ResultSet keys = st.getGeneratedKeys()) {
                    if (keys.next()) {
                        receipt.setId(keys.getInt(1));
                    }
                }
            }
        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void update(Receipt receipt, int id) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement st = con.prepareStatement(UPDATE_RECEIPT)) {
                int k = 0;
                st.setInt(++k, receipt.getClientId());
                st.setInt(++k, receipt.getModelId());
                st.setInt(++k, receipt.getFabricId());
                st.setInt(++k, receipt.getCutterId());
                st.setDate(++k, (java.sql.Date) receipt.getDateAcceptance());
                st.setDate(++k, (java.sql.Date) receipt.getDateFitting());
                st.setDate(++k, (java.sql.Date) receipt.getDueDate());
                st.setBoolean(++k, receipt.getStatus());
                st.setInt(++k, id);
                st.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void deleteById(int id) throws DBException {
        try {
            if (id <= 0) throw new SQLException("deleteById() failed, ID is not natural number");
            try (Connection con = DAOFactory.getConnection(true)) {
                try (PreparedStatement statement = con.prepareStatement(DELETE_RECEIPT_BY_ID)) {
                    statement.setInt(1, id);
                    statement.executeUpdate();
                }
            }

        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateFittingDate(int id, Date date) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement st = con.prepareStatement(UPDATE_FITTING_DATE)) {
                st.setDate(1, date,new  GregorianCalendar());
                st.setInt(2, id);
                st.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Receipt> findById(int id) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement ps = con.prepareStatement(GET_RECEIPT_BY_ID)) {
                ps.setInt(1, id);
                try (ResultSet resultSet = ps.executeQuery()) {
                    List<Receipt> receipts = new ArrayList<>();
                    while (resultSet.next()) {
                        receipts.add(mapReceipt(resultSet));
                    }
                    return receipts;
                }
            }

        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }
}
