package com.nestify.dto;

import com.nestify.entity.User;
import com.nestify.entity.enums.Gender;
import lombok.Data;

@Data
public class GuestDto {
    private String name;
    private Gender gender;
    private Integer age;
}
