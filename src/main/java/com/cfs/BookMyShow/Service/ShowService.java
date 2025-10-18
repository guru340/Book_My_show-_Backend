package com.cfs.BookMyShow.Service;

import com.cfs.BookMyShow.Repostitry.MovieRepository;
import com.cfs.BookMyShow.Repostitry.ScreenRepository;
import com.cfs.BookMyShow.Repostitry.ShowRepository;
import com.cfs.BookMyShow.Repostitry.ShowseatRepository;
import com.cfs.BookMyShow.dto.*;
import com.cfs.BookMyShow.exception.ResourceNotFound;
import com.cfs.BookMyShow.model.Movie;
import com.cfs.BookMyShow.model.Screen;
import com.cfs.BookMyShow.model.Show;
import com.cfs.BookMyShow.model.Showseat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShowService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ScreenRepository screenRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private ShowseatRepository showseatRepository;

    public ShowDto createshow(ShowDto showDto){
        Show show=new Show();
        Movie movie=movieRepository.findById(showDto.getMovie().getId()).orElseThrow(()-> new ResourceNotFound("Movie Not Found"));

        Screen screen=screenRepository.findById(showDto.getMovie().getId())
                .orElseThrow(()-> new ResourceNotFound("Screen Not Found"));


        show.setMovie(movie);
        show.setScreen(screen);
        show.setStartTime(showDto.getStartTime());
        show.setEndTime(showDto.getEndTime());

        Show savedshow=showRepository.save(show);

        List<Showseat> availableseats=showseatRepository.findByShowIdAndStatus(savedshow.getId(),"Available");
        return mapToDto(savedshow,availableseats);
    }

    public ShowDto getShowById(Long id)
    {
        Show show=showRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("Show not found  with id: "+id));
        List<Showseat> availableSeats=
                showseatRepository.findByShowIdAndStatus(show.getId(),"AVAILABLE");
        return mapToDto(show,availableSeats);
    }

    public List<ShowDto> getAllShows()
    {
        List<Show> shows=showRepository.findAll();
        return shows.stream()
                .map(show -> {
                    List<Showseat> availableSeats = showseatRepository.findByShowIdAndStatus(show.getId(), "AVAILABLE");
                    return mapToDto(show,availableSeats);
                })
                .collect(Collectors.toList());
    }

    public List<ShowDto> getShowsByMovie(Long movieId)
    {
        List<Show> shows=showRepository.findByMovieId(movieId);
        return shows.stream()
                .map(show -> {
                    List<Showseat> availableSeats = showseatRepository.findByShowIdAndStatus(show.getId(), "AVAILABLE");
                    return mapToDto(show,availableSeats);
                })
                .collect(Collectors.toList());
    }

    public List<ShowDto> getShowsByMovieAndCity(Long movieId,String city)
    {
        List<Show> shows=showRepository.findByMovie_IdAndScreen_Theater_City(movieId,  city);
        return shows.stream()
                .map(show -> {
                    List<Showseat> availableSeats = showseatRepository.findByShowIdAndStatus(show.getId(), "AVAILABLE");
                    return mapToDto(show,availableSeats);
                })
                .collect(Collectors.toList());
    }

    public List<ShowDto> getShowsByDateRange(LocalDateTime startDate, LocalDateTime endDate)
    {
        List<Show> shows=showRepository.findByStartTimeBetween(startDate,endDate);
        return shows.stream()
                .map(show -> {
                    List<Showseat> availableSeats = showseatRepository.findByShowIdAndStatus(show.getId(), "AVAILABLE");
                    return mapToDto(show,availableSeats);
                })
                .collect(Collectors.toList());
    }

    private ShowDto mapToDto(Show show,List<Showseat> availableSeats){
        ShowDto showDto= new ShowDto();
        showDto.setId(show.getId());
        showDto.setStartTime(show.getStartTime());
        showDto.setEndTime(show.getEndTime());

        showDto.setMovie(new MovieDto(
                show.getMovie().getId(),
                show.getMovie().getTitle(),
                show.getMovie().getDescription(),
                show.getMovie().getLanguage(),
                show.getMovie().getGenre(),
                show.getMovie().getDurationMin(),
                show.getMovie().getReleaseDAte(),
                show.getMovie().getPosterUrl()
        ));

        TheaterDto theaterDto=new TheaterDto(
                show.getScreen().getTheater().getId(),
                show.getScreen().getTheater().getName(),
                show.getScreen().getTheater().getAddress(),
                show.getScreen().getTheater().getCity(),
                show.getScreen().getTheater().getTotalScreen()
        );

        showDto.setScreen(new ScreenDto(
                show.getScreen().getId(),
                show.getScreen().getName(),
                show.getScreen().getTotalseats(),
                theaterDto
        ));

        List<ShowseatDto> seatDtos= availableSeats.stream()
                .map(seat->{
                    ShowseatDto seatDto=new ShowseatDto();
                    seatDto.setId(seat.getId());
                    seatDto.setStatus(seat.getStatus());
                    seatDto.setPrice(seat.getPrice());

                    Seatdto baseSeatDto=new Seatdto();
                    baseSeatDto.setId(seat.getSeat().getId());
                    baseSeatDto.setSeatNumber(seat.getSeat().getSeatnumber());
                    baseSeatDto.setSeatType(seat.getSeat().getSeatType());
                    baseSeatDto.setBasePrice(seat.getSeat().getBaseprice());
                    seatDto.setSeat(baseSeatDto);
                    return seatDto;
                })
                .collect(Collectors.toList());

        showDto.setAvailableseats(seatDtos);
        return showDto;

    }
}
