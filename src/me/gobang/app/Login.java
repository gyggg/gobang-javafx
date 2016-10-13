package me.gobang.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends MinaApplication {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        super.start(primaryStage);
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("五子棋登录");
        primaryStage.setScene(new Scene(root, 400.0D, 400.0D));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
