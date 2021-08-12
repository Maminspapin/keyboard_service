package com.luxoft.chatbot.keyboard.model.entity;

import com.luxoft.chatbot.keyboard.model.dto.ButtonDTO;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "keyboard")
public class Keyboard {

    @Id
    private String id;

    @Field("name")
    private String name;

    @Field("buttons_in_a_row")
    private int buttonsInARow;

    @Field("buttons")
    private List<ButtonDTO> buttons;

    @Field("created_at")
    private LocalDateTime createdAt;

    public Keyboard(String name, int buttonsInARow, List<ButtonDTO> buttons) {
        this.name = name;
        this.buttonsInARow = buttonsInARow;
        this.buttons = buttons;
        createdAt = LocalDateTime.now();
    }
}
