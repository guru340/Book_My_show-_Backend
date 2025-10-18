package com.cfs.BookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Seatdto {
    private Long Id;
    private String seatNumber;
    private String seatType;
    private Double basePrice;
}
