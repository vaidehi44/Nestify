package com.nestify.service;

import com.nestify.dto.HotelDto;
import com.nestify.dto.HotelSearchRequestDto;
import com.nestify.dto.InventoryDto;
import com.nestify.dto.UpdateInventoryRequestDto;
import com.nestify.entity.Room;
import org.springframework.data.domain.Page;

import java.util.List;

public interface InventoryService {

    void initializeInventoryForAYear(Room room);

    void deleteAllInventories(Room room);

    Page<HotelDto> searchHotels(HotelSearchRequestDto hotelSearchRequest);

    List<InventoryDto> getAllInventoryByRoom(Long roomId);

    void updateInventory(Long roomId, UpdateInventoryRequestDto updateInventoryRequestDto);
}
