package me.gobang.socket;

import org.apache.mina.core.session.IoSession;

public abstract class AbstractMina {
    private boolean closeLock = false;
    private MinaHandler minaHandler = new MinaHandler(this);

    public MinaHandler getMinaHandler() {
        return this.minaHandler;
    }

    public void setMinaHandler(MinaHandler minaHandler) {
        this.minaHandler = minaHandler;
    }

    public abstract Boolean send(Object paramObject);

    public abstract void close();

    public abstract void link(IoSession paramIoSession);

    public abstract void remove(IoSession paramIoSession);

    public void cleanHandler() {
        this.minaHandler.cleanListener();
    }

    public boolean isCloseLocked() {
        return this.closeLock;
    }

    public void setCloseLock(boolean closeLock) {
        this.closeLock = closeLock;
    }
}
