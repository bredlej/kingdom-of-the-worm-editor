package com.kingdom.kingdomofthewormeditor.views.api;

import com.kingdom.kingdomofthewormeditor.model.Tile;
import com.kingdom.kingdomofthewormeditor.model.TileProperties;

public interface CanvasOperations {

    void render();
    void addTile(int x, int y, TileProperties properties);
    void removeTile(Tile tile);
}
