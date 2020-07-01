package com.example.demo.user;

import com.example.demo.entitybase.AbstractEntityId;

import java.util.UUID;

public class UserId extends AbstractEntityId<UUID> {
    protected UserId() {

    }

    public UserId(UUID id) {
        super(id);
    }
}
