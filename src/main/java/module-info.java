module com.kingdom.kingdomofthewormeditor {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.kingdom.kingdomofthewormeditor to javafx.fxml;
    opens com.kingdom.kingdomofthewormeditor.views.impl to javafx.fxml;
    opens com.kingdom.kingdomofthewormeditor.views.api to javafx.fxml;
    opens com.kingdom.kingdomofthewormeditor.views.events to javafx.fxml;
    exports com.kingdom.kingdomofthewormeditor;
    exports com.kingdom.kingdomofthewormeditor.model;
    exports com.kingdom.kingdomofthewormeditor.model.components;
    exports com.kingdom.kingdomofthewormeditor.views.api;
    exports com.kingdom.kingdomofthewormeditor.views.impl;
    exports com.kingdom.kingdomofthewormeditor.util;
    opens com.kingdom.kingdomofthewormeditor.util to javafx.fxml;
    exports com.kingdom.kingdomofthewormeditor.controller;
    opens com.kingdom.kingdomofthewormeditor.controller to javafx.fxml;
}
