package com.ddu.ui.activity

import android.os.Bundle
import android.view.KeyEvent
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ddu.R
import com.ddu.app.BaseApp
import com.ddu.icore.common.ext.startActivity
import com.ddu.icore.ui.activity.BaseActivity
import com.ddu.ui.fragment.LifeFragment
import com.ddu.ui.fragment.MeFragment
import com.ddu.ui.fragment.StudyFragment
import com.ddu.ui.fragment.WorkFragment
import com.ddu.util.ToastUtils
import kotlinx.android.synthetic.main.activity_mainr.*

class MainActivityR : BaseActivity(), RadioGroup.OnCheckedChangeListener {

    private lateinit var mStudyFragment: StudyFragment
    private lateinit var mWorkFragment: WorkFragment
    private lateinit var mLifeFragment: LifeFragment
    private lateinit var mMeFragment: MeFragment

    private var isExit: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setBackgroundDrawable(null)
        setContentView(R.layout.activity_mainr)

        savedInstanceState?.let {
            mStudyFragment = supportFragmentManager.findFragmentByTag(TAG_STUDY) as StudyFragment
            mWorkFragment = supportFragmentManager.findFragmentByTag(TAG_WORK) as WorkFragment
            mLifeFragment = supportFragmentManager.findFragmentByTag(TAG_LIFE) as LifeFragment
            mMeFragment = supportFragmentManager.findFragmentByTag(TAG_ME) as MeFragment
        }

        rg_main.setOnCheckedChangeListener(this)
        rg_main.check(R.id.rb_main_study)
        startActivity<MainActivityT>()
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        add(checkedId)
    }

    private fun add(checkedId: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideAll(transaction, mStudyFragment, mWorkFragment, mLifeFragment, mMeFragment)
        when (checkedId) {
            R.id.rb_main_study -> if (null == mStudyFragment) {
                mStudyFragment = StudyFragment.newInstance()
                transaction.add(fl_home_content.id, mStudyFragment, TAG_STUDY)
            } else {
                transaction.show(mStudyFragment)
            }
            R.id.rb_main_work -> if (null == mWorkFragment) {
                mWorkFragment = WorkFragment.newInstance()
                transaction.add(fl_home_content.id, mWorkFragment, TAG_WORK)
            } else {
                transaction.show(mWorkFragment)
            }
            R.id.rb_main_life -> if (null == mLifeFragment) {
                mLifeFragment = LifeFragment.newInstance()
                transaction.add(fl_home_content.id, mLifeFragment, TAG_LIFE)
            } else {
                transaction.show(mLifeFragment)
            }
            R.id.rb_main_me -> if (null == mMeFragment) {
                mMeFragment = MeFragment.newInstance()
                transaction.add(fl_home_content.id, mMeFragment, TAG_ME)
            } else {
                transaction.show(mMeFragment)
            }
        }
        transaction.commitAllowingStateLoss()
    }

    private fun hideAll(transaction: FragmentTransaction, vararg fragment: Fragment) {
        for (f in fragment) {
            if (!f.isHidden) {
                transaction.hide(f)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun exit() {
        if (!isExit) {
            isExit = true
            ToastUtils.showToast(R.string.main_exit_msg)
            BaseApp.postDelayed(Runnable { isExit = false }, 2000)
        } else {
            finish()
        }
    }


    override fun isShowTitleBar(): Boolean {
        return false
    }

    companion object {

        private const val TAG_STUDY = "TAG_STUDY"
        private const val TAG_WORK = "TAG_WORK"
        private const val TAG_LIFE = "TAG_LIFE"
        private const val TAG_ME = "TAG_ME"
    }
}
