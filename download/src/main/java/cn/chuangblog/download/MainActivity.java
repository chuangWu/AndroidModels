package cn.chuangblog.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.chuangblog.download.entities.FileInfo;
import cn.chuangblog.download.service.DownloadService;

public class MainActivity extends AppCompatActivity {

    public static final String URL1 = "http://m.apk.67mo.com/apk/998305_21439281_1437163738280.apk";
    public static final String URL2 = "http://www.apk.anzhi.com/data3/apk/201508/18/5a9453096a155812d7856c5ae25542ce_17577300.apk";
    public static final String URL3 = "http://www.apk.anzhi.com/data3/apk/201508/13/4da55645ab13dcb3c8f7368126aa76bc_55964500.apk";

    @Bind(R.id.progressBar)
     ProgressBar progressBar;

    FileInfo fileInfo;

    BroadcastReceiver receiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        progressBar.setMax(100);
        fileInfo = new FileInfo(0, URL1, "998305_21439281_1437163738280.apk", 0, 0);


        IntentFilter intentFilter = new IntentFilter(ActionConstant.Broadcast.ACTION_UPDATE);


        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int finish = (int) intent.getLongExtra("finished", 0);
                Log.e("TAG",finish+"");
                progressBar.setProgress(finish);
            }
        };

        registerReceiver(receiver, intentFilter);





    }


    @OnClick({R.id.start, R.id.stop})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                Intent intent1 = new Intent(this, DownloadService.class);
                intent1.setAction(ActionConstant.ACTION_START);
                intent1.putExtra("fileInfo", fileInfo);
                startService(intent1);

                break;
            case R.id.stop:
                Intent intent2 = new Intent(this, DownloadService.class);
                intent2.setAction(ActionConstant.ACTION_STOP);
                intent2.putExtra("fileInfo", fileInfo);
                startService(intent2);

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
