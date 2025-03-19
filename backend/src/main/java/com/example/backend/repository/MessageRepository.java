package com.example.backend.repository;

import com.example.backend.domain.Message;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class MessageRepository extends AbstractRepository<Long, Message> {

    @Override
    protected Message createEntity(ResultSet resultSet) throws SQLException {
        Message message = new Message(
                resultSet.getLong("sender"),
                resultSet.getLong("receiver"),
                resultSet.getString("text"),
                resultSet.getLong("reply"));
        message.setId(resultSet.getLong("id"));
        return message;
    }

    @Override
    protected String getTableName() {
        return "messages";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO messages (id, sender, receiver, text, reply) VALUES (?,?,?,?,?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "";
    }

    @Override
    protected void setFindOneStatementParameters(PreparedStatement ps, Long aLong) throws SQLException {
        ps.setLong(1, aLong);
    }

    @Override
    protected void setInsertStatementParameters(PreparedStatement ps, Message message) throws SQLException {
        ps.setLong(1, message.getId());
        ps.setLong(2, message.getSender());
        ps.setLong(3, message.getReceiver());
        ps.setString(4, message.getText());
        ps.setObject(5, message.getReplyMessage());
    }

    @Override
    protected void setDeleteStatementParameters(PreparedStatement ps, Long aLong) throws SQLException {
        ps.setLong(1, aLong);
    }

    @Override
    protected void setUpdateStatementParameters(PreparedStatement ps, Message entity) throws SQLException {
    }
}
