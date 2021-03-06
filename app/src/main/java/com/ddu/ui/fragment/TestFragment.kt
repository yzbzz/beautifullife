//package com.ddu.ui.fragment
//
//import android.Manifest
//import android.content.ActivityNotFoundException
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.graphics.drawable.Drawable
//import android.net.Uri
//import android.net.http.SslError
//import android.os.Bundle
//import android.support.v4.app.ActivityCompat
//import android.support.v4.content.ContextCompat
//import android.text.TextUtils
//import android.util.Log
//import android.view.LayoutInflater
//import android.view.View
//import android.webkit.*
//import android.widget.LinearLayout
//import com.bumptech.glide.Glide
//import com.bumptech.glide.request.target.ImageViewTarget
//import com.bumptech.glide.request.transition.Transition
//import com.ddu.R
//import com.ddu.app.BaseApp
//import com.ddu.icore.ui.fragment.DefaultFragment
//import com.ddu.icore.util.PopupUtils
//import com.ddu.icore.util.UrlUtils
//import com.ddu.sdk.network.OkHttpUtils
//import com.ddu.ui.helper.WebAppInterface
//import com.ddu.util.HttpUtils
//import kotlinx.android.synthetic.main.fragment_test_web.*
//import kotlinx.coroutines.android.UI
//import kotlinx.coroutines.async
//import okhttp3.*
//import org.jetbrains.anko.coroutines.experimental.bg
//import org.json.JSONObject
//import java.io.IOException
//import java.io.InputStream
//import java.net.URISyntaxException
//
///**
// * Created by yzbzz on 16/4/6.
// */
//class TestFragment : DefaultFragment() {
//
//    lateinit var mWebSettings: WebSettings
//    lateinit var mTitle: String
//    var url: String? = null
//
//    val isLoadFirst: Boolean = false
//
//    val webChromeClient = object : WebChromeClient() {
//
//        override fun onReceivedTitle(view: WebView, title: String) {
//            super.onReceivedTitle(view, title)
//            if (!TextUtils.isEmpty(title)) {
//                setDefaultTitle(title)
//            }
//        }
//
//    }
//
//    private val webViewClient = object : WebViewClient() {
//
//        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
//            handler.proceed()
//        }
//
//
//        override fun onPageFinished(view: WebView, url: String) {
//            //            view.loadUrl("javascript:MyApp.resize(document.querySelector('body').offsetHeight);");
//            //            view.loadUrl("javascript:MyApp.resize(document.body.getBoundingClientRect().height)");
//            super.onPageFinished(view, url)
//            //            pbWeb.setVisibility(View.GONE);
//            val title = view.title
//            if (!TextUtils.isEmpty(title)) {
//                setDefaultTitle(title)
//            }
//            wv_web.visibility = View.VISIBLE
//        }
//
//        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
//            view.loadUrl(url)
//            return true
//            //            return super.shouldOverrideUrlLoading(view, data);
//        }
//
//    }
//
//    override fun initData(savedInstanceState: Bundle?) {
//        val bundle = arguments
//        if (null != bundle) {
//            mTitle = bundle.getString("mTitle")
//            url = bundle.getString("data")
//        }
//    }
//
//    override fun getLayoutId(): Int {
//        return R.layout.fragment_test_web
//    }
//
//    override fun initView() {
//        mWebSettings = wv_web.settings
//        mWebSettings.javaScriptEnabled = true
//        mWebSettings.cacheMode = WebSettings.LOAD_NO_CACHE
//        mWebSettings.setSupportZoom(true)
//        mWebSettings.useWideViewPort = true
//        mWebSettings.loadWithOverviewMode = true
//
//        mWebSettings.javaScriptEnabled = true
//        mWebSettings.javaScriptCanOpenWindowsAutomatically = true
//        mWebSettings.cacheMode = WebSettings.LOAD_NO_CACHE
//        mWebSettings.domStorageEnabled = true
//        mWebSettings.databaseEnabled = true
//        mWebSettings.setAppCacheEnabled(true)
//        mWebSettings.allowFileAccess = true
//        mWebSettings.savePassword = true
//        mWebSettings.setSupportZoom(true)
//        mWebSettings.builtInZoomControls = true
//        mWebSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
//        mWebSettings.useWideViewPort = true
//
//        wv_web.webViewClient = webViewClient
//        wv_web.webChromeClient = webChromeClient
//
//        //      reload("protocol.html");
//        wv_web.addJavascriptInterface(WebAppInterface(mContext), "SBridge")
//        wv_web.addJavascriptInterface(this, "SBridge")
//        initTitle()
//        btn_reload.setOnClickListener { post() }
//
//        //        final ObjectAnimator anim = ObjectAnimator.ofInt(iv_place, "ImageLevel", 0, 10000);
//        //        anim.setDuration(800);
//        //        anim.setRepeatCount(ObjectAnimator.INFINITE);
//        //        anim.start();
//        //
//        //        iv_progress.setImageResource(R.drawable.glide_rotate);
//        //        iv_place.setImageResource(R.drawable.glide_rotate);
//        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2760457749,4161462131&fm=27&gp=0.jpg").into<ImageViewTarget<Drawable>>(object : ImageViewTarget<Drawable>(iv_place) {
//            override fun setResource(resource: Drawable?) {
//                Log.v("lhz", "setResource")
//            }
//
//            override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
//                super.onResourceReady(resource, transition)
//                //                Log.v("lhz", "onResourceReady");
//                iv_place.setImageDrawable(resource)
//                //                pb_loading.setVisibility(View.GONE);
//            }
//
//            override fun onLoadFailed(errorDrawable: Drawable?) {
//                super.onLoadFailed(errorDrawable)
//                //                Log.v("lhz", "onLoadFailed");
//            }
//
//            override fun onLoadCleared(placeholder: Drawable?) {
//                super.onLoadCleared(placeholder)
//                //                Log.v("lhz", "onLoadCleared");
//            }
//        })
//
//        //        GlideApp.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2760457749,4161462131&fm=27&gp=0.jpg").listener(new RequestListener<Drawable>() {
//        //            @Override
//        //            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//        //                Log.v("lhz", "onLoadFailed");
//        //                return false;
//        //            }
//        //
//        //            @Override
//        //            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//        //                Log.v("lhz", "onResourceReady");
//        //                return false;
//        //            }
//        //        }).into(new ImageViewTarget<Drawable>(iv_place) {
//        //            @Override
//        //            protected void setResource(@Nullable Drawable resource) {
//        //                iv_place.setImageDrawable(resource);
//        //                Log.v("lhz", "setResource");
//        //            }
//        //        });
//    }
//
//    private fun post1(appId: String, appSecret: String) {
//        val token = async(UI) {
//            val urlParams = hashMapOf<String, String>()
//            urlParams.put("grant_type", "client_credential")
//            urlParams.put("appid", appId)
//            urlParams.put("secret", appSecret)
//
//            val getUrl = OkHttpUtils.appendUrl("https://api.weixin.qq.com/cgi-bin/token", urlParams)
//            val okHttpClient = OkHttpClient().newBuilder().build()
//            val builder = Request.Builder()
//            builder.url(getUrl)
//
//            val request = builder.build()
//            val call = okHttpClient.newCall(request)
//            val response = bg {
//                call.execute()
//            }
//            val data = response.await().body()?.string()
//            val jsonObject = JSONObject(data)
//            val tokenString = jsonObject["access_token"] as String
//            return@async tokenString
//        }
//    }
//
//    fun excute(method: String, json: String) {
//        BaseApp.post(Runnable { wv_web.loadUrl("javascript:$method($json)") })
//    }
//
//
//    private fun post() {
//
//        val okHttpClient = OkHttpClient().newBuilder().build()
//        val builder = Request.Builder()
//
//        val jsonObject = JSONObject()
//        try {
//            jsonObject.put("scene", "park2218")
//            jsonObject.put("page", "pages/main/main")
//            jsonObject.put("width", 1024)
//            jsonObject.put("auto_color", true)
//
//            val jsonObject1 = JSONObject()
//            jsonObject1.put("r", "0")
//            jsonObject1.put("g", "0")
//            jsonObject1.put("b", "0")
//
//            jsonObject.put("line_color", jsonObject1)
//        } catch (e: Exception) {
//
//        }
//
//        val line_color = jsonObject.toString()
//        Log.v("lhz", "data: " + line_color)
//        val JSON = MediaType.parse("application/json;charset=utf-8")
//
//        val requestBody = RequestBody.create(JSON, line_color)
//        builder.addHeader("content-type", "application/json;charset:utf-8")
//
//        val appId = ""
//        val appSecret = ""
//        val token = getToken(appId, appSecret)
//        async {
//            val accessToken = token.await() as String
//            val url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken
//            builder.url(url)
//            builder.post(requestBody)
//            val request = builder.build()
//
//            val call = okHttpClient.newCall(request)
//            val response = bg {
//                call.execute()
//            }
//            val responseBody = response.await().body()
//            saveAdData(responseBody?.byteStream()!!)
//        }
//    }
//
//    inline fun getToken(appId: String, appSecret: String) = async {
//        val urlParams = hashMapOf<String, String>()
//        urlParams.put("grant_type", "client_credential")
//        urlParams.put("appid", appId)
//        urlParams.put("secret", appSecret)
//
//        val getUrl = OkHttpUtils.appendUrl("https://api.weixin.qq.com/cgi-bin/token", urlParams)
//        val okHttpClient = OkHttpClient().newBuilder().build()
//        val builder = Request.Builder()
//        builder.url(getUrl)
//
//        val request = builder.build()
//        val call = okHttpClient.newCall(request)
//        val response = bg {
//            call.execute()
//        }
//        val data = response.await().body()?.string()
//        val jsonObject = JSONObject(data)
//        val token = jsonObject["access_token"]
//        return@async token
//    }
//
//
//    private fun saveAdData(inputStream: InputStream) {
//        val isGranted = ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
//        if (!isGranted) {
//            ActivityCompat.requestPermissions(mActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
//        } else {
//            HttpUtils.saveAd(inputStream)
//        }
//    }
//
//    private fun getQRCode() {
//
//    }
//
//
//    private fun reload(xml: String) {
//        wv_web.clearView()
//        wv_web.measure(10, 10)
//        val am = mContext.assets
//        try {
//            val `is` = am.open(xml)
//            val buffer = ByteArray(10000)
//            `is`.read(buffer)
//            val data = String(buffer)
//            wv_web.loadData(data, "text/html; charset=UTF-8", null)
//            wv_web.requestLayout()
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//
//    }
//
//    @JavascriptInterface
//    fun resize(json: String) {
//        Log.v("lhz", "json: " + json)
//        wv_web.post {
//            try {
//                val jsonObject = JSONObject(json)
//                val type = jsonObject.getString("type")
//                val height = jsonObject.getDouble("height")
//                Log.v("lhz", "height: " + height + " " + wv_web.height)
//                val heightT = (height * resources.displayMetrics.density).toInt()
//                wv_web.layoutParams = LinearLayout.LayoutParams(resources.displayMetrics.widthPixels, heightT)
//            } catch (e: Exception) {
//
//            }
//        }
//    }
//
//    private fun initTitle() {
//        setDefaultTitle(mTitle)
//        setRightImg(R.drawable.ic_more_add_selector, object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                wv_web.pageUp(true)
//            }
//        })
//        setRightImg(R.drawable.ic_more_add_selector, object : View.OnClickListener {
//            override fun onClick(v: View?) {
//            }
//        })
//        titleBar?.setOnClickListener { }
//
//        setTitleBarOnClickListener(object : View.OnClickListener {
//            override fun onClick(v: View?) {
//                wv_web.pageUp(true)
//            }
//        })
//    }
//
//    private fun showPopupWindow(v: View) {
//        val view = LayoutInflater.from(mContext).inflate(R.layout.fragemt_web_more_popup, null)
//        //        PopupUtils.showTopRightPopupWindow(baseActivity, v, view);
//        //        PopupUtils.showPopupWindow(view).showAtLocation(v.getParent(), Gravity.RIGHT,);
//        PopupUtils.showPopupWindow(view).showAsDropDown(v)
//    }
//
//    private fun startActivityForUrl(url: String): Boolean {
//        var intent: Intent
//        // perform generic parsing of the URI to turn it into an Intent.
//        try {
//            intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
//        } catch (ex: URISyntaxException) {
//            Log.w("Browser", "Bad URI " + url + ": " + ex.message)
//            return false
//        }
//
//        // check whether the intent can be resolved. If not, we will see
//        // whether we can download it from the Market.
//
//        if (mContext.packageManager.resolveActivity(intent, 0) == null) {
//            val packagename = intent.`package`
//            if (packagename != null) {
//                try {
//                    intent = Intent(Intent.ACTION_VIEW, Uri.parse("market://search?q=pname:" + packagename))
//                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
//                    startActivity(intent)
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    baseActivity.finish()
//                    return false
//                }
//
//                // before leaving BrowserActivity, close the empty child tab.
//                // If a new tab is created through JavaScript open to load this
//                // data, we would like to close it as we will load this data in a
//                // different Activity.
//                // mController.closeEmptyTab();
//                return true
//            } else {
//                return false
//            }
//        }
//
//        // sanitize the Intent, ensuring web pages can not bypass browser
//        // security (only access to BROWSABLE activities).
//        intent.addCategory(Intent.CATEGORY_BROWSABLE)
//        intent.component = null
//        // Re-use the existing tab if the intent comes back to us
//        // if (tab != null) {
//        // if (tab.getAppId() == null) {
//        // tab.setAppId(mActivity.getPackageName() + "-" + tab.getId());
//        // }
//        // intent.putExtra(Browser.EXTRA_APPLICATION_ID, tab.getAppId());
//        // }
//        // Make sure webkit can handle it ly before checking for specialized
//        // handlers. If webkit can't handle it ly, we need to call
//        // startActivityIfNeeded
//        val m = UrlUtils.ACCEPTED_URI_SCHEMA.matcher(url)
//        if (m.matches()) { //&& !isSpecializedHandlerAvailable(intent)) {
//            return false
//        }
//        try {
//            intent.putExtra(UrlUtils.EXTRA_DISABLE_URL_OVERRIDE, true)
//            if (baseActivity.startActivityIfNeeded(intent, -1)) {
//                // before leaving BrowserActivity, close the empty child tab.
//                // If a new tab is created through JavaScript open to load this
//                // data, we would like to close it as we will load this data in a
//                // different Activity.
//                // mController.closeEmptyTab();
//                return true
//            }
//        } catch (ex: ActivityNotFoundException) {
//            // ignore the error. If no application can handle the URL,
//            // eg about:blank, assume the browser can handle it.
//        }
//
//        return false
//    }
//
//    private fun isSpecializedHandlerAvailable(intent: Intent): Boolean {
//        val pm = mContext.packageManager
//        val handlers = pm.queryIntentActivities(intent, PackageManager.GET_RESOLVED_FILTER)
//        if (handlers == null || handlers.size == 0) {
//            return false
//        }
//        for (resolveInfo in handlers) {
//            val filter = resolveInfo.filter ?: // No intent filter matches this intent?
//            // Error on the side of staying in the browser, ignore
//            continue
//            if (filter.countDataAuthorities() == 0 && filter.countDataPaths() == 0) {
//                // Generic handler, skip
//                continue
//            }
//            return true
//        }
//        return false
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        if (null != wv_web) {
//            wv_web.removeAllViews()
//            wv_web.destroy()
//        }
//    }
//
//    companion object {
//
//
//        fun newInstance(title: String, url: String): TestFragment {
//            val fragment = TestFragment()
//            val args = Bundle()
//            args.putString("mTitle", title)
//            args.putString("data", url)
//            fragment.arguments = args
//            return fragment
//        }
//
//
//        fun appendUrl(url: String?, urlParams: Map<String, String>?): String {
//            if (null == url) {
//                return ""
//            }
//            if (urlParams == null || urlParams.size <= 0) {
//                return url
//            }
//            val httpUrl = HttpUrl.parse(url) ?: return url
//            val urlBuilder = httpUrl.newBuilder()
//            for ((key, value) in urlParams) {
//                urlBuilder.addQueryParameter(key, value)
//            }
//            return urlBuilder.build().toString()
//        }
//    }
//}
