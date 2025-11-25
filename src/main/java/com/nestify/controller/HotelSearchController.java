package com.nestify.controller;

import com.nestify.dto.HotelDto;
import com.nestify.dto.HotelInfoDto;
import com.nestify.dto.HotelSearchRequestDto;
import com.nestify.service.HotelService;
import com.nestify.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelSearchController {

    private final InventoryService inventoryService;
    private final HotelService hotelService;

    @GetMapping("/search")
    public ResponseEntity<Page<HotelDto>> searchHotels(@RequestBody HotelSearchRequestDto hotelSearchRequest) {
        Page<HotelDto> hotelsPage = inventoryService.searchHotels(hotelSearchRequest);
        return ResponseEntity.ok(hotelsPage);
    }

    @GetMapping("/{hotelId}")
    public ResponseEntity<HotelInfoDto> getHotelInfo(@PathVariable("hotelId") long hotelId) {
        HotelInfoDto hotelInfoDto = hotelService.getHotelInfoById(hotelId);
        return ResponseEntity.ok(hotelInfoDto);
    }

}
