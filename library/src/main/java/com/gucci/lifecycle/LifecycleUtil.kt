package com.gucci.lifecycle

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import com.gucci.lifecycle.annotations.*
import java.lang.Exception
import java.lang.reflect.Field

/**
 * Created by 李志云 2019/4/28 03:31
 */
class LifecycleUtil {

    companion object {
        val sListenerMap = HashMap<Class<*>,ArrayList<Field>>()
        fun doAction(lifecycleListener: LifecycleListener, clazz: Class<out Annotation>) {
            lifecycleListener.javaClass.declaredMethods.forEach { method ->
                method.getAnnotation(clazz)?.let {
                    if (method.getParameterTypes().size == 0) {
                        try {
                            InvokeUtil.invoke(lifecycleListener, method)
                        }catch (e:Exception){
                            Log.e("test",e.message)
                        }
                    }
                }
            }
        }

        fun getAllNeedListeneredFields(any: Any): ArrayList<Field> {
            sListenerMap[any.javaClass]?.let {
                return it
            }
            val list = ArrayList<Field>()
            any.javaClass.declaredFields?.let {
                it.forEach {
                    if (isListener(it)) {
                        list.add(it)
                    }
                }
            }
            sListenerMap.put(any.javaClass,list)
            return list
        }

        fun isListener(f: Field): Boolean {
            val cls = f.getType().getInterfaces()
            for (c in cls) {
                if (LifecycleListener::class.java == c) {
                    return true
                }
            }
            return false
        }

        fun init(application: Application){
            application.registerActivityLifecycleCallbacks(object :Application.ActivityLifecycleCallbacks{
                override fun onActivityPaused(activity: Activity) {
                    dispatchAction(activity,OnPause::class.java)
                }

                override fun onActivityResumed(activity: Activity) {
                    dispatchAction(activity,OnResume::class.java)
                }

                override fun onActivityStarted(activity: Activity) {
                    dispatchAction(activity, OnStart::class.java)
                }

                override fun onActivityDestroyed(activity: Activity) {
                    dispatchAction(activity,OnDestory::class.java)
                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
                }

                override fun onActivityStopped(activity: Activity) {
                    dispatchAction(activity,OnStop::class.java)
                }

                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    dispatchAction(activity,OnCreate::class.java)
                }

            })

        }
        fun dispatchAction(any: Any,clazz: Class<out Annotation>){
            getAllNeedListeneredFields(any).forEach {
                val bean = InvokeUtil.getDeclaredFieldObject(it, any)
                if (bean is LifecycleListener) {
                    doAction(bean, clazz)
                }
            }
        }
    }
}