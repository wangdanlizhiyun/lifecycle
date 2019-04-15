package com.gucci.lifecycle

import java.util.*

/**
 * Created by 李志云 2019/4/11 16:31
 * 留待拆解glide使用
 */
class ApplicationLifecycle: Lifecycle {
    override fun getListeners(): Set<LifecycleListener> {
        return lifecycleListeners
    }

    //建议使用它时需要监听application的低电量状态onLowMemory时清空之
    private val lifecycleListeners = Collections.newSetFromMap(WeakHashMap<LifecycleListener,Boolean>())
    override fun addListener(litener: LifecycleListener) {
        litener.onStart()
        lifecycleListeners.add(litener)
    }

    override fun removeListener(litener: LifecycleListener) {
    }
    fun clear(){
        lifecycleListeners.clear()
    }
}