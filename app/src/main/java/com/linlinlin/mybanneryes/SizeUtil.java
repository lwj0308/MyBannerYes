package com.linlinlin.mybanneryes;

import android.content.Context;

public class SizeUtil {
    public static int dip2Px(Context context, float dip) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }
}
