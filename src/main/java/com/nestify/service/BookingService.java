package com.nestify.service;

import com.nestify.dto.BookingDto;
import com.nestify.dto.BookingRequestDto;
import com.nestify.dto.GuestDto;

import java.util.List;

public interface BookingService {

    BookingDto initialiseBooking(BookingRequestDto bookingRequest);

    BookingDto addGuests(Long bookingId, List<GuestDto> guestDtoList);
}
