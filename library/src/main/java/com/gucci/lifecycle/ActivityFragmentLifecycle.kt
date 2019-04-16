package com.gucci.lifecycle

import java.util.*

/**
 * Created by 李志云 2019/4/11 15:43
 */
class ActivityFragmentLifecycle: Lifecycle {
    override fun getListeners(): Set<LifecycleListener> {
        return lifecycleListeners
    }

    val lifecycleListeners = Collections.newSetFromMap(WeakHashMap<LifecycleListener,Boolean>())
    var isStarted = false
    var isResumed = false
    var isDestroyed = false
    var isFirst = true

    override fun removeListener(litener: LifecycleListener) {
        lifecycleListeners.remove(litener)
    }

    override fun addListener(litener: LifecycleListener) {
        lifecycleListeners.add(litener)
        if (isDestroyed){
            litener.onDestory()
        }else if (isStarted){
            litener.onStart()
        }else if (isResumed){
            litener.onResume()
        } else{
            if (isFirst){
                isFirst = false
            }else{
                litener.onStop()
            }
        }
    }

    fun onCreate(){
        lifecycleListeners.getSnapshot().forEach { it.onCreate() }
    }

    fun onResume(){
        isResumed = true
        lifecycleListeners.getSnapshot().forEach { it.onResume() }
    }

    fun onPause(){
        isResumed = false
        lifecycleListeners.getSnapshot().forEach { it.onPause() }
    }
    fun onStart(){
        isStarted = true
        lifecycleListeners.getSnapshot().forEach { it.onStart() }
    }

    fun onStop(){
        isStarted = false
        lifecycleListeners.getSnapshot().forEach { it.onStop() }
    }

    fun onDestory(){
        isDestroyed = true
        lifecycleListeners.getSnapshot().forEach { it.onDestory() }
        lifecycleListeners.clear()
    }

}