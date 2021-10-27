package com.kingdom.kingdomofthewormeditor;

import com.kingdom.kingdomofthewormeditor.model.components.ComponentBase;
import com.kingdom.kingdomofthewormeditor.views.api.CanvasOperations;
import com.kingdom.kingdomofthewormeditor.views.api.ComponentTreeOperations;
import com.kingdom.kingdomofthewormeditor.views.impl.ComponentTree;
import com.kingdom.kingdomofthewormeditor.views.impl.FieldCanvas;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class MainController {
    @FXML
    TextField tilePositionTextField;
    @FXML
    TreeView<ComponentBase> componentTree;
    @FXML
    Canvas sceneCanvas;

    private final CanvasOperations canvasOperations;
    private final ComponentTreeOperations componentTreeOperations;

    public MainController() {
        canvasOperations = new FieldCanvas(this);
        componentTreeOperations = new ComponentTree(this);
    }

    @FXML
    public void initialize() {
        canvasOperations.initialize();
    }

    @FXML
    public void handleCanvasOnMouseOver(MouseEvent mouseEvent) {
        canvasOperations.handleOnMouseOver(mouseEvent);
    }

    @FXML
    public void handleCanvasOnMouseClicked(MouseEvent mouseEvent) {
        canvasOperations.handleOnMouseClicked(mouseEvent);
    }

    public TreeView<ComponentBase> getComponentTree() {
        return componentTree;
    }

    public Canvas getSceneCanvas() {
        return sceneCanvas;
    }

    public TextField getTilePositionTextField() {
        return tilePositionTextField;
    }

    public ComponentTreeOperations getComponentTreeOperations() {
        return componentTreeOperations;
    }
}
