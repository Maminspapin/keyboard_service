package com.luxoft.chatbot.keyboard.dao;

import com.luxoft.chatbot.keyboard.model.entity.Keyboard;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface KeyboardRepository extends ReactiveCrudRepository<Keyboard, String> {

    Mono<Keyboard> findKeyboardByName(String name);
}
