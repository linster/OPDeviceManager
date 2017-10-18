package okio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Logger;

public final class j {
    private static final Logger logger = Logger.getLogger(j.class);

    private j() {
    }

    public static a AE(v vVar) {
        if (vVar != null) {
            return new q(vVar);
        }
        throw new IllegalArgumentException("source == null");
    }

    public static b AF(n nVar) {
        if (nVar != null) {
            return new o(nVar);
        }
        throw new IllegalArgumentException("sink == null");
    }

    public static n AG(OutputStream outputStream) {
        return AH(outputStream, new g());
    }

    private static n AH(OutputStream outputStream, g gVar) {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        } else if (gVar != null) {
            return new y(gVar, outputStream);
        } else {
            throw new IllegalArgumentException("timeout == null");
        }
    }

    public static n AI(Socket socket) {
        if (socket != null) {
            g AM = AM(socket);
            return AM.sink(AH(socket.getOutputStream(), AM));
        }
        throw new IllegalArgumentException("socket == null");
    }

    public static v AJ(InputStream inputStream) {
        return AK(inputStream, new g());
    }

    private static v AK(InputStream inputStream, g gVar) {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        } else if (gVar != null) {
            return new s(gVar, inputStream);
        } else {
            throw new IllegalArgumentException("timeout == null");
        }
    }

    public static v AL(Socket socket) {
        if (socket != null) {
            g AM = AM(socket);
            return AM.source(AK(socket.getInputStream(), AM));
        }
        throw new IllegalArgumentException("socket == null");
    }

    private static i AM(Socket socket) {
        return new A(socket);
    }

    public static n appendingSink(File file) {
        if (file != null) {
            return AG(new FileOutputStream(file, true));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static n sink(File file) {
        if (file != null) {
            return AG(new FileOutputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static v source(File file) {
        if (file != null) {
            return AJ(new FileInputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }
}
