package com.cu.sci.lambdaserver.upload.service;

import org.springframework.web.multipart.MultipartFile;

public interface IimageUploadService {

    String uploadImage(MultipartFile image,String folder);

    String uploadUserImage(MultipartFile image);
}
