package org.example.lab6.domain;

import java.util.Objects;

public class User extends Entity<Long> {

    private final String fullName;
    private final String username;
    private final String password;
    private final String email;
    private final String address;
    private final String zipCode;

    public User(String fullName, String email, String username, String password, String address, String zipCode) {
        this.fullName = fullName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.zipCode = zipCode;
    }

    public User(Long id, String fullName, String username, String password, String email, String address, String zipCode) {
        this.address = address;
        this.zipCode = zipCode;
        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.password = password;
        this.email = email;
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


