package ua.nure.cpp.kasapova.practice5.dao.interfaces;

import ua.nure.cpp.kasapova.practice5.dao.DBException;
import ua.nure.cpp.kasapova.practice5.entity.Receipt;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.List;

public interface ReceiptDAO extends BaseDAO<Receipt> {

    void updateFittingDate(int id, Date date) throws DBException;

    List<Receipt> findById(int id) throws DBException;
}
