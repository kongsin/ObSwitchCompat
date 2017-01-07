package com.example.obswitchcompat;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by kongsin on 1/7/2017.
 */

public class ObSwitchCompatTab extends android.support.v7.widget.AppCompatTextView {

    private Drawable mImage = null;

    public ObSwitchCompatTab(Context context) {
        super(context);
    }

    public ObSwitchCompatTab(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ObSwitchCompatTab(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mImage != null){
            mImage.setBounds((int)(getHeight() * 0.2F), (int)(getHeight() * 0.2F), (int)(getHeight() * 0.8F), (int)(getHeight() * 0.8F));
            mImage.draw(canvas);
        }
    }

    public void setImageDrawable(Drawable img) {
        mImage = img;
    }

}
