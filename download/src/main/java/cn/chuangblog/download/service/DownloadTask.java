package cn.chuangblog.download.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import cn.chuangblog.download.ActionConstant;
import cn.chuangblog.download.dao.ThreadDAO;
import cn.chuangblog.download.dao.ThreadDAOImpl;
import cn.chuangblog.download.entities.FileInfo;
import cn.chuangblog.download.entities.ThreadInfo;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-09-09 17:44
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class DownloadTask {

    private FileInfo fileInfo;
    private Context context;
    private ThreadDAO threadDAO;
    private int finish = 0;
    private boolean pause = false;

    public DownloadTask(FileInfo fileInfo, Context context) {
        this.fileInfo = fileInfo;
        this.context = context;
        threadDAO = new ThreadDAOImpl(context);
    }


    public void download() {
        List<ThreadInfo> threadInfos = threadDAO.getThreads(fileInfo.getUrl());
        ThreadInfo threadInfo = null;
        if (threadInfos.size() == 0) {
            threadInfo = new ThreadInfo(0, fileInfo.getUrl(), 0, fileInfo.getLength(), 0);
        } else {
            threadInfo = threadInfos.get(0);
        }

        new DownloadThread(threadInfo).start();

    }


    class DownloadThread extends Thread {

        private ThreadInfo threadInfo;

        public DownloadThread(ThreadInfo threadInfo) {
            this.threadInfo = threadInfo;
        }

        @Override
        public void run() {
            if (!threadDAO.isExists(threadInfo.getUrl(), threadInfo.getId())) {
                threadDAO.insertThread(threadInfo);
            }

            HttpURLConnection conn = null;
            RandomAccessFile raf = null;
            InputStream is = null;
            try {
                URL url = new URL(threadInfo.getUrl());
                conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(5000);
                conn.setRequestMethod("GET");
                int start = threadInfo.getStart() + threadInfo.getFinished();
                conn.setRequestProperty("Range", "bytes=" + start + "-" + threadInfo.getEnd());

                File file = new File(DownloadService.DOWNLOAD_PATH, fileInfo.getFileName());
                raf = new RandomAccessFile(file, "rwd");
                raf.seek(start);

                Intent intent = new Intent(ActionConstant.Broadcast.ACTION_UPDATE);
                finish += threadInfo.getFinished();
                if (conn.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT) {
                    is = conn.getInputStream();

                    byte[] buf = new byte[1024 * 4];
                    int len = -1;
                    long time = System.currentTimeMillis();

                    while ((len = is.read(buf)) != -1) {
                        raf.write(buf, 0, len);
                        finish += len;

                        if (System.currentTimeMillis() - time > 500) {
                            time = System.currentTimeMillis();
                            intent.putExtra("finished", finish * 100 / fileInfo.getLength());
                            context.sendBroadcast(intent);
                        }


                        if (pause) {
                            threadDAO.updateThread(threadInfo.getUrl(), threadInfo.getId(), finish);
                            return;
                        }
                    }

                    threadDAO.deleteThread(threadInfo.getUrl(), threadInfo.getId());
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    conn.disconnect();
                    is.close();
                    raf.close();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean isPause() {
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
