package com.cfs.BookMyShow.Repostitry;

import com.cfs.BookMyShow.model.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface theaterRepository extends JpaRepository<Theater,Long> {

    List<Theater> findByCity(String city);
}
