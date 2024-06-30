package com.esgi.calendar.repository;

import com.esgi.calendar.business.UserCustomer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserCustomer, Long> {

    UserCustomer findByEmailIgnoreCase(String email);

}

