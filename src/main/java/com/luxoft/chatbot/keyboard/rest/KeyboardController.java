package com.luxoft.chatbot.keyboard.rest;

import com.luxoft.chatbot.keyboard.dao.KeyboardRepository;
import com.luxoft.chatbot.keyboard.model.dto.ButtonDTO;
import com.luxoft.chatbot.keyboard.model.dto.KeyboardDTO;
import com.luxoft.chatbot.keyboard.model.entity.Keyboard;
import com.luxoft.chatbot.keyboard.utils.KeyboardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/keyboard")
public class KeyboardController {

    private final KeyboardRepository keyboardRepository;
    private final KeyboardMapper keyboardMapper;

    @Autowired
    private KeyboardController(KeyboardRepository keyboardRepository) {
        this.keyboardRepository = keyboardRepository;
        this.keyboardMapper = KeyboardMapper.INSTANCE;
    }

    @PostMapping("/new")
    public Mono<Keyboard> createKeyboard(@RequestBody KeyboardDTO keyboardDTO) {

        Keyboard keyboard = keyboardMapper.toEntity(keyboardDTO);
        return keyboardRepository.save(keyboard);
    }

    @GetMapping("/all")
    public Flux<Keyboard> getAllKeyboards() {
        return keyboardRepository.findAll();
    }

    @GetMapping("/{id}") // TODO неправильно, (формат id?)
    public Mono<Keyboard> getKeyboardById(@PathVariable String id) {
        return keyboardRepository.findById(id).defaultIfEmpty(new Keyboard("no keyboard", 0, null));
    }

    @GetMapping("/")
    public Mono<Keyboard> getKeyboardByName(@RequestParam(value = "name") String name) {
        return keyboardRepository.findKeyboardByName(name).defaultIfEmpty(new Keyboard("no keyboard", 0, null));
    }

    @PatchMapping("/{name}")
    public Mono<Keyboard> addButtons(@PathVariable String name, @RequestBody List<ButtonDTO> buttons) {

        Mono<Keyboard> keyboard = keyboardRepository
                .findKeyboardByName(name)
                .map(e -> {
                    List<ButtonDTO> currentButtons = e.getButtons();
                    currentButtons.addAll(buttons);
                    e.setButtons(currentButtons);

                    return e;
                })
                .flatMap(keyboardRepository::save);

        keyboard.subscribe(System.out::print);

        return keyboard;
    }

    // removeButtons, update buttons, update

}
