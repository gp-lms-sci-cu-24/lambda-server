package com.cu.sci.lambdaserver.imageupload.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.user.User;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ImageUploadService implements IimageUploadService {

    @Resource
    private Cloudinary cloudinary;


    private final IAuthenticationFacade authenticationFacade;


    /**
     * Upload an image
     * Save image's url in user
     * @param image the image to upload
     * @return the url of the uploaded image
     */
    @Override
    public String uploadImage(MultipartFile image) {
        try{
            //get the current user
            User user = authenticationFacade.getAuthenticatedUser();

            //define options
            Map<Object, Object> options = new HashMap<>();
            options.put("folder","users");
            options.put("transformation",
                    new Transformation()
                            .quality(80)
                            .width(600)
                            .height(600)
                            .fetchFormat("png"));

            //upload the image
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), options);
            String imageUplodedUrl = (String) uploadResult.get("public_id");
            String url = cloudinary.url().secure(true).generate(imageUplodedUrl) ;

            //save the image url in the user
            user.setProfilePicture(url);

            return url ;

        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.CONFLICT,"Upload image failed");
        }
    }

}
