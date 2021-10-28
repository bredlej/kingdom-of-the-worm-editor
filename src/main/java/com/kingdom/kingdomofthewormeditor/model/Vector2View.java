package com.kingdom.kingdomofthewormeditor.model;

public record Vector2View(int x, int y) {

    @Override
    public String toString() {
        return "Vector2{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
