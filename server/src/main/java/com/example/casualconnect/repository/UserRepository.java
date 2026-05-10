package com.example.casualconnect.repository;

import com.example.casualconnect.models.User;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
  private final JdbcTemplate jdbc;
  public UserRepository(JdbcTemplate jdbc) { this.jdbc = jdbc; }

  public User login(String email, String password) {
    return jdbc.queryForObject("SELECT id, name, email, department, field, bio FROM users WHERE email=? AND password=?", userMapper(), email, password);
  }

  public User find(int id) {
    return jdbc.queryForObject("SELECT id, name, email, department, field, bio FROM users WHERE id=?", userMapper(), id);
  }

  public User update(int id, String name, String department, String field, String bio) {
    jdbc.update("UPDATE users SET name=?, department=?, field=?, bio=? WHERE id=?", name, department, field, bio, id);
    return find(id);
  }

  private org.springframework.jdbc.core.RowMapper<User> userMapper() {
    return (rs, rowNum) -> new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"),
      rs.getString("department"), rs.getString("field"), rs.getString("bio"));
  }
}
