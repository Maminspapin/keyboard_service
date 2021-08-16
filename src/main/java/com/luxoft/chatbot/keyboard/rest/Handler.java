package com.luxoft.chatbot.keyboard.rest;

import com.luxoft.chatbot.keyboard.dao.KeyboardRepository;
import com.luxoft.chatbot.keyboard.model.dto.KeyboardDTO;
import com.luxoft.chatbot.keyboard.model.entity.Keyboard;
import com.luxoft.chatbot.keyboard.util.KeyboardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class Handler {

    private final KeyboardRepository keyboardRepository;
    private final KeyboardMapper keyboardMapper;

    @Autowired
    public Handler(KeyboardRepository keyboardRepository) {
        this.keyboardRepository = keyboardRepository;
        this.keyboardMapper = KeyboardMapper.INSTANCE;
    }

    public Mono<ServerResponse> getAllKeyboards(ServerRequest request) {
        Flux<Keyboard> keyboardFlux = keyboardRepository.findAll();
        return ServerResponse
                .ok()
                .body(BodyInserters.fromPublisher(keyboardFlux, Keyboard.class));
    }

    public Mono<ServerResponse> createKeyboard(ServerRequest request) {
        final Mono<KeyboardDTO> keyboardDTOMono = request.bodyToMono(KeyboardDTO.class);
        return ServerResponse
                .ok()
                .body(BodyInserters.fromPublisher(
                        keyboardDTOMono.map(keyboardMapper::toEntity).flatMap(keyboardRepository::save), Keyboard.class));
    }

    public Mono<ServerResponse> getKeyboardById(ServerRequest request) {
        final String id = request.pathVariable("id");
        final Mono<Keyboard> keyboardDTOMono = keyboardRepository.findById(id);

        return ServerResponse
                .ok()
                .body(BodyInserters.fromPublisher(keyboardDTOMono, Keyboard.class));
    }

    public Mono<ServerResponse> getKeyboardByName(ServerRequest request) {
        final String name = request.queryParam("name").orElse("");
        final Mono<Keyboard> keyboardDTOMono = keyboardRepository.findKeyboardByName(name);

        return ServerResponse
                .ok()
                .body(BodyInserters.fromPublisher(keyboardDTOMono, Keyboard.class));
    }
}
