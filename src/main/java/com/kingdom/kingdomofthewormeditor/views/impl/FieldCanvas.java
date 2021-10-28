package com.kingdom.kingdomofthewormeditor.views.impl;

import com.kingdom.kingdomofthewormeditor.Config;
import com.kingdom.kingdomofthewormeditor.util.CustomEventBus;
import com.kingdom.kingdomofthewormeditor.model.Field;
import com.kingdom.kingdomofthewormeditor.model.Tile;
import com.kingdom.kingdomofthewormeditor.model.TileProperties;
import com.kingdom.kingdomofthewormeditor.model.Vector2View;
import com.kingdom.kingdomofthewormeditor.model.components.FloorComponent;
import com.kingdom.kingdomofthewormeditor.views.events.TileEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class FieldCanvas extends Canvas {

    private final Field field;

    public FieldCanvas() {
        field = new Field();
    }

    @FXML
    public void initialize() {
        redrawCanvas();
    }

    @FXML
    public void handleOnMouseOver(MouseEvent mouseEvent) {
        redrawCanvas();
        describeTileUnderCursor(mouseEvent);
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
        describeTileUnderCursor(mouseEvent);
        redrawCanvas();
    }

    private void handleFieldLeftClick(final Vector2View fieldPosition) {
        if (field.getTileAtXY(fieldPosition.x(), fieldPosition.y()).isEmpty()) {
            var properties = new TileProperties();
            properties.getComponents().add(new FloorComponent());
            var tile = new Tile(fieldPosition, properties);
            field.addTileAtXY(fieldPosition.x(), fieldPosition.y(), tile);
            CustomEventBus.getInstance().fireEvent(new TileEvent(tile, TileEvent.TILE_ADDED));
        }
        selectTileAtXY(field.getTileAtXY(fieldPosition.x(), fieldPosition.y()).get());
    }

    private void handleFieldRightClick(Vector2View fieldPosition) {
        field.getTileAtXY(fieldPosition.x(), fieldPosition.y()).ifPresent(tile -> {
            field.removeTileAtXY(fieldPosition.x(), fieldPosition.y());
            CustomEventBus.getInstance().fireEvent(new TileEvent(tile, TileEvent.TILE_REMOVED));
        });

    }

    private void redrawCanvas() {
        drawGrid();
        drawTiles();
    }

    private void drawTiles() {
        field.getTiles().forEach(this::paintTile);
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
        for (int x = 0; x < sceneCanvas.getWidth(); x += 20) {
            gc.strokeLine(x, 0, x, sceneCanvas.getHeight());
        }
        for (int y = 0; y < sceneCanvas.getHeight(); y += 20) {
            gc.strokeLine(0, y, sceneCanvas.getWidth(), y);
        }
    }

    private void describeTileUnderCursor(MouseEvent mouseEvent) {
        //var tilePositionTextField = controller.getTilePositionTextField();
        /*var fieldPosition = mouseToFieldPosition(mouseEvent.getX(), mouseEvent.getY());

        field.getTileAtXY(fieldPosition.x(), fieldPosition.y())
                .ifPresentOrElse(tile -> tilePositionTextField.setText(String.format("%s, %s", fieldPosition.x(), fieldPosition.y())), () -> tilePositionTextField.setText(""));*/
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

    private void selectTileAtXY(Tile tile) {
        CustomEventBus.getInstance().fireEvent(new TileEvent(tile, TileEvent.TILE_SELECTED));
    }

}
