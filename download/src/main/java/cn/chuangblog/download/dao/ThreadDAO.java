package cn.chuangblog.download.dao;

import java.util.List;

import cn.chuangblog.download.entities.ThreadInfo;

/**
 * @author : created by chuangWu
 * @version : 0.01
 * @email : chuangwu127@gmail.com
 * @created time : 2015-09-09 17:19
 * @description : none
 * @for your attention : none
 * @revise : none
 */
public interface ThreadDAO {

    /**
     * 插入
     *
     * @param threadInfo
     */
    public void insertThread(ThreadInfo threadInfo);


    /**
     * 删除
     *
     * @param url
     */
    public void deleteThread(String url);

    /**
     * 更新
     *
     * @param url
     * @param thread_id
     */
    public void updateThread(String url, int thread_id, int finished);


    /**
     * 查询
     *
     * @param url
     * @return
     */
    public List<ThreadInfo> getThreads(String url);

    /**
     * 线程信息是否存在
     *
     * @param url
     * @param thread_id
     * @return
     */
    public boolean isExists(String url, int thread_id);

}
