package com.luxoft.chatbot.keyboard.util;

import com.luxoft.chatbot.keyboard.model.dto.KeyboardDTO;
import com.luxoft.chatbot.keyboard.model.entity.Keyboard;
import org.mapstruct.Mapper;

@Mapper(componentModel="spring")
public interface KeyboardMapper {

    Keyboard toEntity(KeyboardDTO keyboardDTO);
    KeyboardDTO toDTO(Keyboard keyboard);

}
