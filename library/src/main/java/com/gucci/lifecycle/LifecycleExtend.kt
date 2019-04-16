package com.gucci.lifecycle

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import java.util.ArrayList

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


fun <T> Collection<T>.getSnapshot():List<T>{
    val result = ArrayList<T>(this.size)
    for (item in this) {
        if (item != null) {
            result.add(item)
        }
    }
    return result
}

