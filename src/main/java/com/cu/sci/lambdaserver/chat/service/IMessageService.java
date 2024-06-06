package com.cu.sci.lambdaserver.chat.service;

import com.cu.sci.lambdaserver.chat.dtos.MessageDto;
import com.cu.sci.lambdaserver.chat.dtos.SendMessageDto;

public interface IMessageService {

    MessageDto sendMessage(SendMessageDto sendMessage);
}
