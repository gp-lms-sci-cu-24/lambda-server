package com.cu.sci.lambdaserver.upload.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.UserRepository;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageUploadService implements IimageUploadService {

    @Resource
    private Cloudinary cloudinary;


    private final IAuthenticationFacade authenticationFacade;
    private final UserRepository userRepository;


    /**
     * Upload an image to cloudinary
     * @return url
     */
    @Override
    public String uploadImage(MultipartFile image,String folder ,String customPublicId) {
        try{

            //check file type
            if(!image.getContentType().startsWith("image")){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Only image files are allowed");
            }

            //check image size
            if(image.getSize() > 2 * 1024 * 1024){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Image size must be less than 2MB");
            }

            //define options
            Map<Object, Object> options = new HashMap<>();
            options.put("folder", folder);
            options.put("public_id",customPublicId);
            options.put("transformation",
                    new Transformation()
                            .quality(60)
                            .width(600)
                            .height(600)
                            .fetchFormat("png"));

            //upload the image
            Map uploadResult = cloudinary.uploader().upload(image.getBytes(), options);
            String publicId = (String) uploadResult.get("public_id");
            String url = cloudinary.url().secure(true).generate(publicId) ;

            return url ;

        }catch (Exception e){
            throw new RuntimeException(e) ;
        }
    }


    /**
     * Delete an image from cloudinary
     */
    @Override
    public void deleteImage(String publicId) {
        try {
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        }catch (Exception e){
            throw new RuntimeException(e) ;
        }
    }



    /**
     * Upload a user image
     * @return url
     */
    @Override
    public String uploadUserImage(MultipartFile image) {
        //get the authenticated user
        User user = authenticationFacade.getAuthenticatedUser() ;

        //generate a custom public id
        String customPublicId = "user_"+user.getId()+"-"+ UUID.randomUUID().toString() ;

        //upload the image
        String url = uploadImage(image,"users",customPublicId) ;

        //save the image url to the user
        user.setProfilePicture(url);
        userRepository.save(user) ;

        return url ;

    }



    /**
     * Delete a user image
     */
    @Override
    public void deleteUserImage() {
        //get the authenticated user
        User user = authenticationFacade.getAuthenticatedUser() ;

        //check if user try to delete the default image
        String defaultImageUrl = "https://res.cloudinary.com/dyafviw2c/image/upload/users/defaultuserimage.jpg" ;
        if(user.getProfilePicture().equals(defaultImageUrl)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"You can't delete the default image") ;
        }

        //delete the image
        deleteImage(user.getProfilePicture()) ;

        //update the user with the default image
        user.setProfilePicture(defaultImageUrl);
        userRepository.save(user) ;

    }




}
