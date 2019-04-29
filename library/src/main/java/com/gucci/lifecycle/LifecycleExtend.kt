package com.gucci.lifecycle

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.util.Log
import java.util.ArrayList

/**
 * Created by 李志云 2019/1/2 17:05
 * @see #watch(android.app.Activity)
 * @see #watch(android.app.Fragment)
 * @see #watch(android.support.v4.app.Fragment)
 * @see #watch(android.support.v4.app.FragmentActivity)
 * @see #watch(android.support.v4.app.DialogFragment)
 * @see #watch(android.app.Dialog) 注意：监听Dialog时watch调用必须在setOnDismissListener和setOnShowListener之前，
 * 并且对于dialog而言只有oncreate和onDestroy
 */
fun LifecycleListener.watch(activity:FragmentActivity) {
    ManagerRetriever.get(activity).addListener(this)
}
fun LifecycleListener.watch(activity: Activity) {
    ManagerRetriever.get(activity).addListener(this)
}
fun LifecycleListener.watch(fragment: Fragment?) {
    fragment?.let { ManagerRetriever.get(it).addListener(this) }
}
fun LifecycleListener.watch(fragment: android.app.Fragment) {
    ManagerRetriever.get(fragment).addListener(this)
}
fun LifecycleListener.watch(dialog: Dialog) {
    ManagerRetriever.get(dialog).addListener(this)
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

