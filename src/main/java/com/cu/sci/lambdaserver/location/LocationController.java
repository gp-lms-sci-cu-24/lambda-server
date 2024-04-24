package com.cu.sci.lambdaserver.location;

import com.cu.sci.lambdaserver.location.dto.LocationDto;
import com.cu.sci.lambdaserver.location.service.ILocationService;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/v1/locations")
@RequiredArgsConstructor
public class LocationController {

    private final ILocationService locationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LocationDto createLocation(@RequestBody @Valid LocationDto locationDto) {
        return locationService.createLocation(locationDto);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Page<LocationDto> getAllLocations(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize) {
        return locationService.getAllLocations(pageNo, pageSize);
    }

    @GetMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public LocationDto getLocation(@PathVariable Long id) {
        return locationService.getLocationById(id);
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public LocationDto updateLocation(@RequestBody LocationDto locationDto) {
        return locationService.updateLocationById(locationDto.getId(), locationDto);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public MessageResponse deleteLocation(@PathVariable("id") Long id) {
        return locationService.deleteLocationById(id);
    }
}