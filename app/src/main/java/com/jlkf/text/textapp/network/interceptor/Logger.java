package com.jlkf.text.textapp.network.interceptor;


import com.jlkf.text.textapp.util.LogUtil;

public class Logger implements LoggingInterceptor.Logger {
 
    @Override
    public void log(String message) {
        LogUtil.i("http : " + message);
    }
}
