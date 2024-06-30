package com.esgi.calendar.repository;

import com.esgi.calendar.business.DayOfActualMonth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<DayOfActualMonth, Long> {
}
