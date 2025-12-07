package com.nestify.service;

import com.nestify.dto.HotelDto;
import com.nestify.dto.HotelInfoDto;
import com.nestify.dto.RoomDto;
import com.nestify.entity.Hotel;
import com.nestify.entity.Room;
import com.nestify.entity.User;
import com.nestify.exception.ResourceNotFoundException;
import com.nestify.repository.HotelRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.nestify.utils.AppUtils.getCurrentUser;

@Service
@Slf4j
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository hotelRepository;
    private final InventoryService inventoryService;
    private final RoomService roomService;
    private final ModelMapper modelMapper;

    @Override
    public HotelDto createNewHotel(HotelDto hotelDto) {
        log.info("Creating new hotel: {}", hotelDto.getName());
        Hotel hotel = modelMapper.map(hotelDto, Hotel.class);
        hotel.setIsActive(false);
        hotel = hotelRepository.save(hotel);
        log.info("Hotel created with id: {}", hotel.getId());
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto getHotelById(Long id) {
        log.info("Getting hotel by id: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
        log.info("Hotel found");
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public HotelDto updateHotelById(Long id, HotelDto hotelDto) {
        log.info("Updating hotel by id: {}", id);
        Hotel hotel = hotelRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with id: " + id));
        modelMapper.map(hotelDto, hotel);
        hotelRepository.save(hotel);
        log.info("Hotel updated with id: {}", id);
        return modelMapper.map(hotel, HotelDto.class);
    }

    @Override
    public void deleteHotelById(Long hotelId) {
        log.info("Deleting hotel by id: {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));
//        TODO: delete future inventories for this hotel
//        Have added set cascade true in hotel and room.
//        So, should not be required to manually delete inventories.
//        for (Room room: hotel.getRooms()) {
//            inventoryService.deleteAllInventories(room);
//            roomService.deleteRoomById(room.getId());
//        }
        hotelRepository.deleteById(hotelId);
        log.info("Hotel deleted");
    }

    @Override
    @Transactional
    public void activateHotel(Long hotelId) {
        log.info("Activating the hotel with ID: {}", hotelId);
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: "+hotelId));

        hotel.setIsActive(true);

        // assuming only do it once
        for (Room room: hotel.getRooms()) {
            inventoryService.initializeInventoryForAYear(room);
        }
    }

    @Override
    public HotelInfoDto getHotelInfoById(long hotelId) {
        Hotel hotel = hotelRepository
                .findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));
        List<RoomDto> rooms = hotel.getRooms()
                .stream()
                .map((room) -> modelMapper.map(room, RoomDto.class))
                .collect(Collectors.toList());

        return HotelInfoDto.builder()
                .hotelDto(modelMapper.map(hotel, HotelDto.class))
                .rooms(rooms)
                .build();
    }

    @Override
    public List<HotelDto> getAllHotelsByOwner() {
        User user = getCurrentUser();
        log.info("Getting all hotels for the admin user with ID: {}", user.getId());
        List<Hotel> hotels = hotelRepository.findByOwner(user);

        return hotels
                .stream()
                .map((element) -> modelMapper.map(element, HotelDto.class))
                .collect(Collectors.toList());
    }
}
