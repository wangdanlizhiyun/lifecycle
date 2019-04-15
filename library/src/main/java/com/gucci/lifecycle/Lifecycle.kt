package com.gucci.lifecycle

import java.util.*

/**
 * Created by 李志云 2019/4/11 15:28
 */
interface Lifecycle {
    fun addListener(litener: LifecycleListener)
    fun removeListener(litener: LifecycleListener)
    fun getListeners():Set<LifecycleListener>
}