package com.esgi.calendar.repository;

import com.esgi.calendar.business.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReactionRepository extends JpaRepository<Reaction, Long> {
}