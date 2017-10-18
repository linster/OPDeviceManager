package com.squareup.okhttp.internal.spdy;

public interface IncomingStreamHandler {
    public static final IncomingStreamHandler REFUSE_INCOMING_STREAMS = new IncomingStreamHandler() {
        public void receive(SpdyStream spdyStream) {
            spdyStream.close(ErrorCode.REFUSED_STREAM);
        }
    };

    void receive(SpdyStream spdyStream);
}
