package com.Controller.ControllerMaker.PayLoads;

import jakarta.validation.constraints.*;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
public class UserDto {
    private int id;

    @Size(min = 4, message = "Username must be minimum of 4 characters !!")
    private String userName;

    @Email(message = "Email Address is not Valid !!")
    private String email;

    @Size(min = 8, message = "Password must be minimum of 8 characters with mixed case letters, numbers and punctuation marks.")
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message= "mixed case letters, numbers and punctuation marks.")
    private String password;

    private String imageName;
}
