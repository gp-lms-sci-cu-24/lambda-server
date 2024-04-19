package com.cu.sci.lambdaserver.timingregister;

import com.cu.sci.lambdaserver.courseclasstiming.CourseClassTiming;
import com.cu.sci.lambdaserver.courseclasstiming.dto.CourseClassTimingOutDto;
import com.cu.sci.lambdaserver.timingregister.dto.TimingRegisterInDto;
import com.cu.sci.lambdaserver.timingregister.dto.TimingRegisterOutDto;
import com.cu.sci.lambdaserver.timingregister.mapper.TimingRegisterOutDtoMapper;
import com.cu.sci.lambdaserver.timingregister.service.TimingRegisterService;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "v1/api/timing-register")
@RequiredArgsConstructor
public class TimingRegisterController {
    private final TimingRegisterService timingRegisterService;
    private final TimingRegisterOutDtoMapper timingRegisterOutDtoMapper;
    private final iMapper<CourseClassTiming,CourseClassTimingOutDto> courseClassTimingOutDtoiMapper;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimingRegisterOutDto createCourseRegister(@Validated(TimingRegisterInDto.CreateValidation.class)  @RequestBody TimingRegisterInDto timingRegisterInDto) {
        return timingRegisterOutDtoMapper.mapTo(timingRegisterService.createTimingRegister(timingRegisterInDto) );
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<TimingRegisterOutDto> getAllCourseRegisters(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        Page<TimingRegister> page = timingRegisterService.getAllTimingRegisters(pageNo, pageSize);
        Page<TimingRegisterOutDto> dtoPage = page.map(timingRegisterOutDtoMapper::mapTo);
        return dtoPage;
    }
    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TimingRegisterOutDto getCourseRegister(@PathVariable Long id) {
        return timingRegisterOutDtoMapper.mapTo(timingRegisterService.getTimingRegister(id) );
    }
    @GetMapping(path = "/class/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Collection<CourseClassTimingOutDto> getClassTimings(@PathVariable Long id) {
        return timingRegisterService.getTimingRegisterByClassId(id)
            .stream().map(courseClassTimingOutDtoiMapper::mapTo).collect(Collectors.toList());
    }
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public TimingRegisterOutDto updateCourseRegister(@Validated(TimingRegisterInDto.UpdateValidation.class) @RequestBody TimingRegisterInDto timingRegisterInDto) {
        return timingRegisterOutDtoMapper.mapTo(timingRegisterService.updateTimingRegister(timingRegisterInDto) );
    }
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public TimingRegisterOutDto deleteCourseRegister(@PathVariable("id") Long id) {
        return timingRegisterOutDtoMapper.mapTo(timingRegisterService.deleteTimingRegister(id) );
    }
}