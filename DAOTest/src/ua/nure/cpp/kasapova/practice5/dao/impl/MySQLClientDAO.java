package ua.nure.cpp.kasapova.practice5.dao.impl;


import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


import ua.nure.cpp.kasapova.practice5.dao.DAOFactory;
import ua.nure.cpp.kasapova.practice5.dao.DBException;
import ua.nure.cpp.kasapova.practice5.dao.interfaces.ClientDAO;
import ua.nure.cpp.kasapova.practice5.entity.Client;


public class MySQLClientDAO implements ClientDAO {

    private static final String GET_CLIENT_BY_ID = "SELECT * FROM client WHERE cl_id = ?";
    private static final String ADD_CLIENT = "INSERT INTO client (name, surname) VALUES ( ?, ?)";
    private static final String GET_ALL_CLIENTS = "SELECT * FROM client";
    private static final String DELETE_CLIENT_BY_ID = "DELETE FROM client WHERE cl_id = ?";
    private static final String FIND_CLIENT_ID_FROM_RECEIPT = "SELECT client_id FROM RECEIPT WHERE client_id=?";
    private static final String UPDATE_NAME = "UPDATE client SET name= ? WHERE cl_id= ?";
    private static final String UPDATE_CLIENT = "UPDATE client SET name= ?, surname=? WHERE cl_id=?";


    private static MySQLClientDAO instance;

    public MySQLClientDAO() {
    }


    public static synchronized MySQLClientDAO getInstance() {
        if (instance == null) {
            instance = new MySQLClientDAO();
        }
        return instance;
    }

    @Override
    public List<Client> loadAll() throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (Statement st = con.createStatement()) {
                try (ResultSet resultSet = st.executeQuery(GET_ALL_CLIENTS)) {
                    List<Client> clients = new ArrayList<>();
                    while (resultSet.next()) {
                        clients.add(mapClient(resultSet));
                    }
                    return clients;
                }
            }
        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    private Client mapClient(ResultSet resultSet) throws SQLException {
        Client client = new Client();
        int k = 0;
        client.setId(resultSet.getInt(++k));
        client.setName(resultSet.getString(++k));
        client.setSurname(resultSet.getString(++k));
        return client;
    }

   @Override
    public void add(Client client) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement st = con.prepareStatement(ADD_CLIENT, Statement.RETURN_GENERATED_KEYS)) {
                st.setString(1, client.getName());
                st.setString(2, client.getSurname());
                st.executeUpdate();
                try (ResultSet keys = st.getGeneratedKeys()) {
                    if (keys.next()) {
                        client.setId(keys.getInt(1));
                    }
                }

            }
        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void update(Client client, int id) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement st = con.prepareStatement(UPDATE_CLIENT)) {
                st.setString(1, client.getName());
                st.setString(2, client.getSurname());
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
                try (PreparedStatement st = con.prepareStatement(FIND_CLIENT_ID_FROM_RECEIPT)) {
                    st.setInt(1, id);
                    try (ResultSet resultSet = st.executeQuery()) {
                        if (resultSet.next())
                            throw new SQLException("deleteById() failed. " +
                                    "To delete client, please, firstly delete receipt with this client");
                        try (PreparedStatement statement = con.prepareStatement(DELETE_CLIENT_BY_ID)) {
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
    public void updateName(int id, String name) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement st = con.prepareStatement(UPDATE_NAME)) {
                st.setString(1, name);
                st.setInt(2, id);
                st.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Client> findById(int id) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement ps = con.prepareStatement(GET_CLIENT_BY_ID)) {
                ps.setInt(1, id);
                try (ResultSet resultSet = ps.executeQuery()) {
                    List<Client> clients = new ArrayList<>();
                    while (resultSet.next()) {
                        clients.add(mapClient(resultSet));
                    }
                    return clients;
                }
            }

        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }


}
