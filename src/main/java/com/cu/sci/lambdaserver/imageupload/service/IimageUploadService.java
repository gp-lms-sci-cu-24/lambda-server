package com.cu.sci.lambdaserver.imageupload.service;

import org.springframework.web.multipart.MultipartFile;

public interface IimageUploadService {

    String uploadImage(MultipartFile image);
}
