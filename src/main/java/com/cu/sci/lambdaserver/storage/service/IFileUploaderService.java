package com.cu.sci.lambdaserver.storage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface IFileUploaderService<T extends FileUploadServiceOptions>{
    String uploadFile(MultipartFile file,T options) throws IOException;
    boolean deleteFile(T options);
}
