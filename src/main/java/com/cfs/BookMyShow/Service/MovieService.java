package com.cfs.BookMyShow.Service;

import com.cfs.BookMyShow.Repostitry.MovieRepository;
import com.cfs.BookMyShow.dto.MovieDto;
import com.cfs.BookMyShow.exception.ResourceNotFound;
import com.cfs.BookMyShow.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepo;

        public MovieDto createMovie(MovieDto movieDto) {
        Movie movie=mapToEntity(movieDto);
        Movie saveMovie=movieRepo.save(movie);
        return mapToDto(saveMovie);
        }
    public MovieDto getMovieById(Long id)
    {
        Movie movie=movieRepo.findById(id)
                .orElseThrow(()->new ResourceNotFound("Movie not found with id : "+id));
        return mapToDto(movie);
    }

    public List<MovieDto> getAllMovies(){
            List<Movie> movies=movieRepo.findAll();
            return movies.stream()
                    .map(this::mapToDto).collect(Collectors.toList());
    }

    public List<MovieDto> getMovieByLanguage(String language)
    {
        List<Movie> movies=movieRepo.findBylanguage(language);
        return movies.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<MovieDto> getMovieByGenre(String genre)
    {
        List<Movie> movies=movieRepo.findByGenre(genre);
        return movies.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }
    public List<MovieDto> searchMovies(String title)
    {
        List<Movie> movies=movieRepo.findBylanguage(title);
        return movies.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public MovieDto updateMovie(Long id,MovieDto movieDto)
    {
        Movie movie=movieRepo.findById(id)
                .orElseThrow(()->new ResourceNotFound("Movie not found with id : "+id));
        movie.setTitle(movieDto.getTitle());
        movie.setDescription(movieDto.getDescription());
        movie.setLanguage(movieDto.getLanguage());
        movie.setGenre(movieDto.getGenre());
        movie.setDurationMin(movieDto.getDurationMins());
        movie.setReleaseDAte(movieDto.getReleaseDate());
        movie.setPosterUrl(movieDto.getPosterUrl());

        Movie updatedMovie = movieRepo.save(movie);
        return mapToDto(updatedMovie);
    }

    public void deleteMovie(Long id){
        Movie movie=movieRepo.findById(id)
                .orElseThrow(()->new ResourceNotFound("Movie not found with id : "+id));
        movieRepo.delete(movie);
    }

        public MovieDto mapToDto(Movie movie)
        {
            MovieDto movieDto=new MovieDto();
            movieDto.setId(movie.getId());
            movieDto.setTitle(movie.getTitle());
            movieDto.setDescription(movie.getDescription());
            movieDto.setLanguage(movie.getLanguage());
            movieDto.setGenre(movie.getGenre());
            movieDto.setDurationMins(movie.getDurationMin());
            movieDto.setReleaseDate(movie.getReleaseDAte());
            movieDto.setPosterUrl(movie.getPosterUrl());
            return movieDto;
        }

        public Movie mapToEntity(MovieDto movieDto)
        {
            Movie movie=new Movie();
            movie.setTitle(movieDto.getTitle());
            movie.setDescription(movieDto.getDescription());
            movie.setLanguage(movieDto.getLanguage());
            movie.setGenre(movieDto.getGenre());
            movie.setDurationMin(movieDto.getDurationMins());
            movie.setReleaseDAte(movieDto.getReleaseDate());
            movie.setPosterUrl(movieDto.getPosterUrl());
            return movie;
        }
    }

