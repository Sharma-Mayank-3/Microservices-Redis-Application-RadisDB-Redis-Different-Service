package com.userfeign.serviceImpl;

import com.userfeign.client.UserFeignClient;
import com.userfeign.dto.UserClassDto;
import com.userfeign.exception.FeignNotFoundException;
import com.userfeign.payload.ApiResponse;
import com.userfeign.service.UserClassService;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserClassServiceImpl implements UserClassService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserClassDto createUser(UserClassDto userClassDto) {
        try {
            ResponseEntity<ApiResponse> user = this.userFeignClient.createUser(userClassDto);
            if(!ObjectUtils.isEmpty(user) && !ObjectUtils.isEmpty(user.getBody().getData())){
                Object data = user.getBody().getData();
                return this.modelMapper.map(data, UserClassDto.class);
            }
        }catch (Exception e){
            throw new FeignNotFoundException(e.getMessage());

        }
        return null;
    }

    @Override
    public UserClassDto getUserById(int userId) {
        try {
            ResponseEntity<ApiResponse> user = this.userFeignClient.getUserById(userId);
            if(!ObjectUtils.isEmpty(user) && !ObjectUtils.isEmpty(user.getBody().getData())){
                Object data = user.getBody().getData();
                return this.modelMapper.map(data, UserClassDto.class);
            }
        }catch (Exception e){
            throw new FeignNotFoundException(e.getMessage());
        }
        return null;
    }

    @Override
    public List<UserClassDto> getAllUser() {
        try {
            ResponseEntity<ApiResponse> user = this.userFeignClient.getAllUsers();
            if(!ObjectUtils.isEmpty(user) && !ObjectUtils.isEmpty(user.getBody().getData())){
                List<Object> data = (List<Object>) user.getBody().getData();
                return data.stream().map(d-> this.modelMapper.map(d, UserClassDto.class)).collect(Collectors.toList());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserClassDto updateUser(int userId, UserClassDto userClassDto) {
        try {
            ResponseEntity<ApiResponse> user = this.userFeignClient.updateUser(userId, userClassDto);
            if(!ObjectUtils.isEmpty(user) && !ObjectUtils.isEmpty(user.getBody().getData())){
                Object data = user.getBody().getData();
                return this.modelMapper.map(data, UserClassDto.class);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String deleteUser(int userId) {
        try {
            ResponseEntity<ApiResponse> user = this.userFeignClient.deleteUserById(userId);
            if(!ObjectUtils.isEmpty(user) && !ObjectUtils.isEmpty(user.getBody().getData())){
                Object data = user.getBody().getData();
                return data.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
