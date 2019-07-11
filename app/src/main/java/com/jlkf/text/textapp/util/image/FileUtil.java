package com.jlkf.text.textapp.util.image;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;


import com.jlkf.text.textapp.app.BaseApplication;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by Captain Jack Sparrow on 2017/8/15.
 */

public class FileUtil {
    public static final String ROOT_DIR = "Android/data/"
        + BaseApplication.getContext().getPackageName();
    public static final String DOWNLOAD_DIR = "download";
    public static final String CACHE_DIR = "cache";
    public static final String ICON_DIR = "icon";

    public static final String APP_STORAGE_ROOT = "AndroidNAdaption";


    /** 判断SD卡是否挂载 */
    public static boolean isSDCardAvailable() {
        if (Environment.MEDIA_MOUNTED.equals(Environment
            .getExternalStorageState())) {
            return true;
        } else {
            return false;
        }
    }

    public static void saveFile(Bitmap bm, String fileName) throws Exception {
//        File dirFile = new File(fileName);
//        //检测图片是否存在
//        if (dirFile.exists()) {
//            dirFile.delete();  //删除原图片
//        }
        File myCaptureFile = new File(fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        //100表示不进行压缩，70表示压缩率为30%
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
    }
    //压缩图片尺寸
    public static Bitmap compressBySize(String pathName) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;// 不去真的解析图片，只是获取图片的头部信息，包含宽高等；
        Bitmap bitmap;
        // 得到图片的宽度、高度；
        float imgWidth = opts.outWidth;
        float imgHeight = opts.outHeight;
        // 分别计算图片宽度、高度与目标宽度、高度的比例；取大于等于该比例的最小整数；
        int widthRatio = (int) Math.ceil(imgWidth / (float) 300);
        int heightRatio = (int) Math.ceil(imgHeight / (float) 300);
        opts.inSampleSize = 1;
        if (widthRatio > 1 || widthRatio > 1) {
            if (widthRatio > heightRatio) {
                opts.inSampleSize = widthRatio;
            } else {
                opts.inSampleSize = heightRatio;
            }
        }
        //设置好缩放比例后，加载图片进内容；
        opts.inJustDecodeBounds = false;
        bitmap = BitmapFactory.decodeFile(pathName, opts);
        return bitmap;
    }
    /** 获取下载目录 */
    public static String getDownloadDir() {
        return getDir(DOWNLOAD_DIR);
    }


    /** 获取缓存目录 */
    public static String getCacheDir() {
        return getDir(CACHE_DIR);
    }


    /** 获取icon目录 */
    public static String getIconDir() {
        return getDir(ICON_DIR);
    }


    /** 获取应用目录，当SD卡存在时，获取SD卡上的目录，当SD卡不存在时，获取应用的cache目录 */
    public static String getDir(String name) {
        StringBuilder sb = new StringBuilder();
        if (isSDCardAvailable()) {
            sb.append(getAppExternalStoragePath());
        } else {
            sb.append(getCachePath());
        }
        sb.append(name);
        sb.append(File.separator);
        String path = sb.toString();
        if (createDirs(path)) {
            return path;
        } else {
            return null;
        }
    }


