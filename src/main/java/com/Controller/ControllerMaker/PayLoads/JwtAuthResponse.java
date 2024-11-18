package com.Controller.ControllerMaker.PayLoads;

import lombok.Data;

@Data
public class JwtAuthResponse {

    private String token;

    private UserDto user;
}
