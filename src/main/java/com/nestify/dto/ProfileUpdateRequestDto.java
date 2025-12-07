package com.nestify.dto;

import com.nestify.entity.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ProfileUpdateRequestDto {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private Gender gender;
}
