package com.jlkf.text.textapp.network.error;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.jlkf.text.textapp.util.LogUtil;

import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.text.ParseException;

import okhttp3.ResponseBody;
import retrofit2.HttpException;

public class ExceptionHandle {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;
    private static final int FAIL_QUEST = 406;//无法使用请求的内容特性来响应请求的网页
    private static final int BAD_REQUEST = 400;
    private static ResponseBody body;

    public static ResponseThrowable handleException(Throwable e) {
        ResponseThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponseThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:

                    break;
                case FORBIDDEN:
                    ex.message = "服务器已经理解请求，但是拒绝执行它";
                    break;
                case NOT_FOUND:
                    ex.message = "服务器异常，请稍后再试";
                    break;
                case REQUEST_TIMEOUT:
                    ex.message = "请求超时";
                    break;
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                    ex.message = "服务器遇到了一个未曾预料的状况，无法完成对请求的处理";
                    break;
                case BAD_REQUEST:
                    /*body = ((HttpException) e).response().errorBody();
                    try {
                        String message = body.string();
                        Gson gson = new Gson();
                        Exception_401DTO exceptionDTO_401 = gson.fromJson(message, Exception_401DTO.class);
                        //[size=106 text={"error":"invalid_token","error_description":"Invalid access tok…]
                        *//**
                 * {"error":"invalid_grant","error_description":"Bad credentials"}
                 *//*
                        if (exceptionDTO_401.getError().toString().equals("invalid_grant")) {
                            ex.message = "用户名或密码错误";
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }*/
                    break;
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                case FAIL_QUEST:
                    body = ((HttpException) e).response().errorBody();
                    try {
                        String message = body.string();
                        Gson gson = new Gson();
                        ErrorBodyDTO globalExceptionDTO = gson.fromJson(message, ErrorBodyDTO.class);
                        if (globalExceptionDTO.getErrMsg() != null) {
                            ex.message = globalExceptionDTO.getErrMsg();
                        } else {
                            ex.message = "";
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    break;
                default:
                    ex.message = "网络错误";
                    break;
            }
            LogUtil.e(ex.message);
            return ex;
        } else if (e instanceof ServerException) {
            ServerException resultException = (ServerException) e;
            ex = new ResponseThrowable(resultException, resultException.code);
            ex.message = resultException.message;
            LogUtil.e(ex.message);
            return ex;
        } else if (e instanceof JsonParseException
                || e instanceof JSONException
                || e instanceof ParseException) {
            ex = new ResponseThrowable(e, ERROR.PARSE_ERROR);
            ex.message = "解析错误";
            LogUtil.e(ex.message);
            return ex;
        } else if (e instanceof ConnectException) {
            ex = new ResponseThrowable(e, ERROR.NETWORD_ERROR);
            ex.message = "连接失败";
            LogUtil.e(ex.message);
            return ex;
        } else if (e instanceof javax.net.ssl.SSLHandshakeException) {
            ex = new ResponseThrowable(e, ERROR.SSL_ERROR);
            ex.message = "证书验证失败";
            LogUtil.e(ex.message);
            return ex;
        } else if (e instanceof java.net.SocketTimeoutException) {
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            //ex.message = "连接超时";
            ex.message = "当前网络连接不顺畅，请稍后再试！";
            LogUtil.e(ex.message);
            return ex;
        } else if (e instanceof java.net.UnknownHostException) {
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message = "网络中断，请检查网络状态！";
            LogUtil.e(ex.message);
            return ex;
        } else if (e instanceof javax.net.ssl.SSLException) {
            ex = new ResponseThrowable(e, ERROR.TIMEOUT_ERROR);
            ex.message = "网络中断，请检查网络状态！";
            LogUtil.e(ex.message);
            return ex;
        } else if (e instanceof java.io.EOFException) {
            ex = new ResponseThrowable(e, ERROR.PARSE_EmptyERROR);
            ex.message = "1007";
            LogUtil.e(ex.message);
            return ex;
        } else if (e instanceof NullPointerException) {
            ex = new ResponseThrowable(e, ERROR.PARSE_EmptyERROR);
            ex.message = "数据为空，显示失败";
            LogUtil.e(ex.message);
            return ex;
        } else {
            ex = new ResponseThrowable(e, ERROR.UNKNOWN);
            ex.message = "未知错误";
            LogUtil.e(ex.message);
            return ex;
        }

    }


    /**
     * 约定异常
     */
    public class ERROR {
        /**
         * 未知错误
         */
        public static final int UNKNOWN = 1000;
        /**
         * 解析错误
         */
        public static final int PARSE_ERROR = 1001;
        /**
         * 解析no content错误
         */
        public static final int PARSE_EmptyERROR = 1007;
        /**
         * 网络错误
         */
        public static final int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
        public static final int HTTP_ERROR = 1003;

        /**
         * 证书出错
         */
        public static final int SSL_ERROR = 1005;

        /**
         * 连接超时
         */
        public static final int TIMEOUT_ERROR = 1006;

        public static final int LOGIN_ERROR = -1000;
        public static final int DATA_EMPTY = -2000;


    }

    public static class ResponseThrowable extends Exception {
        public int code;
        public String message;

        public ResponseThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }

        public ResponseThrowable(String message, int code) {
            this.code = code;
            this.message = message;
        }
    }

    public class ServerException extends RuntimeException {
        public int code;
        public String message;

        public ServerException(int code, String message) {
            this.code = code;
            this.message = message;
        }
    }
}