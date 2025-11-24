package com.nestify.dto;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HotelSearchRequestDto {
    private String city;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer roomCount;

    private Integer pageNumber=0;
    private Integer pageSize=10;
}
