package com.frezrik.common.api;

/**
 * 基本的异常
 */
public class BaseException extends Exception {

    /**
     * 未找到该异常
     */
    public static final String NOT_FOUND_EXCEPTION = "Not found exception";

    /**
     * 非法参数异常
     */
    public static final int ERR_INVALID_ARGUMENT = -1001;

    /**
     * 服务不可用异常
     */
    public static final int SERVICE_NOT_AVAILABLE = -1002;

    /**
     * 连接异常
     */
    public static final int CONN_ERROR = -1003;

    /**
     * 不支持的异常
     */
    public static final int NO_SUPPORT_ERROR = -1004;

    /**
     * 没有权限异常
     */
    public static final int NO_PERMISSION_ERROR = -1005;

    /**
     * 使用当前堆栈跟踪和具体的详细信息构造新的异常。
     *
     * @param msg 异常的详细信息
     */
    public BaseException(String msg) {
        super(msg);
    }

    /**
     * 判断异常信息是否为不支持的异常
     *
     * @param msg 异常的详细信息
     * @return true:异常信息为不支持的异常
     */
    private static boolean isNoSupportMessage(String msg) {
        if (msg != null
                && msg.toLowerCase().indexOf("no support") >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断异常信息是否为缺少权限的异常
     *
     * @param msg 异常的详细信息
     * @return true:异常信息为缺少权限的异常
     */
    private static boolean isNoPermissionMessage(String msg) {
        if (msg != null
                && msg.toLowerCase().indexOf("no permission") >= 0) {
            return true;
        }
        return false;
    }

    /**
     * 判断异常信息是否为非法参数异常
     *
     * @param msg 异常的详细信息
     * @return true:异常信息为非法参数的异常
     */
    private static boolean isEINVALMessage(String msg) {
        if (msg != null
                && (msg.toLowerCase().indexOf("invalid argument") >= 0)) {
            return true;
        }
        return false;
    }

    /**
     * 查询异常信息的异常码
     *
     * @param msg 异常的详细信息
     * @return 异常码
     */
    static int searchMessage(String msg) {
        int exCode = CONN_ERROR;
        if (BaseException.isNoSupportMessage(msg)) {
            exCode = NO_SUPPORT_ERROR;
        }

        if (BaseException.isNoPermissionMessage(msg)) {
            exCode = BaseException.NO_PERMISSION_ERROR;
        }

        if (BaseException.isEINVALMessage(msg)) {
            exCode = BaseException.ERR_INVALID_ARGUMENT;
        }

        return exCode;
    }

    /**
     * 查询异常信息
     * @param exCode
     * @return
     */
    static String searchMessage(int exCode) {
        String message = NOT_FOUND_EXCEPTION;
        switch (exCode) {
            case SERVICE_NOT_AVAILABLE:
                message = "Service not available";
                break;
            case ERR_INVALID_ARGUMENT:
                message = "Parameter invalid";
                break;
            case NO_SUPPORT_ERROR:
                message = "Not support for this device";
                break;
            case NO_PERMISSION_ERROR:
                message = "Not permission for this device";
                break;
        }
        return message;
    }

}
