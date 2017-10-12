package com.loc;

import android.os.Environment;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Locale;

/* compiled from: FileLogUtils */
public class al {
    private static String a;

    public static String a() {
        if (a == null) {
            a = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return a;
    }

    public static synchronized void a(String str, byte[] bArr, boolean z) {
        DataOutputStream dataOutputStream;
        Throwable th;
        OutputStream outputStream;
        DataOutputStream dataOutputStream2;
        boolean z2 = true;
        FileOutputStream fileOutputStream = null;
        synchronized (al.class) {
            try {
                if (b() || z) {
                    File file = new File(str);
                    File parentFile = file.getParentFile();
                    if (!parentFile.exists()) {
                        z2 = parentFile.mkdirs();
                    }
                    if (z2) {
                        if (!file.exists()) {
                            z2 = file.createNewFile();
                        }
                        if (z2) {
                            OutputStream fileOutputStream2 = new FileOutputStream(str, true);
                            try {
                                dataOutputStream = new DataOutputStream(fileOutputStream2);
                                try {
                                    dataOutputStream.write(bArr);
                                    dataOutputStream.flush();
                                    fileOutputStream2.flush();
                                    if (dataOutputStream != null) {
                                        try {
                                            dataOutputStream.close();
                                        } catch (Throwable th2) {
                                            v.a(th2, "FileLogUtils", "writeLog1");
                                        }
                                    }
                                    if (fileOutputStream2 != null) {
                                        try {
                                            fileOutputStream2.close();
                                        } catch (Throwable th22) {
                                            v.a(th22, "FileLogUtils", "writeLog2");
                                        }
                                    }
                                } catch (FileNotFoundException e) {
                                    th22 = e;
                                    outputStream = fileOutputStream2;
                                    dataOutputStream2 = dataOutputStream;
                                    try {
                                        v.a(th22, "FileLogUtils", "writeLog");
                                        if (dataOutputStream2 != null) {
                                            try {
                                                dataOutputStream2.close();
                                            } catch (Throwable th222) {
                                                v.a(th222, "FileLogUtils", "writeLog1");
                                            }
                                        }
                                        if (fileOutputStream != null) {
                                            try {
                                                fileOutputStream.close();
                                            } catch (Throwable th2222) {
                                                v.a(th2222, "FileLogUtils", "writeLog2");
                                            }
                                        }
                                    } catch (Throwable th3) {
                                        th2222 = th3;
                                        dataOutputStream = dataOutputStream2;
                                        if (dataOutputStream != null) {
                                            try {
                                                dataOutputStream.close();
                                            } catch (Throwable th4) {
                                                v.a(th4, "FileLogUtils", "writeLog1");
                                            }
                                        }
                                        if (fileOutputStream != null) {
                                            try {
                                                fileOutputStream.close();
                                            } catch (Throwable th5) {
                                                v.a(th5, "FileLogUtils", "writeLog2");
                                            }
                                        }
                                        throw th2222;
                                    }
                                } catch (IOException e2) {
                                    th2222 = e2;
                                    outputStream = fileOutputStream2;
                                    try {
                                        v.a(th2222, "FileLogUtils", "writeLog");
                                        if (dataOutputStream != null) {
                                            try {
                                                dataOutputStream.close();
                                            } catch (Throwable th22222) {
                                                v.a(th22222, "FileLogUtils", "writeLog1");
                                            }
                                        }
                                        if (fileOutputStream != null) {
                                            try {
                                                fileOutputStream.close();
                                            } catch (Throwable th222222) {
                                                v.a(th222222, "FileLogUtils", "writeLog2");
                                            }
                                        }
                                    } catch (Throwable th6) {
                                        th222222 = th6;
                                        if (dataOutputStream != null) {
                                            dataOutputStream.close();
                                        }
                                        if (fileOutputStream != null) {
                                            fileOutputStream.close();
                                        }
                                        throw th222222;
                                    }
                                } catch (Throwable th7) {
                                    th222222 = th7;
                                    outputStream = fileOutputStream2;
                                    if (dataOutputStream != null) {
                                        dataOutputStream.close();
                                    }
                                    if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                    }
                                    throw th222222;
                                }
                            } catch (FileNotFoundException e3) {
                                th222222 = e3;
                                OutputStream outputStream2 = fileOutputStream2;
                                dataOutputStream2 = null;
                                outputStream = outputStream2;
                                v.a(th222222, "FileLogUtils", "writeLog");
                                if (dataOutputStream2 != null) {
                                    dataOutputStream2.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                            } catch (IOException e4) {
                                th222222 = e4;
                                dataOutputStream = null;
                                outputStream = fileOutputStream2;
                                v.a(th222222, "FileLogUtils", "writeLog");
                                if (dataOutputStream != null) {
                                    dataOutputStream.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                            } catch (Throwable th8) {
                                th222222 = th8;
                                dataOutputStream = null;
                                outputStream = fileOutputStream2;
                                if (dataOutputStream != null) {
                                    dataOutputStream.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                                throw th222222;
                            }
                        }
                        return;
                    }
                    return;
                }
            } catch (FileNotFoundException e5) {
                th222222 = e5;
                dataOutputStream2 = null;
                v.a(th222222, "FileLogUtils", "writeLog");
                if (dataOutputStream2 != null) {
                    dataOutputStream2.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e6) {
                th222222 = e6;
                dataOutputStream = null;
                v.a(th222222, "FileLogUtils", "writeLog");
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Throwable th9) {
                th222222 = th9;
                dataOutputStream = null;
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th222222;
            }
        }
    }

    public static synchronized void a(String str, String str2, boolean z) {
        synchronized (al.class) {
            try {
                StringBuilder stringBuilder = new StringBuilder(200);
                stringBuilder.delete(0, stringBuilder.length());
                stringBuilder.append(a(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
                stringBuilder.append("#");
                stringBuilder.append(str2).append("\r\n");
                a(str, stringBuilder.toString().getBytes("UTF-8"), z);
            } catch (Throwable th) {
                v.a(th, "FileLogUtils", "writeLog3");
            }
        }
    }

    private static boolean b() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    private static String a(long j, String str) {
        return ((j > 0 ? 1 : (j == 0 ? 0 : -1)) <= 0 ? 1 : null) == null ? new SimpleDateFormat(str, Locale.US).format(Long.valueOf(j)) : "0000-00-00-00-00-00";
    }
}
