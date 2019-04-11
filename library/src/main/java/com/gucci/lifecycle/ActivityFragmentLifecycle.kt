package com.gucci.lifecycle

import java.util.*

/**
 * Created by 李志云 2019/4/11 15:43
 */
class ActivityFragmentLifecycle: Lifecycle {
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
        getSnapshot(lifecycleListeners).forEach { it.onCreate() }
    }

    fun onResume(){
        isResumed = true
        getSnapshot(lifecycleListeners).forEach { it.onResume() }
    }

    fun onPause(){
        isResumed = false
        getSnapshot(lifecycleListeners).forEach { it.onPause() }
    }
    fun onStart(){
        isStarted = true
        getSnapshot(lifecycleListeners).forEach { it.onStart() }
    }

    fun onStop(){
        isStarted = false
        getSnapshot(lifecycleListeners).forEach { it.onStop() }
    }

    fun onDestory(){
        isDestroyed = true
        getSnapshot(lifecycleListeners).forEach { it.onDestory() }
        lifecycleListeners.clear()
    }

    fun <T> getSnapshot(other: Collection<T>): List<T> {
        val result = ArrayList<T>(other.size)
        for (item in other) {
            if (item != null) {
                result.add(item)
            }
        }
        return result
    }
}