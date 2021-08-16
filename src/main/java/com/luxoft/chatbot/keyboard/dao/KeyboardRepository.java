package com.luxoft.chatbot.keyboard.dao;

import com.luxoft.chatbot.keyboard.model.entity.Keyboard;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Repository
public interface KeyboardRepository extends ReactiveCrudRepository<Keyboard, UUID> {

    Mono<Keyboard> findKeyboardByName(String name);
}
