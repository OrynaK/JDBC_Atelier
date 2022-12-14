package ua.nure.cpp.kasapova.practice5.dao.impl;

import ua.nure.cpp.kasapova.practice5.dao.DAOFactory;
import ua.nure.cpp.kasapova.practice5.dao.DBException;
import ua.nure.cpp.kasapova.practice5.dao.interfaces.CutterDAO;
import ua.nure.cpp.kasapova.practice5.entity.Cutter;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLCutterDAO implements CutterDAO {
    private static final String GET_ALL_CUTTERS = "SELECT * FROM cutter";
    private static final String ADD_CUTTER = "INSERT INTO cutter (name, surname) VALUES ( ?, ?)";
    private static final String UPDATE_CUTTER = "UPDATE cutter SET name= ?, surname=? WHERE c_id=?";
    private static final String FIND_CUTTER_ID_FROM_RECEIPT = "SELECT cutter_id FROM receipt WHERE cutter_id=?";
    private static final String DELETE_CUTTER_BY_ID = "DELETE FROM cutter WHERE c_id = ?";
    private static final String UPDATE_SURNAME = "UPDATE cutter SET surname= ? WHERE c_id= ?";
    private static final String GET_CUTTER_BY_ID = "SELECT * FROM cutter WHERE c_id = ?";

    private static MySQLCutterDAO instance;

    public MySQLCutterDAO() {
    }

    public static synchronized MySQLCutterDAO getInstance() {
        if (instance == null) {
            instance = new MySQLCutterDAO();
        }
        return instance;
    }

    @Override
    public List<Cutter> loadAll() throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (Statement st = con.createStatement()) {
                try (ResultSet resultSet = st.executeQuery(GET_ALL_CUTTERS)) {
                    List<Cutter> cutters = new ArrayList<>();
                    while (resultSet.next()) {
                        cutters.add(mapCutter(resultSet));
                    }
                    return cutters;
                }
            }
        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    private Cutter mapCutter(ResultSet resultSet) throws SQLException {
        Cutter cutter = new Cutter();
        int k = 0;
        cutter.setId(resultSet.getInt(++k));
        cutter.setName(resultSet.getString(++k));
        cutter.setSurname(resultSet.getString(++k));
        return cutter;
    }

    @Override
    public void add(Cutter cutter) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement st = con.prepareStatement(ADD_CUTTER, Statement.RETURN_GENERATED_KEYS)) {
                st.setString(1, cutter.getName());
                st.setString(2, cutter.getSurname());
                st.executeUpdate();
                try (ResultSet keys = st.getGeneratedKeys()) {
                    if (keys.next()) {
                        cutter.setId(keys.getInt(1));
                    }
                }

            }
        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void update(Cutter cutter, int id) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement st = con.prepareStatement(UPDATE_CUTTER)) {
                st.setString(1, cutter.getName());
                st.setString(2, cutter.getSurname());
                st.setInt(3, id);
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
                try (PreparedStatement st = con.prepareStatement(FIND_CUTTER_ID_FROM_RECEIPT)) {
                    st.setInt(1, id);
                    try (ResultSet resultSet = st.executeQuery()) {
                        if (resultSet.next())
                            throw new SQLException("deleteById() failed. " +
                                    "To delete cutter, please, firstly delete receipt with this cutter");
                        try (PreparedStatement statement = con.prepareStatement(DELETE_CUTTER_BY_ID)) {
                            statement.setInt(1, id);
                            statement.executeUpdate();
                        }
                    }
                }
            }

        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void updateSurname(int id, String surname) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement st = con.prepareStatement(UPDATE_SURNAME)) {
                st.setString(1, surname);
                st.setInt(2, id);
                st.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Cutter> findById(int id) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement ps = con.prepareStatement(GET_CUTTER_BY_ID)) {
                ps.setInt(1, id);
                try (ResultSet resultSet = ps.executeQuery()) {
                    List<Cutter> cutters = new ArrayList<>();
                    while (resultSet.next()) {
                        cutters.add(mapCutter(resultSet));
                    }
                    return cutters;
                }
            }

        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }
}
