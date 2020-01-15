package com.ddu.ui.fragment.work.kotlin

import android.os.Bundle
import android.view.View
import com.ddu.R
import com.ddu.icore.ui.fragment.DefaultFragment
import com.ddu.ui.fragment.WebFragment
import com.iannotation.ICodeLabsElement

/**
 * Created by yzbzz on 2018/6/8.
 */
@ICodeLabsElement(path = "Kotlin_CodeLabs", parentId = "2", parentContent = "Advanced Graphics",
        id = "1", content = "Creating Custom Views")
class DialViewFragment : DefaultFragment() {

    override fun getLayoutId(): Int {
        return R.layout.study_customer_dial_view
    }

    override fun initView() {
        setDefaultTitle("DialView")
        setRightText("地址", View.OnClickListener {
            val bundle = Bundle()
            bundle.putString("title", "Creating Custom Views")
            bundle.putString("url", "https://codelabs.developers.google.com/codelabs/advanced-android-kotlin-training-welcome/#0")
            startFragment(WebFragment::class.java,bundle)
        })
    }


}
