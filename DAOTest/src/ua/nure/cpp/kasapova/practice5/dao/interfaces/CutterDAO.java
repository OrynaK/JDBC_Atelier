package ua.nure.cpp.kasapova.practice5.dao.interfaces;

import ua.nure.cpp.kasapova.practice5.dao.DBException;
import ua.nure.cpp.kasapova.practice5.entity.Client;
import ua.nure.cpp.kasapova.practice5.entity.Cutter;

import java.util.List;

public interface CutterDAO extends BaseDAO<Cutter>{

    void updateSurname(int id, String surname) throws DBException;

    List<Cutter> findById(int id) throws DBException;
}
