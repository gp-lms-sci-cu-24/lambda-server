package com.cu.sci.lambdaserver.location;

import com.cu.sci.lambdaserver.location.dto.LocationDto;
import com.cu.sci.lambdaserver.location.mapper.LocationMapper;
import com.cu.sci.lambdaserver.location.service.ILocationService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "v1/api/location")
@RequiredArgsConstructor
public class LocationController {
    private final LocationMapper locationMapper;
    private final ILocationService locationService;

    @PostMapping
    public ResponseEntity<LocationDto> createLocation(@RequestBody LocationDto locationDto) {
        Location locationEntity = locationMapper.mapFrom(locationDto);
        Location savedLocation = locationService.createLocation(locationEntity);
        return new ResponseEntity<>(locationMapper.mapTo(savedLocation), HttpStatus.CREATED);
    }
    @GetMapping
    public ResponseEntity<Page<LocationDto>> getAllLocations(@RequestParam Integer pageNo, @RequestParam Integer pageSize) {
        Page<Location> page = locationService.getAllLocations(pageNo, pageSize);
        Page<LocationDto> dtoPage = page.map(locationMapper::mapTo);
        return new ResponseEntity<>(dtoPage, HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable Long id) {
        Optional<Location> foundLocation = locationService.getLocation(id);
        return foundLocation.map(location -> new ResponseEntity<>(locationMapper.mapTo(location), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PatchMapping
    public ResponseEntity<LocationDto> updateLocation(@RequestBody LocationDto locationDto) {
        try {
            Location updatedLocation = locationService.updateLocation(locationDto);
            return new ResponseEntity<>(locationMapper.mapTo(updatedLocation), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable("id") Long id) {
        try {
            locationService.deleteLocation(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}