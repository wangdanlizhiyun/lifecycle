package com.gucci.lifecycle

import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import com.gucci.lifecycle.util.LifecycleUtil

/**
 * Created by 李志云 2019/6/7 03:55
 */
class LifecycleProvider:ContentProvider() {
    override fun insert(uri: Uri?, values: ContentValues?): Uri? {
        return null
    }

    override fun query(
        uri: Uri?,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        return null
    }

    override fun onCreate(): Boolean {
        LifecycleUtil.init(context as Application)
        return true
    }

    override fun update(uri: Uri?, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun delete(uri: Uri?, selection: String?, selectionArgs: Array<out String>?): Int {
        return 0
    }

    override fun getType(uri: Uri?): String {
        return ""
    }
}