package com.cfs.BookMyShow.Repostitry;

import com.cfs.BookMyShow.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {

    List<Movie> findBylanguage(String language);

    List<Movie> findByTitleContaining(String title);

    List<Movie> findByGenre(String genre);

}
