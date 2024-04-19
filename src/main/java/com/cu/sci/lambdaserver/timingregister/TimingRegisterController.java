package com.cu.sci.lambdaserver.timingregister;

import com.cu.sci.lambdaserver.timingregister.dto.TimingRegisterInDto;
import com.cu.sci.lambdaserver.timingregister.dto.TimingRegisterOutDto;
import com.cu.sci.lambdaserver.timingregister.mapper.TimingRegisterOutDtoMapper;
import com.cu.sci.lambdaserver.timingregister.service.TimingRegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "v1/api/timing-register")
@RequiredArgsConstructor
public class TimingRegisterController {
    private final TimingRegisterService timingRegisterService;
    private final TimingRegisterOutDtoMapper timingRegisterOutDtoMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TimingRegisterOutDto createCourseRegister(@Validated(TimingRegisterInDto.CreateValidation.class) @RequestBody TimingRegisterInDto timingRegisterInDto) {
        return timingRegisterOutDtoMapper.mapTo(timingRegisterService.createTimingRegister(timingRegisterInDto));
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Page<TimingRegisterOutDto> getAllCourseRegisters(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        Page<TimingRegister> page = timingRegisterService.getAllTimingRegisters(pageNo, pageSize);
        return page.map(timingRegisterOutDtoMapper::mapTo);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public TimingRegisterOutDto getCourseRegister(@PathVariable Long id) {
        return timingRegisterOutDtoMapper.mapTo(timingRegisterService.getTimingRegister(id));
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public TimingRegisterOutDto updateCourseRegister(@Validated(TimingRegisterInDto.UpdateValidation.class) @RequestBody TimingRegisterInDto timingRegisterInDto) {
        return timingRegisterOutDtoMapper.mapTo(timingRegisterService.updateTimingRegister(timingRegisterInDto));
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public TimingRegisterOutDto deleteCourseRegister(@PathVariable("id") Long id) {
        return timingRegisterOutDtoMapper.mapTo(timingRegisterService.deleteTimingRegister(id));
    }
}