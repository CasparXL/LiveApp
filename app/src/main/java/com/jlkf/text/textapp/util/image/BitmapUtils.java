package com.jlkf.text.textapp.util.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import java.io.ByteArrayOutputStream;

/**
 * "浪小白" 创建 2019/6/19.
 * 界面名称以及功能:
 */

public class BitmapUtils {
    /**
     * byte[] → Bitmap
     *
     * @return
     */
    public static Bitmap BytesToBitmap(byte[] b) {
        if (b.length != 0) {
            return BitmapFactory.decodeByteArray(b, 0, b.length);
        } else {
            return null;
        }
    }

    /**
     * Bitmap → byte[]
     *
     * @return
     */
    public static byte[] Bitmap2Bytes(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }

    /**
     * 缩放大小
     *
     * @param bitmap
     * @param newWidth
     * @param newHeight
     * @return
     */
    public static Bitmap getNewBitmap(Bitmap bitmap, int newWidth, int newHeight) {
        // 获得图片的宽高.
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算缩放比例.
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数.
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片.
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    /**
     * 清除背景颜色
     *
     * @param mBitmap
     * @param mColor  背景颜色值 eg：Color.WHITE
     * @return
     */
    public static Bitmap getAlphaBitmap(Bitmap mBitmap, int mColor) {
        Bitmap mAlphaBitmap = Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        int mBitmapWidth = mAlphaBitmap.getWidth();
        int mBitmapHeight = mAlphaBitmap.getHeight();

        for (int i = 0; i < mBitmapHeight; i++) {
            for (int j = 0; j < mBitmapWidth; j++) {
                int color = mBitmap.getPixel(j, i);
                if (color != mColor) {
                    mAlphaBitmap.setPixel(j, i, color);
                }
            }
        }

        return mAlphaBitmap;
    }


}
