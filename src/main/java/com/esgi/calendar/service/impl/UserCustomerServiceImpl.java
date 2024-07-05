package com.esgi.calendar.service.impl;

import com.esgi.calendar.business.UserCustomer;
import com.esgi.calendar.repository.UserRepository;
import com.esgi.calendar.service.IUserCustomerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserCustomerServiceImpl implements IUserCustomerService {

    private UserRepository userRepository;

    public UserCustomer recupererUserCustomerParMail(String mail) {
        return userRepository.findByEmailIgnoreCase(mail);
    }
}
