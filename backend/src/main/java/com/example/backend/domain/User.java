package com.example.backend.domain;

import jakarta.persistence.Table;

import java.util.Objects;

@jakarta.persistence.Entity
@Table(name="users")
public class User extends Entity<Long> {

    private String fullName;
    private String username;
    private String password;
    private String email;
    private String address;
    private String zipCode;

    public User() {

    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {return fullName;}

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return username + " " + fullName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), username);
    }

    public String getZipCode() {
        return zipCode;
    }
}


