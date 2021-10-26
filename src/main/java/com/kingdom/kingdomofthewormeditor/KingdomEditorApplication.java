package com.kingdom.kingdomofthewormeditor;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class KingdomEditorApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(KingdomEditorApplication.class.getResource("kingdom-editor-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1600, 1000);
        stage.setTitle("Kingdom of the Worm - Editor");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
