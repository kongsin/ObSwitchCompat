package com.example.obswitchcompat;

import android.support.v4.view.GestureDetectorCompat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by kognsin on 10/7/2016.
 */

public class ToggleSlide implements GestureDetector.OnGestureListener {

    private GestureDetectorCompat gestureDetector;
    private View mView;
    private float start, end;
    private static final String TAG = "ToggleSlide";
    private GesturCallBack mGesturCallBack;

    public ToggleSlide(View view, final float start, final float end, GesturCallBack gesturCallBack){
        this.mView = view;
        this.start = start;
        this.end = end;
        this.mGesturCallBack = gesturCallBack;
        gestureDetector = new GestureDetectorCompat(view.getContext(), this);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gestureDetector.onTouchEvent(event);
                if (event.getAction() == MotionEvent.ACTION_UP){
                    if (mView.getX() < ((start + end) / 2)) {
                        mGesturCallBack.left();
                    } else {
                        mGesturCallBack.right();
                    }
                }
                return true;
            }
        });
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        float pos = mView.getX() + (e2.getX() - e1.getX());
        if (pos < start) pos = start;
        if (pos > end) pos = end;
        mView.setX(pos);
        mGesturCallBack.onScroll(pos);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    public interface GesturCallBack{
        void left();
        void right();
        void onScroll(float posX);
    }
}
