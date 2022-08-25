package io.springbatch.studyspringbatch;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class CustomerRowMapper implements RowMapper<Map<String, Object>> {
    @Override
    public Map<String, Object> mapRow(ResultSet rs, int i) throws SQLException {
        return Map.of(
                "id", rs.getLong("id"),
                "firstname", rs.getString("firstName"),
                "lastname", rs.getString("lastName"),
                "birthdate", rs.getString("birthdate")
        );
    }
}