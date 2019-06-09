package com.lzy.download

import android.support.v7.app.AppCompatActivity

inline fun AppCompatActivity.showCustomDialog(settings:CustomDialogFragment.() -> Unit):CustomDialogFragment{
    val dialog = CustomDialogFragment.newInstance()
    dialog.apply(settings)
    val ft = this.supportFragmentManager.beginTransaction()
    val pre = this.supportFragmentManager.findFragmentByTag("dialoag")
    pre?.let { ft.remove(it) }
    ft.addToBackStack(null)
    dialog.show(ft,"dialog")
    return dialog
}
