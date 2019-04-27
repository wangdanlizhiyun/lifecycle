package com.gucci.lifecycle

import android.os.Handler
import android.os.Message

/**
 * Created by 李志云 2019/4/26 04:25
 */
class HookListenersHandlerCallback(val handler: Handler):Handler.Callback {
    val lifecycle = ActivityFragmentLifecycle()
    override fun handleMessage(msg: Message): Boolean {
        when(msg.what){
            ManagerRetriever.Dialog_SHOW ->{
                lifecycle.onCreate()
            }
            else ->{
                lifecycle.onDestory()
            }
        }
        handler.handleMessage(msg)
        return true
    }
}