    /** 获取SD下的应用目录 */
    public static String getExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(ROOT_DIR);
        sb.append(File.separator);
        return sb.toString();
    }


    /** 获取SD下当前APP的目录 */
    public static String getAppExternalStoragePath() {
        StringBuilder sb = new StringBuilder();
        sb.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        sb.append(File.separator);
        sb.append(APP_STORAGE_ROOT);
        sb.append(File.separator);
        return sb.toString();
    }


    /** 获取应用的cache目录 */
    public static String getCachePath() {
        File f =BaseApplication.getContext().getCacheDir();
        if (null == f) {
            return null;
        } else {
            return f.getAbsolutePath() + "/";
        }
    }


    /** 创建文件夹 */
    public static boolean createDirs(String dirPath) {
        File file = new File(dirPath);
        if (!file.exists() || !file.isDirectory()) {
            return file.mkdirs();
        }
        return true;
    }


    /** 产生图片的路径，这里是在缓存目录下 */
    public static String generateImgePathInStoragePath() {
        return getDir(ICON_DIR) + String.valueOf(System.currentTimeMillis()) + ".jpg";
    }


    /**
     * 发起剪裁图片的请求
     *
     * @param activity 上下文
     * @param srcFile 原文件的File
     * @param output 输出文件的File
     * @param requestCode 请求码
     */
    public static void startPhotoZoom(Activity activity, File srcFile, File output, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(getImageContentUri(activity, srcFile), "image/*");
        // crop为true是设置在开启的intent中设置显示的view可以剪裁
        intent.putExtra("crop", "true");

        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);

        // outputX,outputY 是剪裁图片的宽高
        intent.putExtra("outputX", 800);
        intent.putExtra("outputY", 800);
        // intent.putExtra("return-data", false);

        //        intent.putExtra(MediaStore.EXTRA_OUTPUT,
        //                Uri.fromFile(new File(FileUtils.picPath)));

        intent.putExtra("return-data", false);// true:不返回uri，false：返回uri
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(output));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        // intent.putExtra("noFaceDetection", true); // no face detection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        activity.startActivityForResult(intent, requestCode);
    }


    /** 安卓7.0裁剪根据文件路径获取uri */
    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            new String[] { MediaStore.Images.Media._ID },
            MediaStore.Images.Media.DATA + "=? ",
            new String[] { filePath }, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    /**
     * 复制bm
     */
    public static String saveBitmap(Bitmap bm) {
        String croppath = "";
        try {
            File f = new File(FileUtil.generateImgePathInStoragePath());
            //得到相机图片存到本地的图片
            croppath = f.getPath();
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return croppath;
    }


    /**
     * 按质量压缩bm
     *
     * @param quality 压缩率
     */
    public static String saveBitmapByQuality(Bitmap bm, int quality) {
        String croppath = "";
        try {
            File f = new File(FileUtil.generateImgePathInStoragePath());
            //得到相机图片存到本地的图片
            croppath = f.getPath();
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream out = new FileOutputStream(f);
            bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return croppath;
    }


    /**
     * 将序列化对象保存在文件中
     * 这个也是需要文件读写权限的
     *
     * @param fileName 优先选择类名，且一个类只保存一个对象
     */
    public static void saveSerializableObj(String fileName, Serializable obj, Context context) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(obj);

        } catch (Exception e) {
            //保存文件异常
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @param fileName 传文件名获取其保存在文件中的对象，在使用中我使用类名
     * @return Serializable
     */
    public static Object readSerializableObj(String fileName, Context context) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;

        try {
            fis = context.openFileInput(fileName);
            ois = new ObjectInputStream(fis);
            return ois.readObject();
        } catch (Exception e) {
            //读取文件异常
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;

    }


    /**
     * 若file或者content为空则不执行操作
     *
     * @param file 文件对像
     * @param content 写入文件的内容
     */
    public static void writeTextToFile(File file, String content) {
        if (file != null || !TextUtils.isEmpty(content)) {
            writeTextToFile(file.getAbsolutePath(), content);
        }
    }


    public static void writeTextToFile(File file, String content, boolean flag) {
        if (file != null || !TextUtils.isEmpty(content)) {
            writeTextToFile(file.getAbsolutePath(), content, flag);
        }
    }


    public static String readContentFromFile(File f) {
        if (f != null) {
            return readContentFromFile(f.getAbsolutePath());
        }
        return null;
    }


    public static void writeTextToFile(String fileName, String content) {
        writeTextToFile(fileName, content, false);
    }


    public static void writeTextToFile(String fileName, String content, boolean flag) {
        FileWriter fileWriter = null;
        BufferedWriter bwriter = null;

        try {
            fileWriter = new FileWriter(fileName, flag);
            bwriter = new BufferedWriter(fileWriter);
            bwriter.write(content);
            bwriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bwriter != null) {
                try {
                    bwriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @param fileName 被读取的文件的路径
     * @return 成功返回正常String，否则返回null
     */
    public static String readContentFromFile(String fileName) {
        FileReader fReader = null;
        BufferedReader bReader = null;

        try {
            fReader = new FileReader(fileName);
            bReader = new BufferedReader(fReader);
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = bReader.readLine()) != null) {
                sb.append(str);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (fReader != null) {
                try {
                    fReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bReader != null) {
                try {
                    bReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * @param dir 文件目录地址
     * @param fileName 文件名
     * @return 成功则返回File对象，否则返回null
     */
    public static File createNewFile(String dir, String fileName) {
        if (TextUtils.isEmpty(dir) || TextUtils.isEmpty(fileName)) {
            return null;
        }
        File fdir = new File(dir);
        if (!fdir.exists()) {
            fdir.mkdirs();
        }
        File file = new File(fdir, fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
                return file;
            } catch (IOException e) {
                file = null;
                e.printStackTrace();
            }
        }
        return file;
    }
}
