module org.example.lab6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.net.http;
    requires java.desktop;
    requires com.zaxxer.hikari;
    requires java.sql;
    requires spring.boot.autoconfigure;
    requires spring.security.crypto;

    opens org.example.lab6 to javafx.fxml;
    exports org.example.lab6 to javafx.graphics;
    exports org.example.lab6.util.paging;
    exports org.example.lab6.domain.validators;
    exports org.example.lab6.domain.constants;
    exports org.example.lab6.service;
    exports org.example.lab6.controller;
    exports org.example.lab6.domain;
    exports org.example.lab6.repository;
    exports org.example.lab6.util;
}