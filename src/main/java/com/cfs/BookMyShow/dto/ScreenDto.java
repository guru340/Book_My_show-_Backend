package com.cfs.BookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreenDto {
    private Long Id;
    private String name;
    private Integer totalseats;
    private TheaterDto theater;
}
