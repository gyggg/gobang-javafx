package me.gobang.app;

import java.io.IOException;

import java.net.InetAddress;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;


import me.gobang.socket.SessionListener;
import org.apache.mina.core.session.IoSession;

public class LoginController
        implements Initializable {

    @FXML
    RadioButton createRoom;

    @FXML
    RadioButton joinRoom;

    @FXML
    TextField createIP;

    @FXML
    TextField createPort;

    @FXML
    TextField joinIP;

    @FXML
    TextField joinPort;

    @FXML
    Button okButton;

    @FXML
    Button cancelButton;
    Dialog dialog;
    ToggleGroup group = new ToggleGroup();

    @FXML
    public void onOkClicked() throws Exception {
        if (this.createRoom.isSelected()) {
            becameService();
        } else
            becameClient();
    }

    private void becameService() {
        ProgramManager.getMinaService().getMinaHandler().setOnSessionOpenedListener(new SessionListener() {
            public void onSessioned(IoSession ioSession) {
                Platform.runLater(new Runnable() {
                    public void run() {
                        LoginController.this.dialog.hide();
                    }
                });
                ProgramManager.changeWindow(ProgramManager.mainBoard);
            }
        });
        showDialog();
        ProgramManager.minaService.bind(new Integer(this.joinPort.getText()).intValue());
        ProgramManager.isService = true;
    }

    private void becameClient() {
        ProgramManager.getMinaClient().getMinaHandler().setOnSessionOpenedListener(new SessionListener() {
            public void onSessioned(IoSession ioSession) {
                ProgramManager.changeWindow(ProgramManager.mainBoard);
            }
        });
        String ip = this.joinIP.getText();
        int port = new Integer(this.joinPort.getText()).intValue();
        ProgramManager.minaClient.connect(ip, port);
        ProgramManager.isService = false;
    }

    @FXML
    public void onCancleClicked() throws IOException {
        ProgramManager.closeMina();
    }

    private void initIp() {
        InetAddress ia = null;
        try {
            ia = InetAddress.getLocalHost();
            String localname = ia.getHostName();
            String localip = ia.getHostAddress();
            System.out.println("本机名称是：" + localname);
            this.createIP.setText(localip);
        } catch (Exception e) {
            this.createIP.setText("127.0.0.1");
            e.printStackTrace();
        }
    }

    private void setButtonGroup() {
        this.createRoom.setToggleGroup(this.group);
        this.createRoom.setSelected(true);
        this.joinRoom.setToggleGroup(this.group);
        selectCreateRoom();
    }

    @FXML
    public void selectCreateRoom() {
        this.createPort.setDisable(false);

        this.joinPort.setDisable(true);
        this.joinIP.setDisable(true);
    }

    @FXML
    public void selectJoinRoom() {
        this.createPort.setDisable(true);

        this.joinPort.setDisable(false);
        this.joinIP.setDisable(false);
    }

    public void initialize(URL location, ResourceBundle resources) {
        initIp();
        this.createPort.setText("9100");
        this.joinPort.setText("9100");
        this.createIP.setDisable(true);
        this.joinIP.setText("127.0.0.1");
        initDialog();
        setButtonGroup();
    }

    private void showDialog() {
        if (this.dialog.getOwner() == null)
            this.dialog.initOwner(ProgramManager.nowStage);
        this.dialog.show();
    }

    private void initDialog() {
        this.dialog = new Dialog();
        this.dialog.setTitle("房间开启成功");
        this.dialog.setContentText("等待对手加入中……");
        ButtonType cancleType = new ButtonType("取消", ButtonBar.ButtonData.CANCEL_CLOSE);
        this.dialog.getDialogPane().getButtonTypes().add(cancleType);
        this.dialog.getDialogPane().lookupButton(cancleType).setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                ProgramManager.closeMina();
            }
        });
    }
}
