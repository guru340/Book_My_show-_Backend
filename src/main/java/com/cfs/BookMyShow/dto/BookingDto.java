package com.cfs.BookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {
    private Long id;
    private String BookingNumber;
    private LocalDateTime BookingTime;
    private UserDto user;
    private ShowDto show;
    private String status;
    private double totalAmount;
    private List<ShowseatDto> seats;
    private Payementdto payement;

}
