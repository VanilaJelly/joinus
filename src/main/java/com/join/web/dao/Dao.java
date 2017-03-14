package com.join.web.dao;

import com.join.web.model.DBModel;
import java.sql.SQLException;


public interface Dao {

    public void saveTest(DBModel dbm) throws SQLException;

    public void test() throws SQLException;
}
