package com.frezrik.common.api;

import android.util.Log;

public class DeviceException extends BaseException {
    /**
     * 异常码
     */
    private int exceptionCode = NO_SUPPORT_ERROR;

    public DeviceException(int exCode) {
        super(searchMessage(exCode));
        exceptionCode = exCode;
    }

    protected static String searchMessage(int exCode) {
        String message = BaseException.searchMessage(exCode);
        if (NOT_FOUND_EXCEPTION.equals(message)) {
            switch (exCode) {

            }
        }
        return message;
    }

    /**
     * 查询异常信息的异常码
     *
     * @param msg 异常的详细信息
     * @return 异常码
     */
    public static DeviceException getDeviceException(String msg) {
        int exCode = -1;
        try {
            exCode = (byte) Integer.parseInt(msg);
        } catch (NumberFormatException e) {
            e.printStackTrace();

            exCode = searchMessage(msg);

            if(exCode == -1)
                Log.e("DeviceException", "getDeviceException msg:" + msg);
        }

        return new DeviceException(exCode);
    }
}
