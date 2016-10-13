package me.gobang.socket;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

public class JsonDecoder
        implements ProtocolDecoder {
    public void decode(IoSession ioSession, IoBuffer ioBuffer, ProtocolDecoderOutput protocolDecoderOutput)
            throws Exception {
        String string = ioBuffer.getString(Charset.defaultCharset().newDecoder());
        protocolDecoderOutput.write(string);
    }

    public void finishDecode(IoSession ioSession, ProtocolDecoderOutput protocolDecoderOutput)
            throws Exception {
    }

    public void dispose(IoSession ioSession)
            throws Exception {
    }
}
