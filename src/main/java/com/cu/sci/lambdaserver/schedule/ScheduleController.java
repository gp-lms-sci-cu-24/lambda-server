package com.cu.sci.lambdaserver.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = ("/api/v1/schedule"))
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleService scheduleService;

//    @GetMapping
//    @ResponseStatus(HttpStatus.OK)
//    public List<ScheduleDto> getSchedule() {
//        return scheduleService.getSchedule();
//    }
}
