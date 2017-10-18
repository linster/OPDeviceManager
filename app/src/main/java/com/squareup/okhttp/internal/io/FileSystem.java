package com.squareup.okhttp.internal.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import okio.j;
import okio.n;
import okio.v;

public interface FileSystem {
    public static final FileSystem SYSTEM = new FileSystem() {
        public n appendingSink(File file) {
            try {
                return j.appendingSink(file);
            } catch (FileNotFoundException e) {
                file.getParentFile().mkdirs();
                return j.appendingSink(file);
            }
        }

        public void delete(File file) {
            if (!file.delete() && file.exists()) {
                throw new IOException("failed to delete " + file);
            }
        }

        public void deleteContents(File file) {
            int i = 0;
            File[] listFiles = file.listFiles();
            if (listFiles != null) {
                int length = listFiles.length;
                while (i < length) {
                    File file2 = listFiles[i];
                    if (file2.isDirectory()) {
                        deleteContents(file2);
                    }
                    if (file2.delete()) {
                        i++;
                    } else {
                        throw new IOException("failed to delete " + file2);
                    }
                }
                return;
            }
            throw new IOException("not a readable directory: " + file);
        }

        public boolean exists(File file) {
            return file.exists();
        }

        public void rename(File file, File file2) {
            delete(file2);
            if (!file.renameTo(file2)) {
                throw new IOException("failed to rename " + file + " to " + file2);
            }
        }

        public n sink(File file) {
            try {
                return j.sink(file);
            } catch (FileNotFoundException e) {
                file.getParentFile().mkdirs();
                return j.sink(file);
            }
        }

        public long size(File file) {
            return file.length();
        }

        public v source(File file) {
            return j.source(file);
        }
    };

    n appendingSink(File file);

    void delete(File file);

    void deleteContents(File file);

    boolean exists(File file);

    void rename(File file, File file2);

    n sink(File file);

    long size(File file);

    v source(File file);
}
