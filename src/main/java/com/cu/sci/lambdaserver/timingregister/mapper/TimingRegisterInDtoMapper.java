package com.cu.sci.lambdaserver.timingregister.mapper;

import com.cu.sci.lambdaserver.timingregister.TimingRegister;
import com.cu.sci.lambdaserver.timingregister.dto.TimingRegisterInDto;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimingRegisterInDtoMapper implements IMapper<TimingRegister, TimingRegisterInDto> {
    private final ModelMapper modelMapper;

    @Override
    public TimingRegisterInDto mapTo(TimingRegister timingRegister) {
        return modelMapper.map(timingRegister, TimingRegisterInDto.class);
    }

    @Override
    public TimingRegister mapFrom(TimingRegisterInDto timingRegisterInDto) {
        return modelMapper.map(timingRegisterInDto, TimingRegister.class);
    }

    @Override
    public TimingRegister update(TimingRegisterInDto timingRegisterInDto, TimingRegister timingRegister) {
        modelMapper.map(timingRegisterInDto, timingRegister);
        return timingRegister;
    }
}