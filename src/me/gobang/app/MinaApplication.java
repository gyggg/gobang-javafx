package me.gobang.app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public abstract class MinaApplication extends Application {
    public void stop()
            throws Exception {
        super.stop();
    }

    public void start(Stage primaryStage)
            throws Exception {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                ProgramManager.closeMina();
            }
        });
    }
}
