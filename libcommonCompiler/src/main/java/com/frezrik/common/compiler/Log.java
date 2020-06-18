package com.frezrik.common.compiler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

/**
 * Log for apt
 */
public class Log {
    private final static String PREFIX_OF_LOG = "[libcommon Compiler] ";
    private final static String LOG_FILE = "libcommonCompiler/debug.log";

    public static void clear() {
        File file = new File(LOG_FILE);
        if (file.exists())
            file.delete();
    }

    public static void i(String info) {
        if (isNotEmpty(info)) {
            printMessage(PREFIX_OF_LOG + "I: " + info);
        }
    }

    public static void e(String error) {
        if (isNotEmpty(error)) {
            printMessage(PREFIX_OF_LOG + "E: An exception is encountered, [" + error + "]");
        }
    }

    public static void e(Throwable error) {
        if (null != error) {
            printMessage(PREFIX_OF_LOG + "E: An exception is encountered, [" + error.getMessage() + "]" + "\n" + formatStackTrace(error.getStackTrace()));
        }
    }

    public static void e(Element element, String message, Object... args) {
        if (isNotEmpty(message) && element != null) {
            if (args.length > 0) {
                message = String.format(message, args);
            }

            printMessage(PREFIX_OF_LOG + "E: " + message + ". " +element.toString());
        }
    }

    private static String formatStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append("    at ").append(element.toString());
            sb.append("\n");
        }
        return sb.toString();
    }

    private static boolean isNotEmpty(final CharSequence cs) {
        boolean isEmpty = cs == null || cs.length() == 0;
        return !isEmpty;
    }

    private static String getTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss:SS", Locale.getDefault());
        return sdf.format(new Date());
    }

    private static void printMessage(String msg) {
        try {
            BufferedOutputStream bos =
                    new BufferedOutputStream(new FileOutputStream(new File(LOG_FILE), true));
            bos.write((getTime() + " " + msg + "\n").getBytes());
            bos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
