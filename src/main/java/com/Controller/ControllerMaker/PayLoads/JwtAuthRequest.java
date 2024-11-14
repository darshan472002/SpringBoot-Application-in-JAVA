package com.Controller.ControllerMaker.PayLoads;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String username;
    private String password;
}
