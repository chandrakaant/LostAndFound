package com.Excpetion;

public class EmailExistsException extends Exception {
    private  String message;



    public EmailExistsException() {
    }

    public EmailExistsException(String message) {
        this.message = message;
    }
}
