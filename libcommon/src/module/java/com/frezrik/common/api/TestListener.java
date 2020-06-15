package com.frezrik.common.api;

import com.frezrik.common.aidl.ITestListener;
import com.frezrik.common.aidl.TestResult;

public abstract class TestListener extends ITestListener.Stub {
    @Override
    public abstract void onSuccess(TestResult result);

    @Override
    public abstract void onError(int errorCode);
}
