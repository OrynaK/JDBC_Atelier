package ua.nure.cpp.kasapova.practice5.dao.interfaces;


import ua.nure.cpp.kasapova.practice5.dao.DBException;

import java.util.List;

public interface BaseDAO<T> {
    List<T> loadAll() throws DBException;

    void add(T obj) throws DBException;

    void update(T t, int id) throws DBException;

    void deleteById(int id) throws DBException;

}
