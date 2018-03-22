package com.ddu.icore.dialog

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.*
import com.ddu.icore.R
import kotlinx.android.synthetic.main.fragment_dialog_default.*


class DefaultDialogFragment : DialogFragment(), View.OnClickListener {

    var title = ""
    var msg = ""
    var leftText = ""
    var rightText = ""

    var msgGravity = Gravity.CENTER

    var leftColor = R.color.c_4897fa
    var rightColor = R.color.c_272727

    var mLeftClickListener: ((View, DialogFragment) -> Unit)? = null
    var mRightClickListener: ((View, DialogFragment) -> Unit)? = null

    var size = 17f

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.window.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window.setBackgroundDrawableResource(android.R.color.transparent)
        return inflater.inflate(R.layout.fragment_dialog_default, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!title.isNullOrEmpty()) {
            tv_dialog_title.text = title
            tv_dialog_title.visibility = View.VISIBLE
            view_line.visibility = View.VISIBLE
        }

        tv_dialog_msg.text = msg
        tv_dialog_msg.gravity = msgGravity
        tv_dialog_msg.textSize = size
        tv_dialog_btn_left.text = leftText
        var color = resources.getColor(leftColor)
        tv_dialog_btn_left.setTextColor(color)
        if (!rightText.isNullOrEmpty()) {
            color = resources.getColor(rightColor)
            tv_dialog_btn_right.visibility = View.VISIBLE
            tv_dialog_btn_right.text = rightText
            tv_dialog_btn_right.setTextColor(color)
        }

        tv_dialog_btn_left.setOnClickListener(this)
        tv_dialog_btn_right.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.tv_dialog_btn_left && null != mLeftClickListener) {
            mLeftClickListener?.invoke(v, this)
        } else if (id == R.id.tv_dialog_btn_right && null != mRightClickListener) {
            mRightClickListener?.invoke(v, this)
        }
    }
}
