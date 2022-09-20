package com.tobyspring.studytobyspring.dao;

import com.tobyspring.studytobyspring.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddStatement implements StatementStrategy {

    @Override
    public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
        PreparedStatement ps = c.prepareStatement(
                "insert into users (id, name, password) values (?, ?, ?)"
        );

        /*
        user 객체는 어디에서 가져와야 할까?
         */
//        ps.setString(1, user.getId());
//        ps.setString(2, user.getName());
//        ps.setString(3, user.getPassword());

        ps.executeUpdate();

        return ps;
    }
}
