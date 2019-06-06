package com.gucci.lifecycle

import android.view.View
import com.gucci.lifecycle.annotations.*
import com.gucci.lifecycle.util.LifecycleUtil

/**
 * Created by 李志云 2019/6/7 05:18
 */
class TickListener(private val view: View):LifecycleListener {
    private lateinit var mTickRunnable:Runnable
    init {
        mTickRunnable = Runnable {
            if (view.parent == null)return@Runnable
            LifecycleUtil.doActionOnOnlyItself(view,OnTick::class.java)
            view.postDelayed(mTickRunnable,1000)
        }
        this bind view
    }

    @OnResume
    fun doOnResume(){
        if (view.parent == null)return
        view.removeCallbacks(mTickRunnable)
        view.postDelayed(mTickRunnable,1000)
    }

    @OnPause
    fun doOnPause(){
        view.removeCallbacks(mTickRunnable)
    }

    @OnAttachedToWindow
    fun doOnAttachedToWindow(){
        view.removeCallbacks(mTickRunnable)
        view.postDelayed(mTickRunnable,1000)
    }
    @OnDetachedToWindow
    fun doOnDetachedToWindow(){
        view.removeCallbacks(mTickRunnable)
    }
}