package com.gucci.lifecycle.util

import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.*


class InvokeUtil {
    companion object {

        @Throws(NoSuchFieldException::class)
        fun findField(instance: Any, name: String): Field {
            return findField(instance::class.java, name)
        }

        @Throws(NoSuchFieldException::class)
        fun findField(forName: String, name: String): Field {
            return findField(Class.forName(forName), name)
        }

        @Throws(NoSuchFieldException::class)
        fun findField(c: Class<*>, name: String): Field {
            var clazz = c
            //反射获得
            while (clazz != null) {
                try {
                    val field = clazz.getDeclaredField(name)
                    //如果无法访问 设置为可访问
                    if (!field.isAccessible) {
                        field.isAccessible = true
                    }
                    return field
                } catch (e: NoSuchFieldException) {
                    //如果找不到往父类找
                    clazz = clazz.superclass
                }

            }
            throw NoSuchFieldException("Field " + name + " not found in " + clazz)
        }


        /**
         * 反射获得 指定对象(当前-》父类-》父类...)中的 函数
         * @param instance
         * @param name
         * @param parameterTypes
         * @return
         * @throws NoSuchMethodException
         */
        @Throws(NoSuchMethodException::class)
        fun findMethod(instance: Any, name: String, vararg parameterTypes: Class<*>): Method {
            var clazz: Class<*>? = null
            if (instance is Class<*>){
                clazz = instance
            }else{
                instance::class.java
            }
            while (clazz != null) {
                try {
                    val method = clazz.getDeclaredMethod(name, *parameterTypes)
                    if (!method.isAccessible) {
                        method.isAccessible = true
                    }
                    return method
                } catch (e: NoSuchMethodException) {
                    //如果找不到往父类找
                    clazz = clazz.superclass
                }

            }
            throw NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList(*parameterTypes) + " not found in " + instance.javaClass)
        }

        fun getDeclaredFieldObject(clazz: Class<*>, fieldName: String, `object`: Any?): Any? {
            try {
                val field = clazz.getDeclaredField(fieldName)
                return getDeclaredFieldObject(field, `object`)
            }catch (e:java.lang.Exception){}
            return null
        }
        fun getDeclaredFieldObject(field:Field, `object`: Any?): Any? {
            try {
                field.isAccessible = true
                return field.get(`object`)
            }catch (e:java.lang.Exception){}
            return null
        }



        fun getDeclaredFieldObject(fieldName: String, `object`: Any): Any? {
            return getDeclaredFieldObject(
                `object`::class.java,
                fieldName,
                `object`
            )
        }

        @Throws(Exception::class)
        fun setDeclaredFieldObject(clazz: Class<*>, fieldName: String, `object`: Any, value: Any) {
            val field = clazz.getDeclaredField(fieldName)
            field.isAccessible = true
            field.set(`object`, value)
        }

        @Throws(Exception::class)
        fun setDeclaredFieldObject(fieldName: String, `object`: Any, value: Any) {
            val field = `object`::class.java.getDeclaredField(fieldName)
            field.isAccessible = true
            field.set(`object`, value)
        }
        @Throws(Exception::class)
        fun invoke(instance: Any?,method:Method,vararg value: Any){
            method.isAccessible = true
            method.invoke(instance,value)
        }
        @Throws(Exception::class)
        fun invokeForResult(instance: Any?,method:Method,vararg value: Any):Any{
            method.isAccessible = true
            return method.invoke(instance,value)
        }
        @Throws(Exception::class)
        fun invokeForResult(instance: Any?,method:Method):Any{
            method.isAccessible = true
            return method.invoke(instance)
        }
        @Throws(Exception::class)
        fun invoke(instance: Any?,method:Method){
            method.isAccessible = true
            method.invoke(instance)
        }

        @Throws(NoSuchFieldException::class, IllegalAccessException::class)
        fun expandFieldArray(instance: Any, fieldName: String, extraElements: Array<Any>) {
            val field = findField(instance, fieldName)
            var original = field.get(instance) as Array<Any>
            val combined = java.lang.reflect.Array.newInstance(original.javaClass.componentType, original.size + extraElements.size);
            System.arraycopy(extraElements, 0, combined, 0, extraElements.size)
            System.arraycopy(original, 0, combined, extraElements.size, original.size)
            field.set(instance, combined)
        }

    }

}