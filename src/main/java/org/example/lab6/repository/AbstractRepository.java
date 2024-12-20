package org.example.lab6.repository;

import org.example.lab6.domain.Entity;
import org.example.lab6.domain.validators.ValidationException;
import org.example.lab6.domain.validators.Validator;
import org.example.lab6.util.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractRepository<ID, E extends Entity<ID>> implements Repository<ID, E> {
    protected final Validator<E> validator;

    public AbstractRepository(Validator<E> validator) {
        this.validator = validator;
    }

    protected Connection prepareConnection() throws SQLException {
        return DataSource.getConnection();
    }

    protected abstract E createEntity(ResultSet resultSet) throws SQLException;

    @Override
    public Optional<E> findOne(ID id) {
        String sql = "SELECT * FROM " + getTableName() + " WHERE id = ?";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            setFindOneStatementParameters(ps, id);
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
    public Iterable<E> findAll() {
        List<E> entities = new ArrayList<>();
        String sql = "SELECT * FROM " + getTableName();
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next())
                entities.add(createEntity(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return entities;
    }

    @Override
    public Optional<E> save(E entity) {
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(getInsertQuery())) {
            validator.validate(entity);
            setInsertStatementParameters(ps, entity);
            ps.executeUpdate();
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        } catch(ValidationException ve) {
            throw new RuntimeException(ve.getMessage(), ve);
        }

    }

    @Override
    public Optional<E> delete(ID id) {
        Optional<E> entity = findOne(id);
        if (entity.isEmpty())
            return Optional.empty();
        String sql = "DELETE FROM " + getTableName() + " WHERE id = ?";
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            setDeleteStatementParameters(ps, id);
            ps.executeUpdate();
            return entity;
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<E> update(E entity) {
        validator.validate(entity);
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement(getUpdateQuery())) {
            setUpdateStatementParameters(ps, entity);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected == 0) {
                return Optional.of(entity);
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
    }

    protected abstract String getTableName();

    protected abstract String getInsertQuery();

    protected abstract String getUpdateQuery();

    protected abstract void setFindOneStatementParameters(PreparedStatement ps, ID id) throws SQLException;

    protected abstract void setInsertStatementParameters(PreparedStatement ps, E entity) throws SQLException;

    protected abstract void setDeleteStatementParameters(PreparedStatement ps, ID id) throws SQLException;

    protected abstract void setUpdateStatementParameters(PreparedStatement ps, E entity) throws SQLException;
}
