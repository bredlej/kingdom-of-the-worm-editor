package com.kingdom.kingdomofthewormeditor.model;

import java.util.Optional;

public interface FieldOperations<T> {
    Optional<Tile<T>> getTileAtXY(T x, T y);
    void addTileAtXY(T x, T y, TileProperties properties);
    void removeTileAtXY(T x, T y);
}
