package ua.nure.cpp.kasapova.practice5.dao.interfaces;

import ua.nure.cpp.kasapova.practice5.dao.DBException;
import ua.nure.cpp.kasapova.practice5.entity.Fabric;
import ua.nure.cpp.kasapova.practice5.entity.Model;

import java.math.BigDecimal;
import java.util.List;

public interface ModelDAO extends BaseDAO<Model> {

    void updateTailoringPrice(int id, BigDecimal tailoringPrice) throws DBException;

    List<Model> findById(int id) throws DBException;
}
