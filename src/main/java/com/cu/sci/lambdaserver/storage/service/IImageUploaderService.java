package com.cu.sci.lambdaserver.storage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IImageUploaderService<T extends FileUploadServiceOptions>{
    String uploadImage(MultipartFile file, T options) throws IOException;
    boolean deleteImage(T options);
}