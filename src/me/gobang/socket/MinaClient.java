package me.gobang.socket;

import java.net.InetSocketAddress;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClient extends AbstractMina {
    NioSocketConnector connector;
    private IoSession session = null;
    private String address;
    private int port;

    public MinaClient() {
        this.connector = new NioSocketConnector();
        this.connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new JsonFactory()));
    }

    public void connect(String address, int port) {
        this.address = address;
        this.port = port;

        this.connector.setHandler(getMinaHandler());
        ConnectFuture future = this.connector.connect(new InetSocketAddress(address, port));
        future.awaitUninterruptibly();
        this.session = future.getSession();
    }

    public void close() {
        this.connector.dispose();
        this.session = null;
    }

    public void link(IoSession ioSession) {
        this.session = ioSession;
    }

    public void remove(IoSession ioSession) {
        this.session = null;
    }

    public Boolean send(Object obj) {
        try {
            if (this.session == null) {
                return Boolean.valueOf(false);
            }
            this.session.write(obj);
        } catch (Exception e) {
            return Boolean.valueOf(false);
        }
        return Boolean.valueOf(true);
    }
}
