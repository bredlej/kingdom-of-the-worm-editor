package com.kingdom.kingdomofthewormeditor.controller;

import com.kingdom.kingdomofthewormeditor.views.impl.ComponentTree;
import com.kingdom.kingdomofthewormeditor.views.impl.FieldCanvas;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

public class MainController {
    @FXML
    TextField tilePositionTextField;
    @FXML
    ComponentTree componentTree;
    @FXML
    FieldCanvas fieldCanvas;

    public MainController() {}

    @FXML
    public void initialize() {
        fieldCanvas.initialize();
    }
    @FXML
    public void handleCanvasOnMouseOver(MouseEvent mouseEvent) {
        fieldCanvas.handleOnMouseOver(mouseEvent);
    }

    @FXML
    public void handleCanvasOnMouseClicked(MouseEvent mouseEvent) {
        fieldCanvas.handleOnMouseClicked(mouseEvent);
    }

}
