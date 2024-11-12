package com.Controller.ControllerMaker.Exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Setter
@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    /**
     *
     */

    private String resourceName;
    private String filedName;
    private Object filedValue;

    public ResourceNotFoundException(String resourceName, String filedName, Object filedValue) {
        super(String.format("%s not found in %s : %s", resourceName, filedName, filedValue));
        this.resourceName = resourceName;
        this.filedName = filedName;
        this.filedValue = filedValue;
    }

}
