package com.cu.sci.lambdaserver.chat.dtos;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendMessageDto {

    @NotNull
    private String content;

    @NotNull
    private String receiver;

}
