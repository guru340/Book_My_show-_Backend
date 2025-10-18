package com.cfs.BookMyShow.Repostitry;

import com.cfs.BookMyShow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
     Optional<User> findByEmail(String Email);

     Boolean existsByEmail(String email);
}
