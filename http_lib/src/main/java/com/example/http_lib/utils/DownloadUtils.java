package com.example.http_lib.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.yidao.module_lib.config.Config;
import com.yidao.module_lib.entity.LocalMusicBean;
import com.yidao.module_lib.entity.LocalVideoBean;
import com.yidao.module_lib.utils.FileUtils;
import com.yidao.module_lib.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class DownloadUtils {

    public static JSONArray copyMVToSDCard(Context context) {
        try {
            File dir = new File(Environment.getExternalStorageDirectory() + "/ShortVideo/mvs");
            // copy mv assets to sdcard
            if (!dir.exists()) {
                dir.mkdirs();
                String[] fs = context.getAssets().list("mvs");
                for (String file : fs) {
                    InputStream is = context.getAssets().open("mvs/" + file);
                    FileOutputStream fos = new FileOutputStream(new File(dir, file));
                    byte[] buffer = new byte[1024];
                    int byteCount;
                    while ((byteCount = is.read(buffer)) != -1) {
                        fos.write(buffer, 0, byteCount);
                    }
                    fos.flush();
                    is.close();
                    fos.close();
                }
            }

            FileReader jsonFile = new FileReader(new File(dir, "plsMVs.json"));
            StringBuilder sb = new StringBuilder();
            int read;
            char[] buf = new char[2048];
            while ((read = jsonFile.read(buf, 0, 2048)) != -1) {
                sb.append(buf, 0, read);
            }
            JSONObject json = new JSONObject(sb.toString());
            return json.getJSONArray("MVs");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static File saveBitmapToLocal(Bitmap bitmap) {
        try {
            File file = new File(Config.PHOTO_STORAGE_DIR);
            if (!file.exists()) {
                file.mkdirs();
            }
            File imageFile = new File(Config.PHOTO_STORAGE_DIR + "cover_"+System.currentTimeMillis()+".jpg");
            FileOutputStream out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            return imageFile;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new File("");
    }

    // 获取视频缩略图
    public static Bitmap getVideoThumbnail(String filePath) {
        Bitmap b = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(filePath);
            b = retriever.getFrameAtTime();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (RuntimeException e) {
            e.printStackTrace();

        } finally {
            try {
                retriever.release();
            } catch (RuntimeException e) {
                e.printStackTrace();
            }
        }
        return b;
    }

    public static String getTimeShort(long milliseconds, String format) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        Date currentTime = new Date(milliseconds);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 获取本机视频列表
     *
     * @return
     */
    public static List<LocalVideoBean> getLocalVideoList(Context context) {

        List<LocalVideoBean> videos = new ArrayList<LocalVideoBean>();

        Cursor c = null;
        try {
            // String[] mediaColumns = { "_id", "_data", "_display_name",
            // "_size", "date_modified", "duration", "resolution" };
            ContentResolver mContentResolver = context.getContentResolver();
            c = mContentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Video.Media.DEFAULT_SORT_ORDER);
            while (c.moveToNext()) {
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DATA));// 路径
                if (!FileUtils.isExists(path)) {
                    continue;
                }

                int id = c.getInt(c.getColumnIndexOrThrow(MediaStore.Video.Media._ID));// 视频的id
//                Bitmap bp = getVideoThumbnail(context,id);
                String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME)); // 视频名称
                long duration = c.getLong(c.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION));// 时长
                LocalVideoBean video = new LocalVideoBean(id, name, path, duration);
                videos.add(video);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return videos;
    }


    // 根据id获取视频缩略图
    public static Bitmap getVideoThumbnail(Context context, int id) {
        ContentResolver mContentResolver = context.getContentResolver();
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inDither = false;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        bitmap = MediaStore.Video.Thumbnails.getThumbnail(mContentResolver, id, MediaStore.Images.Thumbnails.MICRO_KIND, options);
        return bitmap;
    }

    /**
     * 获取本机音乐列表
     *
     * @return
     */
    public static List<LocalMusicBean> getMusics(Context context) {
        ArrayList<LocalMusicBean> musics = new ArrayList<>();
        Cursor c = null;
        try {
            c = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                    MediaStore.Audio.Media.DEFAULT_SORT_ORDER);

            while (c != null && c.moveToNext()) {
                String path = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));// 路径

                if (!FileUtils.isExists(path)) {
                    continue;
                }
                int duration = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));// 时长
                if (duration < 1000) {
                    continue;
                }

                String name = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME)); // 歌曲名
                String album = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)); // 专辑
                String artist = c.getString(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)); // 作者
                long size = c.getLong(c.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));// 大小
                int time = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));// 歌曲的id
                // int albumId = c.getInt(c.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM_ID));

                LocalMusicBean music = new LocalMusicBean(name, path, duration);
                musics.add(music);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close();
            }
        }
        return musics;
    }

    /**
     * 强制刷新本地数据库数据
     *
     * @param context
     */
    public static void refreshMedia(Context context) {
        MediaScannerConnection.scanFile(context, new String[]{Environment.getExternalStorageDirectory().getAbsolutePath()}, null, null);
    }


    public static void downloadFile(final String url, final OnDownloadListener listener){
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (!url.startsWith("http")) {
            return;
        }
        if (!FileUtils.isExists(Config.PHOTO_STORAGE_DOWMLOAD_DIR)) {
            new File(Config.PHOTO_STORAGE_DOWMLOAD_DIR).mkdirs();
        }
        String[] str = url.split("/");
        String fileName = str[str.length-1];
        final String filePath = Config.PHOTO_STORAGE_DOWMLOAD_DIR + fileName;
        if (FileUtils.isExists(filePath)) {
            listener.onComplete(filePath);
            return;
        }
        Request request = new Request.Builder().url(url).build();
        Callback downCallBack = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("onFailure=" + e.getMessage());
                listener.onFail();
                if (FileUtils.isExists(filePath)) {
                    FileUtils.deleteFile(filePath);
                    return;
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(filePath);

                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        float progress = (sum * 1.0f / total);
                        // 下载中
                        listener.onProgress(progress);
                    }
                    fos.flush();
                    // 下载完成
                    listener.onComplete(filePath);
                    LogUtils.d("onComplete");
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onFail();
                    if (FileUtils.isExists(filePath)) {
                        FileUtils.deleteFile(filePath);
                        return;
                    }
                    LogUtils.d("onFail=" + e.getMessage());
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        };
        Call call = new OkHttpClient().newCall(request);
        call.enqueue(downCallBack);
    }


    public static void download(final String url, final OnDownloadListener listener) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (!FileUtils.isExists(Config.PHOTO_STORAGE_DOWMLOAD_DIR)) {
            new File(Config.PHOTO_STORAGE_DOWMLOAD_DIR).mkdirs();
        }
        boolean isMp4 = url.endsWith(".mp4");
        String[] str = url.split("/");
        String endPath = isMp4 ? str[str.length - 1] : str[str.length - 1] + ".mp4";
        final String videoPath = Config.PHOTO_STORAGE_DOWMLOAD_DIR + endPath;
        if (FileUtils.isExists(videoPath)) {
            listener.onComplete(videoPath);
            return;
        }
        Request request = new Request.Builder().url(url).build();
        Callback downCallBack = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("onFailure=" + e.getMessage());
                listener.onFail();
                if (FileUtils.isExists(videoPath)) {
                    FileUtils.deleteFile(videoPath);
                    return;
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(videoPath);

                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        float progress = (sum * 1.0f / total);
                        // 下载中
                        listener.onProgress(progress);
                    }
                    fos.flush();
                    // 下载完成
                    listener.onComplete(videoPath);
                    LogUtils.d("onComplete");
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onFail();
                    if (FileUtils.isExists(videoPath)) {
                        FileUtils.deleteFile(videoPath);
                        return;
                    }
                    LogUtils.d("onFail=" + e.getMessage());
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        };
        Call call = new OkHttpClient().newCall(request);
        call.enqueue(downCallBack);
    }

    private Call call;

    public void downloadApk(final String url, final OnDownloadListener listener) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        if (!FileUtils.isExists(Config.PHOTO_STORAGE_DOWMLOAD_DIR)) {
            new File(Config.PHOTO_STORAGE_DOWMLOAD_DIR).mkdirs();
        }

        String[] str = url.split("/");
        String endPath =  str[str.length - 1];
        final String videoPath = Config.PHOTO_STORAGE_DOWMLOAD_DIR + endPath;
        if (FileUtils.isExists(videoPath)) {
            FileUtils.deleteFile(videoPath);
        }
        Request request = new Request.Builder().url(url).build();
        Callback downCallBack = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFail();
                if (FileUtils.isExists(videoPath)) {
                    FileUtils.deleteFile(videoPath);
                    return;
                }
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                // 储存下载文件的目录
                try {
                    is = response.body().byteStream();
                    long total = response.body().contentLength();
                    File file = new File(videoPath);

                    fos = new FileOutputStream(file);
                    long sum = 0;
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                        sum += len;
                        float progress = (sum * 1.0f / total);
                        // 下载中
                        listener.onProgress(progress);
                    }
                    fos.flush();
                    // 下载完成
                    listener.onComplete(videoPath);
                } catch (Exception e) {
                    e.printStackTrace();
                    listener.onFail();
                    if (FileUtils.isExists(videoPath)) {
                        FileUtils.deleteFile(videoPath);
                        return;
                    }
                    LogUtils.d("onFail=" + e.getMessage());
                } finally {
                    try {
                        if (is != null)
                            is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null)
                            fos.close();
                    } catch (IOException e) {
                    }
                }
            }
        };
        call = new OkHttpClient().newCall(request);
        call.enqueue(downCallBack);
    }

    public void cancelDownLoad(){
        if(call!=null && !call.isCanceled()){
            call.cancel();
        }
    }


    public interface OnDownloadListener {
        void onComplete(String fileName);

        void onProgress(float progress);

        void onFail();
    }
}
