package com.example.rsa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {
    double x, y ;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));

        Parent root = fxmlLoader.load() ;
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        movableStage(stage, root) ;
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/mainlogo.png"))) ;
        stage.show();
    }

    private void movableStage(Stage stage, Parent root) {
        root.setOnMousePressed(e ->{
            x = e.getSceneX();
            y = e.getSceneY() ;
        });

        root.setOnMouseDragged(e ->{
            stage.setX(e.getScreenX() - x);
            stage.setY(e.getScreenY() - y);
            root.setStyle("-fx-opacity: 0.7 ; -fx-cursor: move ;");
        });

        root.setOnMouseReleased(e->{
            root.setStyle("-fx-opacity: 1.0 ;  -fx-cursor: default ;");
        });
    }

    public static void main(String[] args) {
        launch();
    }
}