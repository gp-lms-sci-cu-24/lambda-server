package com.cu.sci.lambdaserver.chat.service;


import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.chat.entites.ChatRoom;
import com.cu.sci.lambdaserver.chat.repository.ChatRoomRepository;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService implements IChatRoomService {

    private final ChatRoomRepository chatRoomRepository ;
    private final UserService userService;



    @Override
    public ChatRoom createOrFindChatRoom(String firstUserName, String secondUserName) {

        User firstUser = userService.loadUserByUsername(firstUserName);
        User secondUser = userService.loadUserByUsername(secondUserName);
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByFirstUserAndSecondUser(firstUser, secondUser);

        if(chatRoom.isEmpty()){
            ChatRoom newChatRoom = new ChatRoom();
            newChatRoom.setFirstUser(firstUser);
            newChatRoom.setSecondUser(secondUser);
            return chatRoomRepository.save(newChatRoom);
        }

        return chatRoom.get();
    }
}
