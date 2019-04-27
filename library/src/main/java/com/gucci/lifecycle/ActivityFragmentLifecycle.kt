package com.gucci.lifecycle

import android.view.ViewGroup
import com.gucci.lifecycle.LifecycleUtil.Companion.doAction
import com.gucci.lifecycle.LifecycleUtil.Companion.getAllNeedListeneredFields
import com.gucci.lifecycle.annotations.*
import java.util.*

/**
 * Created by 李志云 2019/4/11 15:43
 */
class ActivityFragmentLifecycle : Lifecycle {
    override fun getListeners(): Set<LifecycleListener> {
        return lifecycleListeners
    }

    val lifecycleListeners = Collections.newSetFromMap(WeakHashMap<LifecycleListener, Boolean>())
    var isStarted = false
    var isResumed = false
    var isDestroyed = false
    var isFirst = true

    override fun removeListener(litener: LifecycleListener) {
        lifecycleListeners.remove(litener)
    }

    override fun addListener(litener: LifecycleListener) {
        lifecycleListeners.add(litener)
        if (isDestroyed) {
            doAction(litener,OnDestory::class.java)
        } else if (isStarted) {
            doAction(litener,OnStart::class.java)
        } else if (isResumed) {
            doAction(litener,OnResume::class.java)
        } else {
            if (isFirst) {
                isFirst = false
            } else {
                doAction(litener,OnStop::class.java)
            }
        }
    }

    fun onCreate() {
        lifecycleListeners.getSnapshot().forEach { performChildrenAndYouSelf(it, OnCreate::class.java) }
    }

    fun onResume() {
        isResumed = true
        lifecycleListeners.getSnapshot().forEach { performChildrenAndYouSelf(it, OnResume::class.java) }
    }

    fun onPause() {
        isResumed = false
        lifecycleListeners.getSnapshot().forEach { performChildrenAndYouSelf(it, OnPause::class.java) }
    }

    fun onStart() {
        isStarted = true
        lifecycleListeners.getSnapshot().forEach { performChildrenAndYouSelf(it, OnStart::class.java) }
    }

    fun onStop() {
        isStarted = false
        lifecycleListeners.getSnapshot().forEach { performChildrenAndYouSelf(it, OnStop::class.java) }
    }

    fun onDestory() {
        isDestroyed = true
        lifecycleListeners.getSnapshot().forEach { performChildrenAndYouSelf(it, OnDestory::class.java) }
        lifecycleListeners.clear()
    }

    fun performChildrenAndYouSelf(lifecycleListener: LifecycleListener, clazz: Class<out Annotation>) {
        if (lifecycleListener is ViewGroup) {
            val count = lifecycleListener.childCount
            for (i in 0 until count) {
                val child = lifecycleListener.getChildAt(i)
                if (child is LifecycleListener) {
                    performChildrenAndYouSelf(child, clazz)
                }
            }
        } else {
            //非ViewGroup，它的内部也可能包含ViewGroup，所以这里的代码还得递归到外面
            getAllNeedListeneredFields(lifecycleListener).forEach {
                val bean = InvokeUtil.getDeclaredFieldObject(it, lifecycleListener)
                if (bean is LifecycleListener) {
                    performChildrenAndYouSelf(bean, clazz)
                }
            }
        }
        doAction(lifecycleListener,clazz)
    }
}