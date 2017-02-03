package com.example.obswitchcompat;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

/**
 * Created by kongsin on 1/7/2017.
 */

public class ObSwitchCompatTab extends android.support.v7.widget.AppCompatTextView {

    private int iconPadding;

    public ObSwitchCompatTab(Context context) {
        super(context);
    }

    public ObSwitchCompatTab(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ObSwitchCompatTab(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public enum ImagePosition {
        LEFT,TOP,RIGHT,BOTTOM
    }

    public void setImageIconPadding(int inset){
        iconPadding = inset;
        setCompoundDrawablePadding(iconPadding);
    }

    public void setImageDrawable(Drawable img) {
        setImageDrawable(img, ImagePosition.TOP);
    }

    public void setImageDrawable(Drawable img, ImagePosition imagePosition) {
        if (img != null) {
            switch (imagePosition) {
                case LEFT:
                    setCompoundDrawablesWithIntrinsicBounds(img, null, null, null);
                    break;
                case TOP:
                    setCompoundDrawablesWithIntrinsicBounds(null, img, null, null);
                    break;
                case RIGHT:
                    setCompoundDrawablesWithIntrinsicBounds(null, null, img, null);
                    break;
                case BOTTOM:
                    setCompoundDrawablesWithIntrinsicBounds(null, null, null, img);
                    break;
            }
            invalidate();
        }
    }

}
