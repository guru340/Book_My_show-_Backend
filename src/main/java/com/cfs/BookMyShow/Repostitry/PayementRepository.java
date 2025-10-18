package com.cfs.BookMyShow.Repostitry;

import com.cfs.BookMyShow.model.Payement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PayementRepository extends JpaRepository<Payement,Long> {


    Optional<Payement> findByTransctionId(String TransctionId);
}
