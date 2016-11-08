package com.example.obswitchcompat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
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
    private int mTabCount = 0;
    private TextView mThumbView;
    private static final String TAG = "ObSwitchCompat";
    private int currentPosition = 0;
    private boolean isScrollNormal = true;
    private boolean checked = false;
    private int titleTextSize = 14;

    private int trackColor = Color.WHITE;
    private int trackStokeColor = Color.GRAY;
    private int trackStokeWidth = DEFAULT_STROKE_WIDTH;
    private int trackTextColor = Color.GRAY;
    private int trackWidth = DEFAULT_TRACK_WIDTH;
    private int trackHeight = DEFAULT_TRACK_HEIGHT;

    private int thumbColor = Color.GRAY;
    private int thumbStokeColor = Color.WHITE;
    private int thumbStokeWidth = DEFAULT_STROKE_WIDTH;
    private int thumbTextColor = Color.WHITE;
    private int thumbWidth = DEFAULT_THRUMB_WIDTH;
    private int thumbHeight = DEFAULT_THRUMB_HEIGHT;

    private int cornerRadians = 50;

    private ViewPager mPager;
    private Drawable trackDrawable;
    private Drawable thumbDrawable;

    public void setCornerRadians(int cornerRadians) {
        this.cornerRadians = cornerRadians;
        invalidateView();
    }

    public int getCornerRadians() {
        return cornerRadians;
    }

    public void setTrackColor(int trackColor) {
        this.trackColor = trackColor;
        initTrack();
    }

    public int getTrackColor() {
        return trackColor;
    }

    public void setTrackStokeColor(int trackStokeColor) {
        this.trackStokeColor = trackStokeColor;
        initTrack();
    }

    public int getTrackStokeColor() {
        return trackStokeColor;
    }

    public void setTrackStokeWidth(int trackStokeWidth) {
        this.trackStokeWidth = trackStokeWidth;
        initTrack();
    }

    public int getTrackStokeWidth() {
        return trackStokeWidth;
    }

    public void setTrackWidth(int trackWidth) {
        this.trackWidth = trackWidth;
        initTrack();
    }

    public void setTrackHeight(int trackHeight) {
        this.trackHeight = trackHeight;
        initTrack();
    }

    public void setThumbWidth(int thumbWidth) {
        this.thumbWidth = thumbWidth;
        initThumbView();
    }

    public void setThumbHeight(int thumbHeight) {
        this.thumbHeight = thumbHeight;
        initThumbView();
    }

    public void setThumbColor(int thumbColor) {
        this.thumbColor = thumbColor;
        initThumbView();
    }

    public int getThumbColor() {
        return thumbColor;
    }

    public void setThumbStokeWidth(int thumbStokeWidth) {
        this.thumbStokeWidth = thumbStokeWidth;
        initThumbView();
    }

    public int getThumbStokeWidth() {
        return thumbStokeWidth;
    }

    public void setThumbStokeColor(int thumbStokeColor) {
        this.thumbStokeColor = thumbStokeColor;
        initThumbView();
    }

    public int getThumbStokeColor() {
        return thumbStokeColor;
    }

    public void setThumbTextColor(int thumbTextColor) {
        this.thumbTextColor = thumbTextColor;
        initThumbView();
    }

    public int getThumbTextColor() {
        return thumbTextColor;
    }

    public void setTrackTextColor(int trackTextColor) {
        this.trackTextColor = trackTextColor;
        initTrack();
    }

    public int getTrackTextColor() {
        return trackTextColor;
    }

    public int getCurrentPosition(){
        if (mPager != null) {
            return mPager.getCurrentItem();
        } else {
            return 0;
        }
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

    private ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (positionOffset > 0) {
                setCurrentTab(positionOffset, position);
            } else {
                if (!checked) {
                    mPager.post(new Runnable() {
                        @Override
                        public void run() {
                            checkScrollType();
                        }
                    });
                }
            }
        }

        @Override
        public void onPageSelected(final int position) {
            mPager.post(new Runnable() {
                @Override
                public void run() {
                    mThumbView.setText(getPageTitle(position));
                    currentPosition = position;
                }
            });
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private String getPageTitle(int position){
        if (mPager != null){
            return mPager.getAdapter().getPageTitle(position).toString();
        } else {
            return "";
        }
    }

    public void setupWithViewPager(ViewPager pager){
        mPager = pager;
        if (mPager != null) {
            mTabCount = mPager.getAdapter().getCount();
            mPager.addOnPageChangeListener(pageChangeListener);
            invalidateView();
        }
    }

    public void setCurrentTab(final float offset, final int position){
        Log.i(TAG, "setCurrentTab: " + offset);
        if (mTabCount > 0) {
            float w = mThumbView.getWidth();
            float newScroll = (int) (w * offset);
            float startFrom = 0;
            if (position == 0){
                startFrom += mainLayout.getPaddingLeft();
            } else {
                startFrom = (w * position) - mainLayout.getPaddingRight();
            }
            newScroll += startFrom;
            mThumbView.setX(newScroll);
            currentPosition = position;
        }
    }

    public void initView(AttributeSet attrs){
        inflatLayout(attrs);
    }

    public void initView(){
        inflatLayout(null);
    }

    private void inflatLayout(AttributeSet attrs){
        LayoutInflater inflaterCompat = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflaterCompat.inflate(R.layout.ob_switch_compat_layout, this);
        mainLayout = (RelativeLayout) findViewById(R.id.main_layout);
        titleLayout = (LinearLayout) findViewById(R.id.titleLayout);
        invalidateView();
    }

    private void invalidateView(){
        checked = false;
        initMainLayout();
        initTrack();
        initTitle();
        initThumbView();
        initCurrentTab();

        if (isInEditMode()){
            setThumbText("TAB"+currentPosition);
        } else {
            setThumbText(getPageTitle(currentPosition));
        }

        if (isInEditMode()) {
            setThumbText("TAB1");
        }

        setThumbMoveable();
    }

    private void checkScrollType(){
        if (mPager != null && !checked){
            if (mPager.getScrollX() != currentPosition * mPager.getWidth()){
                isScrollNormal = false;
            } else {
                isScrollNormal = true;
            }
            checked = true;
        }
    }

    private void initTitle() {
        if (mPager != null) {
            titleLayout.removeAllViews();
            for (TextView textView : getTitleViews()) {
                titleLayout.addView(textView);
            }
        }
    }

    private void initMainLayout() {
        mainLayout.setPadding(parseDP(1), parseDP(1), parseDP(1), parseDP(1));
        mainLayout.getLayoutParams().width = parseDP(thumbWidth * mTabCount);
        mainLayout.getLayoutParams().height = parseDP(trackHeight);
    }

    public void initThumbView(){
        mainLayout.removeView(mThumbView);
        mainLayout.addView(getThumbView());
        setThumbMoveable();
    }

    private void initCurrentTab() {
        if (mPager != null && mPager.getAdapter().getCount() > 0) {
            mPager.post(new Runnable() {
                @Override
                public void run() {
                    setCurrentTab(mPager.getCurrentItem(), currentPosition);
                    setThumbText(getPageTitle(currentPosition));
                }
            });
        } else {
            setCurrentTab(currentPosition, currentPosition);
        }
    }

    private void initTrack() {

        titleLayout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
        titleLayout.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
        titleLayout.setGravity(Gravity.CENTER);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mainLayout.setBackground(trackDrawable != null ? trackDrawable : getTrack());
        } else {
            mainLayout.setBackgroundDrawable(trackDrawable != null ? trackDrawable : getTrack());
        }

    }

    public void setThumbMoveable() {
        int[] posXY = new int[2];
        mainLayout.getLocationOnScreen(posXY);
        new ToggleSlide(mThumbView, posXY[0] + mainLayout.getPaddingLeft(), ((parseDP(thumbWidth) * mTabCount) - mainLayout.getPaddingRight()) - parseDP(thumbWidth), parseDP(thumbWidth), mTabCount, new ToggleSlide.GesturCallBack() {

            @Override
            public void onSelectedPage(int index) {
                mThumbView.setText(getPageTitle(index));
                scrollWidthFixedWrongPosition(index * mPager.getWidth());
                mPager.setCurrentItem(index, true);
                setCurrentTab(0, index);
            }

            @Override
            public void onScroll(float posX) {
                float persent = ((posX * 100)/mThumbView.getWidth());
                int scrollValue = (int) ((persent * mPager.getWidth()) / 100);
                scrollWidthFixedWrongPosition(scrollValue);
            }
        });
    }

    private void scrollWidthFixedWrongPosition(int position){
        int pageWidth = mPager.getWidth();
        if (!isScrollNormal) {
            pageScroll(position - pageWidth);
        } else {
            pageScroll(position);
        }
    }

    private void pageScroll(int position){
        mPager.scrollTo(position, 0);
    }

    private List<TextView> getTitleViews(){

        LinearLayout.LayoutParams params = new LayoutParams(0, parseDP(30));
        params.weight = 1;
        params.gravity = Gravity.CENTER;

        for (int i = 0; i < mTabCount; i++) {
            TextView textView = new TextView(getContext());
            textView.setPadding(parseDP(4), parseDP(2), parseDP(4), parseDP(2));
            textView.setLayoutParams(params);
            textView.setText(getPageTitle(i));
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(this);
            textView.setTextSize(this.titleTextSize);
            textView.setTag(i);
            titleViews.add(textView);
        }
        return titleViews;
    }

    public void setTitleTextSize(int val){
        this.titleTextSize = parseDP(val);
    }

    private TextView getThumbView(){

        mThumbView = new TextView(getContext());
        mThumbView.setGravity(Gravity.CENTER);
        mThumbView.setTextColor(Color.WHITE);
        mThumbView.setWidth(parseDP(thumbWidth));
        mThumbView.setHeight(parseDP(thumbHeight));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            mThumbView.setBackground(thumbDrawable != null ? thumbDrawable : getThumb());
        } else {
            mThumbView.setBackgroundDrawable(thumbDrawable != null ? thumbDrawable : getThumb());
        }

        return mThumbView;
    }

    public void setThumbText(String text){
        mThumbView.setText(text);
    }

    private Drawable getTrack(){
        GradientDrawable trackDrawable = new GradientDrawable();
        trackDrawable.setColor(trackColor);
        trackDrawable.setStroke(parseDP(trackStokeWidth), trackStokeColor);
        trackDrawable.setCornerRadius(parseDP(cornerRadians));
        trackDrawable.setSize(parseDP(trackWidth), parseDP(trackHeight));
        trackDrawable.setShape(GradientDrawable.RECTANGLE);
        trackDrawable.setBounds(parseDP(0), parseDP(0), parseDP(trackWidth), parseDP(trackHeight));
        return trackDrawable;
    }

    private Drawable getThumb(){
        GradientDrawable thumbDrawable = new GradientDrawable();
        thumbDrawable.setColor(thumbColor);
        thumbDrawable.setStroke(parseDP(thumbStokeWidth), thumbStokeColor);
        thumbDrawable.setCornerRadius(parseDP(cornerRadians));
        thumbDrawable.setSize(parseDP(thumbWidth), parseDP(thumbHeight));
        thumbDrawable.setBounds(parseDP(0), parseDP(0), parseDP(thumbWidth), parseDP(thumbHeight));
        thumbDrawable.setShape(GradientDrawable.RECTANGLE);
        return thumbDrawable;
    }

    public void setTrackDrawable(Drawable drawable){
        trackDrawable = drawable;
        initTrack();
    }

    public void setThumbDrawable(Drawable drawable){
        thumbDrawable = drawable;
        initThumbView();
    }

    private int parseDP(int dp){
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView){
            int position = (int) v.getTag();
            mPager.setCurrentItem(position, true);
            setCurrentTab(position, position);
        }
    }
}
