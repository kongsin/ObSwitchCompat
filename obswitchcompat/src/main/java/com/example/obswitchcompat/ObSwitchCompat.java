package com.example.obswitchcompat;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by kognsin on 10/6/2016.
 */

@RemoteViews.RemoteView
public class ObSwitchCompat extends LinearLayout implements View.OnClickListener {

    private final int DEFAULT_TRACK_WIDTH = 160;
    private final int DEFAULT_TRACK_HEIGHT = 30;

    private final int DEFAULT_THRUMB_WIDTH = 80;
    private final int DEFAULT_THRUMB_HEIGHT = 30;

    private final int DEFAULT_STROKE_WIDTH = 1;

    private RelativeLayout mainLayout;
    private LinearLayout titleLayout;
    private List<TextView> titleViews = new ArrayList<>();
    private int mTabCount;
    private String[] mTitle;
    private TextView mThrumbView;
    private static final String TAG = "ObSwitchCompat";
    private int currentPosition = 0;
    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    private int trackColor = Color.WHITE;
    private int trackStokeColor = Color.GRAY;
    private int trackStokeWidth = DEFAULT_STROKE_WIDTH;

    private int thrumbColor = Color.GRAY;
    private int thrumbStokeColor = Color.WHITE;
    private int thrumbStokeWidth = DEFAULT_STROKE_WIDTH;

    private int thrumbTextColor = Color.WHITE;
    private int trackTextColor = Color.GRAY;
    private int cornerRadians = 50;

    private int trackWidth = DEFAULT_TRACK_WIDTH;
    private int trackHeight = DEFAULT_TRACK_HEIGHT;

    private int thrumbWidth = DEFAULT_THRUMB_WIDTH;
    private int thrumbHeight = DEFAULT_THRUMB_HEIGHT;
    private ViewPager mPager;

    public void setCornerRadians(int cornerRadians) {
        this.cornerRadians = cornerRadians;
        invalidateView();
    }

    public int getCornerRadians() {
        return cornerRadians;
    }

    public void setTrackColor(int trackColor) {
        this.trackColor = trackColor;
        invalidateView();
    }

    public int getTrackColor() {
        return trackColor;
    }

    public void setTrackStokeColor(int trackStokeColor) {
        this.trackStokeColor = trackStokeColor;
        invalidateView();
    }

    public int getTrackStokeColor() {
        return trackStokeColor;
    }

    public void setTrackStokeWidth(int trackStokeWidth) {
        this.trackStokeWidth = trackStokeWidth;
        invalidateView();
    }

    public int getTrackStokeWidth() {
        return trackStokeWidth;
    }

    public void setThrumbColor(int thrumbColor) {
        this.thrumbColor = thrumbColor;
        invalidateView();
    }

    public int getThrumbColor() {
        return thrumbColor;
    }

    public void setThrumbStokeWidth(int thrumbStokeWidth) {
        this.thrumbStokeWidth = thrumbStokeWidth;
        invalidateView();
    }

    public int getThrumbStokeWidth() {
        return thrumbStokeWidth;
    }

    public void setThrumbStokeColor(int thrumbStokeColor) {
        this.thrumbStokeColor = thrumbStokeColor;
        invalidateView();
    }

    public int getThrumbStokeColor() {
        return thrumbStokeColor;
    }

    public void setThrumbTextColor(int thrumbTextColor) {
        this.thrumbTextColor = thrumbTextColor;
        invalidateView();
    }

    public int getThrumbTextColor() {
        return thrumbTextColor;
    }

    public void setTrackTextColor(int trackTextColor) {
        this.trackTextColor = trackTextColor;
        invalidateView();
    }

    public int getTrackTextColor() {
        return trackTextColor;
    }

    public ObSwitchCompat(Context context) {
        super(context);
        initView();
    }

    public ObSwitchCompat(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public ObSwitchCompat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private SharedPreferences getPref(){
        return getContext().getSharedPreferences("OBSW", Context.MODE_PRIVATE);
    }

    private void saveInx(int inx){
        getPref().edit().putInt("INX", inx).apply();
    }

    private int getInx(){
        return getPref().getInt("INX", 0);
    }

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (positionOffset > 0){
                setCurrentTab(positionOffset);
            } else {
                setCurrentTab(getInx());
            }
        }

