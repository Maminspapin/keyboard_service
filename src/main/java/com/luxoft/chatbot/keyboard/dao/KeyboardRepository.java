package com.luxoft.chatbot.keyboard.dao;

import com.luxoft.chatbot.keyboard.model.entity.Keyboard;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyboardRepository extends ReactiveCrudRepository<Keyboard, Integer> {
}
