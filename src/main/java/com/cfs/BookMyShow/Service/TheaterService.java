package com.cfs.BookMyShow.Service;

import com.cfs.BookMyShow.Repostitry.theaterRepository;
import com.cfs.BookMyShow.dto.TheaterDto;
import com.cfs.BookMyShow.exception.ResourceNotFound;
import com.cfs.BookMyShow.model.Theater;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TheaterService {
    @Autowired
    private theaterRepository theaterRepository;

        public TheaterDto createThearate(TheaterDto theaterDto){
            Theater theater=mapToEntity(theaterDto);
            Theater savedTheater = theaterRepository.save(theater);
            return mapToDto(savedTheater);
        }

    private TheaterDto mapToDto(Theater theater) {
        TheaterDto theaterDto=new TheaterDto();
        theaterDto.setId(theater.getId());
        theaterDto.setName(theater.getName());
        theaterDto.setCity(theater.getCity());
        theaterDto.setAddress(theater.getAddress());
        theaterDto.setTotalScreen(theater.getTotalScreen());
        return theaterDto;
    }

    private Theater mapToEntity(TheaterDto theaterDto) {
        Theater theater=new Theater();
        theater.setName(theaterDto.getName());
        theater.setAddress(theaterDto.getAddress());
        theater.setCity(theaterDto.getCity());
        theater.setTotalScreen(theaterDto.getTotalScreen());
        return theater;
    }


    public TheaterDto getTheaterById(Long id)
    {
        Theater theater=theaterRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFound("Theater not found with id: "+id));
        return mapToDto(theater);
    }

    private List<TheaterDto> getAllTheaters()
    {
        List<Theater> theaters=theaterRepository.findAll();
        return theaters.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private List<TheaterDto> getAllTheaterByCity(String city)
    {
        List<Theater> theaters=theaterRepository.findByCity(city);
        return theaters.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    }
