package ua.nure.cpp.kasapova.practice5.dao.impl;

import ua.nure.cpp.kasapova.practice5.dao.DAOFactory;
import ua.nure.cpp.kasapova.practice5.dao.DBException;
import ua.nure.cpp.kasapova.practice5.dao.interfaces.ModelDAO;
import ua.nure.cpp.kasapova.practice5.entity.Model;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLModelDAO implements ModelDAO {
    private static final String GET_ALL_MODELS = "SELECT * FROM model";
    private static final String ADD_MODEL = "INSERT INTO model (name, proposed_fabric, tailoring_price, consumption) VALUES ( ?, ?, ?, ?)";
    private static final String UPDATE_MODEL = "UPDATE model SET name=?, proposed_fabric=?, tailoring_price=?, consumption=? WHERE m_id=?";
    private static final String FIND_MODEL_ID_FROM_RECEIPT = "SELECT model_id FROM receipt WHERE model_id=?";
    private static final String DELETE_MODEL_BY_ID = "DELETE FROM model WHERE m_id = ?";
    private static final String UPDATE_TAILORING_PRICE = "UPDATE model SET tailoring_price= ? WHERE m_id= ?";
    private static final String GET_MODEL_BY_ID = "SELECT * FROM model WHERE m_id = ?";
    private static MySQLModelDAO instance;

    public MySQLModelDAO() {
    }



    public static synchronized MySQLModelDAO getInstance() {
        if (instance == null) {
            instance = new MySQLModelDAO();
        }
        return instance;
    }

    @Override
    public List<Model> loadAll() throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (Statement st = con.createStatement()) {
                try (ResultSet resultSet = st.executeQuery(GET_ALL_MODELS)) {
                    List<Model> models = new ArrayList<>();
                    while (resultSet.next()) {
                        models.add(mapModel(resultSet));
                    }
                    return models;
                }
            }
        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }
    private Model mapModel(ResultSet resultSet) throws SQLException {
        Model model = new Model();
        int k = 0;
        model.setId(resultSet.getInt(++k));
        model.setName(resultSet.getString(++k));
        model.setProposedFabric(resultSet.getInt(++k));
        model.setTailoringPrice(resultSet.getBigDecimal(++k));
        model.setConsumption(resultSet.getBigDecimal(++k));
        return model;
    }



    @Override
    public void add(Model model) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement st = con.prepareStatement(ADD_MODEL, Statement.RETURN_GENERATED_KEYS)) {
                int k = 0;
                st.setString(++k, model.getName());
                st.setInt(++k, model.getProposedFabric());
                st.setBigDecimal(++k, model.getTailoringPrice());
                st.setBigDecimal(++k, model.getConsumption());
                st.executeUpdate();
                try (ResultSet keys = st.getGeneratedKeys()) {
                    if (keys.next()) {
                        model.setId(keys.getInt(1));
                    }
                }
            }
        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    @Override
    public void update(Model model, int id) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement st = con.prepareStatement(UPDATE_MODEL)) {
                int k = 0;
                st.setString(++k, model.getName());
                st.setInt(++k, model.getProposedFabric());
                st.setBigDecimal(++k, model.getTailoringPrice());
                st.setBigDecimal(++k, model.getConsumption());
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
                try (PreparedStatement st = con.prepareStatement(FIND_MODEL_ID_FROM_RECEIPT)) {
                    st.setInt(1, id);
                    try (ResultSet resultSet = st.executeQuery()) {
                        if (resultSet.next())
                            throw new SQLException("deleteById() failed. " +
                                    "To delete model, please, firstly delete receipt with this model");
                        try (PreparedStatement statement = con.prepareStatement(DELETE_MODEL_BY_ID)) {
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
    public void updateTailoringPrice(int id, BigDecimal tailoringPrice) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement st = con.prepareStatement(UPDATE_TAILORING_PRICE)) {
                st.setBigDecimal(1, tailoringPrice);
                st.setInt(2, id);
                st.executeUpdate();
            }
        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }

    @Override
    public List<Model> findById(int id) throws DBException {
        try (Connection con = DAOFactory.getConnection(true)) {
            try (PreparedStatement ps = con.prepareStatement(GET_MODEL_BY_ID)) {
                ps.setInt(1, id);
                try (ResultSet resultSet = ps.executeQuery()) {
                    List<Model> models = new ArrayList<>();
                    while (resultSet.next()) {
                        models.add(mapModel(resultSet));
                    }
                    return models;
                }
            }

        } catch (SQLException | IOException e) {
            throw new DBException(e);
        }
    }
}
