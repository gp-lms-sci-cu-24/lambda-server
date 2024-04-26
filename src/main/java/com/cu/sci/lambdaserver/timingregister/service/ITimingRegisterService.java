package com.cu.sci.lambdaserver.timingregister.service;

import com.cu.sci.lambdaserver.courseclass.entity.CourseClassTiming;
import com.cu.sci.lambdaserver.timingregister.TimingRegister;
import com.cu.sci.lambdaserver.timingregister.dto.TimingRegisterInDto;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITimingRegisterService {
    TimingRegister createTimingRegister(TimingRegisterInDto timingRegisterInDto);

    Page<TimingRegister> getAllTimingRegisters(Integer pageNo, Integer pageSize);

    TimingRegister getTimingRegister(Long id);

    TimingRegister updateTimingRegister(TimingRegisterInDto timingRegisterInDto);

    TimingRegister deleteTimingRegister(Long id);

    List<CourseClassTiming> getTimingRegisterByClassId(Long classId);
}