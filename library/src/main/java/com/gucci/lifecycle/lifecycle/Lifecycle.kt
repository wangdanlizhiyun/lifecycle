package com.gucci.lifecycle.lifecycle

import com.gucci.lifecycle.LifecycleListener

/**
 * Created by 李志云 2019/4/11 15:28
 */
interface Lifecycle {
    fun addListener(litener: LifecycleListener)
    fun removeListener(litener: LifecycleListener)
    fun getListeners():Set<LifecycleListener>
}