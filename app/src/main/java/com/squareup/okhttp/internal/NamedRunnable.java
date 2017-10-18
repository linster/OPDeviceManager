package com.squareup.okhttp.internal;

public abstract class NamedRunnable implements Runnable {
    protected final String name;

    public NamedRunnable(String str, Object... objArr) {
        this.name = String.format(str, objArr);
    }

    protected abstract void execute();

    public final void run() {
        String name = Thread.currentThread().getName();
        Thread.currentThread().setName(this.name);
        try {
            execute();
        } finally {
            Thread.currentThread().setName(name);
        }
    }
}