        @Override
        public void onPageSelected(int position) {
            mThrumbView.setText(mTitle[position]);
            currentPosition = position;
            saveInx(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    public void setupWithViewPager(ViewPager pager){
        mPager = pager;
        mPager.addOnPageChangeListener(pageChangeListener);
    }

    public void setCurrentTab(float currentPosition){
        mThrumbView.setX((mainLayout.getX() + mainLayout.getPaddingLeft()) + (((mainLayout.getWidth() / mTabCount) - mainLayout.getPaddingLeft() - mainLayout.getPaddingRight()) * currentPosition));
    }

    public void initView(AttributeSet attrs){
        inflatLayout();
    }

    public void initView(){
        inflatLayout();
    }

    public void setTitle(String[] title){
        this.mTitle = title;
        if (title != null){
            this.mTabCount = title.length;
        }
    }

    private void inflatLayout(){
        LayoutInflater inflaterCompat = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflaterCompat.inflate(R.layout.ob_switch_compat_layout, this);

        mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        mainLayout.setPadding(parseDP(1), parseDP(1), parseDP(1), parseDP(1));

        titleLayout = (LinearLayout) findViewById(R.id.titleLayout);

        invalidateView();
    }

    private void invalidateView(){
        mainLayout.getLayoutParams().width = parseDP(trackWidth);
        mainLayout.getLayoutParams().height = parseDP(trackHeight);

        titleLayout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        titleLayout.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        titleLayout.setGravity(Gravity.CENTER);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mainLayout.setBackground(getTrack());
        } else {
            mainLayout.setBackgroundDrawable(getTrack());
        }

        if (isInEditMode()){
            setTitle(new String[]{"TAB1", "TAB2"});
        }

        if (mTitle != null && mTitle.length > 0) {
            for (TextView textView : getTitleViews()) {
                titleLayout.addView(textView);
            }
        }

        mainLayout.removeView(mThrumbView);
        mainLayout.addView(getThrumbView());

        if (isInEditMode()) {
            setThrumbText("TAB1");
        }

        if (mTitle != null && mTitle.length > 0) {
            setThrumbText(mTitle[currentPosition]);
        }

        if (mPager != null){
            mPager.setCurrentItem(currentPosition);
        }

        setThumbMoveable();

    }

    public void setThumbMoveable() {
        int[] posXY = new int[2];
        mainLayout.getLocationOnScreen(posXY);
        new ToggleSlide(mThrumbView, posXY[0] + mainLayout.getPaddingLeft(), (((posXY[0] + parseDP(trackWidth)) - mainLayout.getPaddingRight()) - parseDP(thrumbWidth)), new ToggleSlide.GesturCallBack() {
            @Override
            public void left() {
                currentPosition = 0;
                mPager.setCurrentItem(0);
                mPager.scrollTo(0, 0);
                setCurrentTab(0);
            }

            @Override
            public void right() {
                currentPosition = 1;
                mPager.setCurrentItem(1);
                mPager.scrollTo((mPager.getWidth() * mTabCount) - mPager.getWidth(), 0);
                setCurrentTab(1);
            }

            @Override
            public void onScroll(float posX) {
                float persent = ((posX * 100)/mThrumbView.getWidth());
                mPager.scrollTo((int) ((persent * mPager.getWidth()) / 100), 0);
            }
        });
    }

    private List<TextView> getTitleViews(){

        LinearLayout.LayoutParams params = new LayoutParams(0, parseDP(30));
        params.weight = 1;
        params.gravity = Gravity.CENTER;

        for (int i = 0; i < mTabCount; i++) {
            TextView textView = new TextView(getContext());
            textView.setPadding(parseDP(4), parseDP(2), parseDP(4), parseDP(2));
            textView.setLayoutParams(params);
            textView.setText(mTitle[i]);
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(this);
            textView.setTag(i);
            titleViews.add(textView);
        }
        return titleViews;
    }

    private TextView getThrumbView(){

        mThrumbView = new TextView(getContext());
        mThrumbView.setGravity(Gravity.CENTER);
        mThrumbView.setTextColor(Color.WHITE);
        mThrumbView.setWidth(parseDP(thrumbWidth));
        mThrumbView.setHeight(parseDP(thrumbHeight));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mThrumbView.setBackground(getThrumb());
        } else {
            mThrumbView.setBackgroundDrawable(getThrumb());
        }

        return mThrumbView;
    }

    public void setThrumbText(String text){
        mThrumbView.setText(text);
    }

    private Drawable getTrack(){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(trackColor);
        drawable.setStroke(parseDP(trackStokeWidth), trackStokeColor);
        drawable.setCornerRadius(parseDP(cornerRadians));
        drawable.setSize(parseDP(trackWidth), parseDP(trackHeight));
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setBounds(parseDP(0), parseDP(0), parseDP(trackWidth), parseDP(trackHeight));
        return drawable;
    }

    private Drawable getThrumb(){
        GradientDrawable drawable = new GradientDrawable();
        drawable.setColor(thrumbColor);
        drawable.setStroke(parseDP(thrumbStokeWidth), thrumbStokeColor);
        drawable.setCornerRadius(parseDP(cornerRadians));
        drawable.setSize(parseDP(thrumbWidth), parseDP(thrumbHeight));
        drawable.setBounds(parseDP(0), parseDP(0), parseDP(thrumbWidth), parseDP(thrumbHeight));
        drawable.setShape(GradientDrawable.RECTANGLE);
        return drawable;
    }

    private int parseDP(int dp){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView){
            int position = (int) v.getTag();
            setCurrentTab(position);
            mPager.setCurrentItem(position);
            currentPosition = position;
        }
    }
}
