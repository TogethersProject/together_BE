package com.together.calendar.bean;

import lombok.Data;

@Data
public class GetCalendars {
    private String startDate;
    private String endDate;
    private String memberId;
}
