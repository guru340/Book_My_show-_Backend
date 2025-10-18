package com.cfs.BookMyShow.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {

    private Long Id;
    private String title;
    private String description;
    private String language;
    private String genre;
    private Integer DurationMins;
    private String releaseDate;
    private String posterUrl;
}
