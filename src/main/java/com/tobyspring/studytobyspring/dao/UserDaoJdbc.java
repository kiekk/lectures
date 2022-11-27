package com.tobyspring.studytobyspring.dao;

import com.tobyspring.studytobyspring.domain.User;
import com.tobyspring.studytobyspring.enums.Level;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class UserDaoJdbc implements UserDao {

    private JdbcTemplate jdbcTemplate;
    private DataSource dataSource;
    private final int MAX_RETRY = 3;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    @Override
    public void add(User user) {
        this.jdbcTemplate.update("insert into users(id, name, password, level, login, recommend, email) values (?, ?, ?, ?, ?, ?, ?)",
                user.getId(), user.getName(), user.getPassword(), user.getLevel().intValue(), user.getLogin(), user.getRecommend(), user.getEmail());
    }

    @Override
    public User get(String id) {
        return this.jdbcTemplate.queryForObject("select * from users where id = ?",
                new Object[]{id}, userRowMapper);
    }

    @Override
    public List<User> getAll() {
        return this.jdbcTemplate.query("select * from users order by id", userRowMapper);
    }

    @Override
    public void deleteAll() {
        this.jdbcTemplate.update("delete from users");
    }

    @Override
    public int getCount() {
        return this.jdbcTemplate.query(con -> con.prepareStatement("select count(*) from users"), rs -> {
            rs.next();
            return rs.getInt(1);
        });
    }

    @Override
    public void update(User user) {
        this.jdbcTemplate.update(
                "update users set name = ?, password = ?, level = ?, login = ?, recommend = ?, email = ? where id = ?",
                user.getName(),
                user.getPassword(),
                user.getLevel().intValue(),
                user.getLogin(),
                user.getRecommend(),
                user.getEmail(),
                user.getId()
        );
    }

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> {
        User user = new User();
        user.setId(rs.getString("id"));
        user.setName(rs.getString("name"));
        user.setPassword(rs.getString("password"));
        user.setLevel(Level.valueOf(rs.getInt("level")));
        user.setLogin(rs.getInt("login"));
        user.setRecommend(rs.getInt("recommend"));
        user.setEmail(rs.getString("email"));
        return user;
    };
}
