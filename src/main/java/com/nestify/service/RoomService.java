package com.nestify.service;

import com.nestify.dto.RoomDto;

import java.util.List;

public interface RoomService {

    RoomDto createNewRoom(Long hotelId, RoomDto roomDto);

    List<RoomDto> getAllRoomsInHotel(Long hotelId);

    RoomDto getRoomById(Long roomId);

    RoomDto updateRoomById(Long roomId, RoomDto roomDto);

    void deleteRoomById(Long roomId);
}
