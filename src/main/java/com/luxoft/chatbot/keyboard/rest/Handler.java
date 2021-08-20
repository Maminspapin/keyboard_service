package com.luxoft.chatbot.keyboard.rest;

import com.luxoft.chatbot.keyboard.dao.KeyboardRepository;
import com.luxoft.chatbot.keyboard.model.dto.KeyboardDTO;
import com.luxoft.chatbot.keyboard.model.entity.Keyboard;
import com.luxoft.chatbot.keyboard.util.KeyboardValidator;
import com.luxoft.chatbot.keyboard.util.KeyboardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.util.UUID;

@Component
public class Handler {

    private final KeyboardRepository keyboardRepository;
    private final KeyboardMapper keyboardMapper;
    private final KeyboardValidator validator;

    public Handler(@Autowired KeyboardRepository keyboardRepository,
                   @Autowired KeyboardMapper keyboardMapper,
                   @Autowired KeyboardValidator validator) {
        this.keyboardRepository = keyboardRepository;
        this.keyboardMapper = keyboardMapper;
        this.validator = validator;
    }

    public Mono<ServerResponse> getAllKeyboards(ServerRequest request) {
        final Flux<Keyboard> keyboardFlux = keyboardRepository.findAll();

        return ServerResponse.ok()
                .body(BodyInserters.fromPublisher(keyboardFlux, Keyboard.class));
    }

    public Mono<ServerResponse> createKeyboard(ServerRequest request) {
        final UUID id = UUID.randomUUID();
        final Mono<KeyboardDTO> keyboardDTOMono = request.bodyToMono(KeyboardDTO.class);

        Mono<Keyboard> responseBody = keyboardDTOMono
                .map(body -> {
                    Errors errors = new BeanPropertyBindingResult(
                            body,
                            KeyboardDTO.class.getName());
                    validator.validate(body, errors);

                    if (errors.getAllErrors().isEmpty()) {
                        Keyboard keyboard = keyboardMapper.toEntity(body);
                        keyboard.setId(id);

                        return keyboard;
                    } else {
                        throw new ResponseStatusException(
                                HttpStatus.BAD_REQUEST,
                                errors.getAllErrors().toString());
                    }
                })
                .flatMap(keyboardRepository::save);

        return ServerResponse.ok()
                .body(responseBody, Keyboard.class);
    }

    public Mono<ServerResponse> getKeyboardById(ServerRequest request) {
        final UUID id = UUID.fromString(request.pathVariable("id"));
        final Mono<Keyboard> keyboardMono = keyboardRepository.findById(id);

        return ServerResponse.ok()
                .body(BodyInserters.fromPublisher(keyboardMono, Keyboard.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getKeyboardByName(ServerRequest request) {
        final String name = request.queryParam("name").orElse("");
        final Mono<Keyboard> keyboardDTOMono = keyboardRepository.findKeyboardByName(name);

        return ServerResponse.ok()
                .body(BodyInserters.fromPublisher(keyboardDTOMono, Keyboard.class))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> patchKeyboard(ServerRequest request) {
        final UUID id = UUID.fromString(request.pathVariable("id"));
        final Mono<Keyboard> keyboardMono = keyboardRepository.findById(id);
        final Mono<KeyboardDTO> keyboardDTOMono = request.bodyToMono(KeyboardDTO.class);
        final Mono<Tuple2<Keyboard, KeyboardDTO>> result = keyboardMono.zipWith(keyboardDTOMono);

        return ServerResponse.ok()
                .body(BodyInserters.fromPublisher(
                        result.map(e -> {
                            Keyboard current = e.getT1();
                            KeyboardDTO dto = e.getT2();
                            Keyboard data = keyboardMapper.toEntity(dto);

                            if (data.getButtons() != null) current.setButtons(data.getButtons());
                            if (data.getName() != null) current.setName(data.getName());
                            current.setButtonsInARow(data.getButtonsInARow());

                            return current;
                        }).flatMap(keyboardRepository::save), Keyboard.class))
                .switchIfEmpty(ServerResponse.notFound().build());

    }

    public Mono<ServerResponse> deleteKeyboardById(ServerRequest request) {
        final UUID id = UUID.fromString(request.pathVariable("id"));
        final Mono<Keyboard> keyboardMono = keyboardRepository.findById(id);

        return keyboardMono.flatMap(e -> ServerResponse.noContent().build(keyboardRepository.deleteById(id)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}