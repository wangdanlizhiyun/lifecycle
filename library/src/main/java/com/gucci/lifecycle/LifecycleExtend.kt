package com.gucci.lifecycle

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity

/**
 * Created by 李志云 2019/1/2 17:05
 */
fun LifecycleListener.watch(activity:FragmentActivity) {
    ManagerRetriever.get(activity).addListener(this)
}
fun LifecycleListener.watch(activity: Activity) {
    ManagerRetriever.get(activity).addListener(this)
}
fun LifecycleListener.watch(fragment: Fragment) {
    ManagerRetriever.get(fragment).addListener(this)
}
fun LifecycleListener.watch(fragment: android.app.Fragment) {
    ManagerRetriever.get(fragment).addListener(this)
}

//var lifecycle: Lifecycle? = null

