package okio;

import java.net.Socket;
import java.util.logging.Level;

class A extends i {
    final /* synthetic */ Socket vc;

    A(Socket socket) {
        this.vc = socket;
    }

    protected void timedOut() {
        try {
            this.vc.close();
        } catch (Throwable e) {
            j.logger.log(Level.WARNING, "Failed to close timed out socket " + this.vc, e);
        }
    }
}
