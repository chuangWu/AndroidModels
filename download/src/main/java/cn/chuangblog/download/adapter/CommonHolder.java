package cn.chuangblog.download.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-09-09 16:16
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public class CommonHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;

    public CommonHolder(Context context, int position, ViewGroup parent, int layoutId) {
        this.mPosition = position;
        this.mViews = new SparseArray<View>();
        mConvertView = View.inflate(context, layoutId, null);
        mConvertView.setTag(this);
    }

    public static CommonHolder get(Context context, int position, View convertView, ViewGroup parent, int layoutId) {
        if (convertView == null) {
            return new CommonHolder(context, position, parent, layoutId);
        } else {
            CommonHolder holder = (CommonHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    public View getConvertView() {
        return mConvertView;
    }

    public <T extends View> T getView(int resId) {
        View view = mViews.get(resId);
        if (view == null) {
            view = mConvertView.findViewById(resId);
            mViews.put(resId, view);
        }
        return (T) view;
    }


    public CommonHolder setText(int resId, String text) {
        TextView textView = getView(resId);
        textView.setText(text);
        return this;
    }

    public CommonHolder setImageResource(int resId, int imgId) {
        ImageView imageView = getView(resId);
        imageView.setImageResource(imgId);
        return this;
    }

    public CommonHolder setImageURL(int resId, String url) {
        ImageView imageView = getView(resId);
        //   ImageLoader.getInstance().loadImage(iamgeView,url);
        return this;
    }

    public int getPosition() {
        return mPosition;
    }

    public CommonHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }


    public EditText getEditText(int resId) {
        EditText editText = getView(resId);
        return editText;
    }
}
