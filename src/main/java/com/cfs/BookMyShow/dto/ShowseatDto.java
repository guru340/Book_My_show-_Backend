package com.cfs.BookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowseatDto {
    private Long Id;
    private Seatdto seat;
    private String status;
    private Double price;
}
