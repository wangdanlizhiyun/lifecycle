package com.gucci.lifecycle

import android.annotation.TargetApi
import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.ContextWrapper
import android.os.*
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.util.ArrayMap
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
    private val tempViewToSupportFragment = ArrayMap<View, Fragment>()
    private val tempViewToFragment = ArrayMap<View, android.app.Fragment>()
    private val tempBundle = Bundle()
    private val FRAGMENT_INDEX_KEY = "key"
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
    private fun findActivity(context: Context): Activity? {
        return context as? Activity ?: if (context is ContextWrapper) {
            findActivity(context.baseContext)
        } else {
            null
        }
    }
    fun get(view: View): Lifecycle {
        if (Looper.getMainLooper() != Looper.myLooper()) {
            return applicationLifecycle
        }
        val activity = findActivity(view.context) ?: return get(view.context.applicationContext)
        if (activity is FragmentActivity) {
            val fragment = findSupportFragment(view, activity)
            return fragment?.let { get(it) } ?: get(activity)
        }
        // Standard Fragments.
        val fragment = findFragment(view, activity) ?: return get(activity)
        return get(fragment)
    }
    private fun findFragment(target: View, activity: Activity): android.app.Fragment? {
        tempViewToFragment.clear()
        findAllFragmentsWithViews(activity.fragmentManager, tempViewToFragment)
        var result: android.app.Fragment? = null
        val activityRoot = activity.findViewById<View>(android.R.id.content)
        var current = target
        while (current != activityRoot) {
            result = tempViewToFragment.get(current)
            if (result != null) {
                break
            }
            if (current.parent is View) {
                current = current.parent as View
            } else {
                break
            }
        }
        tempViewToFragment.clear()
        return result
    }
    @TargetApi(Build.VERSION_CODES.O)
    private fun findAllFragmentsWithViews(
        fragmentManager: android.app.FragmentManager, result: ArrayMap<View, android.app.Fragment>
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (fragment in fragmentManager.fragments) {
                if (fragment.view != null) {
                    result[fragment.view] = fragment
                    findAllFragmentsWithViews(fragment.childFragmentManager, result)
                }
            }
        } else {
            findAllFragmentsWithViewsPreO(fragmentManager, result)
        }
    }
    private fun findAllFragmentsWithViewsPreO(
        fragmentManager: android.app.FragmentManager, result: ArrayMap<View, android.app.Fragment>
    ) {
        var index = 0
        while (true) {
            tempBundle.putInt(FRAGMENT_INDEX_KEY, index++)
            var fragment: android.app.Fragment? = null
            try {
                fragment = fragmentManager.getFragment(tempBundle, FRAGMENT_INDEX_KEY)
            } catch (e: Exception) {
                // This generates log spam from FragmentManager anyway.
            }

            if (fragment == null) {
                break
            }
            if (fragment.view != null) {
                result[fragment.view] = fragment
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    findAllFragmentsWithViews(fragment.childFragmentManager, result)
                }
            }
        }
    }

    private fun findSupportFragment(target: View, activity: FragmentActivity): Fragment? {
        tempViewToSupportFragment.clear()
        findAllSupportFragmentsWithViews(
            activity.supportFragmentManager.fragments, tempViewToSupportFragment
        )
        var result: Fragment? = null
        val activityRoot = activity.findViewById<View>(android.R.id.content)
        var current = target
        while (current != activityRoot) {
            result = tempViewToSupportFragment[current]
            if (result != null) {
                break
            }
            if (current.parent is View) {
                current = current.parent as View
            } else {
                break
            }
        }

        tempViewToSupportFragment.clear()
        return result
    }
    private fun findAllSupportFragmentsWithViews(
        topLevelFragments: Collection<Fragment>?,
        result: MutableMap<View, Fragment>
    ) {
        if (topLevelFragments == null) {
            return
        }
        for (fragment in topLevelFragments) {
            fragment?.also {
                it.view?.let{view->
                    result[view] = fragment
                    findAllSupportFragmentsWithViews(fragment.childFragmentManager.fragments, result)
                }
            }

        }
    }
}