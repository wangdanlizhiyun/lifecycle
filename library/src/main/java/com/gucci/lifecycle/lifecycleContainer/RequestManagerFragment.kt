package com.gucci.lifecycle.lifecycleContainer

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import com.gucci.lifecycle.lifecycle.ActivityFragmentLifecycle


class RequestManagerFragment : Fragment() {

    val lifecycle = ActivityFragmentLifecycle()


    override fun onStart() {
        super.onStart()
        lifecycle.onStart()
    }

    override fun onStop() {
        super.onStop()
        lifecycle.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        lifecycle.onDestory()
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

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }

}