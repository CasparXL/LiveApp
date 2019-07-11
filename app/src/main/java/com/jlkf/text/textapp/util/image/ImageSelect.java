package com.jlkf.text.textapp.util.image;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;


import com.jlkf.text.textapp.app.BaseApplication;

import java.io.File;

/**
 * 相机相册
 */

public class ImageSelect {
    public static final int TAKE_PHOTO = 0x00;
    public static final int CHOOSE_PHOTO = 0x01;

    /**
     * 相机
     */
    public static void doOpenCamera(final Activity context, final String img) {
        File imgFile = new File(img);
        Uri imgUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            //如果是7.0或以上
            imgUri = FileProvider.getUriForFile(context,
                    BaseApplication.getContext().getPackageName() + ".fileprovider", imgFile);//fileprovider
        } else {
            imgUri = Uri.fromFile(imgFile);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        context.startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 相册
     */
    public static void doOpenAlbum(final Activity context) {
        //"android.intent.action.PICK",MediaStore.Images.Media.EXTERNAL_CONTENT_URI

        /**
         * Intent intent = new Intent();
         * if (Build.VERSION.SDK_INT < 19) {
         * intent.setAction(Intent.ACTION_GET_CONTENT);
         *intent.setType("image/*");
         *} else {
         * intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
         *intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
         * }
         * context.startActivityForResult(intent, CHOOSE_PHOTO);
         */

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        context.startActivityForResult(intent, CHOOSE_PHOTO);


    }


    private final static String DEFAULT_APK_PATH = "/Biscuit/";
    private final static String DEFAULT_IMAGE_PATH = "image/";

    public static String getImageDir() {
        String root = getAppPath() + DEFAULT_IMAGE_PATH;
        File file = new File(root);
        if (!file.exists()) file.mkdir();
        return root;
    }

    /**
     * get app root path
     *
     * @return
     */
    public static String getAppPath() {
        String path = null;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory() + DEFAULT_APK_PATH;
        }
        File file = new File(path);
        if (!file.exists()) file.mkdir();
        return path;
    }
}
