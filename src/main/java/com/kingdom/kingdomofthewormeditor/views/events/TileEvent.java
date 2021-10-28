package com.kingdom.kingdomofthewormeditor.views.events;

import com.kingdom.kingdomofthewormeditor.util.KingdomEditorEvents;
import com.kingdom.kingdomofthewormeditor.model.Tile;
import javafx.event.Event;
import javafx.event.EventType;

public class TileEvent extends Event {

    public static final EventType<TileEvent> TILE_ADDED = new EventType<>(Event.ANY, KingdomEditorEvents.TILE_ADDED.name());
    public static final EventType<TileEvent> TILE_SELECTED = new EventType<>(Event.ANY, KingdomEditorEvents.TILE_SELECTED.name());
    public static final EventType<TileEvent> TILE_REMOVED = new EventType<>(Event.ANY, KingdomEditorEvents.TILE_REMOVED.name());

    private final Tile tile;

    public TileEvent(Tile tile, EventType<TileEvent> eventType) {
        super(eventType);
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }
}
