package com.kingdom.kingdomofthewormeditor.views.impl;

import com.kingdom.kingdomofthewormeditor.Config;
import com.kingdom.kingdomofthewormeditor.util.CustomEventBus;
import com.kingdom.kingdomofthewormeditor.model.Field;
import com.kingdom.kingdomofthewormeditor.model.Tile;
import com.kingdom.kingdomofthewormeditor.model.TileProperties;
import com.kingdom.kingdomofthewormeditor.model.Vector2View;
import com.kingdom.kingdomofthewormeditor.model.components.FloorComponent;
import com.kingdom.kingdomofthewormeditor.views.api.CanvasOperations;
import com.kingdom.kingdomofthewormeditor.views.events.TileEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class FieldCanvas extends Canvas implements CanvasOperations {

    private final Field field;

    public FieldCanvas() {
        field = new Field();
    }

    @FXML
    public void initialize() {
        render();
    }

    @FXML
    public void handleOnMouseOver(MouseEvent mouseEvent) {
        render();
        paintCursor(mouseEvent);
    }

    @FXML
    public void handleOnMouseClicked(MouseEvent mouseEvent) {
        var fieldPosition = mouseToFieldPosition(mouseEvent.getX(), mouseEvent.getY());

        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            handleFieldLeftClick(fieldPosition);
        } else {
            handleFieldRightClick(fieldPosition);
        }
        render();
    }

    @Override
    public void addTile(int x, int y, TileProperties properties) {
        if (field.getTileAtXY(x, y).isEmpty()) {
            var tile = new Tile(new Vector2View(x, y), properties);
            field.addTileAtXY(x, y, tile);
            CustomEventBus.getInstance().fireEvent(new TileEvent(tile, TileEvent.TILE_ADDED));
        }
    }

    @Override
    public void removeTile(Tile tile) {
        field.removeTileAtXY(tile.position().x(), tile.position().y());
        CustomEventBus.getInstance().fireEvent(new TileEvent(tile, TileEvent.TILE_REMOVED));
    }

    @Override
    public void render() {
        drawGrid();
        drawTiles();
    }

    private void handleFieldLeftClick(final Vector2View fieldPosition) {
        field.getTileAtXY(fieldPosition.x(), fieldPosition.y())
                .ifPresentOrElse(this::selectTile, () -> {
                    var properties = new TileProperties();
                    properties.getComponents().add(new FloorComponent());
                    addTile(fieldPosition.x(), fieldPosition.y(), properties);

                    //noinspection OptionalGetWithoutIsPresent
                    selectTile(field.getTileAtXY(fieldPosition.x(), fieldPosition.y()).get());
                });
    }

    private void handleFieldRightClick(Vector2View fieldPosition) {
        field.getTileAtXY(fieldPosition.x(), fieldPosition.y())
                .ifPresent(this::removeTile);
    }

    private void drawTiles() {
        field.getTiles()
                .forEach(this::paintTile);
    }

    private void paintTile(Tile integerTile) {
        var gc = getGraphicsContext2D();
        gc.setFill(Color.GREENYELLOW);
        var fieldPosition = integerTile.position();
        gc.fillRect(fieldPosition.x() * Config.TILE_SIZE, fieldPosition.y() * Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
    }

    private void drawGrid() {
        var sceneCanvas = this;
        var gc = sceneCanvas.getGraphicsContext2D();
        gc.clearRect(0, 0, sceneCanvas.getWidth(), sceneCanvas.getHeight());
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1);
        sceneCanvas.setWidth(800);
        sceneCanvas.setHeight(800);
        for (int x = 0; x < sceneCanvas.getWidth(); x += Config.TILE_SIZE) {
            gc.strokeLine(x, 0, x, sceneCanvas.getHeight());
        }
        for (int y = 0; y < sceneCanvas.getHeight(); y += Config.TILE_SIZE) {
            gc.strokeLine(0, y, sceneCanvas.getWidth(), y);
        }
    }

    private void paintCursor(MouseEvent mouseEvent) {
        var sceneCanvas = this;
        var gc = sceneCanvas.getGraphicsContext2D();
        gc.setFill(Color.GREENYELLOW);
        gc.setStroke(Color.GREENYELLOW);
        gc.setLineWidth(2);
        var fieldPosition = mouseToFieldPosition(mouseEvent.getX(), mouseEvent.getY());
        gc.strokeRect(fieldPosition.x() * Config.TILE_SIZE, fieldPosition.y() * Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
        gc.setLineWidth(1);
    }

    private Vector2View mouseToFieldPosition(double mouseX, double mouseY) {
        return new Vector2View((int) (mouseX / Config.TILE_SIZE), (int) (mouseY / Config.TILE_SIZE));
    }

    private void selectTile(Tile tile) {
        CustomEventBus.getInstance().fireEvent(new TileEvent(tile, TileEvent.TILE_SELECTED));
    }
}
