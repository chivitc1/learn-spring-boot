package com.example.demojpa.blog.infra.entitybase;

public interface Entity<T extends EntityId> {
    T getId();
}
