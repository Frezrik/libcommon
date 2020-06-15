package com.frezrik.common.utils;

import com.frezrik.core.api.ConvertUtil;
import com.frezrik.router.annotation.ApiImpl;

@ApiImpl
public class ConvertUtilImpl implements ConvertUtil {

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
