package com.shinhan.addf;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by IC-INTPC-087101 on 2017-03-24.
 */

public class SingerItemView extends LinearLayout {
    public SingerItemView(Context context) {
        super(context);
        init(context);
    }
    public SingerItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
}
