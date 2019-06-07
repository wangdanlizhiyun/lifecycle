package com.gucci.lifecycle.demo

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.TextView
import com.gucci.lifecycle.annotations.OnTick
import com.gucci.lifecycle.tick

/**
 * Created by 李志云 2019/6/4 00:50
 */
class TestView: TextView {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    init { tick() }
    @OnTick fun doOntick(){
        Log.e("test","doOntick")
    }
}