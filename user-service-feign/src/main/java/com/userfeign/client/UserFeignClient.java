package com.userfeign.client;

import com.userfeign.dto.UserClassDto;
import com.userfeign.payload.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "http://localhost:8081/api/redis/user")
public interface UserFeignClient {
    @PostMapping("/")
    public ResponseEntity<ApiResponse> createUser(@RequestBody UserClassDto userClassDto);

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable("userId") int userId);

    @GetMapping("/")
    public ResponseEntity<ApiResponse> getAllUsers();

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable("userId") int userId);

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable("userId") int userId,
                                                  @RequestBody UserClassDto userClassDto
    );
}
