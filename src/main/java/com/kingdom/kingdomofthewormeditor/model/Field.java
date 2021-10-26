package com.kingdom.kingdomofthewormeditor.model;

import java.util.*;

public class Field<T> implements FieldOperations<T> {

    private final Map<Integer, Tile<T>> tiles;

    public Field() {
        tiles = new HashMap<>();
    }

    @Override
    public Optional<Tile<T>> getTileAtXY(T x, T y) {
        Tile<T> tile = tiles.get(Objects.hash(x, y));
        return tile != null ? Optional.of(tile) : Optional.empty();
    }

    @Override
    public void addTileAtXY(T x, T y, TileProperties properties) {
        tiles.put(Objects.hash(x, y), new Tile<>(new Vector2View<>(x, y), properties));
    }

    public void addTileAtXY(T x, T y) {
        addTileAtXY(x, y, new TileProperties());
    }

    @Override
    public void removeTileAtXY(T x, T y) {
        tiles.remove(Objects.hash(x, y));
    }

    public Collection<Tile<T>> getTiles() {
        return tiles.values();
    }
}
