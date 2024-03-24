package com.cu.sci.lambdaserver.location.service;

import com.cu.sci.lambdaserver.courseClass.CourseClass;
import com.cu.sci.lambdaserver.location.Location;
import com.cu.sci.lambdaserver.location.LocationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LocationService implements iLocationService {

    private final LocationRepository locationRepository;

    @Override
    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Page<Location> getAllLocations(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return locationRepository.findAll(pageable);
    }

    @Override
    public Optional<Location> getLocation(Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public Location updateLocation(Long id, Location locationDetails) {
        return locationRepository.findById(id).map(existingLocation -> {
            existingLocation.setLocationPath(locationDetails.getLocationPath());
            existingLocation.setMaxCapacity(locationDetails.getMaxCapacity());
            existingLocation.setLocationInfo(locationDetails.getLocationInfo());
            return locationRepository.save(existingLocation);
        }).orElseThrow(() -> new EntityNotFoundException("Location with ID " + id + " does not exist"));
    }

    @Override
    public void deleteLocation(Long id) {
        if (!locationRepository.existsById(id)) {
            throw new EntityNotFoundException("Location with ID " + id + " does not exist");
        }
        locationRepository.deleteById(id);
    }
}
