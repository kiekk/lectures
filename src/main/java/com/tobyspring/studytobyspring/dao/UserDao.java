package com.tobyspring.studytobyspring.dao;

import com.tobyspring.studytobyspring.domain.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    private final ConnectionMaker connectionMaker;

    public UserDao() {
        ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
        this.connectionMaker = context.getBean("connectionMaker", ConnectionMaker.class);
    }

    /*
    일반적으로 서버에서는 제한된 개수의 DB 커넥션을 만들어서 재사용 가능한
    풀로 관리하기 때문에 매번 사용한 커넥션을 명시적으로 close() 해서 돌려줘야 합니다.
    어떤 상황에서도 커넥션이 반환될 수 있도록 try-resource-finally 구문으로 작성합니다.

    DB에서 문제가 발생해 Connection, PreparedStatement, ResultSet을 가져오지 못하고
    에러가 발생될 수 있습니다. 이 때는 finally에서 각 객체들이 전부 null이기 때문에
    close() 호출시 NullPointerException이 발생하지 않도록 null 체크가 필요합니다.
    또한 close() 시에도 예외가 발생할 수 있기 때문에 각 close() 부분을 try-catch로
    감싸줘야 합니다.
     */
    /*
        만약 특정 메소드에서만 사용된다면 별도의 클래스로 만들 필요 없이
        내부 클래스로 만드는 것도 하나의 방법입니다.

        내부 클래스 사용시 장점은 메소드의 로컬 변수를 내부 클래스에서 사용할 수 있기 때문에
        따로 생성자를 통해 파라미터를 전달할 필요가 없습니다.
        이 때 로컬 변수를 내부 클래스에서 사용할 경우 변경을 할 수 없도록 final로
        선언하는 것이 좋습니다.

        한번만 사용되기 때문에 굳이 다시 클래스에 이름을 줄 필요가 없을 경우는
        아래와 같이 익명 클래스로 구현합니다.
     */
    public void add(final User user) throws ClassNotFoundException, SQLException {
        StatementStrategy stmt = new StatementStrategy() {
            @Override
            public PreparedStatement makePreparedStatement(Connection c) throws SQLException {
                PreparedStatement ps = c.prepareStatement(
                        "insert into users (id, name, password) values (?, ?, ?)"
                );

                ps.setString(1, user.getId());
                ps.setString(2, user.getName());
                ps.setString(3, user.getPassword());

                ps.executeUpdate();

                return ps;
            }
        };

        jdbcContextWithStatementStrategy(stmt);
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }

        if (user == null) {
            throw new EmptyResultDataAccessException(1);
        }

        return user;
    }

    /*
        먼저 변경되는 부분이 아닌 컨텍스트 코드를 별도의
        jdbcContextWithStatementStrategy 메소드로 분리합니다.
        그리고 StatementStrategy 전략 인터페이스를 파라미터로 전달합니다.
     */
    public void deleteAll() throws SQLException, ClassNotFoundException {
        StatementStrategy strategy = new DeleteAllStatement();
        jdbcContextWithStatementStrategy(strategy);
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
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    public void jdbcContextWithStatementStrategy(StatementStrategy stmt) throws SQLException, ClassNotFoundException {
        Connection c = null;
        PreparedStatement ps = null;
        try {
            c = connectionMaker.makeConnection();

            ps = stmt.makePreparedStatement(c);

            ps.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                }
            }
            if (c != null) {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }
        }
    }

}
