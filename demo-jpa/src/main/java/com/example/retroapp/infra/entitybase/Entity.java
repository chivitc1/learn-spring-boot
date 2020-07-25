package com.example.retroapp.infra.entitybase;

public interface Entity<T extends EntityId> {
    T getId();
}
