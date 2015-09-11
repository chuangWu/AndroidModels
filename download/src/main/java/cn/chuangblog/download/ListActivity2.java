package cn.chuangblog.download;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.chuangblog.download.adapter.FileAdapter;
import cn.chuangblog.download.adapter.FileAdapter2;
import cn.chuangblog.download.entities.FileInfo;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-09-10 10:21
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class ListActivity2 extends Activity {


    public static final String URL1 = "http://m.apk.67mo.com/apk/998305_21439281_1437163738280.apk";
    public static final String URL2 = "http://www.apk.anzhi.com/data3/apk/201508/18/5a9453096a155812d7856c5ae25542ce_17577300.apk";
    public static final String URL3 = "http://www.apk.anzhi.com/data3/apk/201508/13/4da55645ab13dcb3c8f7368126aa76bc_55964500.apk";
    public static final String URL4 = "http://www.apk.anzhi.com/data3/apk/201509/08/f133f96f1b2526a369e3ed3836215035_93579000.apk";
    public static final String URL5 = "http://qd.shouji.360tpcdn.com/nqapk/lm_117936/150701/f8c02ce68b12f5848f1f9fc68c9da51c/cn.mobage.g12000128.a360_20150610.apk";


    @Bind(R.id.listView)
    ListView listView;

    List<FileInfo> lists;
    FileAdapter2 adapter;
    BroadcastReceiver receiver = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ac_list);
        ButterKnife.bind(this);

        lists = new ArrayList<FileInfo>();

        lists.add(new FileInfo(0, URL1, "998305_21439281_1437163738280.apk", 0, 0));
        lists.add(new FileInfo(1, URL2, "5a9453096a155812d7856c5ae25542ce_17577300.apk", 0, 0));
        lists.add(new FileInfo(2, URL3, "4da55645ab13dcb3c8f7368126aa76bc_55964500.apk", 0, 0));
        lists.add(new FileInfo(3, URL4, "f133f96f1b2526a369e3ed3836215035_93579000.apk", 0, 0));
        lists.add(new FileInfo(4, URL5, "cn.mobage.g12000128.a360_20150610.apk", 0, 0));

        adapter = new FileAdapter2(this, lists, R.layout.item_file2);

        listView.setAdapter(adapter);
        registerReceiver();

    }


    public void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ActionConstant.Broadcast.ACTION_UPDATE);
        intentFilter.addAction(ActionConstant.Broadcast.ACTION_FINISH);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ActionConstant.Broadcast.ACTION_UPDATE)) {
                    int finish = (int) intent.getLongExtra("finished", 0);
                    Log.e("TAG", finish + "");
                    int id = intent.getIntExtra("id", 0);
                    adapter.updateProgress(id, finish);
                } else if (intent.getAction().equals(ActionConstant.Broadcast.ACTION_FINISH)) {
                    FileInfo fileInfo = (FileInfo) intent.getSerializableExtra("fileInfo");
                    adapter.updateProgress(fileInfo.getId(), 100);
                    Toast.makeText(ListActivity2.this, lists.get(fileInfo.getId()).getFileName() + "下载完成", Toast.LENGTH_SHORT).show();



                }
            }
        };
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
