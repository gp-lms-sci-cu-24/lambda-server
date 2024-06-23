//package com.cu.sci.lambdaserver.chat.mapper;
//
//import com.cu.sci.lambdaserver.chat.dtos.MessageDto;
//import com.cu.sci.lambdaserver.chat.entites.Message;
//import com.cu.sci.lambdaserver.utils.mapper.config.IMapper;
//import lombok.RequiredArgsConstructor;
//import org.modelmapper.ModelMapper;
//import org.springframework.stereotype.Component;
//
//
//@Component
//@RequiredArgsConstructor
//public class MessageMapper implements IMapper<Message, MessageDto> {
//
//    private final ModelMapper modelMapper;
//
//    @Override
//    public MessageDto mapTo(Message message) {
//        return modelMapper.map(message, MessageDto.class);
//    }
//
//    @Override
//    public Message mapFrom(MessageDto messageDto) {
//        return modelMapper.map(messageDto, Message.class);
//    }
//
//    @Override
//    public Message update(MessageDto messageDto, Message message) {
//        return null;
//    }
//}
