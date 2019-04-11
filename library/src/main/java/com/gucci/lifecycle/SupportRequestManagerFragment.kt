package com.gucci.lifecycle

import android.os.Bundle
import android.support.v4.app.Fragment
import com.gucci.lifecycle.ActivityFragmentLifecycle

/**
 * Created by 李志云 2019/4/11 17:02
 */
class SupportRequestManagerFragment : Fragment() {
    val lifecycle = ActivityFragmentLifecycle()
    override fun onDestroy() {
        super.onDestroy()
        lifecycle.onDestory()
    }

    override fun onStart() {
        super.onStart()
        lifecycle.onStart()
    }

    override fun onStop() {
        super.onStop()
        lifecycle.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.onCreate()
    }

    override fun onPause() {
        super.onPause()
        lifecycle.onPause()
    }

    override fun onResume() {
        super.onResume()
        lifecycle.onResume()
    }
}