package com.gucci.lifecycle.lifecycleContainer

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gucci.lifecycle.lifecycle.ActivityFragmentLifecycle

class SupportRequestManagerFragment : Fragment() {
    val lifecycle = ActivityFragmentLifecycle()
    private var rootRequestManagerFragment:SupportRequestManagerFragment? = null
    private val childRequestManagerFragments = HashSet<SupportRequestManagerFragment>()
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


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onDetach() {
        super.onDetach()
    }


}