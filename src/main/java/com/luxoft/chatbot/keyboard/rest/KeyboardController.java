package com.luxoft.chatbot.keyboard.rest;

import com.luxoft.chatbot.keyboard.dao.KeyboardRepository;
import com.luxoft.chatbot.keyboard.model.dto.KeyboardDTO;
import com.luxoft.chatbot.keyboard.model.entity.Keyboard;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/keyboard")
public class KeyboardController {

    private final KeyboardRepository keyboardRepository;

    private KeyboardController(KeyboardRepository keyboardRepository) {
        this.keyboardRepository = keyboardRepository;
    }

    @PostMapping("/new")
    public Mono<Keyboard> createButton(@RequestBody KeyboardDTO keyboardDTO) {

        Keyboard keyboard = new Keyboard(keyboardDTO.getName(),
                keyboardDTO.getButtonsInARow(),
                keyboardDTO.getButtons());

        return keyboardRepository.save(keyboard);
    }

}
