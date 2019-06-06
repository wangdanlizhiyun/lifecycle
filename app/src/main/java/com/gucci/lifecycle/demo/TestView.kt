package com.gucci.lifecycle.demo

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import com.gucci.lifecycle.LifecycleListener
import com.gucci.lifecycle.TickListener
import com.gucci.lifecycle.annotations.*
import com.gucci.lifecycle.bind

/**
 * Created by 李志云 2019/6/4 00:50
 */
class TestView: TextView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }
    init {

        text = "testView"
        object : LifecycleListener{
            @OnResume fun doOnResume(){
                Log.e("test","doOnResume")
            }

            @OnPause fun doOnPause(){
                Log.e("test","doOnPause")
            }

            @OnAttachedToWindow fun doOnAttachedToWindow(){
                Log.e("test","doOnAttachedToWindow")
            }
            @OnDetachedToWindow fun doOnDetachedToWindow(){
                Log.e("test","doOnDetachedToWindow")
            }
        } bind this


        TickListener(this)
    }


    @OnTick
    fun doOntick(){
        Log.e("test","doOntick")
    }

}