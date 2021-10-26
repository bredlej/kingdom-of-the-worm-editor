package com.kingdom.kingdomofthewormeditor.model;

public record Tile<T>(Vector2View<T> position, TileProperties properties) {

    @Override
    public String toString() {
        return "Tile{" +
                "position=" + position +
                '}';
    }
}
