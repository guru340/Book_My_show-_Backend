package com.cfs.BookMyShow.Repostitry;

import com.cfs.BookMyShow.model.Showseat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowseatRepository extends JpaRepository<Showseat,Long> {
    List<Showseat> findByShowId(Long movieId);

    List<Showseat> findByShowIdAndStatus(Long showId,String status);
}
