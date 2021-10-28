package com.kingdom.kingdomofthewormeditor.model;

import java.util.Optional;

public interface FieldOperations {
    Optional<Tile> getTileAtXY(int x, int y);
    void addTileAtXY(int x, int y, TileProperties properties);
    void addTileAtXY(int x, int y, Tile tile);
    void removeTileAtXY(int x, int y);
}
