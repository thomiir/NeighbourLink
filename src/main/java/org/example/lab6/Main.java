package org.example.lab6;

import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        Application.launch(HelloApplication.class, args);
    }

}
/**
    TO-DO LIST:
        1) cleanup services
        3) implement validators for all objects [ALMOST DONE]
            - fix expressions n validators
        4) make interface pretty! also a logo
        5) My Tasks button?
        6) edit / delete a message
            - editing a message is a method that needs to be implemented in
            - service, you shouldn't set the text directly in the object
            - add [EDITED] text after successful edit
        7) mark tasks as solved
        9) clean up HelloApplication
        11) solve login/register controller issue [ALMOST DONE]
            - solve duplicated code issue

 **/
