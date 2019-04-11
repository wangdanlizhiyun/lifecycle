package com.gucci.lifecycle

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import java.util.*


/**
 * Created by 李志云 2019/4/11 15:25
 */
object ManagerRetriever {


    val applicationLifecycle: Lifecycle = ApplicationLifecycle()

    val FRAG_TAG = "gucci_fragment"
    val ID_REMOVE_SUPPORT_FRAGMENT_MANAGER = 1
    val ID_REMOVE_FRAGMENT_MANAGER = 2
    val supports: HashMap<FragmentManager, SupportRequestManagerFragment> = HashMap()
    val pendingRequestManagerFragments: HashMap<android.app.FragmentManager, RequestManagerFragment> = HashMap()
    val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                ID_REMOVE_SUPPORT_FRAGMENT_MANAGER -> {
                    supports.remove(msg.obj)
                }
                ID_REMOVE_FRAGMENT_MANAGER -> {
                    pendingRequestManagerFragments.remove(msg.obj)
                }
            }
        }
    }

    fun get(activity: Activity): Lifecycle {
        return fragmentGet(activity.fragmentManager)
    }

    fun get(activity: FragmentActivity): Lifecycle {
        return supportFragmentGet(activity.supportFragmentManager)
    }

    fun get(context: Context): Lifecycle {
        if (Looper.myLooper() == Looper.getMainLooper() && !(context is Application)) {
            when (context) {
                is FragmentActivity -> {
                    return get(context)
                }
                is Activity -> {
                    return get(context)
                }
                is ContextWrapper -> {
                    return get(context.baseContext)
                }
            }
        }
        return applicationLifecycle
    }


    fun supportFragmentGet(fm: FragmentManager): Lifecycle {
        return getSupportRequestManagerFragment(fm).lifecycle
    }

    fun fragmentGet(fm: android.app.FragmentManager): Lifecycle {
        return getFragment(fm).lifecycle
    }

    private fun getSupportRequestManagerFragment(fm: FragmentManager): SupportRequestManagerFragment {
        var fragment: SupportRequestManagerFragment? = fm.findFragmentByTag(FRAG_TAG) as SupportRequestManagerFragment?
        if (fragment == null) {
            fragment = supports.get(fm)
            if (fragment == null) {
                fragment = SupportRequestManagerFragment()
                supports[fm] = fragment
                fm.beginTransaction().add(fragment, FRAG_TAG).commitAllowingStateLoss()
                handler.obtainMessage(ID_REMOVE_SUPPORT_FRAGMENT_MANAGER, fm).sendToTarget()
            }
            return fragment
        } else {
            return fragment
        }
    }

    private fun getFragment(fm: android.app.FragmentManager): RequestManagerFragment {
        var fragment: RequestManagerFragment? = fm.findFragmentByTag(FRAG_TAG) as RequestManagerFragment?
        if (fragment == null) {
            fragment = pendingRequestManagerFragments.get(fm)
            if (fragment == null) {
                fragment = RequestManagerFragment()
                pendingRequestManagerFragments[fm] = fragment
                fm.beginTransaction().add(fragment, FRAG_TAG).commitAllowingStateLoss()
                handler.obtainMessage(ID_REMOVE_FRAGMENT_MANAGER, fm).sendToTarget()
            }
            return fragment
        } else {
            return fragment
        }
    }
}