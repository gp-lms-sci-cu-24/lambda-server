package com.cu.sci.lambdaserver.storage.service;

import java.util.Map;

/**
 * This abstract class represents the options for file upload services.
 * It provides a method to convert these options into a Map.
 */
public abstract class FileUploadServiceOptions {

    /**
     * Converts the options for the file upload service into a Map.
     *
     * @return A Map representing the options for the file upload service.
     */
    public abstract Map<String,Object> toMap();
}