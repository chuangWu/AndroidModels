package cn.chuangblog.download;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-09-09 16:36
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public interface ActionConstant {


    String ACTION_START = "ACTION_START";
    String ACTION_STOP = "ACTION_STOP";


    interface Broadcast {
        String ACTION_UPDATE = "ACTION_UPDATE";
        String ACTION_FINISH = "ACTION_FINISH";
    }
}
