package com.esgi.calendar.repository;

import com.esgi.calendar.business.Theme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ThemeRepository extends JpaRepository<Theme, Long> {

    Theme findByName(String name);

}
