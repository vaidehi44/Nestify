package com.nestify.service;

import com.nestify.dto.HotelDto;
import com.nestify.dto.HotelSearchRequestDto;
import com.nestify.entity.Room;
import org.springframework.data.domain.Page;

public interface InventoryService {

    void initializeInventoryForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequestDto hotelSearchRequest);
}
