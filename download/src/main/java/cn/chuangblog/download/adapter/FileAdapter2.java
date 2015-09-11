package cn.chuangblog.download.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.File;
import java.util.List;

import cn.chuangblog.download.ActionConstant;
import cn.chuangblog.download.R;
import cn.chuangblog.download.entities.FileInfo;
import cn.chuangblog.download.service.DownloadService;


public class FileAdapter2 extends CommonAdapter<FileInfo> {

    public FileAdapter2(Context context, List<FileInfo> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(CommonHolder holder, final FileInfo fileInfo) {
        holder.setText(R.id.fileName, fileInfo.getFileName());

        TextView download = holder.getView(R.id.download);
        if (download.getTag().equals("download")) {
            download.setText("下载");
        } else if (download.getTag().equals("progress")) {
            download.setText(fileInfo.getFinished() + "%");
            if (fileInfo.getFinished() == 100) {
                download.setText("安装");
                download.setTag("install");
            }
        } else if (download.getTag().equals("pause")) {
            download.setText("暂停");
            download.setTag("download");
            Intent intent2 = new Intent(mContext, DownloadService.class);
            intent2.setAction(ActionConstant.ACTION_STOP);
            intent2.putExtra("fileInfo", fileInfo);
            mContext.startService(intent2);

        }

        holder.setOnClickListener(R.id.download, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getTag().equals("download")) {
                    Intent intent1 = new Intent(mContext, DownloadService.class);
                    intent1.setAction(ActionConstant.ACTION_START);
                    intent1.putExtra("fileInfo", fileInfo);
                    mContext.startService(intent1);
                    view.setTag("progress");
                    notifyDataSetChanged();
                } else if (view.getTag().equals("progress")) {
                    view.setTag("pause");
                    notifyDataSetChanged();
                } else if (view.getTag().equals("pause")) {
                    view.setTag("progress");
                    notifyDataSetChanged();
                } else if (view.getTag().equals("install")) {
                    Intent intent1 = new Intent(Intent.ACTION_VIEW);
                    Uri uri = Uri.fromFile(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/download/", fileInfo.getFileName()));
                    Log.e("TAG", uri.toString());
                    intent1.setDataAndType(uri, "application/vnd.android.package-archive");
                    mContext.startActivity(intent1);
                }


            }
        });


    }


    public void updateProgress(int id, int progress) {
        FileInfo fileInfo = getDataList().get(id);
        fileInfo.setFinished(progress);
        notifyDataSetChanged();
    }
}
