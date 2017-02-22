package com.walle.progressbar;

/**
 * 进度条进度的监听
 * Created by Wall-E on 2017/2/15.
 */

public interface OnProgressBarListener {
    /**
     * 进度条进度变化
     * @param current 当前进度
     * @param max 最大进度
     */
    void onProgressChange(int current, int max);
}
