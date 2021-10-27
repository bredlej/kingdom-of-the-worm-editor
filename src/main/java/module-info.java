module com.kingdom.kingdomofthewormeditor {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.kingdom.kingdomofthewormeditor to javafx.fxml;
    exports com.kingdom.kingdomofthewormeditor;
    exports com.kingdom.kingdomofthewormeditor.model;
    exports com.kingdom.kingdomofthewormeditor.model.components;
    exports com.kingdom.kingdomofthewormeditor.views.api;
    exports com.kingdom.kingdomofthewormeditor.views.impl;
}
