package com.together.user_calendar.controller;

import com.together.user_calendar.bean.User_calendarDTO;
import com.together.user_calendar.service.CalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(path={"calendar"})
public class CalendarController {
    @Autowired
    private CalendarService calendarService;

    @PostMapping(path={"writecalendar"})
    private void writeCalendar(@RequestBody User_calendarDTO calendarDTO){
        System.out.println("writeCalendar: " + calendarDTO);//User_calendar(calendar_id=0, calendar_time=null, title=이번엔된다, content=20일이던가, color=null, calendar_start=Wed May 22 00:00:00 KST 2024, calendar_end=null, calendar_memberId=hong)
        calendarService.writeCalendar(calendarDTO);
    }

    //이번 달 정보 + 유저이름 -> 이번 달 유저 일정 정보
    @GetMapping(path={"getCalendarlist"})
    private List<User_calendarDTO> getCalendarList(@RequestParam("startDate")String startDateStr
                                                    ,@RequestParam("endDate")String endDateStr
                                                    , @RequestParam("memberId")String memberId){
        System.out.println("calendarList: 이번달은 " + startDateStr +" ~ " + endDateStr + " 이고 나는 " + memberId +"입니다.");//KST: T00:00:00+09:00
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX");
        LocalDateTime startDateTime = LocalDateTime.parse(startDateStr, formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(endDateStr, formatter);

        Date startDate = Date.from(startDateTime.atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endDateTime.atZone(ZoneId.systemDefault()).toInstant());

        return calendarService.getCalendarList(startDate, endDate, memberId);
    }

    @PostMapping(path = {"updateCalendar"})
    private void updateCalendar(@RequestBody User_calendarDTO userCalendarDTO){
        System.out.println("updateCalendar");

        // start와 end 값 확인
        System.out.println("Start: " + userCalendarDTO.getStart());
        System.out.println("End: " + userCalendarDTO.getEnd());
        System.out.println(userCalendarDTO);
        calendarService.updateCalendar(userCalendarDTO);
    }

    @PostMapping(path={"getOneCalendar"})
    private Optional<User_calendarDTO> getOneCalendar(@RequestBody String calendar_id){
        Integer id = Integer.parseInt(calendar_id.split("=")[0]);
        System.out.println(id);
        return calendarService.getOneCalendar(id);
    }

    @PostMapping(path={"deleteCalendar"})
    private void deleteCalendar(@RequestParam("memberId")String memberId){
        Integer member_id = Integer.parseInt(memberId);
        System.out.println("delete: " + member_id);
        calendarService.deleteCalendar(member_id);
    }

}
