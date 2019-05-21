package com.gucci.lifecycle.demo

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import com.gucci.lifecycle.bind

/**
 * Created by 李志云 2019/4/26 01:13
 */
class CProcessDialog : ProgressDialog {
    constructor(context: Context) : super(context) {}

    constructor(context: Context, theme: Int) : super(context, theme) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        B() bind this
    }

}
