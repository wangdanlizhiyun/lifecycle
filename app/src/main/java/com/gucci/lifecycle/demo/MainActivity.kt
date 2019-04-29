package com.gucci.lifecycle.demo

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.gucci.lifecycle.watch
import com.lzy.download.CustomDialogFragment
import com.lzy.download.showCustomDialog
import org.jetbrains.anko.button
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.verticalLayout

class MainActivity : AppCompatActivity() {
    lateinit var that:Activity

    var dialogFragment : CustomDialogFragment? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("test","MainActivity onCreate ${hashCode()}")
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
        }

        A().watch(this)
    }



}
