package com.github.javanti.chatgame.model;

import lombok.NonNull;

import java.util.List;
import java.util.Objects;

public record GameQuestion(@NonNull String id, @NonNull String question, @NonNull List<String> answer, int prize) {
    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        GameQuestion that = (GameQuestion) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
