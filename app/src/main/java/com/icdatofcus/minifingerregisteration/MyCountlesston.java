package com.icdatofcus.minifingerregisteration;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by DAVID OGUNDEPO on 07/12/2017.
 *
 */

public class MyCountlesston {

    private static MyCountlesston mInstance;
    private RequestQueue mRequestQueue;
    private static Context mContext;

    private MyCountlesston(Context context) {
        mContext = context;
        mRequestQueue = getRequestQueue();
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        return mRequestQueue;
    }

    public static synchronized MyCountlesston getmInstance(Context context) {
        if (mInstance == null) {
            mInstance = new MyCountlesston(context);
        }
        return mInstance;
    }

    public <O>void addToRequestQueue(Request<O> request) {
        mRequestQueue.add(request);
    }
}

