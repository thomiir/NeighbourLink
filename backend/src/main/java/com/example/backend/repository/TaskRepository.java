package com.example.backend.repository;

import com.example.backend.domain.Task;
import com.example.backend.domain.constants.TaskType;
import com.example.backend.util.paging.Page;
import com.example.backend.util.paging.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskRepository extends AbstractRepository<Long, Task> implements PagedRepository<Long, Task> {

    private UserRepository userRepository;

    @Override
    protected Task createEntity(ResultSet resultSet) throws SQLException {
        return new Task(
                resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("description"),
                userRepository.findOne(resultSet.getLong("poster_id")).get(),
                userRepository.findOne(resultSet.getLong("solver_id")).get(),
                Date.valueOf(resultSet.getTimestamp("date_posted").toLocalDateTime().toLocalDate()).toString(),
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

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Page<Task> findAllOnPage(Pageable pageable) {
        List<Task> entities = new ArrayList<>();
        try (Connection connection = prepareConnection();
             PreparedStatement ps = connection.prepareStatement("SELECT * FROM tasks limit " + pageable.pageSize() + " OFFSET " + (pageable.pageNumber() - 1) * pageable.pageSize());
             ResultSet resultSet = ps.executeQuery()) {
            while (resultSet.next())
                entities.add(createEntity(resultSet));
        } catch (SQLException e) {
            throw new RuntimeException("Database error: " + e.getMessage(), e);
        }
        return new Page<>(entities);
    }
}
