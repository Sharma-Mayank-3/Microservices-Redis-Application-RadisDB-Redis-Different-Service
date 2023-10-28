package com.userredis.controller;

import com.userredis.dto.UserClassDto;
import com.userredis.payload.ApiResponse;
import com.userredis.service.UserClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/redis/user")
public class UserController {

    @Autowired
    private UserClassService userClassService;

    @PostMapping("/")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserClassDto userClassDto){
        UserClassDto user = this.userClassService.createUser(userClassDto);
        ApiResponse build = ApiResponse.builder().message("user-created").data(user).serviceName("user-service").status(true).build();
        return new ResponseEntity<>(build, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable("userId") int userId){
        UserClassDto user = this.userClassService.getUserById(userId);
        ApiResponse build = ApiResponse.builder().message("get user byId").data(user).serviceName("user-service").status(true).build();
        return new ResponseEntity<>(build, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllUsers(){
        List<UserClassDto> allUser = this.userClassService.getAllUser();
        ApiResponse build = ApiResponse.builder().message("get all users").data(allUser).serviceName("user-service").status(true).build();
        return new ResponseEntity<>(build, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable("userId") int userId){
        String s = this.userClassService.deleteUser(userId);
        ApiResponse build = ApiResponse.builder().message("delete user byId").data(s).serviceName("user-service").status(true).build();
        return new ResponseEntity<>(build, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable("userId") int userId,
                            @RequestBody UserClassDto userClassDto
                                                      ){
        UserClassDto userClassDto1 = this.userClassService.updateUser(userId, userClassDto);
        ApiResponse build = ApiResponse.builder().message("user-updated").data(userClassDto1).serviceName("user-service").status(true).build();
        return new ResponseEntity<>(build, HttpStatus.OK);
    }

}
