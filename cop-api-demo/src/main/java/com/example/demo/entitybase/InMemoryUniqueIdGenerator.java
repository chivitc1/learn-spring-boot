package com.example.demo.entitybase;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class InMemoryUniqueIdGenerator implements UniqueIdGenerator<UUID> {

    @Override
    public UUID nextUniqueId() {
        return UUID.randomUUID();
    }
}
