package com.gucci.lifecycle.demo

import android.app.Activity
import android.app.Dialog
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.gucci.lifecycle.watch
import com.lzy.download.CustomDialogFragment
import com.lzy.download.showCustomDialog
import org.jetbrains.anko.button
import org.jetbrains.anko.matchParent
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.verticalLayout

class MainActivity : AppCompatActivity() {
    lateinit var that:Activity
    lateinit var mCustomVideoView:CustomVideoView
    var dialogFragment : CustomDialogFragment? = null
    lateinit var mC:C
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        that = this
        verticalLayout {
            button("showDialogFragment"){
                onClick {
                    if (dialogFragment == null){
                        dialogFragment = showCustomDialog{
                            message = "fdafa"
                            cancelOutside = false
                        }
                    }else{
                        dialogFragment?.show(supportFragmentManager,"dialog")
                    }
                }
            }
            button("showDialog"){
                onClick {
                    CProcessDialog(that).show()
                }
            }
            mCustomVideoView = customVideoView(Uri.parse("http://tb-video.bdstatic.com/tieba-smallvideo-transcode/10923707_80db72f5f649f4c0dc62c8520184d3d7_0.mp4"))
            {}.lparams(width = matchParent,height = 500)
        }
        mC = C()
        A().watch(this)
    }
}
