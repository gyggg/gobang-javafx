package me.gobang.socket;

import me.gobang.model.Position;
import me.gobang.utils.JsonTool;

public class Message {
    int color;
    Position position;
    int msgId;
    public static final int GAME_INIT = 0;
    public static final int SET_CHESS = 1;

    public int getMsgId() {
        return this.msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public int getColor() {
        return this.color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String toString() {
        return JsonTool.objToJsonString(this);
    }
}