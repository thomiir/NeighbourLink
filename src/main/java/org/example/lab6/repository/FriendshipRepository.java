package org.example.lab6.repository;

import org.example.lab6.domain.Friendship;
import org.example.lab6.domain.Tuple;
import org.example.lab6.domain.validators.Validator;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FriendshipRepository extends AbstractRepository<Tuple<Long, Long>, Friendship> {
    public FriendshipRepository(Validator<Friendship> validator) {
        super(validator);
    }

    @Override
    protected Friendship createEntity(ResultSet resultSet) throws SQLException {
        Friendship friendship = new Friendship();
        friendship.setId(new Tuple<>(resultSet.getLong("ID1"), resultSet.getLong("ID2")));
        friendship.setDateSent(resultSet.getTimestamp("FriendsFrom").toLocalDateTime());
        return friendship;
    }

    @Override
    protected String getTableName() {
        return "friendships";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO friendships (\"ID1\",\"ID2\",\"FriendsFrom\") VALUES(?,?,?)";
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
    protected void setInsertStatementParameters(PreparedStatement ps, Friendship entity) throws SQLException {
        ps.setLong(1, entity.getId().getLeft());
        ps.setLong(2, entity.getId().getRight());
        ps.setDate(3, Date.valueOf(entity.getDateSent().toLocalDate()));
    }

    @Override
    protected void setDeleteStatementParameters(PreparedStatement ps, Tuple<Long, Long> tuple) throws SQLException {
        ps.setLong(1, tuple.getLeft());
        ps.setLong(2, tuple.getRight());
    }

    @Override
    protected void setUpdateStatementParameters(PreparedStatement ps, Friendship entity) throws SQLException {

    }

    @Override
    public Optional<Friendship> findOne(Tuple<Long, Long> tuple) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE (\"ID1\" = ? AND \"ID2\" = ?) OR (\"ID1\" = ? AND \"ID2\" = ?)";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, tuple.getLeft());
            ps.setLong(2, tuple.getRight());
            ps.setLong(3, tuple.getRight());
            ps.setLong(4, tuple.getLeft());
            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next())
                return Optional.of(createEntity(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Friendship> delete(Tuple<Long, Long> tuple) {
        Optional<Friendship> friendship = findOne(tuple);
        if (friendship.isEmpty()) {
            return Optional.empty();
        }
        String sql = "DELETE FROM " + getTableName() + " WHERE (\"ID1\" = ? AND \"ID2\" = ?) OR (\"ID1\" = ? AND \"ID2\" = ?)";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setLong(1, tuple.getLeft());
            ps.setLong(2, tuple.getRight());
            ps.setLong(3, tuple.getRight());
            ps.setLong(4, tuple.getLeft());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return friendship;
    }


}