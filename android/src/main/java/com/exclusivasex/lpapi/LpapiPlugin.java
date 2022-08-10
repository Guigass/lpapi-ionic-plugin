package com.exclusivasex.lpapi;

import android.util.Log;

public class LpapiPlugin {
    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }
}
