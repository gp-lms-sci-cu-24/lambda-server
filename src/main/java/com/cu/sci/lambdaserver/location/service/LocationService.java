package com.cu.sci.lambdaserver.location.service;

import com.cu.sci.lambdaserver.location.Location;
import com.cu.sci.lambdaserver.location.LocationRepository;
import com.cu.sci.lambdaserver.location.dto.CreateUpdateLocationDto;
import com.cu.sci.lambdaserver.location.dto.LocationDto;
import com.cu.sci.lambdaserver.location.mapper.LocationMapper;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService implements ILocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    /**
     * {@inheritDoc}
     */
    @Override
    public LocationDto createLocation(CreateUpdateLocationDto dto) {
        boolean exists = locationRepository.existsByNameIgnoreCase(dto.name());
        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Location with name " + dto.name() + " already exists");
        }
        final String info = dto.info() == null ? "" : dto.info();

        Location location = locationRepository.save(Location.builder()
                .path(dto.path())
                .name(dto.name())
                .maxCapacity(dto.maxCapacity())
                .info(info)
                .build()
        );

        return locationMapper.mapTo(location);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<LocationDto> getAllLocations(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Location> locations = locationRepository.findAll(pageable);
        //check if list empty
        if (locations.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }
        return locations.map(locationMapper::mapTo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocationDto getLocationById(Long id) {
        Location location = locationRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Location with ID " + id + " does not exist")
                );

        return locationMapper.mapTo(location);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public LocationDto updateLocationById(Long id, CreateUpdateLocationDto locationDetails) {
        /*@TODO: Logic need to write by better way*/

        Optional<Location> location = locationRepository.findById(id);

        if (location.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location with ID " + id + " does not exist");
        }

        if (!locationDetails.name().equalsIgnoreCase(location.get().getName())) {
            Optional<Location> locationByName = locationRepository.findByNameIgnoreCase(locationDetails.name());
            if (locationByName.isPresent() && !locationByName.get().getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Location with name " + locationDetails.name() + " already exists");
            }
        }

        final String info = locationDetails.info() == null ? "" : locationDetails.info();

        Location updatedLocation = location.get();
        updatedLocation.setName(locationDetails.name());
        updatedLocation.setPath(locationDetails.path());
        updatedLocation.setMaxCapacity(locationDetails.maxCapacity());
        updatedLocation.setInfo(info);

        Location saved = locationRepository.save(updatedLocation);
        return locationMapper.mapTo(saved);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MessageResponse deleteLocationById(Long id) {
        Location location = locationRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Location with ID " + id + " does not exist")
                );

        locationRepository.delete(location);

        return new MessageResponse("Location with ID " + id + " has been deleted");
    }
}
