package net.oneplus.odm.insight;

import java.io.File;

class h implements Runnable {
    private Process aY;
    final /* synthetic */ LogParserJobService aZ;
    private File file;

    h(LogParserJobService logParserJobService, File file) {
        this.aZ = logParserJobService;
        this.file = file;
    }

    public void run() {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1439)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1461)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r11 = this;
        r10 = 2;
        r1 = 0;
        r2 = 0;
        r0 = r11.aY;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        if (r0 == 0) goto L_0x0017;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
    L_0x0008:
        r0 = "LogParserJobService";	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r4 = "Parse buffer";	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        android.util.Log.v(r0, r4);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r0 = r11.aY;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r1 = r0.getInputStream();	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
    L_0x0017:
        r0 = r11.file;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        if (r0 == 0) goto L_0x002c;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
    L_0x001b:
        r0 = "LogParserJobService";	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r4 = "Parse file";	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        android.util.Log.v(r0, r4);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r0 = new java.io.FileInputStream;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r4 = r11.file;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r0.<init>(r4);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r1 = r0;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
    L_0x002c:
        r0 = new java.io.InputStreamReader;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r0.<init>(r1);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r4 = new java.io.BufferedReader;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r4.<init>(r0);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
    L_0x0036:
        r0 = r4.readLine();	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        if (r0 == 0) goto L_0x0085;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
    L_0x003c:
        r5 = "(: ')|(', ')";	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r0 = r0.split(r5);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r5 = r0.length;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r6 = r0.length;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r7 = 4;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        if (r6 < r7) goto L_0x0036;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
    L_0x0048:
        r6 = r11.aZ;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r6 = r6.aQ;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r7 = 1;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r6 = r6.obtainMessage(r7);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r7 = new android.content.ContentValues;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r7.<init>();	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r8 = "OP_payload";	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r9 = r5 + -3;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r9 = r0[r9];	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r7.put(r8, r9);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r8 = "OP_payload_timestamp";	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r9 = r5 + -2;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r9 = r0[r9];	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r7.put(r8, r9);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r8 = "OP_payload_category";	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r5 = r5 + -1;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r0 = r0[r5];	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r7.put(r8, r0);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r6.obj = r7;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r0 = r11.aZ;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r0 = r0.aQ;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r0.sendMessage(r6);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r6 = 1;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r2 = r2 + r6;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        goto L_0x0036;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
    L_0x0085:
        r0 = "LogParserJobService";	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r4 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r4.<init>();	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r5 = "Add Event:";	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r4 = r4.append(r5);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r2 = r4.append(r2);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r2 = r2.toString();	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        android.util.Log.v(r0, r2);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        if (r1 == 0) goto L_0x00a4;
    L_0x00a1:
        r1.close();	 Catch:{ IOException -> 0x00d9, all -> 0x0107 }
    L_0x00a4:
        r0 = r11.file;	 Catch:{ IOException -> 0x00d9, all -> 0x0107 }
        if (r0 == 0) goto L_0x00b6;	 Catch:{ IOException -> 0x00d9, all -> 0x0107 }
    L_0x00a8:
        r0 = "LogParserJobService";	 Catch:{ IOException -> 0x00d9, all -> 0x0107 }
        r1 = "mdm log file delete";	 Catch:{ IOException -> 0x00d9, all -> 0x0107 }
        android.util.Log.v(r0, r1);	 Catch:{ IOException -> 0x00d9, all -> 0x0107 }
        r0 = r11.file;	 Catch:{ IOException -> 0x00d9, all -> 0x0107 }
        r0.delete();	 Catch:{ IOException -> 0x00d9, all -> 0x0107 }
    L_0x00b6:
        r0 = net.oneplus.odm.insight.LogParserJobService.aO;
        r0 = r0 + -1;
        net.oneplus.odm.insight.LogParserJobService.aO = r0;
        r0 = net.oneplus.odm.insight.LogParserJobService.aO;
        if (r0 != 0) goto L_0x00d8;
    L_0x00c5:
        r0 = r11.aZ;
        r0 = r0.aQ;
        r1 = r11.aZ;
        r1 = r1.aQ;
        r1 = r1.obtainMessage(r10);
        r0.sendMessage(r1);
    L_0x00d8:
        return;
    L_0x00d9:
        r0 = move-exception;
        r1 = "LogParserJobService";	 Catch:{ IOException -> 0x00d9, all -> 0x0107 }
        r0 = r0.getMessage();	 Catch:{ IOException -> 0x00d9, all -> 0x0107 }
        android.util.Log.e(r1, r0);	 Catch:{ IOException -> 0x00d9, all -> 0x0107 }
        r0 = net.oneplus.odm.insight.LogParserJobService.aO;
        r0 = r0 + -1;
        net.oneplus.odm.insight.LogParserJobService.aO = r0;
        r0 = net.oneplus.odm.insight.LogParserJobService.aO;
        if (r0 != 0) goto L_0x00d8;
    L_0x00f3:
        r0 = r11.aZ;
        r0 = r0.aQ;
        r1 = r11.aZ;
        r1 = r1.aQ;
        r1 = r1.obtainMessage(r10);
        r0.sendMessage(r1);
        goto L_0x00d8;
    L_0x0107:
        r0 = move-exception;
        r1 = net.oneplus.odm.insight.LogParserJobService.aO;
        r1 = r1 + -1;
        net.oneplus.odm.insight.LogParserJobService.aO = r1;
        r1 = net.oneplus.odm.insight.LogParserJobService.aO;
        if (r1 != 0) goto L_0x012a;
    L_0x0117:
        r1 = r11.aZ;
        r1 = r1.aQ;
        r2 = r11.aZ;
        r2 = r2.aQ;
        r2 = r2.obtainMessage(r10);
        r1.sendMessage(r2);
    L_0x012a:
        throw r0;
    L_0x012b:
        r0 = move-exception;
        r2 = "LogParserJobService";	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r3.<init>();	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r4 = "IOException:";	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r3 = r3.append(r4);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r0 = r0.getMessage();	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r0 = r3.append(r0);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        r0 = r0.toString();	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        android.util.Log.e(r2, r0);	 Catch:{ IOException -> 0x012b, all -> 0x01d8 }
        if (r1 == 0) goto L_0x014f;
    L_0x014c:
        r1.close();	 Catch:{ IOException -> 0x0185, all -> 0x01b4 }
    L_0x014f:
        r0 = r11.file;	 Catch:{ IOException -> 0x0185, all -> 0x01b4 }
        if (r0 == 0) goto L_0x0161;	 Catch:{ IOException -> 0x0185, all -> 0x01b4 }
    L_0x0153:
        r0 = "LogParserJobService";	 Catch:{ IOException -> 0x0185, all -> 0x01b4 }
        r1 = "mdm log file delete";	 Catch:{ IOException -> 0x0185, all -> 0x01b4 }
        android.util.Log.v(r0, r1);	 Catch:{ IOException -> 0x0185, all -> 0x01b4 }
        r0 = r11.file;	 Catch:{ IOException -> 0x0185, all -> 0x01b4 }
        r0.delete();	 Catch:{ IOException -> 0x0185, all -> 0x01b4 }
    L_0x0161:
        r0 = net.oneplus.odm.insight.LogParserJobService.aO;
        r0 = r0 + -1;
        net.oneplus.odm.insight.LogParserJobService.aO = r0;
        r0 = net.oneplus.odm.insight.LogParserJobService.aO;
        if (r0 != 0) goto L_0x00d8;
    L_0x0170:
        r0 = r11.aZ;
        r0 = r0.aQ;
        r1 = r11.aZ;
        r1 = r1.aQ;
        r1 = r1.obtainMessage(r10);
        r0.sendMessage(r1);
        goto L_0x00d8;
    L_0x0185:
        r0 = move-exception;
        r1 = "LogParserJobService";	 Catch:{ IOException -> 0x0185, all -> 0x01b4 }
        r0 = r0.getMessage();	 Catch:{ IOException -> 0x0185, all -> 0x01b4 }
        android.util.Log.e(r1, r0);	 Catch:{ IOException -> 0x0185, all -> 0x01b4 }
        r0 = net.oneplus.odm.insight.LogParserJobService.aO;
        r0 = r0 + -1;
        net.oneplus.odm.insight.LogParserJobService.aO = r0;
        r0 = net.oneplus.odm.insight.LogParserJobService.aO;
        if (r0 != 0) goto L_0x00d8;
    L_0x019f:
        r0 = r11.aZ;
        r0 = r0.aQ;
        r1 = r11.aZ;
        r1 = r1.aQ;
        r1 = r1.obtainMessage(r10);
        r0.sendMessage(r1);
        goto L_0x00d8;
    L_0x01b4:
        r0 = move-exception;
        r1 = net.oneplus.odm.insight.LogParserJobService.aO;
        r1 = r1 + -1;
        net.oneplus.odm.insight.LogParserJobService.aO = r1;
        r1 = net.oneplus.odm.insight.LogParserJobService.aO;
        if (r1 != 0) goto L_0x01d7;
    L_0x01c4:
        r1 = r11.aZ;
        r1 = r1.aQ;
        r2 = r11.aZ;
        r2 = r2.aQ;
        r2 = r2.obtainMessage(r10);
        r1.sendMessage(r2);
    L_0x01d7:
        throw r0;
    L_0x01d8:
        r0 = move-exception;
        if (r1 == 0) goto L_0x01de;
    L_0x01db:
        r1.close();	 Catch:{ IOException -> 0x0213, all -> 0x0241 }
    L_0x01de:
        r1 = r11.file;	 Catch:{ IOException -> 0x0213, all -> 0x0241 }
        if (r1 == 0) goto L_0x01f0;	 Catch:{ IOException -> 0x0213, all -> 0x0241 }
    L_0x01e2:
        r1 = "LogParserJobService";	 Catch:{ IOException -> 0x0213, all -> 0x0241 }
        r2 = "mdm log file delete";	 Catch:{ IOException -> 0x0213, all -> 0x0241 }
        android.util.Log.v(r1, r2);	 Catch:{ IOException -> 0x0213, all -> 0x0241 }
        r1 = r11.file;	 Catch:{ IOException -> 0x0213, all -> 0x0241 }
        r1.delete();	 Catch:{ IOException -> 0x0213, all -> 0x0241 }
    L_0x01f0:
        r1 = net.oneplus.odm.insight.LogParserJobService.aO;
        r1 = r1 + -1;
        net.oneplus.odm.insight.LogParserJobService.aO = r1;
        r1 = net.oneplus.odm.insight.LogParserJobService.aO;
        if (r1 != 0) goto L_0x0212;
    L_0x01ff:
        r1 = r11.aZ;
        r1 = r1.aQ;
        r2 = r11.aZ;
        r2 = r2.aQ;
        r2 = r2.obtainMessage(r10);
        r1.sendMessage(r2);
    L_0x0212:
        throw r0;
    L_0x0213:
        r1 = move-exception;
        r2 = "LogParserJobService";	 Catch:{ IOException -> 0x0213, all -> 0x0241 }
        r1 = r1.getMessage();	 Catch:{ IOException -> 0x0213, all -> 0x0241 }
        android.util.Log.e(r2, r1);	 Catch:{ IOException -> 0x0213, all -> 0x0241 }
        r1 = net.oneplus.odm.insight.LogParserJobService.aO;
        r1 = r1 + -1;
        net.oneplus.odm.insight.LogParserJobService.aO = r1;
        r1 = net.oneplus.odm.insight.LogParserJobService.aO;
        if (r1 != 0) goto L_0x0212;
    L_0x022d:
        r1 = r11.aZ;
        r1 = r1.aQ;
        r2 = r11.aZ;
        r2 = r2.aQ;
        r2 = r2.obtainMessage(r10);
        r1.sendMessage(r2);
        goto L_0x0212;
    L_0x0241:
        r0 = move-exception;
        r1 = net.oneplus.odm.insight.LogParserJobService.aO;
        r1 = r1 + -1;
        net.oneplus.odm.insight.LogParserJobService.aO = r1;
        r1 = net.oneplus.odm.insight.LogParserJobService.aO;
        if (r1 != 0) goto L_0x0264;
    L_0x0251:
        r1 = r11.aZ;
        r1 = r1.aQ;
        r2 = r11.aZ;
        r2 = r2.aQ;
        r2 = r2.obtainMessage(r10);
        r1.sendMessage(r2);
    L_0x0264:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: net.oneplus.odm.insight.h.run():void");
    }
}
