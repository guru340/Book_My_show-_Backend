package com.cfs.BookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TheaterDto {

    private Long Id;
    private String name;
    private String address;
    private String City;
    private String totalScreen;
}
