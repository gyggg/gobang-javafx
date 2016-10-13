package me.gobang.socket;

import org.apache.mina.core.session.IoSession;

public abstract interface SessionListener {
    public abstract void onSessioned(IoSession paramIoSession);
}
