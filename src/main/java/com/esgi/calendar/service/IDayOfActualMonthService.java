package com.esgi.calendar.service;

import com.esgi.calendar.business.DayOfActualMonth;

import java.util.List;

public interface IDayOfActualMonthService {

    public List<DayOfActualMonth> getBetweenToday();

}
