package com.kingdom.kingdomofthewormeditor.views.impl;

import com.kingdom.kingdomofthewormeditor.util.CustomEventBus;
import com.kingdom.kingdomofthewormeditor.model.Tile;
import com.kingdom.kingdomofthewormeditor.model.components.ComponentBase;
import com.kingdom.kingdomofthewormeditor.model.components.ComponentRoot;
import com.kingdom.kingdomofthewormeditor.views.api.ComponentTreeOperations;
import com.kingdom.kingdomofthewormeditor.views.events.TileEvent;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.event.WeakEventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class ComponentTree extends TreeView<ComponentBase> implements ComponentTreeOperations, EventHandler<Event> {

    private final Map<EventType<?>, Consumer<Event>> eventHandlers;

    public ComponentTree() {
        eventHandlers = new HashMap<>();
        registerEvents();
    }

    private void registerEvents() {
        CustomEventBus.getInstance().addListener(TileEvent.TILE_SELECTED, new WeakEventHandler<>(this));
        CustomEventBus.getInstance().addListener(TileEvent.TILE_ADDED, new WeakEventHandler<>(this));
        CustomEventBus.getInstance().addListener(TileEvent.TILE_REMOVED, new WeakEventHandler<>(this));

        eventHandlers.put(TileEvent.TILE_SELECTED, event -> handleTileSelected((TileEvent) event));
        eventHandlers.put(TileEvent.TILE_REMOVED, event -> handleTileUnselected());
    }

    @Override
    public void handle(Event event) {
        Optional.ofNullable(eventHandlers.get(event.getEventType())).ifPresent(handler -> handler.accept(event));
    }

    @Override
    public void showComponentsOf(Tile tile) {
        updateComponentTree(tile);
    }

    private void handleTileUnselected() {
        this.setRoot(new TreeItem<>(new ComponentRoot()));
        this.setShowRoot(false);
    }

    private void handleTileSelected(TileEvent event) {
        showComponentsOf(event.getTile());
    }

    private void updateComponentTree(Tile tile) {
        TreeItem<ComponentBase> root = new TreeItem<>(new ComponentRoot());
        root.setExpanded(true);
        tile.properties().getComponents().forEach(component -> root.getChildren().add(new TreeItem<>(component)));

        this.setRoot(root);
        this.setShowRoot(false);
    }


}
