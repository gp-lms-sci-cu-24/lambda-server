package com.cu.sci.lambdaserver.location.mapper;

import com.cu.sci.lambdaserver.location.Location;
import com.cu.sci.lambdaserver.location.dto.LocationDto;
import com.cu.sci.lambdaserver.utils.mapper.config.iMapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
@Component
public class LocationMapper implements iMapper<Location, LocationDto> {
    private final ModelMapper modelMapper;
    public LocationMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public LocationDto mapTo(Location location) {
        return modelMapper.map(location, LocationDto.class);
    }

    @Override
    public Location mapFrom(LocationDto locationDto) {
        return modelMapper.map(locationDto, Location.class);
    }

    @Override
    public Location update(LocationDto locationDto, Location location) {
        return null;
    }
}