package com.Controller.ControllerMaker.Controller;

import com.Controller.ControllerMaker.Exception.ApiResponse;
import com.Controller.ControllerMaker.Model.User;
import com.Controller.ControllerMaker.PayLoads.UserResponse;
import com.Controller.ControllerMaker.Service.FileService;
import com.Controller.ControllerMaker.Service.UserService;
import com.Controller.ControllerMaker.PayLoads.UserDto;
import com.Controller.ControllerMaker.Utils.ImageValidator;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${project.image}")
    private String path;

    //    build get all user REST API
    @GetMapping()
    public ResponseEntity<UserResponse> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "4", required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir)
    {

        UserResponse userResponse = this.userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    //    build get user by id REST API
    @GetMapping("{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") long userId) {
        return ResponseEntity.ok(this.userService.getUserById(userId));
    }

//    build the post request if the user
    @PostMapping("")
    public ResponseEntity<UserDto> saveUser(@Valid @RequestBody UserDto userDto) {
        UserDto createUserDto = this.userService.saveUser(userDto);
        return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }


//     build update user REST API
    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("id") long id) {
        UserDto updatedUser = this.userService.updateUser(userDto, id);
        return ResponseEntity.ok(updatedUser);
    }

//    build delete user REST API
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("id") long userId) {
        this.userService.deleteUser(userId);
        return new ResponseEntity<>(new ApiResponse("User Deleted Successfully", true), HttpStatus.OK);
    }

//    _______________________________________________________________Image File____________________________________________________________________________

//    post image upload
    @PostMapping("/image/{id}")
    public ResponseEntity<?> uploadUserImage(@RequestParam("image") MultipartFile image,
                                             @PathVariable Integer id) throws IOException {

        if (!ImageValidator.isValidImage(image)) {
            return new ResponseEntity<>(new ApiResponse("Invalid image file: only JPG, PNG allowed and size should be under 2 MB", false), HttpStatus.BAD_REQUEST);
        }

        UserDto userDto = this.userService.getUserById(id);

        String fileName = this.fileService.uploadImage(path, image);
        userDto.setImageName(fileName);
        UserDto updatedUser = this.userService.updateUser(userDto, id);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //    Method to serve files
    @GetMapping(value = "/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void getUserImage(@PathVariable("imageName") String imageName, HttpServletResponse response) throws IOException {

        InputStream resource = this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

//    Method to Update file
    @PutMapping("/image/update/{id}")
    public ResponseEntity<?> updateUserImage(
            @PathVariable("id") Integer id,
            @RequestParam("image") MultipartFile image) throws IOException {

        if (!ImageValidator.isValidImage(image)) {
            return new ResponseEntity<>(new ApiResponse("Invalid image file: only JPG, PNG allowed and size should be under 2 MB", false), HttpStatus.BAD_REQUEST);
        }

        // Fetch the existing user by id
        UserDto userDto = this.userService.getUserById(id);

        // Get the name of the existing image file to be replaced
        String existingFileName = userDto.getImageName();

        // Use the FileService to replace the existing image with the new one
        String updatedFileName = this.fileService.updateImage(path, existingFileName, image);

        // Update the user DTO with the new file name
        userDto.setImageName(updatedFileName);

        // Save the updated user information
        UserDto updatedUser = this.userService.updateUser(userDto, id);

        // Return the updated user information
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

//    Method to delete file
    @DeleteMapping("/image/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUserImage(@PathVariable("id") Integer id) throws IOException {

        // Fetch the user by id to get the image name
        UserDto userDto = this.userService.getUserById(id);

        // Get the name of the existing image file
        String fileName = userDto.getImageName();

        if (fileName != null && !fileName.isEmpty()) {

            // Delete the image file using the FileService
            this.fileService.deleteImage(path, fileName);

            // Set the image name in the user DTO to null or empty
            userDto.setImageName(null);

            // Update the user to remove the image reference
            this.userService.updateUser(userDto, id);

            // Return a response indicating success
            return new ResponseEntity<>(new ApiResponse("User image deleted successfully", true), HttpStatus.OK);
        } else {

            // Return a response indicating failed
            return new ResponseEntity<>(new ApiResponse("No image found for this user", false), HttpStatus.NOT_FOUND);
        }
    }


//    ___________________________________________________________Search User___________________________________________________________________________

    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsers(@RequestParam String keyword) {
        System.out.println("Searching with " + keyword);
        List<User> users = userService.searchUsers(keyword);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


}
