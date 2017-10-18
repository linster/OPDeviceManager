package okio;

final class t extends Thread {
    public t() {
        super("Okio Watchdog");
        setDaemon(true);
    }

    public void run() {
        while (true) {
            try {
                i access$000 = i.awaitTimeout();
                if (access$000 != null) {
                    access$000.timedOut();
                }
            } catch (InterruptedException e) {
            }
        }
    }
}
