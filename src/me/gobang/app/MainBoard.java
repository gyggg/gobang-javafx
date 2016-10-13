package me.gobang.app;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainBoard extends MinaApplication {
    public void start(Stage primaryStage)
            throws Exception {
        super.start(primaryStage);
        Parent root = (Parent) FXMLLoader.load(getClass().getResource("main.fxml"));
        String mode;
        if (ProgramManager.isService)
            mode = "（服务端）";
        else
            mode = "（客户端）";
        primaryStage.setTitle("五子棋" + mode);
        primaryStage.setScene(new Scene(root, 1000.0D, 1000.0D));

        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
