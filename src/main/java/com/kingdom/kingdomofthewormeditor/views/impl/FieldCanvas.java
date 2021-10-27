package com.kingdom.kingdomofthewormeditor.views.impl;

import com.kingdom.kingdomofthewormeditor.Config;
import com.kingdom.kingdomofthewormeditor.MainController;
import com.kingdom.kingdomofthewormeditor.model.Field;
import com.kingdom.kingdomofthewormeditor.model.Tile;
import com.kingdom.kingdomofthewormeditor.model.TileProperties;
import com.kingdom.kingdomofthewormeditor.model.Vector2View;
import com.kingdom.kingdomofthewormeditor.model.components.FloorComponent;
import com.kingdom.kingdomofthewormeditor.views.api.CanvasOperations;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class FieldCanvas implements CanvasOperations {

    MainController controller;

    private final Field<Integer> field;

    public FieldCanvas(MainController controller) {
        this.field = new Field<>();
        this.controller = controller;
    }

    @Override
    public void initialize() {
        redrawCanvas();
    }

    @Override
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

    private void handleFieldRightClick(Vector2View<Integer> fieldPosition) {
        field.removeTileAtXY(fieldPosition.x(), fieldPosition.y());
        clearComponentTree();
    }

    private void clearComponentTree() {
    }

    @Override
    public void handleOnMouseOver(MouseEvent mouseEvent) {
        redrawCanvas();
        describeTileUnderCursor(mouseEvent);
        paintCursor(mouseEvent);
    }

    private void redrawCanvas() {
        drawGrid();
        drawTiles();
    }

    private void handleFieldLeftClick(final Vector2View<Integer> fieldPosition) {
        if (field.getTileAtXY(fieldPosition.x(), fieldPosition.y()).isEmpty()) {
            var properties = new TileProperties();
            properties.getComponents().add(new FloorComponent());
            field.addTileAtXY(fieldPosition.x(), fieldPosition.y(), properties);
        }
        selectTileAtXY(field.getTileAtXY(fieldPosition.x(), fieldPosition.y()).get());
    }

    private void drawTiles() {
        field.getTiles().forEach(this::paintTile);
    }

    private void paintTile(Tile<Integer> integerTile) {
        var gc = controller.getSceneCanvas().getGraphicsContext2D();
        gc.setFill(Color.GREENYELLOW);
        var fieldPosition = integerTile.position();
        gc.fillRect(fieldPosition.x() * Config.TILE_SIZE, fieldPosition.y() * Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
    }

    private void drawGrid() {
        var sceneCanvas = controller.getSceneCanvas();
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
        var tilePositionTextField = controller.getTilePositionTextField();
        var fieldPosition = mouseToFieldPosition(mouseEvent.getX(), mouseEvent.getY());

        field.getTileAtXY(fieldPosition.x(), fieldPosition.y())
                .ifPresentOrElse(tile -> tilePositionTextField.setText(String.format("%s, %s", fieldPosition.x(), fieldPosition.y())), () -> tilePositionTextField.setText(""));
    }

    private void paintCursor(MouseEvent mouseEvent) {
        var sceneCanvas = controller.getSceneCanvas();
        var gc = sceneCanvas.getGraphicsContext2D();
        gc.setFill(Color.GREENYELLOW);
        gc.setStroke(Color.GREENYELLOW);
        gc.setLineWidth(2);
        var fieldPosition = mouseToFieldPosition(mouseEvent.getX(), mouseEvent.getY());
        gc.strokeRect(fieldPosition.x() * Config.TILE_SIZE, fieldPosition.y() * Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
        gc.setLineWidth(1);
    }

    private Vector2View<Integer> mouseToFieldPosition(double mouseX, double mouseY) {
        return new Vector2View<>((int) (mouseX / Config.TILE_SIZE), (int) (mouseY / Config.TILE_SIZE));
    }

    private void selectTileAtXY(Tile<Integer> tile) {
        // TODO implement event handling, eg. 'TileSelectedEvent'
        controller.getComponentTreeOperations().showComponentsOf(tile);
    }
}
