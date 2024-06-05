package com.cu.sci.lambdaserver.storage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * This interface defines the operations for uploading and deleting files.
 * It is a generic interface where T is a type that extends FileUploadServiceOptions.
 * FileUploadServiceOptions is a class that encapsulates the options for file upload services.
 */
public interface IFileUploaderService<T extends FileUploadServiceOptions>{

    /**
     * Uploads a file.
     *
     * @param file    The file to be uploaded.
     * @param options The options for the file upload service.
     * @return The URL of the uploaded file.
     * @throws IOException If an error occurs during the file upload.
     */
    String uploadFile(MultipartFile file,T options) throws IOException;

    /**
     * Deletes a file.
     *
     * @param options The options for the file upload service.
     * @return true if the file was successfully deleted, false otherwise.
     */
    boolean deleteFile(T options);
}