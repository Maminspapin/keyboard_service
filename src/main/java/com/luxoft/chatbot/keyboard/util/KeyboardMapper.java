package com.luxoft.chatbot.keyboard.util;

import com.luxoft.chatbot.keyboard.model.dto.KeyboardDTO;
import com.luxoft.chatbot.keyboard.model.entity.Keyboard;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface KeyboardMapper {

    KeyboardMapper INSTANCE = Mappers.getMapper(KeyboardMapper.class);

    Keyboard toEntity(KeyboardDTO keyboardDTO);
    KeyboardDTO toDTO(Keyboard keyboard);

}
