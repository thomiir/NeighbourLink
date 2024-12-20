package org.example.lab6.repository;

import org.example.lab6.domain.User;
import org.example.lab6.domain.validators.Validator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository extends AbstractRepository<Long, User> {
    public UserRepository(Validator<User> validator) {
        super(validator);
    }

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