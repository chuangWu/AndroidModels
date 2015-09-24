package cn.chuangblog.download.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-09-24 10:13
 * @description : none
 * @for your attention : http://blog.csdn.net/lmj623565791/article/details/47143563
 * @revise : none
 */
public class DownloadIntentService extends IntentService {
    public DownloadIntentService() {
        super("download game service");
    }

    public static void startDownload(Context context, String path) {
        Intent intent = new Intent(context, DownloadIntentService.class);
        intent.setAction("action");
        intent.putExtra("path", path);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if ("action".equals(action)) {
                final String path = intent.getStringExtra("path");
                handleDownloadTask(path);
            }
        }
    }

    private void handleDownloadTask(String path) {
        try {
            Thread.sleep(3000);

            Intent intent = new Intent("result");
            intent.putExtra("download_path", path);
            sendBroadcast(intent);


        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
