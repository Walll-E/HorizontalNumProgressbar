package com.walle.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;


/**
 * 水平的数字进度条，可以显示当前进度，也可以不显示当前进度
 *
 * Created by Wall-E on 2016-10-16.
 */
public class NumberProgressBar extends View {

    private int mMaxProgress = 100;

    /**
     * 当前进度
     */
    private int mCurrentProgress = 0;

    /**
     * 当前进度所覆盖的颜色
     */
    private int mReachedBarColor;

    /**
     * 未覆盖的颜色
     */
    private int mUnreachedBarColor;

    /**
     * 文本颜色
     */
    private int mTextColor;


    /**
     * 围绕文本矩形框的颜色
     */
    private int mRectColor;

    /**
     * 文本字体大小
     */
    private float mTextSize;


    /**
     * 围绕文本矩形框的高度
     */
    private float mRectfHeight;



    /**
     * 当前进度的高度
     */
    private float mReachedBarHeight;

    /**
     * 未覆盖区域的高度
     */
    private float mUnreachedBarHeight;

    /**
     * 文本的后缀
     */
    private String mSuffix = " ";

    /**
     * 文本的前缀
     */
    private String mPrefix = " ";


    /**
     * 默认文本的颜色
     */
    private final int default_text_color = Color.rgb(66, 145, 241);
    /**
     * 默认覆盖区域的颜色
     */
    private final int default_reached_color = Color.rgb(66, 145, 241);
    /**
     * 默认未覆盖区域的颜色
     */
    private final int default_unreached_color = Color.rgb(204, 204, 204);
    /**
     * 默认包裹文本框的颜色
     */
    private final int default_out_rect_color = Color.rgb(204, 204, 204);
    /**
     * 默认当前进度的默认文本偏移
     */
    private final float default_progress_text_offset;
    /**
     * 默认文本的大小
     */
    private final float default_text_size;
    /**
     * 默认覆盖区域的高度
     */
    private final float default_reached_bar_height;
    /**
     * 默认未覆盖区域的高度
     */
    private final float default_unreached_bar_height;
    /**
     * 默认文本包裹边框的高度
     */
    private final float default_out_rectF_bar_height;

    /**
     * 进度条属性的存储
     */
    private static final String INSTANCE_STATE = "saved_instance";
    private static final String INSTANCE_TEXT_COLOR = "text_color";
    private static final String INSTANCE_TEXT_SIZE = "text_size";
    private static final String INSTANCE_REACHED_BAR_HEIGHT = "reached_bar_height";
    private static final String INSTANCE_REACHED_BAR_COLOR = "reached_bar_color";
    private static final String INSTANCE_UNREACHED_BAR_HEIGHT = "unreached_bar_height";
    private static final String INSTANCE_UNREACHED_BAR_COLOR = "unreached_bar_color";
    private static final String INSTANCE_RECTF_HEIGHT = "rectf_height";
    private static final String INSTANCE_RECTF_COLOR = "rectf_color";
    private static final String INSTANCE_MAX = "max";
    private static final String INSTANCE_PROGRESS = "progress";
    private static final String INSTANCE_SUFFIX = "suffix";
    private static final String INSTANCE_PREFIX = "prefix";
    private static final String INSTANCE_TEXT_VISIBILITY = "text_visibility";

    private static final int PROGRESS_TEXT_VISIBLE = 0;



    private int progress;


    /**
     * 绘制文本的宽度
     */
    private float mDrawTextWidth;

    /**
     * 绘制文本的起始位置
     */
    private float mDrawTextStart;

    /**
     * 绘制文本的结束位置
     */
    private float mDrawTextEnd;

    /**
     * 当前绘制的文本
     */
    private String mCurrentDrawText;

    /**
     * 覆盖区域的Paint
     */
    private Paint mReachedBarPaint;
    /**
     * 未覆盖区域的Paint.
     */
    private Paint mUnreachedBarPaint;
    /**
     * 文本Paint
     */
    private Paint mTextPaint;

    /**
     * 外边框Paint
     */
    private Paint mOutPaint;

    /**
     * 未覆盖区域 RectF
     */
    private RectF mUnreachedRectF = new RectF(0, 0, 0, 0);
    /**
     * 覆盖区域 RectF
     */
    private RectF mReachedRectF = new RectF(0, 0, 0, 0);

    /**
     * 外边框的 RectF
     */
    private RectF mOutRectF = new RectF(0,0,0,0);

