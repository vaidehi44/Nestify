package com.nestify.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
public class HotelContactInfo {
    private String phoneNumber;
    private String email;
    private String address;
    private String country;
}
