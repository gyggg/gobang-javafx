package me.gobang.socket;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoder;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

public class JsonEncoder
        implements ProtocolEncoder {
    public void encode(IoSession ioSession, Object message, ProtocolEncoderOutput protocolEncoderOutput)
            throws Exception {
        if (message != null) {
            String string = message.toString();
            IoBuffer ioBuffer = IoBuffer.allocate(1024).setAutoExpand(true);
            ioBuffer.setAutoShrink(true);
            ioBuffer.setAutoExpand(true);
            ioBuffer.putString(string, Charset.defaultCharset().newEncoder());
            ioBuffer.flip();
            protocolEncoderOutput.write(ioBuffer);
        }
    }

    public void dispose(IoSession ioSession)
            throws Exception {
    }
}
