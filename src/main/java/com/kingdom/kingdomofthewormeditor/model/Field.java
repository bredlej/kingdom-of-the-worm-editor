package com.kingdom.kingdomofthewormeditor.model;

import java.util.*;

public class Field implements FieldOperations {

    private final Map<Integer, Tile> tiles;

    public Field() {
        tiles = new HashMap<>();
    }

    @Override
    public Optional<Tile> getTileAtXY(int x, int y) {
        Tile tile = tiles.get(Objects.hash(x, y));
        return tile != null ? Optional.of(tile) : Optional.empty();
    }

    @Override
    public void addTileAtXY(int x, int y, TileProperties properties) {
        tiles.put(Objects.hash(x, y), new Tile(new Vector2View(x, y), properties));
    }

    @Override
    public void addTileAtXY(int x, int y, Tile tile) {
        tiles.put(Objects.hash(x, y), tile);
    }

    @Override
    public void removeTileAtXY(int x, int y) {
        tiles.remove(Objects.hash(x, y));
    }

    public Collection<Tile> getTiles() {
        return tiles.values();
    }
}
