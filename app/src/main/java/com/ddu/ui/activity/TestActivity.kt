package com.ddu.ui.activity

import android.content.Intent
import android.graphics.PixelFormat
import android.os.Bundle
import android.util.Log
import com.ddu.icore.ui.activity.BaseActivity
import com.ddu.util.NavigatorUtils

/**
 * Created by yzbzz on 2018/1/17.
 */
class TestActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFormat(PixelFormat.TRANSLUCENT)

        Log.v("lhz", "TestActivity onCreate: " + intent.action)
        if (NavigatorUtils.navigatorToNative(this, intent)) {
            finish()
        }

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.v("lhz", "TestActivity onNewIntent: " + intent?.action)
    }

}