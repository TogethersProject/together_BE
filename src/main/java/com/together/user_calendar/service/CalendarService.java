package com.together.user_calendar.service;

import com.together.user_calendar.bean.User_calendarDTO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface CalendarService {
    void writeCalendar(User_calendarDTO calendarDTO);

    List<User_calendarDTO> getCalendarList(Date startDate, Date endDate, String memberId);

    void updateCalendar(User_calendarDTO userCalendarDTO);

    Optional<User_calendarDTO> getOneCalendar(Integer calendarId);

    void deleteCalendar(Integer memberId);
}