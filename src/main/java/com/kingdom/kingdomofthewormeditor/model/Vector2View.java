package com.kingdom.kingdomofthewormeditor.model;

public record Vector2View<T>(T x, T y) {

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
