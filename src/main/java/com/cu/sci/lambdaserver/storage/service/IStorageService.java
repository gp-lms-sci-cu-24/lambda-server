package com.cu.sci.lambdaserver.storage.service;

import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

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
     * @param image          The image file to be uploaded.
     * @return A MessageResponse object containing the URL of the uploaded image and a success message.
     */
    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse uploadDepartmentImage(String departmentCode, MultipartFile image);

    /**
     * Uploads an image for a location.
     * This method can only be accessed by users with the 'ADMIN' role.
     *
     * @param image The image file to be uploaded.
     * @return A MessageResponse object containing the URL of the uploaded image and a success message.
     */
    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse uploadLocationImage(Long locationId, MultipartFile image);

    /**
     * Uploads an image for a course.
     * This method can only be accessed by users with the 'ADMIN' role.
     *
     * @param courseCode The code of the course for which the image is being uploaded.
     * @param image      The image file to be uploaded.
     * @return A MessageResponse object containing the URL of the uploaded image and a success message.
     */
    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse uploadCourseImage(String courseCode , MultipartFile image) ;

    /**
     * Deletes an image for a specific department.
     * This method can only be accessed by users with the 'ADMIN' role.
     *
     * @param departmentCode The code of the department for which the image is being deleted.
     * @return A MessageResponse object containing a success message if the image was successfully deleted, or an error message otherwise.
     */
    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse deleteDepartmentImage(String departmentCode);

    /**
     * Deletes a location image.
     * This method can only be accessed by users with the 'ADMIN' role.
     *
     * @return A MessageResponse object containing a success message if the image was successfully deleted, or an error message otherwise.
     */
    @PreAuthorize("hasRole('ADMIN')")
    MessageResponse deleteLocationImage();

    /**
     * Uploads an image for a user.
     *
     * @param user  The user for which the image is being uploaded.
     * @param image The image file to be uploaded.
     * @return A MessageResponse object containing the URL of the uploaded image and a success message.
     */
    MessageResponse uploadUserImage(User user, MultipartFile image);

    /**
     * Uploads an image for a user.
     *
     * @param username a username for which the image is being uploaded.
     * @param image    The image file to be uploaded.
     * @return A MessageResponse object containing the URL of the uploaded image and a success message.
     */
    MessageResponse uploadUserImage(String username, MultipartFile image);

    /**
     * Deletes a user's image.
     *
     * @param user The user for which the image is being deleted.
     * @return A MessageResponse object containing a success message if the image was successfully deleted, or an error message otherwise.
     */
    MessageResponse deleteUserImage(User user);
}