package com.nestify.dto;

import com.nestify.entity.HotelContactInfo;
import com.nestify.entity.Room;
import lombok.Data;
import java.util.List;

@Data
public class HotelDto {
    private String name;
    private String city;
    private String[] photos;
    private String[] amenities;
    private HotelContactInfo contactInfo;
}
