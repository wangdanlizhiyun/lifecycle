package com.gucci.lifecycle

import android.support.v4.app.FragmentActivity

/**
 * Created by 李志云 2019/1/2 17:05
 */
fun LifecycleListener.watch(activity:FragmentActivity) {
    ManagerRetriever.get(activity).addListener(this)
}

//var lifecycle: Lifecycle? = null

