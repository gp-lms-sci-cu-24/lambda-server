package com.cu.sci.lambdaserver.location.service;

import com.cu.sci.lambdaserver.location.Location;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface ILocationService {
    Location createLocation(Location location);

    Page<Location> getAllLocations(Integer pageNo, Integer pageSize);

    Optional<Location> getLocation(Long id);

    Location updateLocation(Long id, Location location);

    void deleteLocation(Long id);
}