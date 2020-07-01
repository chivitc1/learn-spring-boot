package com.example.demo.entitybase;

public interface Entity<T extends EntityId> {
    T getId();
}
