package com.Controller.ControllerMaker.Model;

import com.Controller.ControllerMaker.PayLoads.UserDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Users")
@NoArgsConstructor
@Getter
@Setter
public class User extends UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "UserName must be Required !!")
    @Column(name = "username", nullable = false, length = 100)
    private String userName;

    @NotEmpty(message = "Email must be Required !!")
    private String email;

    @NotEmpty(message = "Password must be Required !!")
    private String password;

//    @NotEmpty(message = "Image must be Required !!")
    private String imageName;
}

