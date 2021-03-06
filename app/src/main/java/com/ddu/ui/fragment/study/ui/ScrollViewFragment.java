package com.ddu.ui.fragment.study.ui;

import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.core.widget.NestedScrollView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import com.ddu.R;
import com.ddu.icore.refresh.PullToRefreshBase;
import com.ddu.icore.refresh.PullToRefreshScrollView;
import com.ddu.icore.refresh.internal.RotateLoadingLayout;
import com.ddu.icore.ui.fragment.DefaultFragment;
import com.ddu.icore.util.AnimatorUtils;
import com.ddu.ui.view.CustomerScrollView;
import com.ddu.ui.view.CustomerView;
import com.iannotation.IElement;

/**
 * Created by yzbzz on 2017/5/26.
 */
@IElement("UI")
public class ScrollViewFragment extends DefaultFragment implements CustomerScrollView.ScrollViewListener {

    private PullToRefreshScrollView customerScrollView;
    private FrameLayout frameLayout;
    private RotateLoadingLayout rotateLoadingLayout;
    private CustomerView customerView;

    private FrameLayout mFlAnimator;
    private ImageView mIvG;
    private ImageView mIvCar;
    private float mRotationPivotX, mRotationPivotY;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_scroll_view;
    }

    @Override
    public void initView() {
        mFlAnimator = findViewById(R.id.fl_animator);

        initAnimator();

        frameLayout = findViewById(R.id.fl_refresh_content);
        customerScrollView = findViewById(R.id.csv_activity_base);
        rotateLoadingLayout = new RotateLoadingLayout(getMContext(), PullToRefreshBase.Mode.PULL_FROM_START, customerScrollView.getPullToRefreshScrollDirection());

        customerView = new CustomerView(getMContext(), PullToRefreshBase.Mode.PULL_FROM_START, customerScrollView);
        customerView.setCarImageView(mIvCar);
        customerView.setIvG(mIvG);


        frameLayout.addView(customerView);
//        customerScrollView.setHeader(customerView);
        customerScrollView.setRefreshView(customerView);
        customerScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<NestedScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<NestedScrollView> refreshView) {
                customerScrollView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        customerScrollView.onRefreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<NestedScrollView> refreshView) {
                customerScrollView.onRefreshComplete();
            }
        });
    }

    private Matrix mHeaderImageMatrix;

    private void initAnimator() {

        mIvCar = findViewById(R.id.iv_car);

        mIvG = findViewById(R.id.iv_g);
        mIvG.setScaleType(ImageView.ScaleType.MATRIX);
        mHeaderImageMatrix = new Matrix();

        mRotationPivotX = Math.round(getDrawable().getIntrinsicWidth() / 2f);
        mRotationPivotY = Math.round(getDrawable().getIntrinsicHeight() / 2f);

        mHeaderImageMatrix.setRotate(60);
//        mIvG.setImageMatrix(mHeaderImageMatrix);

        mIvG.post(new Runnable() {
            @Override
            public void run() {
                mIvG.setPivotY(mIvG.getHeight() / 2);
                mIvG.setPivotX(mIvG.getWidth());
                AnimatorUtils.rotationY(mIvG, 300, 0, 60).start();
            }
        });
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }

    @Override
    public void onScrollChanged(ScrollView scrollView, int x, int y, int oldx, int oldy) {
    }

    private Drawable getDrawable() {
        return getMContext().getResources().getDrawable(R.drawable.ptf_g);
    }

    @Override
    public boolean isShowTitleBar() {
        return false;
    }
}
