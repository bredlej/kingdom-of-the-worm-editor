package com.kingdom.kingdomofthewormeditor;

import com.kingdom.kingdomofthewormeditor.model.Field;
import com.kingdom.kingdomofthewormeditor.model.Tile;
import com.kingdom.kingdomofthewormeditor.model.TileProperties;
import com.kingdom.kingdomofthewormeditor.model.Vector2View;
import com.kingdom.kingdomofthewormeditor.model.components.ComponentBase;
import com.kingdom.kingdomofthewormeditor.model.components.ComponentRoot;
import com.kingdom.kingdomofthewormeditor.model.components.FloorComponent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class KingdomEditorController {
    @FXML
    public TextField tilePosition;
    @FXML
    public TreeView<ComponentBase> componentTree;
    @FXML
    Canvas sceneCanvas;

    private final Field<Integer> field;

    public KingdomEditorController() {
        field = new Field<>();
    }
    @FXML
    public void initialize() {
        redrawCanvas();
    }

    private void redrawCanvas() {
        drawGrid();
        drawTiles();
    }

    private void drawTiles() {
        field.getTiles().forEach(this::paintTile);
    }

    private void paintTile(Tile<Integer> integerTile) {
        var gc = sceneCanvas.getGraphicsContext2D();
        gc.setFill(Color.GREENYELLOW);
        var fieldPosition = integerTile.position();
        gc.fillRect(fieldPosition.x() * Config.TILE_SIZE, fieldPosition.y() * Config.TILE_SIZE, Config.TILE_SIZE, Config.TILE_SIZE);
    }

    private void drawGrid() {
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

    @FXML
    public void handleOnMouseOver(MouseEvent mouseEvent) {
        redrawCanvas();
        describeTileUnderCursor(mouseEvent);
        paintCursor(mouseEvent);
    }

    private void describeTileUnderCursor(MouseEvent mouseEvent) {
        var fieldPosition =mouseToFieldPosition(mouseEvent.getX(), mouseEvent.getY());
        field.getTileAtXY(fieldPosition.x(), fieldPosition.y()).ifPresentOrElse(tile -> {
            tilePosition.setText(String.format("%s, %s", fieldPosition.x(), fieldPosition.y()));
        }, () -> {tilePosition.setText("");});

    }

    private void paintCursor(MouseEvent mouseEvent) {
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

    public void handleOnMouseClicked(MouseEvent mouseEvent) {
        var fieldPosition = mouseToFieldPosition(mouseEvent.getX(), mouseEvent.getY());

        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            handleFieldLeftClick(fieldPosition);
        }
        else {
            field.removeTileAtXY(fieldPosition.x(), fieldPosition.y());
        }
        describeTileUnderCursor(mouseEvent);
        redrawCanvas();
    }

    private void handleFieldLeftClick(final Vector2View<Integer> fieldPosition) {
        if (field.getTileAtXY(fieldPosition.x(), fieldPosition.y()).isEmpty()) {
            var properties = new TileProperties();
            properties.getComponents().add(new FloorComponent());
            field.addTileAtXY(fieldPosition.x(), fieldPosition.y(), properties);
        }
        selectTileAtXY(field.getTileAtXY(fieldPosition.x(), fieldPosition.y()).get());
    }

    private void selectTileAtXY(Tile<Integer> integerTile) {
        TreeItem<ComponentBase> root = new TreeItem<>(new ComponentRoot());
        root.setExpanded(true);
        integerTile.properties().getComponents().forEach(component -> {
            root.getChildren().add(new TreeItem<>(component));
        });
        componentTree.setRoot(root);
        componentTree.setShowRoot(false);
    }
}
