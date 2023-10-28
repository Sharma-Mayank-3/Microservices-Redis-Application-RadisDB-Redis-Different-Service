package com.userredis.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserClassDto implements Serializable {

    private int userId;
    private String userName;
    private int userAge;
}
