package com.vinaymaneti.coderschooltodolistapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by vinaymaneti on 9/11/16.
 */
public class Util {

    static ProgressDialog mProgressDialog;

    public Util(ProgressDialog progressDialog) {
        mProgressDialog = progressDialog;
    }


    @NonNull
    public static ProgressDialog showProgressDialog(Context context) {
        mProgressDialog = new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage(context.getString(R.string.authenticating));
        mProgressDialog.show();
        return mProgressDialog;
    }

    public static void dismissProgessDialog() {
        mProgressDialog.dismiss();
    }
}
