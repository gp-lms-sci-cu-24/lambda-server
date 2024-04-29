package com.cu.sci.lambdaserver.storage.service;

import org.springframework.web.multipart.MultipartFile;

public interface IimageUploadService {

    String uploadImage(MultipartFile image, String folder, String customPublicId);

    String uploadUserImage(MultipartFile image);

    void deleteImage(String publicId);

    void deleteUserImage();
}
