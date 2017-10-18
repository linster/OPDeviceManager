package com.squareup.okhttp.internal.spdy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;
import okio.ByteString;
import okio.a;
import okio.j;
import okio.k;
import okio.m;
import okio.u;

class NameValueBlockReader {
    private int compressedLimit;
    private final m inflaterSource;
    private final a source = j.AE(this.inflaterSource);

    public NameValueBlockReader(a aVar) {
        this.inflaterSource = new m(new u(aVar) {
            public long read(k kVar, long j) {
                if (NameValueBlockReader.this.compressedLimit == 0) {
                    return -1;
                }
                long read = super.read(kVar, Math.min(j, (long) NameValueBlockReader.this.compressedLimit));
                if (read == -1) {
                    return -1;
                }
                NameValueBlockReader.this.compressedLimit = (int) (((long) NameValueBlockReader.this.compressedLimit) - read);
                return read;
            }
        }, new Inflater() {
            public int inflate(byte[] bArr, int i, int i2) {
                int inflate = super.inflate(bArr, i, i2);
                if (inflate != 0 || !needsDictionary()) {
                    return inflate;
                }
                setDictionary(Spdy3.DICTIONARY);
                return super.inflate(bArr, i, i2);
            }
        });
    }

    private void doneReading() {
        if (this.compressedLimit > 0) {
            this.inflaterSource.AY();
            if (this.compressedLimit != 0) {
                throw new IOException("compressedLimit > 0: " + this.compressedLimit);
            }
        }
    }

    private ByteString readByteString() {
        return this.source.zT((long) this.source.zP());
    }

    public void close() {
        this.source.close();
    }

    public List readNameValueBlock(int i) {
        int i2 = 0;
        this.compressedLimit += i;
        int zP = this.source.zP();
        if (zP < 0) {
            throw new IOException("numberOfPairs < 0: " + zP);
        } else if (zP <= 1024) {
            List arrayList = new ArrayList(zP);
            while (i2 < zP) {
                ByteString AA = readByteString().AA();
                ByteString readByteString = readByteString();
                if (AA.size() != 0) {
                    arrayList.add(new Header(AA, readByteString));
                    i2++;
                } else {
                    throw new IOException("name.size == 0");
                }
            }
            doneReading();
            return arrayList;
        } else {
            throw new IOException("numberOfPairs > 1024: " + zP);
        }
    }
}
