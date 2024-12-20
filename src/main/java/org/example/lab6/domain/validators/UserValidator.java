package org.example.lab6.domain.validators;

import org.example.lab6.domain.User;

import java.util.function.Predicate;

public class UserValidator implements Validator<User> {

    final static Predicate<String> containsDigits = x -> x.matches(".*[0-9]+.*");
    final static Predicate<String> isValidEmail = x -> x.matches("[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+]");
    final static Predicate<String> isEmpty = String::isEmpty;

    @Override
    public void validate(User entity) throws ValidationException {
        if (entity.getFullName().isEmpty() || entity.getUsername().isEmpty())
            throw new ValidationException("Name must not be null!\n");
        if (containsDigits.test(entity.getFullName()))
            throw new ValidationException("Name must not contain digits!\n");
        if (isValidEmail.test(entity.getEmail()))
            throw new ValidationException("Email address is not valid!\n");
        if (isEmpty.test(entity.getUsername()))
            throw new ValidationException("Username must not be empty!\n");
        if (isEmpty.test(entity.getPassword()))
            throw new ValidationException("Password must not be empty!\n");
        if (isEmpty.test(entity.getFullName()))
            throw new ValidationException("First name must not be empty!\n");
        if (isEmpty.test(entity.getZipCode()))
            throw new ValidationException("Zip code must not be empty!\n");
    }
}