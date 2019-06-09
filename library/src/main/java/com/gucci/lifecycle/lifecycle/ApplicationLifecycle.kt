package com.gucci.lifecycle.lifecycle

import com.gucci.lifecycle.LifecycleListener
import com.gucci.lifecycle.annotations.OnStart
import com.gucci.lifecycle.util.LifecycleUtil
import java.util.*

/**
 * 留待拆解glide使用
 */
class ApplicationLifecycle : Lifecycle {
    override fun getListeners(): Set<LifecycleListener> {
        return lifecycleListeners
    }

    //建议使用它时需要监听application的低电量状态onLowMemory时清空之
    private val lifecycleListeners = Collections.newSetFromMap(WeakHashMap<LifecycleListener, Boolean>())

    override fun addListener(litener: LifecycleListener) {
        LifecycleUtil.doAction(litener,OnStart::class.java)
        lifecycleListeners.add(litener)
    }

    override fun removeListener(litener: LifecycleListener) {
    }

    fun clear() {
        lifecycleListeners.clear()
    }
}