//package com.cu.sci.lambdaserver.storage.service;
//
//import com.cloudinary.Transformation;
//import com.cu.sci.lambdaserver.storage.cloudinary.CloudinaryUploadServiceOptions;
//import com.cu.sci.lambdaserver.user.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.stereotype.Service;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.util.UUID;
//
//@Service
//@RequiredArgsConstructor
//@Qualifier("storage-cloudinary")
//public class StorageService implements IStorageService{
//    private final IImageUploaderService<CloudinaryUploadServiceOptions> imageUploaderService;
//
//    @Override
//    public String uploadDepartmentImage(String departmentCode, MultipartFile image) throws IOException {
//        String publicId = "department_" + departmentCode + "_" + UUID.randomUUID();
//        CloudinaryUploadServiceOptions options = CloudinaryUploadServiceOptions
//                .builder()
//                .folder("departments")
//                .publicId(publicId)
//                .transformation(new Transformation().quality(60))
//                .build();
//
//        return imageUploaderService.uploadImage(image, options);
//    }
//
//    @Override
//    public String uploadLocationImage(MultipartFile image) throws IOException {
//        String publicId = "location_" + UUID.randomUUID();
//        CloudinaryUploadServiceOptions options = CloudinaryUploadServiceOptions
//                .builder()
//                .folder("locations")
//                .publicId(publicId)
//                .transformation(new Transformation().quality(60))
//                .build();
//
//        return imageUploaderService.uploadImage(image, options);
//    }
//
//    @Override
//    public boolean deleteDepartmentImage(String departmentCode) {
//        throw new UnsupportedOperationException("Not implemented yet");
//    }
//
//    @Override
//    public boolean deleteLocationImage() {
//        throw new UnsupportedOperationException("Not implemented yet");
//    }
//
//    @Override
//    public String uploadUserImage(User user, MultipartFile image) {
//        throw new UnsupportedOperationException("Not implemented yet");
//    }
//
//    @Override
//    public boolean deleteUserImage(User user) {
//        throw new UnsupportedOperationException("Not implemented yet");
//    }
//}
