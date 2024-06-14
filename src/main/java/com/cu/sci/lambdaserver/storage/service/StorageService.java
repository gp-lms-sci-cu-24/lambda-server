package com.cu.sci.lambdaserver.storage.service;

import com.cu.sci.lambdaserver.course.entites.Course;
import com.cu.sci.lambdaserver.course.repositries.CourseRepository;
import com.cu.sci.lambdaserver.department.Department;
import com.cu.sci.lambdaserver.department.DepartmentRepository;
import com.cu.sci.lambdaserver.location.Location;
import com.cu.sci.lambdaserver.location.LocationRepository;
import com.cu.sci.lambdaserver.storage.StorageFile;
import com.cu.sci.lambdaserver.storage.StorageFileRepository;
import com.cu.sci.lambdaserver.storage.cloudinary.CloudinaryUploadServiceOptions;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.UserRepository;
import com.cu.sci.lambdaserver.utils.dto.MessageResponse;
import com.cu.sci.lambdaserver.utils.enums.FileType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Qualifier("storage-cloudinary")
public class StorageService implements IStorageService {

    @Qualifier("cloudinary-image-uploader")
    private final IImageUploaderService<CloudinaryUploadServiceOptions> imageUploaderService;
    private final DepartmentRepository departmentRepository;
    private final StorageFileRepository storageFileRepository;
    private final LocationRepository locationRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public MessageResponse uploadDepartmentImage(String departmentCode, MultipartFile image) {
        Department department = departmentRepository.findDepartmentByCodeIgnoreCase(departmentCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Department not found"));

        String publicId = "department_" + departmentCode + "_" + UUID.randomUUID();
        CloudinaryUploadServiceOptions options = CloudinaryUploadServiceOptions
                .builder()
                .folder("departments")
                .publicId(publicId)
//                .transformation(new Transformation().quality(60))
                .build();

        final String url = uploadImage(image, options);
        department.setImage(url);
        departmentRepository.save(department);
        return new MessageResponse("Image uploaded successfully");
    }

    @Override
    public MessageResponse uploadLocationImage(Long locationId, MultipartFile image) {
        Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found"));


        String publicId = "location_" + locationId + "_" + UUID.randomUUID();
        CloudinaryUploadServiceOptions options = CloudinaryUploadServiceOptions
                .builder()
                .folder("locations")
                .publicId(publicId)
//                .transformation(new Transformation().quality(60))
                .build();

        final String url = uploadImage(image, options);
        location.setImage(url);
        locationRepository.save(location);
        return new MessageResponse("Image uploaded successfully");
    }

    @Override
    public MessageResponse uploadCourseImage(String courseCode, MultipartFile image) {
        Course course = courseRepository.findByCodeIgnoreCase(courseCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found"));


        String publicId = "course_" + courseCode + "_" + UUID.randomUUID();
        CloudinaryUploadServiceOptions options = CloudinaryUploadServiceOptions
                .builder()
                .folder("courses")
                .publicId(publicId)
                .build();

        final String url = uploadImage(image, options);
        course.setImage(url);
        courseRepository.save(course);
        return new MessageResponse("Image uploaded successfully");
    }


    @Override
    public MessageResponse uploadUserImage(User user, MultipartFile image) {
        if (user == null) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");

        String publicId = "user_" + user.getUsername() + "_" + UUID.randomUUID();
        CloudinaryUploadServiceOptions options = CloudinaryUploadServiceOptions
                .builder()
                .folder("users")
                .publicId(publicId)
                .build();

        final String url = uploadImage(image, options);
        user.setProfilePicture(url);
        userRepository.save(user);
        return new MessageResponse("Image uploaded successfully");
    }

    @Override
    public MessageResponse uploadUserImage(String username, MultipartFile image) {
        User user = userRepository.findByUsernameIgnoreCase(username)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found")
                );
        return uploadUserImage(user, image);
    }

    @Override
    public MessageResponse deleteUserImage(User user) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public MessageResponse deleteDepartmentImage(String departmentCode) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public MessageResponse deleteLocationImage() {
        throw new UnsupportedOperationException("Not implemented yet");
    }


    private String uploadImage(MultipartFile image, CloudinaryUploadServiceOptions options) {
        try {
            final String url = imageUploaderService.uploadImage(image, options);
            // save image file to storage
            storageFileRepository.save(
                    StorageFile.builder()
                            .url(url)
                            .fileType(FileType.IMAGE)
                            .build());
            return url;
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error uploading image");
        }
    }
}
