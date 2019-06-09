package com.gucci.lifecycle.lifecycle

import com.gucci.lifecycle.LifecycleListener

interface Lifecycle {
    fun addListener(litener: LifecycleListener)
    fun removeListener(litener: LifecycleListener)
    fun getListeners():Set<LifecycleListener>
}