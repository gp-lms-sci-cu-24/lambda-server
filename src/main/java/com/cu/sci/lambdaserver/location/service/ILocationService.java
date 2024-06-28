package com.cu.sci.lambdaserver.location.service;

import com.cu.sci.lambdaserver.location.dto.CreateUpdateLocationDto;
import com.cu.sci.lambdaserver.location.dto.LocationDto;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

/**
 * The ILocationService interface defines the contract for the Location service.
 * It contains methods for creating, updating, deleting, and retrieving Location entities.
 * Each method is annotated with @PreAuthorize to restrict access to users with 'ADMIN' role.
 */
public interface ILocationService {

    /**
     * Creates a new Location entity.
     *
     * @param location The LocationDto object containing the data to be used in creating the Location entity.
     * @return The created LocationDto object.
     */
    @PreAuthorize("hasRole('ADMIN')")
    LocationDto createLocation(CreateUpdateLocationDto location);

    /**
     * Updates an existing Location entity by its ID.
     *
     * @param id       The ID of the Location entity to be updated.
     * @param location The LocationDto object containing the updated data.
     * @return The updated LocationDto object.
     */
    @PreAuthorize("hasRole('ADMIN')")
    LocationDto updateLocationById(Long id, CreateUpdateLocationDto location);

    /**
     * Deletes a Location entity by its ID.
     *
     * @param id The ID of the Location entity to be deleted.
     * @return A MessageResponse object containing the result of the deletion operation.
     */
    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse deleteLocationById(Long id);

    /**
     * Retrieves all Location entities.
     *
     * @return A List object containing LocationDto objects. Each LocationDto represents a Location entity.
     */
    List<LocationDto> getAllLocations();

    /**
     * Retrieves a Location entity by its ID.
     *
     * @param id The ID of the Location entity to be retrieved.
     * @return The retrieved LocationDto object.
     */
    LocationDto getLocationById(Long id);
}