    /**
     * 进度条文本的偏移量
     */
    private float mOffset;


    private boolean mDrawUnreachedBar = true;

    private boolean mDrawReachedBar = true;

    private boolean mIfDrawText = true;

    private boolean mIfDrawOutRectf = true;

    /**
     * 进度条变化的监听
     */
    private OnProgressBarListener mListener;

    /**
     * 进度条文本和文本外边框是否显示的枚举
     */
    public enum ProgressTextVisibility {
        Visible, Invisible
    }

    public NumberProgressBar(Context context) {
        this(context, null);
    }

    public NumberProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.numberProgressBarStyle);
    }

    public NumberProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        default_out_rectF_bar_height = dp2px(20.0f);
        default_reached_bar_height = dp2px(20.0f);
        default_unreached_bar_height = dp2px(20.0f);
        default_text_size = sp2px(16);
        default_progress_text_offset = dp2px(3.0f);

        //load styled attributes.
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.NumberProgressBar,
                defStyleAttr, 0);

        mReachedBarColor = attributes.getColor(R.styleable.NumberProgressBar_progress_reached_color, default_reached_color);
        mUnreachedBarColor = attributes.getColor(R.styleable.NumberProgressBar_progress_unreached_color, default_unreached_color);
        mTextColor = attributes.getColor(R.styleable.NumberProgressBar_progress_text_color, default_text_color);
        mTextSize = attributes.getDimension(R.styleable.NumberProgressBar_progress_text_size, default_text_size);
        mRectColor = attributes.getColor(R.styleable.NumberProgressBar_progress_out_bar_color,default_out_rect_color);


        mReachedBarHeight = attributes.getDimension(R.styleable.NumberProgressBar_progress_reached_bar_height, default_reached_bar_height);
        mUnreachedBarHeight = attributes.getDimension(R.styleable.NumberProgressBar_progress_unreached_bar_height, default_unreached_bar_height);
        mRectfHeight = attributes.getDimension(R.styleable.NumberProgressBar_progress_out_bar_height,default_out_rectF_bar_height);
        mOffset = attributes.getDimension(R.styleable.NumberProgressBar_progress_text_offset, default_progress_text_offset);

        int textVisible = attributes.getInt(R.styleable.NumberProgressBar_progress_text_visibility, PROGRESS_TEXT_VISIBLE);
        if (textVisible != PROGRESS_TEXT_VISIBLE) {
            mIfDrawText = false;
            mIfDrawOutRectf = false;
        }

        setProgress(attributes.getInt(R.styleable.NumberProgressBar_progress_current, 0));
        setMax(attributes.getInt(R.styleable.NumberProgressBar_progress_max, 100));

        attributes.recycle();
        initializePainters();
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) mTextSize;
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return Math.max((int)mRectfHeight, Math.max((int) mTextSize, Math.max( Math.max((int) mReachedBarHeight, (int) mUnreachedBarHeight),(int)mRectfHeight)))+(int) dp2px(2);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
    }

    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop()+(int) dp2px(5) + (int) dp2px(5)+getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mIfDrawText) {
            calculateDrawRectF();
        } else {
            calculateDrawRectFWithoutProgressText();
        }

        if (mDrawReachedBar) {
            canvas.drawRect(mReachedRectF, mReachedBarPaint);
        }

        if (mDrawUnreachedBar) {
            canvas.drawRect(mUnreachedRectF, mUnreachedBarPaint);
        }

        if (mIfDrawText)
            canvas.drawText(mCurrentDrawText, mDrawTextStart, mDrawTextEnd, mTextPaint);


        if (mIfDrawOutRectf){
            canvas.drawRect(mOutRectF, mOutPaint);
        }
    }

    private void initializePainters() {
        mReachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mReachedBarPaint.setColor(mReachedBarColor);

        mUnreachedBarPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mUnreachedBarPaint.setColor(mUnreachedBarColor);


        mOutPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mOutPaint.setStyle(Paint.Style.STROKE);
        mOutPaint.setStrokeWidth(dp2px(1));
        mOutPaint.setColor(mRectColor);


        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);
    }


    private void calculateDrawRectFWithoutProgressText() {
        mReachedRectF.left = getPaddingLeft();
        mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight / 2.0f;
        mReachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight()) / (getMax() * 1.0f) * getProgress() + getPaddingLeft();
        mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight / 2.0f;

        mUnreachedRectF.left = mReachedRectF.right;
        mUnreachedRectF.right = getWidth() - getPaddingRight();
        mUnreachedRectF.top = getHeight() / 2.0f + -mUnreachedBarHeight / 2.0f;
        mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight / 2.0f;

        mOutRectF.left = mUnreachedRectF.left-mReachedRectF.right;
        mOutRectF.right = mUnreachedRectF.right;
        mOutRectF.top = getHeight()/2.0f-mRectfHeight/1.5f;
        mOutRectF.bottom = getHeight()/2.0f+mRectfHeight/1.5f;
    }

    private void calculateDrawRectF() {

        mCurrentDrawText = String.format("%d", getProgress() * 100 / getMax());
        mCurrentDrawText = mPrefix + mCurrentDrawText + mSuffix;
        mDrawTextWidth = mTextPaint.measureText(mCurrentDrawText);

        if (getProgress() == 0) {
            mDrawReachedBar = false;
            mDrawTextStart = getPaddingLeft();
        } else {
            mDrawReachedBar = true;
            mReachedRectF.left = getPaddingLeft();
            mReachedRectF.top = getHeight() / 2.0f - mReachedBarHeight / 2.0f;
            mReachedRectF.right = (getWidth() - getPaddingLeft() - getPaddingRight()) / (getMax() * 1.0f) * getProgress() - mOffset + getPaddingLeft();
            mReachedRectF.bottom = getHeight() / 2.0f + mReachedBarHeight / 2.0f;

            mDrawTextStart = (mReachedRectF.right + mOffset);
        }

        mDrawTextEnd = (int) ((getHeight() / 2.0f) - ((mTextPaint.descent() + mTextPaint.ascent()) / 2.0f));

        if ((mDrawTextStart + mDrawTextWidth) >= getWidth() - getPaddingRight()) {
            mDrawTextStart = getWidth() - getPaddingRight() - mDrawTextWidth;
            mReachedRectF.right = mDrawTextStart - mOffset;
            mOutRectF.left = mReachedRectF.right;
        }

        float unreachedBarStart = mDrawTextStart + mDrawTextWidth + mOffset;
        if (unreachedBarStart >= getWidth() - getPaddingRight()) {
            mDrawUnreachedBar = false;
        } else {
            mDrawUnreachedBar = true;
            mUnreachedRectF.left = unreachedBarStart;
            mUnreachedRectF.right = getWidth() - getPaddingRight();
            mUnreachedRectF.top = getHeight() / 2.0f + -mUnreachedBarHeight / 2.0f;
            mUnreachedRectF.bottom = getHeight() / 2.0f + mUnreachedBarHeight / 2.0f;
        }
        mOutRectF.left = mReachedRectF.right;
        mOutRectF.top = getHeight()/2.0f + -mRectfHeight/1.5f;
        mOutRectF.bottom = getHeight()/2.0f + mRectfHeight/1.5f;
        mOutRectF.right = unreachedBarStart;
    }

    /**
     * 获取进度条颜色
     *
     * @return
     */
    public int getTextColor() {
        return mTextColor;
    }


    /**
     * 获取进度条显示文本大小
     *
     * @return
     */
    public float getProgressTextSize() {
        return mTextSize;
    }

    public int getUnreachedBarColor() {
        return mUnreachedBarColor;
    }

    public int getReachedBarColor() {
        return mReachedBarColor;
    }

    public int getProgress() {
        return mCurrentProgress;
    }

    public int getMax() {
        return mMaxProgress;
    }

    public float getReachedBarHeight() {
        return mReachedBarHeight;
    }

    public float getUnreachedBarHeight() {
        return mUnreachedBarHeight;
    }

    public float getmRectfHeight(){
        return  mRectfHeight;
    }

    public int getmRectColor(){
        return mRectColor;
    }

    public void setProgressTextSize(float textSize) {
        this.mTextSize = textSize;
        mTextPaint.setTextSize(mTextSize);
        invalidate();
    }

    public void setProgressTextColor(int textColor) {
        this.mTextColor = textColor;
        mTextPaint.setColor(mTextColor);
        invalidate();
    }

    public void setUnreachedBarColor(int barColor) {
        this.mUnreachedBarColor = barColor;
        mUnreachedBarPaint.setColor(mUnreachedBarColor);
        invalidate();
    }

    public void setReachedBarColor(int progressColor) {
        this.mReachedBarColor = progressColor;
        mReachedBarPaint.setColor(mReachedBarColor);
        invalidate();
    }

    public void setmRectColor(int rectColor){
        this.mRectColor = rectColor;
        mOutPaint.setColor(mRectColor);
        invalidate();
    }



    public void setmRectfHeight(float height){
        mRectfHeight = height;
    }

    public void setReachedBarHeight(float height) {
        mReachedBarHeight = height;
    }

    public void setUnreachedBarHeight(float height) {
        mUnreachedBarHeight = height;
    }

    public void setMax(int maxProgress) {
        if (maxProgress > 0) {
            this.mMaxProgress = maxProgress;
            invalidate();
        }
    }

    public void setSuffix(String suffix) {
        if (suffix == null) {
            mSuffix = "";
        } else {
            mSuffix = suffix;
        }
    }

    public String getSuffix() {
        return mSuffix;
    }

    public void setPrefix(String prefix) {
        if (prefix == null)
            mPrefix = "";
        else {
            mPrefix = prefix;
        }
    }

    public String getPrefix() {
        return mPrefix;
    }

    public void incrementProgressBy(int by) {
        if (by > 0) {
            setProgress(getProgress() + by);
        }

        if(mListener != null){
            mListener.onProgressChange(getProgress(), getMax());
        }
    }

    public void setProgress(int progress) {
        if (progress <= getMax() && progress >= 0) {
            this.mCurrentProgress = progress;
            invalidate();
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        final Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState());
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor());
        bundle.putFloat(INSTANCE_TEXT_SIZE, getProgressTextSize());
        bundle.putFloat(INSTANCE_REACHED_BAR_HEIGHT, getReachedBarHeight());
        bundle.putFloat(INSTANCE_UNREACHED_BAR_HEIGHT, getUnreachedBarHeight());
        bundle.putInt(INSTANCE_REACHED_BAR_COLOR, getReachedBarColor());
        bundle.putInt(INSTANCE_UNREACHED_BAR_COLOR, getUnreachedBarColor());
        bundle.putInt(INSTANCE_MAX, getMax());
        bundle.putInt(INSTANCE_PROGRESS, getProgress());
        bundle.putString(INSTANCE_SUFFIX, getSuffix());
        bundle.putString(INSTANCE_PREFIX, getPrefix());
        bundle.putBoolean(INSTANCE_TEXT_VISIBILITY, getProgressTextVisibility());
        bundle.putFloat(INSTANCE_RECTF_HEIGHT, getmRectfHeight());
        bundle.putInt(INSTANCE_RECTF_COLOR,getmRectColor());
        return bundle;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            final Bundle bundle = (Bundle) state;
            mTextColor = bundle.getInt(INSTANCE_TEXT_COLOR);
            mTextSize = bundle.getFloat(INSTANCE_TEXT_SIZE);
            mReachedBarHeight = bundle.getFloat(INSTANCE_REACHED_BAR_HEIGHT);
            mUnreachedBarHeight = bundle.getFloat(INSTANCE_UNREACHED_BAR_HEIGHT);
            mReachedBarColor = bundle.getInt(INSTANCE_REACHED_BAR_COLOR);
            mUnreachedBarColor = bundle.getInt(INSTANCE_UNREACHED_BAR_COLOR);
            mRectfHeight = bundle.getFloat(INSTANCE_RECTF_HEIGHT);
            mRectColor = bundle.getInt(INSTANCE_RECTF_COLOR);
            initializePainters();
            setMax(bundle.getInt(INSTANCE_MAX));
            setProgress(bundle.getInt(INSTANCE_PROGRESS));
            setPrefix(bundle.getString(INSTANCE_PREFIX));
            setSuffix(bundle.getString(INSTANCE_SUFFIX));
            setProgressTextVisibility((bundle.getBoolean(INSTANCE_TEXT_VISIBILITY) ? 0 : 1));
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATE));
            return;
        }
        super.onRestoreInstanceState(state);
    }

    public float dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return dp * scale + 0.5f;
    }

    public float sp2px(float sp) {
        final float scale = getResources().getDisplayMetrics().scaledDensity;
        return sp * scale;
    }

    public void setProgressTextVisibility(int visibility) {
        mIfDrawText = visibility == 0;
        mIfDrawOutRectf = visibility==0;
        invalidate();
    }

    public boolean getProgressTextVisibility() {
        return mIfDrawText;
    }

    public void setOnProgressBarListener(OnProgressBarListener listener){
        mListener = listener;
    }
}

