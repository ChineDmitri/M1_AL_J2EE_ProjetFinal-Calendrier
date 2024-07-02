package com.esgi.calendar.repository;

import com.esgi.calendar.business.DayOfActualMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface DayOfActualMonthRepository extends JpaRepository<DayOfActualMonth, Long> {

    @Query(
            "SELECT d FROM DayOfActualMonth d " +
                    "WHERE d.date >= ?1 AND d.date <= ?2 " +
                    "ORDER BY d.date ASC"
    )
    List<DayOfActualMonth> findSevenDaysFromToday(LocalDate startDate, LocalDate endDate);

}