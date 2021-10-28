package com.kingdom.kingdomofthewormeditor.model;

public record Tile(Vector2View position, TileProperties properties) {

    @Override
    public String toString() {
        return "Tile{" +
                "position=" + position +
                '}';
    }
}
