package com.luxoft.chatbot.keyboard.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class KeyboardDTO {

    private String name;
    private int buttonsInARow;
    private List<ButtonDTO> buttons;
}
