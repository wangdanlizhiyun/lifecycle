package com.gucci.lifecycle

import android.app.Activity
import android.app.Dialog
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import com.gucci.lifecycle.annotations.OnAttachedToWindow
import com.gucci.lifecycle.annotations.OnDetachedToWindow
import com.gucci.lifecycle.lifecycle.Lifecycle
import com.gucci.lifecycle.util.LifecycleUtil
import java.util.*

/**
 * @see #watch(android.app.Activity)
 * @see #watch(android.app.Fragment)
 * @see #watch(android.support.v4.app.Fragment)
 * @see #watch(android.support.v4.app.FragmentActivity)
 * @see #watch(android.support.v4.app.DialogFragment)
 * @see #watch(android.app.Dialog) 注意：监听Dialog时watch调用必须在setOnDismissListener和setOnShowListener之前，
 * 并且对于dialog而言只有oncreate和onDestroy
 */
infix fun LifecycleListener.bind(lifecycle: Lifecycle) {
    lifecycle.addListener(this)
}

infix fun LifecycleListener.bind(activity: FragmentActivity) {
    this bind ManagerRetriever.get(activity)
}

infix fun LifecycleListener.bind(activity: Activity) {
    this bind ManagerRetriever.get(activity)
}

infix fun LifecycleListener.bind(fragment: Fragment?) {
    fragment?.let { this bind ManagerRetriever.get(it) }
}

infix fun LifecycleListener.bind(fragment: android.app.Fragment) {
    this bind ManagerRetriever.get(fragment)
}

infix fun LifecycleListener.bind(dialog: Dialog) {
    this bind ManagerRetriever.get(dialog)
}

infix fun LifecycleListener.bind(view: View) {
    val onAttachStateChangeListener = object : View.OnAttachStateChangeListener {
        override fun onViewDetachedFromWindow(v: View?) {
            LifecycleUtil.doAction(this@bind, OnDetachedToWindow::class.java)
        }

        override fun onViewAttachedToWindow(v: View?) {
            LifecycleUtil.doAction(this@bind, OnAttachedToWindow::class.java)
        }
    }
    view.addOnAttachStateChangeListener(onAttachStateChangeListener)
    this bind ManagerRetriever.get(view)
}

fun View.tick() {
    Tick(this)
}


//var lifecycle: Lifecycle? = null


fun <T> Collection<T>.getSnapshot(): List<T> {
    val result = ArrayList<T>(this.size)
    for (item in this) {
        if (item != null) {
            result.add(item)
        }
    }
    return result
}

