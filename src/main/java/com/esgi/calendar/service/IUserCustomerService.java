package com.esgi.calendar.service;

import com.esgi.calendar.business.UserCustomer;

public interface IUserCustomerService {
    UserCustomer recupererUserCustomerParMail(String mail);
}
