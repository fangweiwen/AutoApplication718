package com.example.autoapplication.util;


import android.support.v7.app.AppCompatActivity;

import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.functions.Consumer;

/**
 * author WeiWen Fang
 * on 2019/6/5.
 */
public class RxPermissionsUtils {

    public static void requestPermission(AppCompatActivity mContext, Consumer<Boolean> mConsumer, String permission1, String permission2) {
        RxPermissions rxPermissions = new RxPermissions(mContext);
        if (hasPermission(rxPermissions, permission1, permission2)) {

        } else {
            rxPermissions.request(permission1, permission2)
                    .subscribe(mConsumer);
        }

    }

    private static boolean hasPermission(RxPermissions rxPermissions, String permission1, String permission2) {
        return rxPermissions.isGranted(permission1) && rxPermissions.isGranted(permission2);
    }

}
