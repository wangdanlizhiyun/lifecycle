package com.gucci.lifecycle

/**
 * Created by 李志云 2019/4/11 15:28
 */
interface LifecycleListener {

    fun onStart()
    fun onStop()
    fun onDestory()

    fun onResume()
    fun onCreate()
    fun onPause()

}