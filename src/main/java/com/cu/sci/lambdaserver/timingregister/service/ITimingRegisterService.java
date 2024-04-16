package com.cu.sci.lambdaserver.timingregister.service;

import com.cu.sci.lambdaserver.timingregister.TimingRegister;
import com.cu.sci.lambdaserver.timingregister.dto.TimingRegisterInDto;
import org.springframework.data.domain.Page;

public interface ITimingRegisterService {
    TimingRegister createTimingRegister(TimingRegisterInDto timingRegisterInDto);

    Page<TimingRegister> getAllTimingRegisters(Integer pageNo, Integer pageSize);

    TimingRegister getTimingRegister(Long id);

    TimingRegister updateTimingRegister(TimingRegisterInDto timingRegisterInDto);

    TimingRegister deleteTimingRegister(Long id);
}