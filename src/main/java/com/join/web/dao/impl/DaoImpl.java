package com.join.web.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.join.web.dao.Dao;
import com.join.web.model.DBModel;

@Repository
public class DaoImpl implements Dao {

    @Autowired
    private SqlSession query;

    @Override
    public void saveTest(DBModel dbm) throws SQLException {
        query.insert("query.saveTest", dbm);
    }

}


