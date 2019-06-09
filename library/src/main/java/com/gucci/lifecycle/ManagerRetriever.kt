package com.gucci.lifecycle

import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.View
import com.gucci.lifecycle.lifecycle.ApplicationLifecycle
import com.gucci.lifecycle.lifecycle.Lifecycle
import com.gucci.lifecycle.lifecycleContainer.HookListenersHandlerCallback
import com.gucci.lifecycle.lifecycleContainer.RequestManagerFragment
import com.gucci.lifecycle.lifecycleContainer.SupportRequestManagerFragment
import com.gucci.lifecycle.util.InvokeUtil
import java.lang.Exception
import java.util.*

object ManagerRetriever {
    var Dialog_SHOW = -1
    val applicationLifecycle: Lifecycle = ApplicationLifecycle()

    val FRAG_TAG = "gucci_fragment"
    val ID_REMOVE_SUPPORT_FRAGMENT_MANAGER = 1
    val ID_REMOVE_FRAGMENT_MANAGER = 2
    val pendingSupportsRequestManagerFragments: HashMap<android.support.v4.app.FragmentManager, SupportRequestManagerFragment> = HashMap()
    val pendingRequestManagerFragments: HashMap<android.app.FragmentManager, RequestManagerFragment> = HashMap()
    val handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when (msg.what) {
                ID_REMOVE_SUPPORT_FRAGMENT_MANAGER -> {
                    pendingSupportsRequestManagerFragments.remove(msg.obj)
                }
                ID_REMOVE_FRAGMENT_MANAGER -> {
                    pendingRequestManagerFragments.remove(msg.obj)
                }
            }
        }
    }

    fun get(activity: Activity): Lifecycle {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            return applicationLifecycle
        }
        return fragmentGet(activity.fragmentManager)
    }

    fun get(activity: FragmentActivity): Lifecycle {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            return applicationLifecycle
        }
        return supportFragmentGet(activity.supportFragmentManager)
    }

    fun get(dialog: Dialog): Lifecycle {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            return applicationLifecycle
        }

        val dialogClass = Class.forName("android.app.Dialog")
        val handlerClass = Class.forName("android.os.Handler")

        val mListenersHandler: Handler = InvokeUtil.getDeclaredFieldObject(dialogClass,"mListenersHandler", dialog) as Handler
        var mCallback = InvokeUtil.getDeclaredFieldObject(handlerClass,"mCallback", mListenersHandler)
        if (Dialog_SHOW == -1) {
            try {
                Dialog_SHOW =
                    InvokeUtil.getDeclaredFieldObject(dialogClass, "SHOW", null) as Int
            } catch (e: Exception) {
            }
        }
        var hookListenersHandlerCallback: HookListenersHandlerCallback? = null
        mCallback?.let { hookListenersHandlerCallback = mCallback as HookListenersHandlerCallback? }

        hookListenersHandlerCallback?.let {
            return it.lifecycle
        }

        hookListenersHandlerCallback =
            HookListenersHandlerCallback(mListenersHandler)
        InvokeUtil.setDeclaredFieldObject(
            handlerClass,
            "mCallback",
            mListenersHandler,
            hookListenersHandlerCallback!!
        )
        mCallback = InvokeUtil.getDeclaredFieldObject(handlerClass,"mCallback", mListenersHandler)

        if (InvokeUtil.getDeclaredFieldObject("mDismissMessage",dialog) == null){
            dialog.setOnDismissListener {  }
        }
        if (InvokeUtil.getDeclaredFieldObject("mShowMessage",dialog) == null){
            dialog.setOnShowListener {  }
        }
        return hookListenersHandlerCallback!!.lifecycle
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


    fun get(fragment: Fragment): Lifecycle {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            return applicationLifecycle
        }
        return getSupportRequestManagerFragment(fragment.childFragmentManager).lifecycle
    }

    fun get(fragment: android.app.Fragment): Lifecycle {
        if (Looper.getMainLooper() != Looper.myLooper() || Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            return applicationLifecycle
        } else {
            return getFragment(fragment.childFragmentManager).lifecycle
        }
    }

    fun supportFragmentGet(fm: android.support.v4.app.FragmentManager): Lifecycle {
        return getSupportRequestManagerFragment(fm).lifecycle
    }

    fun fragmentGet(fm: android.app.FragmentManager): Lifecycle {
        return getFragment(fm).lifecycle
    }

    private fun getSupportRequestManagerFragment(fm: android.support.v4.app.FragmentManager): SupportRequestManagerFragment {
        var fragment: SupportRequestManagerFragment? = fm.findFragmentByTag(FRAG_TAG) as SupportRequestManagerFragment?
        if (fragment == null) {
            fragment = pendingSupportsRequestManagerFragments.get(fm)
            if (fragment == null) {
                fragment = SupportRequestManagerFragment()
                pendingSupportsRequestManagerFragments[fm] = fragment
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
    fun get(view: View): Lifecycle {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            return applicationLifecycle
        }
        return get(view.context)
    }
}