package com.gucci.lifecycle.demo

import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewManager
import android.widget.VideoView
import com.gucci.lifecycle.LifecycleListener
import com.gucci.lifecycle.annotations.*
import org.jetbrains.anko.custom.ankoView

/**
 * Created by 李志云 2019/5/1 21:36
 * 坑逼videoview内部有泄漏
 */
class CustomVideoView: VideoView,LifecycleListener {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.getDefaultSize(0, widthMeasureSpec)
        val height = View.getDefaultSize(0, heightMeasureSpec)
        setMeasuredDimension(width, height)
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setOnInfoListener { mp, what, extra ->
                mp.isLooping = true
                false
            }
        }
    }

    @OnResume fun onResume() {
        Log.e("test", "CustomVideoView onResume mCurrentPosition=$mCurrentPosition")
        resume()
    }

    @OnStart fun onStart() {
        Log.e("test", "CustomVideoView onStart mCurrentPosition=$mCurrentPosition")
        seekTo(mCurrentPosition)
        start()
    }

    @OnPause fun onPause() {
        Log.e("test", "CustomVideoView onPause")
        mCurrentPosition = currentPosition
        pause()
    }

    @OnStop fun onStop() {
        Log.e("test", "CustomVideoView OnStop")
        stopPlayback()
    }

    @OnDestory fun onDestroy() {
        Log.e("test", "CustomVideoView onDestroy")
        suspend()
    }
    protected var mCurrentPosition = 0
}
inline fun ViewManager.customVideoView() = customVideoView {}
inline fun ViewManager.customVideoView(init: CustomVideoView.() -> Unit) = ankoView(::CustomVideoView, 0, init)
inline fun ViewManager.customVideoView(uri: Uri, init: CustomVideoView.() -> Unit) = customVideoView {
    setVideoURI(uri)
}