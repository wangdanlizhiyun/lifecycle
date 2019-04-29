package com.gucci.lifecycle

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
                        InvokeUtil.invoke(lifecycleListener, method)
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
    }
}