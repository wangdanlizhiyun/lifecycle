package com.gucci.lifecycle.demo

import android.app.Application
import android.content.Context
import com.squareup.leakcanary.LeakCanary

/**
 * Created by 李志云 2019/4/10 18:28
 */
class MyApp :Application(){

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
    }
}