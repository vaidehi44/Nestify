package com.nestify.service;

import com.nestify.dto.HotelDto;
import com.nestify.dto.HotelInfoDto;
import com.nestify.entity.Hotel;

public interface HotelService {

    HotelDto createNewHotel(HotelDto hotelDto);

    HotelDto getHotelById(Long id);

    HotelDto updateHotelById(Long id, HotelDto hotelDto);

    void deleteHotelById(Long id);

    void activateHotel(Long hotelId);

    HotelInfoDto getHotelInfoById(long hotelId);
}
