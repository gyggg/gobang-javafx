package me.gobang.socket;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class JsonFactory
        implements ProtocolCodecFactory {
    private static JsonDecoder jsonDecoder = new JsonDecoder();
    private static JsonEncoder jsonEncoder = new JsonEncoder();

    public ProtocolEncoder getEncoder(IoSession ioSession) throws Exception {
        return jsonEncoder;
    }

    public ProtocolDecoder getDecoder(IoSession ioSession) throws Exception {
        return jsonDecoder;
    }
}
