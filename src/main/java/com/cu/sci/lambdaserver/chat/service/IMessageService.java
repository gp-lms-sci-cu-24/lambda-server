package com.cu.sci.lambdaserver.chat.service;

import com.cu.sci.lambdaserver.chat.dtos.SendMessageDto;
import com.cu.sci.lambdaserver.chat.entites.Message;

public interface IMessageService {

    Message sendMessage(SendMessageDto sendMessage);
}
