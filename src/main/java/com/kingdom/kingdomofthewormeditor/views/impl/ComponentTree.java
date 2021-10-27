package com.kingdom.kingdomofthewormeditor.views.impl;

import com.kingdom.kingdomofthewormeditor.MainController;
import com.kingdom.kingdomofthewormeditor.model.Tile;
import com.kingdom.kingdomofthewormeditor.model.components.ComponentBase;
import com.kingdom.kingdomofthewormeditor.model.components.ComponentRoot;
import com.kingdom.kingdomofthewormeditor.views.api.ComponentTreeOperations;
import javafx.scene.control.TreeItem;

public class ComponentTree implements ComponentTreeOperations {

    MainController controller;

    public ComponentTree(MainController controller) {
        this.controller = controller;
    }

    @Override
    public void showComponentsOf(Tile<Integer> tile) {
        updateComponentTree(tile);
    }

    private void updateComponentTree(Tile<Integer> tile) {
        TreeItem<ComponentBase> root = new TreeItem<>(new ComponentRoot());
        root.setExpanded(true);
        tile.properties().getComponents().forEach(component -> root.getChildren().add(new TreeItem<>(component)));

        var componentTree = controller.getComponentTree();
        componentTree.setRoot(root);
        componentTree.setShowRoot(false);
    }
}
