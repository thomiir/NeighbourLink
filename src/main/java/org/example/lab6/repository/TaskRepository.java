package org.example.lab6.repository;

import org.example.lab6.domain.Task;
import org.example.lab6.domain.constants.TaskType;
import org.example.lab6.domain.validators.Validator;
import org.example.lab6.util.paging.Page;
import org.example.lab6.util.paging.Pageable;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository extends AbstractRepository<Long, Task> implements PagingRepository<Long, Task> {

    public TaskRepository(Validator<Task> validator) {
        super(validator);
    }

    @Override
    protected Task createEntity(ResultSet resultSet) throws SQLException {
        return new Task(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                resultSet.getLong("poster_id"),
                resultSet.getLong("solver_id"),
                resultSet.getTimestamp("date_posted").toLocalDateTime(),
                resultSet.getString("length"),
                TaskType.valueOf(resultSet.getString("type")));
    }

    @Override
    protected String getTableName() {
        return "tasks";
    }

    @Override
    protected String getInsertQuery() {
        return "";
    }

    @Override
    protected String getUpdateQuery() {
        return "";
    }

    @Override
    protected void setFindOneStatementParameters(PreparedStatement ps, Long aLong) throws SQLException {

    }

    @Override
    protected void setInsertStatementParameters(PreparedStatement ps, Task entity) throws SQLException {

    }

    @Override
    protected void setDeleteStatementParameters(PreparedStatement ps, Long aLong) throws SQLException {

    }

    @Override
    protected void setUpdateStatementParameters(PreparedStatement ps, Task entity) throws SQLException {

    }

    @Override
    public Page<Task> findAllOnPage(Pageable pageable) {
        List<Task> entities = new ArrayList<>();
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM tasks limit " + pageable.getPageSize() + " OFFSET " + (pageable.getPageNumber() - 1) * pageable.getPageSize());
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next())
                entities.add(createEntity(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return new Page<>(entities);
    }

}
