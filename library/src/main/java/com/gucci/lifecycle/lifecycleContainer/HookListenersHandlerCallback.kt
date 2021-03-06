package com.gucci.lifecycle.lifecycleContainer

import android.os.Handler
import android.os.Message
import com.gucci.lifecycle.ManagerRetriever
import com.gucci.lifecycle.lifecycle.ActivityFragmentLifecycle

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