package org.example.lab6.domain.validators;

import org.example.lab6.domain.Message;

public class MessageValidator implements Validator<Message> {
    @Override
    public void validate(Message entity) throws ValidationException {
        if ("".equals(entity.getText()))
            throw new ValidationException("Text cannot be empty!");
    }
}
