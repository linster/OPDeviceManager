package net.oneplus.odm.insight;

import java.io.File;

class g implements Runnable {
    final /* synthetic */ LogParserJobService aX;
    private File file;

    g(LogParserJobService logParserJobService, File file) {
        this.aX = logParserJobService;
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
        r6 = this;
        r5 = 2;
        r0 = new java.io.FileInputStream;	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        r1 = r6.file;	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        r0.<init>(r1);	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        r1 = new java.io.InputStreamReader;	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        r1.<init>(r0);	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        r2 = new java.io.BufferedReader;	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        r2.<init>(r1);	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        r1 = new java.lang.StringBuilder;	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        r1.<init>();	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
    L_0x0017:
        r3 = r2.readLine();	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        if (r3 == 0) goto L_0x005c;	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
    L_0x001d:
        r1.append(r3);	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        goto L_0x0017;
    L_0x0021:
        r0 = move-exception;
        r1 = "LogParserJobService";	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        r0 = r0.getMessage();	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        android.util.Log.e(r1, r0);	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        r0 = r6.file;
        r0 = r0.exists();
        if (r0 == 0) goto L_0x0039;
    L_0x0034:
        r0 = r6.file;
        r0.delete();
    L_0x0039:
        r0 = net.oneplus.odm.insight.LogParserJobService.aO;
        r0 = r0 + -1;
        net.oneplus.odm.insight.LogParserJobService.aO = r0;
        r0 = net.oneplus.odm.insight.LogParserJobService.aO;
        if (r0 != 0) goto L_0x005b;
    L_0x0048:
        r0 = r6.aX;
        r0 = r0.aQ;
        r1 = r6.aX;
        r1 = r1.aQ;
        r1 = r1.obtainMessage(r5);
        r0.sendMessage(r1);
    L_0x005b:
        return;
    L_0x005c:
        r0.close();	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        r0 = r1.toString();	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        if (r0 == 0) goto L_0x0093;
    L_0x0065:
        r1 = r6.aX;
        r1 = r1.aQ;
        r2 = 3;
        r1 = r1.obtainMessage(r2);
        r2 = new android.os.Bundle;
        r2.<init>();
        r3 = "tag";
        r4 = r6.file;
        r4 = r4.getName();
        r2.putString(r3, r4);
        r3 = "log";
        r2.putString(r3, r0);
        r1.setData(r2);
        r0 = r6.aX;
        r0 = r0.aQ;
        r0.sendMessage(r1);
    L_0x0093:
        r0 = r6.file;
        r0 = r0.exists();
        if (r0 == 0) goto L_0x00a0;
    L_0x009b:
        r0 = r6.file;
        r0.delete();
    L_0x00a0:
        r0 = net.oneplus.odm.insight.LogParserJobService.aO;
        r0 = r0 + -1;
        net.oneplus.odm.insight.LogParserJobService.aO = r0;
        r0 = net.oneplus.odm.insight.LogParserJobService.aO;
        if (r0 != 0) goto L_0x005b;
    L_0x00af:
        r0 = r6.aX;
        r0 = r0.aQ;
        r1 = r6.aX;
        r1 = r1.aQ;
        r1 = r1.obtainMessage(r5);
        r0.sendMessage(r1);
        goto L_0x005b;
    L_0x00c3:
        r0 = move-exception;
        r1 = "LogParserJobService";	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        r0 = r0.getMessage();	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        android.util.Log.e(r1, r0);	 Catch:{ FileNotFoundException -> 0x0021, IOException -> 0x00c3, all -> 0x00ff }
        r0 = r6.file;
        r0 = r0.exists();
        if (r0 == 0) goto L_0x00db;
    L_0x00d6:
        r0 = r6.file;
        r0.delete();
    L_0x00db:
        r0 = net.oneplus.odm.insight.LogParserJobService.aO;
        r0 = r0 + -1;
        net.oneplus.odm.insight.LogParserJobService.aO = r0;
        r0 = net.oneplus.odm.insight.LogParserJobService.aO;
        if (r0 != 0) goto L_0x005b;
    L_0x00ea:
        r0 = r6.aX;
        r0 = r0.aQ;
        r1 = r6.aX;
        r1 = r1.aQ;
        r1 = r1.obtainMessage(r5);
        r0.sendMessage(r1);
        goto L_0x005b;
    L_0x00ff:
        r0 = move-exception;
        r1 = r6.file;
        r1 = r1.exists();
        if (r1 == 0) goto L_0x010d;
    L_0x0108:
        r1 = r6.file;
        r1.delete();
    L_0x010d:
        r1 = net.oneplus.odm.insight.LogParserJobService.aO;
        r1 = r1 + -1;
        net.oneplus.odm.insight.LogParserJobService.aO = r1;
        r1 = net.oneplus.odm.insight.LogParserJobService.aO;
        if (r1 != 0) goto L_0x012f;
    L_0x011c:
        r1 = r6.aX;
        r1 = r1.aQ;
        r2 = r6.aX;
        r2 = r2.aQ;
        r2 = r2.obtainMessage(r5);
        r1.sendMessage(r2);
    L_0x012f:
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: net.oneplus.odm.insight.g.run():void");
    }
}
