package ua.nure.cpp.kasapova.practice5.dao.interfaces;

import ua.nure.cpp.kasapova.practice5.dao.DBException;
import ua.nure.cpp.kasapova.practice5.entity.Cutter;
import ua.nure.cpp.kasapova.practice5.entity.Fabric;

import java.math.BigDecimal;
import java.util.List;

public interface FabricDAO extends BaseDAO<Fabric> {

    void updateWidth(int id, BigDecimal width) throws DBException;

    List<Fabric> findById(int id) throws DBException;
}
