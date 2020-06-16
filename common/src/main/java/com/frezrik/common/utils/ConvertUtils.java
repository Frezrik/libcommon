package com.frezrik.common.utils;

import com.frezrik.common.annotation.ApiImpl;
import com.frezrik.core.api.ConvertUtil;

@ApiImpl
public class ConvertUtils implements com.frezrik.core.api.ConvertUtil {

    @Override
    public byte[] randomBytes(int length) {
        if (length < 0)
            return new byte[0];

        byte[] b = new byte[length];
        for (int i = 0; i < length; i++) {
            b[i] = (byte) (Math.random() * 0xff);
        }
        return b;
    }
}
