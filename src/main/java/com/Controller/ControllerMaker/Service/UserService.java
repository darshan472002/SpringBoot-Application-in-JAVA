package com.Controller.ControllerMaker.Service;

import com.Controller.ControllerMaker.Model.User;
import com.Controller.ControllerMaker.PayLoads.UserDto;
import com.Controller.ControllerMaker.PayLoads.UserResponse;

import java.util.List;

public interface UserService {
    UserDto saveUser(UserDto user);
    UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    UserDto getUserById(long id);
    UserDto updateUser(UserDto user, long id);
    void deleteUser(long id);
    List<User> searchUsers(String keyword);

}
