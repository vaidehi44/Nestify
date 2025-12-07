package com.nestify.repository;

import com.nestify.entity.Guest;
import com.nestify.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GuestRepository extends JpaRepository<Guest, Long> {

    List<Guest> findByUser(User user);
}
