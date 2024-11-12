package com.Controller.ControllerMaker.Service.Impl;

import com.Controller.ControllerMaker.Exception.ResourceNotFoundException;
import com.Controller.ControllerMaker.Model.User;
import com.Controller.ControllerMaker.PayLoads.UserResponse;
import com.Controller.ControllerMaker.Repository.UserRepository;
import com.Controller.ControllerMaker.Service.UserService;
import com.Controller.ControllerMaker.PayLoads.UserDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

//    GET method
    @Override
    public UserResponse getAllUsers(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("asc"))
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable p = PageRequest.of(pageNumber, pageSize, sort);
        Page<User> pageUsers = this.userRepository.findAll(p);
        List<User> users = pageUsers.getContent();
        List<UserDto> userDtos = users.stream().map(this::convertToDto)
                .collect(Collectors.toList());

//        List<User> allUsers = this.userRepository.findAll();
//        List<UserDto> userDto = allUsers.stream().map(this::convertToDto)
//                .collect(Collectors.toList());
        UserResponse userResponse = new UserResponse();

        userResponse.setContent(userDtos);
        userResponse.setPageNumber(pageUsers.getNumber());
        userResponse.setPageSize(pageUsers.getSize());
        userResponse.setTotalElements(pageUsers.getTotalElements());
        userResponse.setTotalPages(pageUsers.getTotalPages());
        userResponse.setLastPage(pageUsers.isLast());

        return userResponse;
    }

//    GET by ID method
    @Override
    public UserDto getUserById(long id) {
        User user = this.userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        return this.convertToDto(user);
    }

//    POST method
    @Override
    public UserDto saveUser(UserDto userDto) {
        User user = this.convertToEntity(userDto);
        User savedUser = this.userRepository.save(user);
        return savedUser;
//        return this.convertToDto(savedUser);
    }

//    UPDATE method
    @Override
    public UserDto updateUser(UserDto userDto, long id) {
//        we need to check whether employee with given id is existed in DB or not
        User existingUser = this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));

        existingUser.setUserName(userDto.getUserName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPassword(userDto.getPassword());

        User updatedUser = this.userRepository.save(existingUser);
        UserDto userDto1 = this.convertToDto(updatedUser);

        return userDto1;
    }

//    DELETE method
    @Override
    public void deleteUser(long id) {
//        check whether an employee exist in a DB or not
        this.userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        this.userRepository.deleteById(id);
    }

    @Override
    public List<User> searchUsers(String keyword) {
        return userRepository.searchUsers(keyword);
    }


    // Utility method to convert User entity to UserDto
    private UserDto convertToDto(User user) {
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
//        userDto.setUserName(user.getUserName());
//        userDto.setEmail(user.getEmail());
//        userDto.setPassword(user.getPassword());
        return userDto;
    }

    // Utility method to convert UserDto to User entity
    private User convertToEntity(UserDto userDto) {
        User user = this.modelMapper.map(userDto, User.class);
//        user.setUserName(userDto.getUserName());
//        user.setEmail(userDto.getEmail());
//        user.setPassword(userDto.getPassword());
        return user;
    }
}
