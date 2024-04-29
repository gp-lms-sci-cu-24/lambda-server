package com.cu.sci.lambdaserver.storage.service;

import com.cu.sci.lambdaserver.user.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * This interface defines the storage services that can be performed.
 * All methods are secured and can only be accessed by users with the appropriate roles.
 */
public interface IStorageService {

    /**
     * Uploads an image for a specific department.
     * This method can only be accessed by users with the 'ADMIN' role.
     *
     * @param departmentCode The code of the department for which the image is being uploaded.
     * @param image The image file to be uploaded.
     * @return The URL of the uploaded image.
     */
    @PreAuthorize("hasRole('ADMIN')")
    String uploadDepartmentImage(String departmentCode, MultipartFile image) throws IOException;

    /**
     * Uploads an image for a location.
     * This method can only be accessed by users with the 'ADMIN' role.
     *
     * @param image The image file to be uploaded.
     * @return The URL of the uploaded image.
     */
    @PreAuthorize("hasRole('ADMIN')")
    String uploadLocationImage(MultipartFile image) throws IOException;

    /**
     * Deletes an image for a specific department.
     * This method can only be accessed by users with the 'ADMIN' role.
     *
     * @param departmentCode The code of the department for which the image is being deleted.
     * @return true if the image was successfully deleted, false otherwise.
     */
    @PreAuthorize("hasRole('ADMIN')")
    boolean deleteDepartmentImage(String departmentCode);

    /**
     * Deletes a location image.
     * This method can only be accessed by users with the 'ADMIN' role.
     *
     * @return true if the image was successfully deleted, false otherwise.
     */
    @PreAuthorize("hasRole('ADMIN')")
    boolean deleteLocationImage();

    /**
     * Uploads an image for a user.
     *
     * @param user The user for which the image is being uploaded.
     * @param image The image file to be uploaded.
     * @return The URL of the uploaded image.
     */
    String uploadUserImage(User user,MultipartFile image);

    /**
     * Deletes a user's image.
     *
     * @param user The user for which the image is being deleted.
     * @return true if the image was successfully deleted, false otherwise.
     */
    boolean deleteUserImage(User user);
}