package com.ddu.icore.navigation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.ddu.icore.dialog.ShareDialogFragment;
import com.ddu.icore.ui.activity.ShowDetailActivity;

/**
 * Created by yzbzz on 2016/10/28.
 */

public class Navigator {

    public static void startShowDetailActivity(@NonNull Context context, @NonNull Class<? extends Fragment> fragment, Bundle bundle) {
        startShowDetailActivity(context, fragment.getName(), bundle);
    }

    public static void startShowDetailActivity(@NonNull Context context, String fragmentName, Bundle bundle) {
        Intent intent = ShowDetailActivity.getShowDetailIntent(context, fragmentName, bundle);
        context.startActivity(intent);
    }

    public static void startShareFragmentDialog(@NonNull FragmentActivity activity) {
        ShareDialogFragment shareDialogFragment = ShareDialogFragment.newInstance();
        shareDialogFragment.show(activity.getSupportFragmentManager(),"shareDialogFragment");
    }

}
