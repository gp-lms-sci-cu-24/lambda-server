package com.cu.sci.lambdaserver.chat.service;


import com.cu.sci.lambdaserver.auth.security.IAuthenticationFacade;
import com.cu.sci.lambdaserver.chat.dtos.MessageDto;
import com.cu.sci.lambdaserver.chat.dtos.SendMessageDto;
import com.cu.sci.lambdaserver.chat.entites.ChatRoom;
import com.cu.sci.lambdaserver.chat.entites.Message;
import com.cu.sci.lambdaserver.chat.mapper.MessageMapper;
import com.cu.sci.lambdaserver.chat.repository.MessageRepository;
import com.cu.sci.lambdaserver.user.User;
import com.cu.sci.lambdaserver.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MessageService implements IMessageService  {

    private final MessageRepository messageRepository;
    private final IAuthenticationFacade authenticationFacade;
    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final MessageMapper messageMapper;



    @Override
    public MessageDto sendMessage(SendMessageDto sendMessage) {

        User sender =  authenticationFacade.getAuthenticatedUser();
        User receiver = userService.loadUserByUsername(sendMessage.getReceiver());
        ChatRoom chatRoom = chatRoomService.createOrFindChatRoom(sender.getUsername(), receiver.getUsername());

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(sendMessage.getContent());
        message.setChatRoom(chatRoom);

        Message savedMessage =  messageRepository.save(message) ;

        MessageDto messageDto = MessageDto
                .builder()
                .sender(savedMessage.getSender().getUsername())
                .content(savedMessage.getContent())
                .receiver(savedMessage.getReceiver().getUsername())
                .build();

        return messageDto ;

    }
}
