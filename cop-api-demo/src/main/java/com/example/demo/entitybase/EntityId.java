package com.example.demo.entitybase;

import java.io.Serializable;

public interface EntityId<T> extends Serializable {
    T getId();

    String asString();
}
