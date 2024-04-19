package com.cu.sci.lambdaserver.user.service;

import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.professor.service.ProfessorService;
import com.cu.sci.lambdaserver.student.service.StudentService;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.UserMapper;
import com.cu.sci.lambdaserver.user.UserRepository;
import com.cu.sci.lambdaserver.user.dto.UserDto;
import com.cu.sci.lambdaserver.utils.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final IAuthenticationFacade authenticationFacade;
    private final StudentService studentService ;
    private final ProfessorService professorService ;
    private final UserMapper userMapper;



    /**
     * Get the state of the current user
     * @return the state of the current user
     */
    public UserDto getMyState() {

       //get the current user
        User user = authenticationFacade.getAuthenticatedUser();

        //check the role of the user
        if (user.getRoles().contains(Role.STUDENT)) {
            return studentService.getStudent(user.getUsername()) ;
        } else if (user.getRoles().contains(Role.PROFESSOR)) {
            return professorService.getProfessor(user.getId()) ;
        } else {
            return userMapper.mapTo(user);
        }

    }
}
