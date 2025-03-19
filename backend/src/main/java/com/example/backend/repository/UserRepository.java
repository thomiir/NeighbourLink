package com.example.backend.repository;

import com.example.backend.domain.User;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserRepository extends AbstractRepository<Long, User> {

    @Override
    protected User createEntity(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getLong(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getString(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7));
    }

    @Override
    protected String getTableName() {
        return "users";
    }

    @Override
    protected String getInsertQuery() {
        return "insert into \"users\" (\"id\", \"full_name\", \"username\", \"password\", \"email\", \"address\", \"zip_code\")  values(?,?,?,?,?,?,?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "";
    }

    @Override
    protected void setFindOneStatementParameters(PreparedStatement ps, Long id) throws SQLException {
        ps.setLong(1, id);
    }

    @Override
    protected void setInsertStatementParameters(PreparedStatement ps, User user) throws SQLException {
        ps.setLong(1, user.getId());
        ps.setString(2, user.getFullName());
        ps.setString(3, user.getUsername());
        ps.setString(4, user.getPassword());
        ps.setString(5, user.getEmail());
        ps.setString(6, user.getAddress());
        ps.setString(7, user.getZipCode());
    }

    @Override
    protected void setDeleteStatementParameters(PreparedStatement ps, Long id) throws SQLException {
        ps.setLong(1, id);
    }

    @Override
    protected void setUpdateStatementParameters(PreparedStatement ps, User entity) throws SQLException {

    }
}