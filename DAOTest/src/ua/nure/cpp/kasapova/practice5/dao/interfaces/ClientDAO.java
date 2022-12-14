package ua.nure.cpp.kasapova.practice5.dao.interfaces;

import ua.nure.cpp.kasapova.practice5.dao.DBException;
import ua.nure.cpp.kasapova.practice5.entity.Client;
import ua.nure.cpp.kasapova.practice5.entity.Receipt;

import java.util.List;

public interface ClientDAO extends BaseDAO<Client> {

    void updateName(int id, String name) throws DBException;

    List<Client> findById(int id) throws DBException;

}
