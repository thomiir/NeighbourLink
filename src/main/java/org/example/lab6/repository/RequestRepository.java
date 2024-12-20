package org.example.lab6.repository;

import org.example.lab6.domain.Request;
import org.example.lab6.domain.Tuple;
import org.example.lab6.domain.validators.Validator;

import java.sql.*;
import java.util.Optional;

public class RequestRepository extends AbstractRepository<Tuple<Long, Long>, Request> {
    public RequestRepository(Validator<Request> validator) {
        super(validator);
    }

    @Override
    protected Request createEntity(ResultSet resultSet) throws SQLException {
        return new Request(
            resultSet.getLong("sender"),
            resultSet.getLong("receiver"),
            resultSet.getTimestamp("dateSent").toLocalDateTime(),
            resultSet.getString("status")
            );
    }

    @Override
    protected String getTableName() {
        return "requests";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO requests (status, sender, receiver, \"dateSent\") VALUES (?,?,?,?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "";
    }

    @Override
    protected void setFindOneStatementParameters(PreparedStatement ps, Tuple<Long, Long> tuple) throws SQLException {
        ps.setLong(1, tuple.getLeft());
        ps.setLong(2, tuple.getRight());
    }

    @Override
    protected void setInsertStatementParameters(PreparedStatement ps, Request entity) throws SQLException {
        ps.setString(1, entity.getStatus());
        ps.setLong(2, entity.getSender());
        ps.setLong(3, entity.getReceiver());
        ps.setTimestamp(4, Timestamp.valueOf(entity.getDateSent()));
    }

    @Override
    protected void setDeleteStatementParameters(PreparedStatement ps, Tuple<Long, Long> tuple) throws SQLException {
        ps.setLong(1, tuple.getLeft());
        ps.setLong(2, tuple.getRight());
    }

    @Override
    protected void setUpdateStatementParameters(PreparedStatement ps, Request entity) throws SQLException {

    }

    @Override
    public Optional<Request> findOne(Tuple<Long, Long> tuple) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE sender = ? OR receiver = ?";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, tuple.getLeft());
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return Optional.of(createEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Request> delete(Tuple<Long, Long> id) {
        String sql = "DELETE FROM " + getTableName() + " WHERE sender = ? AND receiver = ?";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, id.getLeft());
            ps.setLong(2, id.getRight());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return Optional.empty();
    }



}
