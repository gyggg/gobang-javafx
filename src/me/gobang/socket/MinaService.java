package me.gobang.socket;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

public class MinaService extends AbstractMina {
    private NioSocketAcceptor acceptor = null;
    private List<IoSession> sessions = new ArrayList();
    private int port;

    public MinaService() {
        if (this.acceptor == null) {
            this.acceptor = new NioSocketAcceptor(Runtime.getRuntime().availableProcessors() + 1);
        }
        this.acceptor.setReuseAddress(true);
        this.acceptor.getSessionConfig().setReadBufferSize(8192);
        this.acceptor.getFilterChain().addLast("codec", new ProtocolCodecFilter(new JsonFactory()));
    }

    public boolean bind(int port) {
        try {
            this.acceptor.setHandler(getMinaHandler());
            this.acceptor.bind(new InetSocketAddress(port));
            this.port = port;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public void close() {
        this.acceptor.dispose();
        this.sessions.clear();
    }

    public void link(IoSession ioSession) {
        if (this.sessions.size() > 0) {
            setCloseLock(true);
            ioSession.closeNow();
            setCloseLock(false);
            return;
        }
        this.sessions.add(ioSession);
    }

    public void remove(IoSession ioSession) {
        this.sessions.remove(ioSession);
    }

    public Boolean send(Object obj) {
        if (this.sessions.size() == 0) {
            return Boolean.valueOf(false);
        }
        Boolean flag = Boolean.valueOf(true);
        for (IoSession session : this.sessions) {
            try {
                session.write(obj);
            } catch (Exception e) {
                e.printStackTrace();
                flag = Boolean.valueOf(false);
            }
        }
        return flag;
    }
}
