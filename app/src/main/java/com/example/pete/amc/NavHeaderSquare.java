package com.example.pete.amc;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class NavHeaderSquare extends ImageView{

    public NavHeaderSquare(Context context) {
        super(context);
    }

    public NavHeaderSquare(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NavHeaderSquare(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }
}