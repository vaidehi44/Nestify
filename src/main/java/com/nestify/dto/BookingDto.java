package com.nestify.dto;

import com.nestify.entity.enums.BookingStatus;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class BookingDto {
    private Integer roomsCount;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private BookingStatus bookingStatus;
    private Set<GuestDto> guests;
}
