package me.gobang.app;


import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.application.Platform;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogEvent;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import me.gobang.model.Position;
import me.gobang.socket.AbstractMina;
import me.gobang.socket.Message;
import me.gobang.socket.MessageListener;

import me.gobang.socket.SessionListener;
import me.gobang.utils.JsonTool;
import org.apache.mina.core.session.IoSession;

public class MainController
        implements Initializable {

    @FXML
    ImageView board;

    @FXML
    Pane parent;

    @FXML
    Label myName;

    @FXML
    Label opName;

    @FXML
    Label nowTurn;

    @FXML
    TextArea message;

    @FXML
    Button cleanButton;

    @FXML
    Button findEnemyButton;
    private int[][] chesses;
    public static final int WHITE = -1;
    public static final int BLACK = 1;
    private int color;
    private ImageView lastPoint;
    private Random random;
    private AbstractMina mina;
    private boolean boardLock;

    public MainController() {
        this.chesses = new int[19][19];

        this.color = -1;

        this.random = new Random();

        this.boardLock = false;
    }

    @FXML
    public void onMessageClicked() {
    }

    @FXML
    public void clear() {
        this.chesses = new int[19][19];
        if (this.lastPoint != null) {
            this.parent.getChildren().remove(this.lastPoint);
            this.lastPoint = null;
        }
        if (this.parent.getChildren().size() > 2)
            this.parent.getChildren().remove(2, this.parent.getChildren().size());
        setMessage("棋盘已清空");
        this.boardLock = true;
    }

    @FXML
    public void onBoardClicked(MouseEvent event) {
        if (!this.boardLock) {
            System.out.println(event.getX() + ", " + event.getY());
            if (setChess((int) event.getX(), (int) event.getY(), this.color)) {
                this.boardLock = true;
            } else
                showDialog("落子位置不合法", "提示", null);
        }
    }

    @FXML
    public boolean judge(Position p) {
        if (p == null)
            return false;
        Position np = p.clone();
        int flag = 0;
        int times = 0;
        while (checkPos(np)) {
            if (flag == 0) {
                np = np.xplus(1);
                times++;
            } else if (flag == 1) {
                np = np.xplus(-1);
                times++;
            }
            if ((checkPos(np)) &&
                    (times >= 5)) {
                return true;
            }
            if ((flag == 0) && (!checkPos(np))) {
                flag++;
                times--;
                np = p.clone();
            }
            if ((flag == 1) && (!checkPos(np))) {
                if (times >= 5)
                    return true;
                flag = 0;
                times = 0;
                np = p.clone();
            }
        }

        while (checkPos(np)) {
            if (flag == 0) {
                np = np.yplus(1);
                times++;
            } else if (flag == 1) {
                np = np.yplus(-1);
                times++;
            }
            if ((checkPos(np)) &&
                    (times >= 5)) {
                return true;
            }
            if ((flag == 0) && (!checkPos(np))) {
                flag++;
                times--;
                np = p.clone();
            }
            if ((flag == 1) && (!checkPos(np))) {
                if (times >= 5)
                    return true;
                flag = 0;
                times = 0;
                np = p.clone();
            }
        }

        while (checkPos(np)) {
            if (flag == 0) {
                np = np.plus(1, 1);
                times++;
            } else if (flag == 1) {
                np = np.plus(-1, -1);
                times++;
            }
            if ((checkPos(np)) &&
                    (times >= 5)) {
                return true;
            }
            if ((flag == 0) && (!checkPos(np))) {
                flag++;
                times--;
                np = p.clone();
            }
            if ((flag == 1) && (!checkPos(np))) {
                if (times >= 5)
                    return true;
                flag = 0;
                times = 0;
                np = p.clone();
            }
        }

        while (checkPos(np)) {
            if (flag == 0) {
                np = np.plus(1, -1);
                times++;
            } else if (flag == 1) {
                np = np.plus(-1, 1);
                times++;
            }
            if ((checkPos(np)) &&
                    (times >= 5)) {
                return true;
            }
            if ((flag == 0) && (!checkPos(np))) {
                flag++;
                times--;
                np = p.clone();
            }
            if ((flag == 1) && (!checkPos(np))) {
                if (times >= 5)
                    return true;
                flag = 0;
                times = 0;
                np = p.clone();
            }
        }

        return false;
    }

    public boolean checkPos(Position p) {
        if (p == null) {
            return false;
        }
        return this.chesses[p.x][p.y] == p.color;
    }

    private Image getImage(int color) {
        Image i;
        if (color == 1)
            i = new Image(getResourse("res/black.png"));
        else
            i = new Image(getResourse("res/white.png"));
        return i;
    }

    private String getResourse(String path) {
        return getClass().getResource(path).toString();
    }

    private void setChess(Position position) {
        Image i = getImage(position.color);
        ImageView iv = new ImageView();
        int x = (position.x + 1) * 50;
        int y = (position.y + 1) * 50;
        setLastPoint(position.x, position.y);
        iv.setImage(i);
        iv.setFitHeight(50.0D);
        iv.setFitWidth(50.0D);
        iv.setX(x - 25);
        iv.setY(y - 25);
        this.parent.getChildren().add(iv);
        this.chesses[position.x][position.y] = position.color;
        if (judge(position))
            showDialog("你输了", "游戏结束", new Runnable() {
                public void run() {
                    MainController.this.gameover();
                }
            });
    }

    private void gameover() {
        this.boardLock = true;
        clear();
        this.mina.close();
        ProgramManager.changeWindow(ProgramManager.login);
    }

    private boolean setChess(int x, int y, int color) {
        Image i = getImage(color);
        ImageView iv = new ImageView();
        x = getPixel(x);
        y = getPixel(y);
        if ((x == 0) || (y == 0))
            return false;
        if (this.chesses[((x - 50) / 50)][((y - 50) / 50)] != 0) {
            return false;
        }

        iv.setImage(i);
        iv.setFitHeight(50.0D);
        iv.setFitWidth(50.0D);
        iv.setX(x - 25);
        iv.setY(y - 25);
        this.parent.getChildren().add(iv);
        this.chesses[((x - 50) / 50)][((y - 50) / 50)] = color;
        Position position = new Position();
        position.x = ((x - 50) / 50);
        position.y = ((y - 50) / 50);
        position.color = color;
        setLastPoint(position.x, position.y);
        sendPosition(position);
        if (judge(position))
            showDialog("你赢了", "游戏结束", new Runnable() {
                public void run() {
                    MainController.this.gameover();
                }
            });
        return true;
    }

    public void showDialog(String message, String title, Runnable runnable) {
        Dialog dialog = new Dialog();
        dialog.setTitle(title);
        dialog.setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        dialog.setOnCloseRequest(new EventHandler<DialogEvent>() {
            public void handle(DialogEvent event) {
                if (runnable != null)
                    runnable.run();
            }
        });
        dialog.show();
    }

    public void sendPosition(Position position) {
        Message message = new Message();
        message.setMsgId(1);
        message.setPosition(position);
        this.mina.send(message);
    }

    private static int getPixel(int pixel) {
        if ((pixel <= 25) || (pixel >= 975)) {
            return 0;
        }
        if (pixel % 50 > 25) {
            pixel /= 50;
            pixel = (pixel + 1) * 50;
        } else {
            pixel /= 50;
            pixel *= 50;
        }
        return pixel;
    }

    public void setMessage(String message) {
        this.message.setText(this.message.getText() + "\r\n" + message);
    }

    public void setLable(Label lable, String string) {
        Platform.runLater(new Runnable() {
            public void run() {
                lable.setText(string);
            }
        });
    }

    public void setLastPoint(int x, int y) {
        Platform.runLater(new Runnable() {
            public void run() {
                if (MainController.this.lastPoint != null)
                    MainController.this.parent.getChildren().remove(MainController.this.lastPoint);
                MainController.this.lastPoint = new ImageView();
                Image i = new Image(MainController.this.getResourse("res/red_point.png"));
                MainController.this.lastPoint.setImage(i);
                MainController.this.lastPoint.setFitHeight(20.0D);
                MainController.this.lastPoint.setFitWidth(20.0D);
                MainController.this.lastPoint.setX(x * 50 + 40);
                MainController.this.lastPoint.setY(y * 50 + 40);
                MainController.this.parent.getChildren().add(MainController.this.lastPoint);
            }
        });
    }

    private void initLastPoint() {
    }

    public void initialize(URL location, ResourceBundle resources) {
        initLastPoint();
        this.boardLock = true;
        if (ProgramManager.isService) {
            this.mina = ProgramManager.getMinaService();
        } else {
            this.mina = ProgramManager.getMinaClient();
        }
        this.mina.getMinaHandler().setOnMessageRecivedListener(new MyMessageRecivedListener());
        this.mina.getMinaHandler().setOnSessionOpenedListener(null);
        this.mina.getMinaHandler().setOnSessionClosedListener(new MySessionCloseListener());
        gameStart();
    }

    public void onBlackChess() {
        this.color = 1;
        this.boardLock = false;
        ProgramManager.showMessage("您是先手。", "游戏开始。");
    }

    public void onWhiteChess() {
        this.color = -1;
        this.boardLock = true;
        ProgramManager.showMessage("您是后手。", "游戏开始。");
    }

    public void gameStart() {
        if (ProgramManager.isService) {
            if (this.random.nextInt(2) == 0)
                onBlackChess();
            else {
                onWhiteChess();
            }
            Message message = new Message();
            message.setColor(-this.color);
            message.setMsgId(0);
            this.mina.send(message);
        }
    }

    class MyMessageRecivedListener
            implements MessageListener {
        MyMessageRecivedListener() {
        }

        public void onMessaged(Object message) {
            String jsonString = (String) message;
            Message msg = (Message) JsonTool.jsonStringToObj(jsonString, Message.class);
            Platform.runLater(new Runnable() {
                public void run() {
                    switch (msg.getMsgId()) {
                        case Message.GAME_INIT:
                            MainController.this.color = msg.getColor();
                            if (MainController.this.color == 1) {
                                MainController.this.onBlackChess();
                            } else {
                                MainController.this.onWhiteChess();
                            }
                            break;
                        case 1:
                            MainController.this.boardLock = false;
                            MainController.this.setChess(msg.getPosition());
                    }
                }
            });
        }
    }

    class MySessionCloseListener
            implements SessionListener {
        MySessionCloseListener() {
        }

        public void onSessioned(IoSession ioSession) {
            MainController.this.mina.close();
            MainController.this.mina.cleanHandler();
            ProgramManager.changeWindow(ProgramManager.login);
            ProgramManager.showError("链接中断");
        }
    }
}
