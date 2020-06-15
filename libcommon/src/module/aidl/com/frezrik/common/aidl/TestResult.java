package com.frezrik.common.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class TestResult implements Parcelable {
	private byte[] captureImage;
	private byte[] featureCode;

	@Override
	public int describeContents() {
		return 0;
	}

	public TestResult(byte[] captureImage, byte[] featureCode) {
	    this.captureImage = captureImage;
	    this.featureCode = featureCode;
	}

    private TestResult(Parcel in) {
        this.captureImage = in.createByteArray();
        this.featureCode = in.createByteArray();
    }

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByteArray(this.captureImage);
		dest.writeByteArray(this.featureCode);
	}

	public void readFromParcel(Parcel source) {
		source.readByteArray(captureImage);
		source.readByteArray(featureCode);
	}
	public static final Creator<TestResult> CREATOR = new Creator<TestResult>() {
		@Override
		public TestResult createFromParcel(Parcel source) {
			return new TestResult(source);
		}

		@Override
		public TestResult[] newArray(int size) {
			return new TestResult[size];
		}
	};
}