package com.frezrik.common.aidl;
import com.frezrik.common.aidl.TestResult;

interface ITestListener {
    /**
     * 成功
     */
    void onSuccess(in TestResult result);

    /**
     * 失败时，通过errorCode通知调用者，对应的失败的原因
     */
    void onError(int errorCode);
}
