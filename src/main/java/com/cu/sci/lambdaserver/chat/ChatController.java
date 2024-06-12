package com.cu.sci.lambdaserver.chat;

import com.cu.sci.lambdaserver.chat.dtos.MessageDto;
import com.cu.sci.lambdaserver.chat.dtos.SendMessageDto;
import com.cu.sci.lambdaserver.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chat.sendMassage")
    public void sendMassage(@Payload SendMessageDto sendMessage) {
        System.out.println(sendMessage);
        MessageDto message = messageService.sendMessage(sendMessage);
        messagingTemplate.convertAndSendToUser(message.getReceiver(), "/topic/messages", message);
    }

}
