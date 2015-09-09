package cn.chuangblog.download.adapter;

import android.content.Context;

import java.util.List;

import cn.chuangblog.download.entities.FileInfo;


public class FileAdapter extends CommonAdapter<FileInfo> {

    public FileAdapter(Context context, List<FileInfo> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(CommonHolder holder, FileInfo fileInfo) {

    }
}
