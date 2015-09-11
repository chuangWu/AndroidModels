package cn.chuangblog.download.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import cn.chuangblog.download.ActionConstant;
import cn.chuangblog.download.R;
import cn.chuangblog.download.entities.FileInfo;
import cn.chuangblog.download.service.DownloadService;


public class FileAdapter extends CommonAdapter<FileInfo> {

    public FileAdapter(Context context, List<FileInfo> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(CommonHolder holder, final FileInfo fileInfo) {
        holder.setText(R.id.fileName, fileInfo.getFileName());
        holder.setProgress(R.id.progressBar, fileInfo.getFinished());




        holder.setOnClickListener(R.id.start, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(mContext, DownloadService.class);
                intent1.setAction(ActionConstant.ACTION_START);
                intent1.putExtra("fileInfo", fileInfo);
                mContext.startService(intent1);
            }
        });

        holder.setOnClickListener(R.id.stop, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(mContext, DownloadService.class);
                intent2.setAction(ActionConstant.ACTION_STOP);
                intent2.putExtra("fileInfo", fileInfo);
                mContext.startService(intent2);
            }
        });
    }


    public void updateProgress(int id, int progress) {
        FileInfo fileInfo = getDataList().get(id);
        fileInfo.setFinished(progress);
        notifyDataSetChanged();
    }
}
