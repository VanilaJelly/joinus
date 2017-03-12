package com.join.web.dao;

import java.sql.SQLException;

import com.join.web.model.JoinModel;

public interface Dao {

    public void saveTest(JoinModel model) throws SQLException;

    public void test() throws SQLException;

}
