package com.esgi.calendar.repository;

import com.esgi.calendar.business.UserApplication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserApplication, Long> {
}
