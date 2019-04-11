package com.gucci.lifecycle

/**
 * Created by 李志云 2019/4/11 16:31
 * 留待拆解glide使用
 */
class ApplicationLifecycle: Lifecycle {
    override fun addListener(litener: LifecycleListener) {
        litener.onStart()
    }

    override fun removeListener(litener: LifecycleListener) {
    }
}