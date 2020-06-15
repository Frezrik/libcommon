package com.frezrik.common.aidl;
import com.frezrik.common.aidl.ITestListener;

interface IDeviceManager {

    int setOnTestListener(ITestListener listener);

}
