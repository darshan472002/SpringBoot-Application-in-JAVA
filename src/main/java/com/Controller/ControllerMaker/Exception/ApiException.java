package com.Controller.ControllerMaker.Exception;

public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException() {
        super();
    }
}
