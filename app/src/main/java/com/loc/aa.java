package com.loc;

/* synthetic */ class aa {
    static final /* synthetic */ int[] mI = null;

    static {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.loc.aa.<clinit>():void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: java.lang.NullPointerException
	at jadx.core.dex.nodes.MethodNode.addJump(MethodNode.java:370)
	at jadx.core.dex.nodes.MethodNode.initJumps(MethodNode.java:360)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:106)
	... 7 more
*/
        /*
        r0 = com.amap.api.location.AMapLocationClientOption$AMapLocationMode.values();
        r0 = r0.length;
        r0 = new int[r0];
        mI = r0;
        r0 = mI;	 Catch:{ NoSuchFieldError -> 0x0029 }
        r1 = com.amap.api.location.AMapLocationClientOption$AMapLocationMode.Battery_Saving;	 Catch:{ NoSuchFieldError -> 0x0029 }
        r2 = 1;	 Catch:{ NoSuchFieldError -> 0x0029 }
        r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0029 }
        r0 = mI;	 Catch:{ NoSuchFieldError -> 0x0027 }
        r1 = com.amap.api.location.AMapLocationClientOption$AMapLocationMode.Device_Sensors;	 Catch:{ NoSuchFieldError -> 0x0027 }
        r2 = 2;	 Catch:{ NoSuchFieldError -> 0x0027 }
        r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0027 }
        r0 = mI;	 Catch:{ NoSuchFieldError -> 0x0025 }
        r1 = com.amap.api.location.AMapLocationClientOption$AMapLocationMode.Hight_Accuracy;	 Catch:{ NoSuchFieldError -> 0x0025 }
        r2 = 3;	 Catch:{ NoSuchFieldError -> 0x0025 }
        r0[r1] = r2;	 Catch:{ NoSuchFieldError -> 0x0025 }
    L_0x0025:
        r0 = move-exception;
        goto L_0x0024;
    L_0x0027:
        r0 = move-exception;
        goto L_0x001b;
    L_0x0029:
        r0 = move-exception;
        goto L_0x0012;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loc.aa.<clinit>():void");
    }
}
