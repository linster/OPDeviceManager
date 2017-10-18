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

public class ar {
    private static String a;

    public static String oT() {
        if (a == null) {
            a = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return a;
    }

    public static synchronized void oU(String str, byte[] bArr, boolean z) {
        Throwable th;
        OutputStream outputStream;
        DataOutputStream dataOutputStream;
        boolean z2 = true;
        FileOutputStream fileOutputStream = null;
        synchronized (ar.class) {
            DataOutputStream dataOutputStream2;
            try {
                if (oW() || z) {
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
                                dataOutputStream2 = new DataOutputStream(fileOutputStream2);
                                try {
                                    dataOutputStream2.write(bArr);
                                    dataOutputStream2.flush();
                                    fileOutputStream2.flush();
                                    if (dataOutputStream2 != null) {
                                        try {
                                            dataOutputStream2.close();
                                        } catch (Throwable th2) {
                                            D.mF(th2, "FileLogUtils", "writeLog1");
                                        }
                                    }
                                    if (fileOutputStream2 != null) {
                                        try {
                                            fileOutputStream2.close();
                                        } catch (Throwable th22) {
                                            D.mF(th22, "FileLogUtils", "writeLog2");
                                        }
                                    }
                                } catch (FileNotFoundException e) {
                                    th22 = e;
                                    outputStream = fileOutputStream2;
                                    dataOutputStream = dataOutputStream2;
                                    try {
                                        D.mF(th22, "FileLogUtils", "writeLog");
                                        if (dataOutputStream != null) {
                                            try {
                                                dataOutputStream.close();
                                            } catch (Throwable th222) {
                                                D.mF(th222, "FileLogUtils", "writeLog1");
                                            }
                                        }
                                        if (fileOutputStream != null) {
                                            try {
                                                fileOutputStream.close();
                                            } catch (Throwable th2222) {
                                                D.mF(th2222, "FileLogUtils", "writeLog2");
                                            }
                                        }
                                    } catch (Throwable th3) {
                                        th2222 = th3;
                                        dataOutputStream2 = dataOutputStream;
                                        if (dataOutputStream2 != null) {
                                            try {
                                                dataOutputStream2.close();
                                            } catch (Throwable th4) {
                                                D.mF(th4, "FileLogUtils", "writeLog1");
                                            }
                                        }
                                        if (fileOutputStream != null) {
                                            try {
                                                fileOutputStream.close();
                                            } catch (Throwable th5) {
                                                D.mF(th5, "FileLogUtils", "writeLog2");
                                            }
                                        }
                                        throw th2222;
                                    }
                                } catch (IOException e2) {
                                    th2222 = e2;
                                    outputStream = fileOutputStream2;
                                    try {
                                        D.mF(th2222, "FileLogUtils", "writeLog");
                                        if (dataOutputStream2 != null) {
                                            try {
                                                dataOutputStream2.close();
                                            } catch (Throwable th22222) {
                                                D.mF(th22222, "FileLogUtils", "writeLog1");
                                            }
                                        }
                                        if (fileOutputStream != null) {
                                            try {
                                                fileOutputStream.close();
                                            } catch (Throwable th222222) {
                                                D.mF(th222222, "FileLogUtils", "writeLog2");
                                            }
                                        }
                                    } catch (Throwable th6) {
                                        th222222 = th6;
                                        if (dataOutputStream2 != null) {
                                            dataOutputStream2.close();
                                        }
                                        if (fileOutputStream != null) {
                                            fileOutputStream.close();
                                        }
                                        throw th222222;
                                    }
                                } catch (Throwable th7) {
                                    th222222 = th7;
                                    outputStream = fileOutputStream2;
                                    if (dataOutputStream2 != null) {
                                        dataOutputStream2.close();
                                    }
                                    if (fileOutputStream != null) {
                                        fileOutputStream.close();
                                    }
                                    throw th222222;
                                }
                            } catch (FileNotFoundException e3) {
                                th222222 = e3;
                                OutputStream outputStream2 = fileOutputStream2;
                                dataOutputStream = null;
                                outputStream = outputStream2;
                                D.mF(th222222, "FileLogUtils", "writeLog");
                                if (dataOutputStream != null) {
                                    dataOutputStream.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                            } catch (IOException e4) {
                                th222222 = e4;
                                dataOutputStream2 = null;
                                outputStream = fileOutputStream2;
                                D.mF(th222222, "FileLogUtils", "writeLog");
                                if (dataOutputStream2 != null) {
                                    dataOutputStream2.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                            } catch (Throwable th8) {
                                th222222 = th8;
                                dataOutputStream2 = null;
                                outputStream = fileOutputStream2;
                                if (dataOutputStream2 != null) {
                                    dataOutputStream2.close();
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
                dataOutputStream = null;
                D.mF(th222222, "FileLogUtils", "writeLog");
                if (dataOutputStream != null) {
                    dataOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e6) {
                th222222 = e6;
                dataOutputStream2 = null;
                D.mF(th222222, "FileLogUtils", "writeLog");
                if (dataOutputStream2 != null) {
                    dataOutputStream2.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (Throwable th9) {
                th222222 = th9;
                dataOutputStream2 = null;
                if (dataOutputStream2 != null) {
                    dataOutputStream2.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw th222222;
            }
        }
    }

    public static synchronized void oV(String str, String str2, boolean z) {
        synchronized (ar.class) {
            try {
                StringBuilder stringBuilder = new StringBuilder(200);
                stringBuilder.delete(0, stringBuilder.length());
                stringBuilder.append(oX(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss"));
                stringBuilder.append("#");
                stringBuilder.append(str2).append("\r\n");
                oU(str, stringBuilder.toString().getBytes("UTF-8"), z);
            } catch (Throwable th) {
                D.mF(th, "FileLogUtils", "writeLog3");
            }
        }
    }

    private static boolean oW() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    private static String oX(long j, String str) {
        return ((j > 0 ? 1 : (j == 0 ? 0 : -1)) <= 0 ? 1 : null) == null ? new SimpleDateFormat(str, Locale.US).format(Long.valueOf(j)) : "0000-00-00-00-00-00";
    }
}
