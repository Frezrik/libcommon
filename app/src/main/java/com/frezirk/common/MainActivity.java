package com.frezirk.common;

import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.frezrik.common.Core;
import com.frezrik.common.annotation.BindApi;
import com.frezrik.common.api.ConvertUtil;

public class MainActivity extends AppCompatActivity {

    @BindApi ConvertUtil mConvertUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Core.bind(this);

        byte[] bytes = mConvertUtil.randomBytes(10);

        for (byte b : bytes) {
            Log.d("zmzm", "" + b);
        }

    }
}
