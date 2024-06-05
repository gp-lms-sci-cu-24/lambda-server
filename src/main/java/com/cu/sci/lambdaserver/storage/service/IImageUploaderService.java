package com.cu.sci.lambdaserver.storage.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * This interface defines the operations for uploading and deleting images.
 * It is a generic interface where T is a type that extends FileUploadServiceOptions.
 * FileUploadServiceOptions is a class that encapsulates the options for file upload services.
 */
public interface IImageUploaderService<T extends FileUploadServiceOptions>{

    /**
     * Uploads an image.
     *
     * @param file    The image file to be uploaded.
     * @param options The options for the file upload service.
     * @return The URL of the uploaded image.
     * @throws IOException If an error occurs during the file upload.
     */
    String uploadImage(MultipartFile file, T options) throws IOException;

    /**
     * Deletes an image.
     *
     * @param options The options for the file upload service.
     * @return true if the image was successfully deleted, false otherwise.
     */
    boolean deleteImage(T options);
}