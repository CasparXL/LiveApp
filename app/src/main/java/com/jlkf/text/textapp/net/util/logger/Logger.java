package com.jlkf.text.textapp.net.util.logger;


import com.jlkf.text.textapp.util.LogUtil;

public class Logger implements LoggingInterceptor.Logger {
 
    @Override
    public void log(String message) {
        LogUtil.i("http : " + message);
    }
}
