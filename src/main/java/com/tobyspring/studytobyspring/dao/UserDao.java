package com.tobyspring.studytobyspring.dao;

import com.tobyspring.studytobyspring.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UserDao {

    private final ConnectionMaker connectionMaker;

    public UserDao() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        this.connectionMaker = context.getBean("connectionMaker", ConnectionMaker.class);
    }

    /*
    일반적으로 서버에서는 제한된 개수의 DB 커넥션을 만들어서 재사용 가능한
    풀로 관리하기 때문에 매번 사용한 커넥션을 명시적으로 close() 해서 돌려줘야 합니다.
    어떤 상황에서도 커넥션이 반환될 수 있도록 try-catch-finally 구문으로 작성합니다.
     */
    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = connectionMaker.makeConnection();
            ps = c.prepareStatement(
                    "insert into users (id, name, password) values (?, ?, ?)"
            );

            ps.setString(1, user.getId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getPassword());

            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            ps.close();
            c.close();
        }
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        User user = null;
        try {
            c = connectionMaker.makeConnection();
            ps = c.prepareStatement(
                    "select * from users where id = ?"
            );

            ps.setString(1, id);

            rs = ps.executeQuery();


            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }
        } catch (Exception e) {
            throw e;
        } finally {
            rs.close();
            ps.close();
            c.close();
        }

        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return user;
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = connectionMaker.makeConnection();

            ps = c.prepareStatement("delete from users");

            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            ps.close();
            c.close();
        }
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            c = connectionMaker.makeConnection();

            ps = c.prepareStatement("select count(*) from users");

            rs = ps.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (Exception e) {
            throw e;
        } finally {
            rs.close();
            ps.close();
            c.close();
        }
    }

}
