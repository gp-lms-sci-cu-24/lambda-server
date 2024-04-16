package com.cu.sci.lambdaserver.timingregister.mapper;

import com.cu.sci.lambdaserver.timingregister.TimingRegister;
import com.cu.sci.lambdaserver.timingregister.dto.TimingRegisterOutDto;
import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TimingRegisterOutDtoMapper implements IMapper<TimingRegister, TimingRegisterOutDto> {
    private final ModelMapper modelMapper;

    @Override
    public TimingRegisterOutDto mapTo(TimingRegister timingRegister) {
        return modelMapper.map(timingRegister, TimingRegisterOutDto.class);
    }

    @Override
    public TimingRegister mapFrom(TimingRegisterOutDto courseRegisterInDto) {
        return modelMapper.map(courseRegisterInDto, TimingRegister.class);
    }

    @Override
    public TimingRegister update(TimingRegisterOutDto timingRegisterOutDto, TimingRegister timingRegister) {
        return null;
    }
}