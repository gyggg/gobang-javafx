package me.gobang.app;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import me.gobang.socket.MinaClient;
import me.gobang.socket.MinaService;

public class ProgramManager extends Application {
    public static Login login = new Login();
    public static MainBoard mainBoard = new MainBoard();
    public static Stage nowStage;
    public static MinaService minaService = new MinaService();
    public static MinaClient minaClient = new MinaClient();
    public static boolean isService;

    public static Login getLogin() {
        return login;
    }

    public static void setLogin(Login login) {
        login = login;
    }

    public static MainBoard getMainBoard() {
        return mainBoard;
    }

    public static void setMainBoard(MainBoard mainBoard) {
        mainBoard = mainBoard;
    }

    public static Stage getNowStage() {
        return nowStage;
    }

    public static void setNowStage(Stage nowStage) {
        nowStage = nowStage;
    }

    public static MinaService getMinaService() {
        return minaService;
    }

    public static void setMinaService(MinaService minaService) {
        minaService = minaService;
    }

    public static MinaClient getMinaClient() {
        return minaClient;
    }

    public static void setMinaClient(MinaClient minaClient) {
        minaClient = minaClient;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void closeMina() {
        minaClient.close();
        minaService.close();
        System.out.println("mina closed");
        minaService = new MinaService();
        minaClient = new MinaClient();
    }

    public void start(Stage primaryStage) throws Exception {
        nowStage = primaryStage;
        login.start(primaryStage);
    }

    public static void changeWindow(Application newWindow) {
        try {
            Platform.runLater(new Runnable() {
                public void run() {
                    ProgramManager.nowStage.close();
                    ProgramManager.nowStage = new Stage();
                    try {
                        newWindow.start(ProgramManager.nowStage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showMessage(String message, String title) {
        Platform.runLater(new Runnable() {
            public void run() {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK);
                alert.initOwner(ProgramManager.nowStage);
                alert.show();
            }
        });
    }

    public static void showError(String error) {
        Platform.runLater(new Runnable() {
            public void run() {
                Alert alert = new Alert(Alert.AlertType.ERROR, error, ButtonType.OK);
                alert.initOwner(ProgramManager.nowStage);
                alert.show();
            }
        });
    }
}
