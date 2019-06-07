package com.gucci.lifecycle

import android.view.View
import com.gucci.lifecycle.annotations.*
import com.gucci.lifecycle.util.LifecycleUtil

/**
 * Created by 李志云 2019/6/7 05:18
 */
class Tick(private val view: View):LifecycleListener {
    private lateinit var mTickRunnable:Runnable
    init {
        mTickRunnable = Runnable {
            if (view.parent == null)return@Runnable
            LifecycleUtil.doActionOnOnlyItself(view,OnTick::class.java)
            view.postDelayed(mTickRunnable,1000)
        }
        this bind view
    }

    @OnStart
    fun doOnStart(){
        if (view.parent == null)return
        view.removeCallbacks(mTickRunnable)
        LifecycleUtil.doActionOnOnlyItself(view,OnTick::class.java)
        view.postDelayed(mTickRunnable,1000)
    }

    @OnStop
    fun doOnStop(){
        view.removeCallbacks(mTickRunnable)
    }

    @OnAttachedToWindow
    fun doOnAttachedToWindow(){
        view.removeCallbacks(mTickRunnable)
        LifecycleUtil.doActionOnOnlyItself(view,OnTick::class.java)
        view.postDelayed(mTickRunnable,1000)
    }
    @OnDetachedToWindow
    fun doOnDetachedToWindow(){
        view.removeCallbacks(mTickRunnable)
    }
}