package ua.nure.cpp.kasapova.practice5.dao.impl;

import ua.nure.cpp.kasapova.practice5.dao.DAOFactory;
import ua.nure.cpp.kasapova.practice5.dao.DBException;
import ua.nure.cpp.kasapova.practice5.dao.interfaces.FabricDAO;
import ua.nure.cpp.kasapova.practice5.entity.Fabric;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLFabricDAO implements FabricDAO {
    private static final String GET_ALL_FABRICS = "SELECT * FROM fabric";
    private static final String ADD_FABRIC = "INSERT INTO fabric (name, width, price_per_meter) VALUES ( ?, ?, ?)";
    private static final String UPDATE_FABRIC = "UPDATE fabric SET name=?, width=?, price_per_meter=? WHERE f_id=?";
    private static final String FIND_FABRIC_ID_FROM_RECEIPT = "SELECT fabric_id FROM receipt WHERE fabric_id=?";
    private static final String FIND_FABRIC_ID_FROM_MODEL = "SELECT proposed_fabric FROM model WHERE proposed_fabric=?";
    private static final String DELETE_FABRIC_BY_ID = "DELETE FROM fabric WHERE f_id = ?";
    private static final String UPDATE_WIDTH = "UPDATE fabric SET width= ? WHERE f_id= ?";
    private static final String GET_FABRIC_BY_ID = "SELECT * FROM fabric WHERE f_id = ?";
    private static MySQLFabricDAO instance;

    public MySQLFabricDAO() {
    }

    public static synchronized MySQLFabricDAO getInstance() {
        if (instance == null) {
            instance = new MySQLFabricDAO();
        }
        return instance;
    }


    @Override
    public List<Fabric> loadAll() throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (Statement st = con.createStatement()) {
                try (ResultSet resultSet = st.executeQuery(GET_ALL_FABRICS)) {
                    List<Fabric> fabrics = new ArrayList<>();
                    while (resultSet.next()) {
                        fabrics.add(mapFabric(resultSet));
                    }
                    return fabrics;
                }
            }
        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    private Fabric mapFabric(ResultSet resultSet) throws SQLException {
        Fabric fabric = new Fabric();
        int k = 0;
        fabric.setId(resultSet.getInt(++k));
        fabric.setName(resultSet.getString(++k));
        fabric.setWidth(resultSet.getBigDecimal(++k));
        fabric.setPricePerMeter(resultSet.getBigDecimal(++k));
        return fabric;
    }

    @Override
    public void add(Fabric fabric) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement st = con.prepareStatement(ADD_FABRIC, Statement.RETURN_GENERATED_KEYS)) {
                int k = 0;
                st.setString(++k, fabric.getName());
                st.setBigDecimal(++k, fabric.getWidth());
                st.setBigDecimal(++k, fabric.getPricePerMeter());
                st.executeUpdate();
                try (ResultSet keys = st.getGeneratedKeys()) {
                    if (keys.next()) {
                        fabric.setId(keys.getInt(1));
                    }
                }
            }
        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void update(Fabric fabric, int id) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement st = con.prepareStatement(UPDATE_FABRIC)) {
                int k = 0;
                st.setString(++k, fabric.getName());
                st.setBigDecimal(++k, fabric.getWidth());
                st.setBigDecimal(++k, fabric.getPricePerMeter());
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
                try (PreparedStatement st = con.prepareStatement(FIND_FABRIC_ID_FROM_RECEIPT)) {
                    st.setInt(1, id);
                    try (ResultSet resultSet = st.executeQuery()) {
                        if (resultSet.next())
                            throw new SQLException("deleteById() failed. " +
                                    "To delete fabric, please, firstly delete receipt with this fabric");

                        try (PreparedStatement stat = con.prepareStatement(FIND_FABRIC_ID_FROM_MODEL)) {
                            stat.setInt(1, id);
                            try (ResultSet res = stat.executeQuery()) {
                                if (res.next()) throw new SQLException("deleteById() failed. " +
                                        "To delete fabric, please, firstly delete model with this proposed fabric");
                                try (PreparedStatement statement = con.prepareStatement(DELETE_FABRIC_BY_ID)) {
                                    statement.setInt(1, id);
                                    statement.executeUpdate();
                                }
                            }
                        }
                    }
                }
            }

        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateWidth(int id, BigDecimal width) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement st = con.prepareStatement(UPDATE_WIDTH)) {
                st.setBigDecimal(1, width);
                st.setInt(2, id);
                st.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Fabric> findById(int id) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement ps = con.prepareStatement(GET_FABRIC_BY_ID)) {
                ps.setInt(1, id);
                try (ResultSet resultSet = ps.executeQuery()) {
                    List<Fabric> fabrics = new ArrayList<>();
                    while (resultSet.next()) {
                        fabrics.add(mapFabric(resultSet));
                    }
                    return fabrics;
                }
            }

        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }
}
