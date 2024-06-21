package com.cu.sci.lambdaserver.schedule;

import com.cu.sci.lambdaserver.schedule.service.IScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping(path = ("/api/v1/schedule"))
@RequiredArgsConstructor
public class ScheduleController {

    private final IScheduleService scheduleService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<ScheduleDto> getSchedule() {
        return scheduleService.getMySchedule();
    }
}
