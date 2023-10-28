package com.userfeign.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.userfeign.payload.ApiResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalException {

    @Autowired
    private ModelMapper modelMapper;

    @ExceptionHandler(FeignNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundException(FeignNotFoundException ex) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        int end = ex.getMessage().indexOf("}");
        int start = ex.getMessage().indexOf("{");

        ApiResponse apiResponse = objectMapper.readValue(ex.getMessage().substring(start, end + 1), ApiResponse.class);
        return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }
}
