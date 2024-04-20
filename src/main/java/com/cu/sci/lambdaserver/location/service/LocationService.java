package com.cu.sci.lambdaserver.location.service;

import com.cu.sci.lambdaserver.location.Location;
import com.cu.sci.lambdaserver.location.LocationRepository;
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
    public LocationDto createLocation(LocationDto locationDto) {
        boolean exists = locationRepository.existsByNameIgnoreCase(locationDto.getName());
        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Location with name " + locationDto.getName() + " already exists");
        }

        Location location = locationRepository.save(locationMapper.mapFrom(locationDto));
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
    public LocationDto updateLocationById(Long id, LocationDto locationDetails) {
        /*@TODO: Logic need to write by better way*/

        Optional<Location> location = locationRepository.findById(id);

        if (location.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location with ID " + id + " does not exist");
        }

        if (!locationDetails.getName().equalsIgnoreCase(location.get().getName())) {
            Optional<Location> locationByName = locationRepository.findByNameIgnoreCase(locationDetails.getName());
            if (locationByName.isPresent() && !locationByName.get().getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Location with name " + locationDetails.getName() + " already exists");
            }
        }

        Location updatedLocation = location.map(existingLocation -> {
            existingLocation.setPath(locationDetails.getPath());
            existingLocation.setMaxCapacity(locationDetails.getMaxCapacity());
            existingLocation.setInfo(locationDetails.getInfo());
            existingLocation.setImage(locationDetails.getImage());
            existingLocation.setName(locationDetails.getName());
            return locationRepository.save(existingLocation);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR));

        return locationMapper.mapTo(updatedLocation);
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
