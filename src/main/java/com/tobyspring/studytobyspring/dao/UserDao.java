package com.tobyspring.studytobyspring.dao;

import com.mysql.cj.exceptions.MysqlErrorNumbers;
import com.tobyspring.studytobyspring.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserDao {
    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;

    private final int MAX_RETRY = 3;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        return user;
    };


    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    public void add(final User user) {
        try {
            this.jdbcTemplate.update("insert into users(id, name, password) values (?, ?, ?)",
                    user.getId(), user.getName(), user.getPassword());
        } catch (SQLException e) {
            // 로그 출력
            // Duplicate Entry 예외일경우 직접 작성한 예외로 전환
            if(e.getErrorCode() == MysqlErrorNumbers.ER_DUP_ENTRY) {
                // 원본 예외 담아서 반환
                throw new DuplicateUserIdException().initCause(e);
            } else {
                throw e;
            }

            // 서비스 계층에서 SQLException 을 그대로 반환하면 어떤 상황에서 예외가 발생했는지 알기 어렵다.
            // 따라서 좀 더 구체적인 예외로 전환해주는 것이 좋다.
            // 하지만 주로 예외처리를 강제하는 체크 예외를 언체크 예외인 런타임 예외로 바꾸는 경우에 사용한다.
        }
    }

    public User get(String id) throws ClassNotFoundException, SQLException {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[]{id}, userRowMapper);
    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        this.jdbcTemplate.update("delete from users");
    }

    public int getCount() throws SQLException, ClassNotFoundException {
        return this.jdbcTemplate.query(con -> con.prepareStatement("select count(*) from users"), rs -> {
            rs.next();
            return rs.getInt(1);
        });
    }

    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id", userRowMapper);
    }

}
