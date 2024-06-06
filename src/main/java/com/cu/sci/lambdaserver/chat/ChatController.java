package com.cu.sci.lambdaserver.chat;

import com.cu.sci.lambdaserver.chat.dtos.ChatMessage;
import com.cu.sci.lambdaserver.chat.entites.Message;
import com.cu.sci.lambdaserver.chat.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChatController {

    private final MessageService messageService;
    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chat")
    public void sendMassage(ChatMessage sendMessage) {
        Message message = messageService.sendMessage(sendMessage);
        messagingTemplate.convertAndSendToUser(message.getReceiver().getUsername(), "/queue/messages", message);
    }

}
