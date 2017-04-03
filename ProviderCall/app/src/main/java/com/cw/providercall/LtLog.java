package com.cw.providercall;

import android.util.Log;

/**
 * Created by lt on 4/3/17.
 */

public class LtLog {
    public static void verbose(String tag, String message) {
        if (ENABLE_LOG) {
            Log.v(tag, getClassNameLineNumberAndMethodName() + message);
        }
    }

    public static String __FILE__() {
        return Thread.currentThread().getStackTrace()[STACK_TRACE_LEVELS_UP].getFileName();
    }

    public static int __LINE__() {
        return Thread.currentThread().getStackTrace()[STACK_TRACE_LEVELS_UP].getLineNumber();
    }

    public static String __func__() {
        return Thread.currentThread().getStackTrace()[STACK_TRACE_LEVELS_UP].getMethodName();
    }

    private static String getClassNameLineNumberAndMethodName() {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        return "[" + trace[STACK_TRACE_LEVELS_UP + 1].getFileName()
                + " : " + trace[STACK_TRACE_LEVELS_UP + 1].getLineNumber()
                + " : " + trace[STACK_TRACE_LEVELS_UP + 1].getMethodName() + "()"  + "] ";
    }

    private static boolean ENABLE_LOG = true;
    private static final int STACK_TRACE_LEVELS_UP = 3;
}
