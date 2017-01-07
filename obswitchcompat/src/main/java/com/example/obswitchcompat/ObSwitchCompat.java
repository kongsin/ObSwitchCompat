package com.example.obswitchcompat;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
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

    private final int DEFAULT_TRACK_WIDTH       = 160;
    private final int DEFAULT_TRACK_HEIGHT      = 30;

    private final int DEFAULT_THUMB_WIDTH       = 80;
    private final int DEFAULT_THRUMB_HEIGHT     = 30;

    private final int DEFAULT_PADDING           = 1;

    private final int DEFAULT_STROKE_WIDTH      = 1;

    private RelativeLayout mainLayout;
    private LinearLayout titleLayout;
    private List<TextView> titleViews           = new ArrayList<>();
    private int mTabCount = 0;
    private ObSwitchCompatTab mThumbView;
    private static final String TAG             = "ObSwitchCompat";
    private int currentPosition                 = 0;
    private boolean isScrollNormal              = true;
    private boolean checked                     = false;
    private int titleTextSize                   = -1;

    private int trackColor                      = Color.WHITE;
    private int trackStokeColor                 = Color.GRAY;
    private int trackStokeWidth                 = -1;
    private int trackTextColor                  = Color.GRAY;
    private int trackWidth                      = -1;
    private int trackHeight                     = -1;

    private int thumbColor                      = Color.GRAY;
    private int thumbStokeColor                 = Color.WHITE;
    private int thumbStokeWidth                 = -1;
    private int thumbTextColor                  = Color.WHITE;
    private int thumbWidth                      = -1;
    private int thumbHeight                     = -1;
    private int trackPadding                    = -1;
    private int[] thumbPadding;

    private int cornerRadians                   = -1;

    private ViewPager mPager;
    private Drawable trackDrawable;
    private Drawable thumbDrawable;
    private ObSwitchCompatIconAdapter mTabIcons;

    public void setThumbPadding(int[] thumbPadding) {
        if (thumbPadding != null && thumbPadding.length != 4) {
            throw new Error("Thumb padding length must be 4 : [left][top][right][bottom]");
        } else {
            this.thumbPadding = thumbPadding;
        }
    }

    public int[] getThumbPadding() {
        return thumbPadding == null ? thumbPadding = new int[]{parseDP(4), parseDP(2), parseDP(4), parseDP(2)} : thumbPadding; // left top right bottom
    }

    public void setCornerRadians(int cornerRadians) {
        this.cornerRadians = cornerRadians;
        invalidateView();
    }

    public int getCornerRadians() {
        return cornerRadians == -1 ? parseDP(cornerRadians = 50) : cornerRadians;
    }

    public void setTrackColor(int trackColor) {
        this.trackColor = trackColor;
        initTrack();
    }

    public void setTrackPadding(int trackPadding){
        this.trackPadding = trackPadding;
    }

    public int getTrackPadding() {
        return trackPadding == -1 ? parseDP(trackPadding = DEFAULT_PADDING) : trackPadding;
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
        return trackStokeWidth == -1 ? parseDP(trackStokeWidth = DEFAULT_STROKE_WIDTH) : trackStokeWidth;
    }

    public void setTrackWidth(int trackWidth) {
        this.trackWidth = trackWidth;
        initTrack();
    }

    public int getTrackWidth() {
        return trackWidth == -1 ? parseDP(trackWidth = DEFAULT_TRACK_WIDTH) : trackWidth;
    }

    public void setTrackHeight(int trackHeight) {
        this.trackHeight = trackHeight;
        initTrack();
    }

    public int getTrackHeight() {
        return trackHeight == -1 ? parseDP(trackHeight = DEFAULT_TRACK_HEIGHT) : trackHeight;
    }

    public void setThumbWidth(int thumbWidth) {
        this.thumbWidth = thumbWidth;
        initThumbView();
    }

    public int getThumbWidth() {
        return thumbWidth == -1 ? parseDP(thumbWidth = DEFAULT_THUMB_WIDTH) : thumbWidth;
    }

    public void setThumbHeight(int thumbHeight) {
        this.thumbHeight = thumbHeight;
        initThumbView();
    }

    public int getThumbHeight() {
        return thumbHeight == -1 ? parseDP(thumbHeight = DEFAULT_THRUMB_HEIGHT) : thumbHeight;
    }

    public void setTitleTextSize(int val){
        this.titleTextSize = val;
    }

    public int getTitleTextSize() {
        return titleTextSize == -1 ? parseDP(titleTextSize = 14) : titleTextSize;
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
        return thumbStokeWidth == -1 ? parseDP(thumbStokeWidth = DEFAULT_STROKE_WIDTH) : thumbStokeWidth;
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
            setCurrentTab(positionOffset, position);
            if (!checked) {
                mPager.post(new Runnable() {
                    @Override
                    public void run() {
                        checkScrollType();
                    }
                });
            }
        }

        @Override
        public void onPageSelected(final int position) {
            mPager.post(new Runnable() {
                @Override
                public void run() {
                    setThumbText(getPageTitle(position));
                    setThumbIcon();
                    currentPosition = position;
                    setCurrentTab(0, position);
                }
            });
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private String getPageTitle(int position){
        if (isInEditMode()){
            return "TAB" + position;
        } else {
            if (mPager != null) {
                return mPager.getAdapter().getPageTitle(position).toString();
            } else {
                return "";
            }
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

        if (mTabCount > 0) {
            float w = mThumbView.getWidth();
            float newScroll = (int) (w * offset);
            float startFrom = (w * position);
            newScroll += startFrom;

            if (newScroll > ((mainLayout.getWidth() - mThumbView.getWidth()) - mainLayout.getPaddingRight())){
                newScroll = ((mainLayout.getWidth() - mThumbView.getWidth()) - mainLayout.getPaddingRight());
            }

            if (newScroll < mainLayout.getPaddingLeft()) {
                newScroll += mainLayout.getPaddingLeft();
            }

            mThumbView.animate().x(Math.abs(newScroll)).setDuration(0).start();
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
        if (isInEditMode()){
            mTabCount = 2;
        }
        initMainLayout();
        initTrack();
        initTitle();
        initThumbView();
        initCurrentTab();

        if (isInEditMode()){
            setThumbText("TAB"+currentPosition);
        } else {
            setThumbText(getPageTitle(currentPosition));
            setThumbIcon();
        }

        setThumbMoveable();
    }

    private void checkScrollType(){
        if (mPager != null && !checked){
            isScrollNormal = mPager.getScrollX() == currentPosition * mPager.getWidth();
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
        mainLayout.setPadding(getTrackPadding(), getTrackPadding(), getTrackPadding(), getTrackPadding());
        mainLayout.getLayoutParams().width = (getThumbWidth() * mTabCount);
        mainLayout.getLayoutParams().height = getTrackHeight();
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
                    setThumbIcon();
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

    public void setTabIcon(ObSwitchCompatIconAdapter tabIcon){
        this.mTabIcons = tabIcon;
        invalidate();
    }

    public void setThumbMoveable() {
        int[] posXY = new int[2];
        mainLayout.getLocationOnScreen(posXY);
        new ToggleSlide(mThumbView, posXY[0] + mainLayout.getPaddingLeft(), ((getThumbWidth() * mTabCount) - mainLayout.getPaddingRight()) - getThumbWidth(), getThumbWidth(), mTabCount, new ToggleSlide.GesturCallBack() {

            @Override
            public void onSelectedPage(int index) {
                setThumbText(getPageTitle(index));
                setThumbIcon();
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

        LinearLayout.LayoutParams params = new LayoutParams(0, getTrackHeight());
        params.weight = 1;
        params.gravity = Gravity.CENTER;
        int[] padding = getThumbPadding();
        for (int i = 0; i < mTabCount; i++) {
            ObSwitchCompatTab textView = new ObSwitchCompatTab(getContext());
            textView.setPadding(padding[0], padding[1], padding[2], padding[3]);
            textView.setLayoutParams(params);
            textView.setText(getPageTitle(i));
            textView.setImageDrawable(getTabDrawable(i));
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(this);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, getTitleTextSize());
            textView.setTag(i);
            titleViews.add(textView);
        }
        return titleViews;
    }

    private Drawable getTabDrawable(int i) {
        if (mTabIcons != null && mTabIcons.getItemCount() > i){
            return ContextCompat.getDrawable(getContext(), mTabIcons.getTabIcon(i));
        }
        return null;
    }

    private TextView getThumbView(){
        int[] padding = getThumbPadding();
        mThumbView = new ObSwitchCompatTab(getContext());
        mThumbView.setGravity(Gravity.CENTER);
        mThumbView.setTextColor(Color.WHITE);
        mThumbView.setTextSize(TypedValue.COMPLEX_UNIT_SP, getTitleTextSize());
        mThumbView.setPadding(padding[0], padding[1], padding[2], padding[3]);
        mThumbView.setWidth(getThumbWidth());
        mThumbView.setHeight(getThumbHeight());

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

    private void setThumbIcon(){
        mThumbView.post(new Runnable() {
            @Override
            public void run() {
                mThumbView.setImageDrawable(getTabDrawable(getCurrentPosition()));
                mThumbView.invalidate();
            }
        });
    }

    private Drawable getTrack(){
        GradientDrawable trackDrawable = new GradientDrawable();
        trackDrawable.setColor(trackColor);
        trackDrawable.setStroke(getTrackStokeWidth(), trackStokeColor);
        trackDrawable.setCornerRadius(getCornerRadians());
        trackDrawable.setSize(getTrackWidth(), getTrackHeight());
        trackDrawable.setShape(GradientDrawable.RECTANGLE);
        trackDrawable.setBounds(0, 0, getTrackWidth(), getTrackHeight());
        return trackDrawable;
    }

    private Drawable getThumb(){
        GradientDrawable thumbDrawable = new GradientDrawable();
        thumbDrawable.setColor(getThumbColor());
        thumbDrawable.setStroke(getThumbStokeWidth(), getThumbStokeColor());
        thumbDrawable.setCornerRadius(getCornerRadians());
        thumbDrawable.setSize(getThumbWidth(), getThumbHeight());
        thumbDrawable.setBounds(0, 0, getThumbWidth(), getThumbHeight());
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
