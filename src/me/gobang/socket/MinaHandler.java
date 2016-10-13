package me.gobang.socket;


import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class MinaHandler extends IoHandlerAdapter {
    private MessageListener onMessageRecivedListener;
    private MessageListener onMessageSendedListener;
    private SessionListener onSessionOpenedListener;
    private SessionListener onSessionClosedListener;
    private AbstractMina mina;

    public void cleanListener() {
        this.onMessageRecivedListener = null;
        this.onMessageSendedListener = null;
        this.onSessionClosedListener = null;
        this.onSessionOpenedListener = null;
    }

    public MinaHandler(AbstractMina mina) {
        this.mina = mina;
    }

    public AbstractMina getMina() {
        return this.mina;
    }

    public void setMina(AbstractMina mina) {
        this.mina = mina;
    }

    public MessageListener getOnMessageRecivedListener() {
        return this.onMessageRecivedListener;
    }

    public void setOnMessageRecivedListener(MessageListener onMessageRecivedListener) {
        this.onMessageRecivedListener = onMessageRecivedListener;
    }

    public MessageListener getOnMessageSendedListener() {
        return this.onMessageSendedListener;
    }

    public void setOnMessageSendedListener(MessageListener onMessageSendedListener) {
        this.onMessageSendedListener = onMessageSendedListener;
    }

    public SessionListener getOnSessionOpenedListener() {
        return this.onSessionOpenedListener;
    }

    public void setOnSessionOpenedListener(SessionListener onSessionOpenedListener) {
        this.onSessionOpenedListener = onSessionOpenedListener;
    }

    public SessionListener getOnSessionClosedListener() {
        return this.onSessionClosedListener;
    }

    public void setOnSessionClosedListener(SessionListener onSessionClosedListener) {
        this.onSessionClosedListener = onSessionClosedListener;
    }

    public void sessionCreated(IoSession session) throws Exception {
        super.sessionCreated(session);
    }

    public void sessionOpened(IoSession session) throws Exception {
        super.sessionOpened(session);
        if (this.onSessionOpenedListener != null)
            this.onSessionOpenedListener.onSessioned(session);
        if (this.mina != null)
            this.mina.link(session);
        log("session opened");
    }

    public void sessionClosed(IoSession session) throws Exception {
        super.sessionClosed(session);
        if (this.mina.isCloseLocked())
            return;
        if (this.onSessionClosedListener != null)
            this.onSessionClosedListener.onSessioned(session);
        if (this.mina != null)
            this.mina.remove(session);
        this.mina.close();
        log("session closed");
    }

    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        super.sessionIdle(session, status);
    }

    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
        cause.printStackTrace();
    }

    public void messageReceived(IoSession session, Object message) throws Exception {
        super.messageReceived(session, message);
        if (this.onMessageRecivedListener != null)
            this.onMessageRecivedListener.onMessaged(message);
        log("message recived");
    }

    public void messageSent(IoSession session, Object message) throws Exception {
        super.messageSent(session, message);
        if (this.onMessageSendedListener != null)
            this.onMessageSendedListener.onMessaged(message);
        log("message sended");
    }

    public void inputClosed(IoSession session) throws Exception {
        super.inputClosed(session);
    }

    public void log(String message) {
        System.out.println(message);
    }
}
