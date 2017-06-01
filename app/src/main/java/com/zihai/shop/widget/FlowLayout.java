package com.zihai.shop.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.zihai.shop.R;

/**
 * Created by Administrator on 2016/7/22.
 */
public class FlowLayout extends ViewGroup{
    private static final int DEFAULT_HORIZONTAL_SPACE = 5;
    private static final int DEFAULT_VERTICAL_SPACE = 5;
    private int mHorizontalSpace;
    private int mVerticalSpace;
    public FlowLayout(Context context) {
        this(context,null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);

    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        try {
              mHorizontalSpace = typedArray.getDimensionPixelSize(R.styleable.FlowLayout_horizontal_space,DEFAULT_HORIZONTAL_SPACE);
             mVerticalSpace = typedArray.getDimensionPixelSize(R.styleable.FlowLayout_vertical_space,DEFAULT_VERTICAL_SPACE);
        } finally {
             typedArray.recycle();
        }
    }

    public void setVerticalSpace(int verticalSpace) {
        mVerticalSpace = verticalSpace;
    }

    public void setHorizontalSpace(int horizontalSpace) {
        mHorizontalSpace = horizontalSpace;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mPaddingLeft = getPaddingLeft();
        int mPaddingRight = getPaddingRight();
        int mPaddingTop = getPaddingTop();
        int mPaddingBottom = getPaddingBottom();

        int mWidth = MeasureSpec.getSize(widthMeasureSpec);

     //   int mHeight = MeasureSpec.getSize(heightMeasureSpec);
    //    int mHeightModel = MeasureSpec.getMode(heightMeasureSpec);
        int childLeft = mPaddingLeft;
        int childTop = mPaddingTop;
        int lineHeight = 0;
        int childCount = getChildCount();

        for (int i=0;i<childCount;i++){

            View child = getChildAt(i);
            if(child.getVisibility() != View.GONE){

                measureChild(child, widthMeasureSpec, heightMeasureSpec);

                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();

                lineHeight = Math.max(childHeight, lineHeight);

                if(childWidth+childLeft+mPaddingRight > mWidth){
                    childLeft = mPaddingLeft;
                    childTop += mVerticalSpace + lineHeight;
                    lineHeight = childHeight;
                }else{
                    childLeft += childWidth + mHorizontalSpace;
                }
            }
        }
        int realHeight = childTop + lineHeight + mPaddingBottom;
        setMeasuredDimension(mWidth,resolveSize(realHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        int realWidth = right-left;

        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
     //   int paddingBottom = getPaddingBottom();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;
        int childCount = getChildCount();

        for (int i=0;i<childCount;i++) {

            View child = getChildAt(i);

            if (child.getVisibility() != View.GONE) {

                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();

                lineHeight = Math.max(childHeight, lineHeight);

                if(childWidth + childLeft + paddingRight > realWidth){
                    childLeft = paddingLeft;
                    childTop +=mVerticalSpace + lineHeight;
                    lineHeight = childHeight;
                }
                child.layout(childLeft, childTop, childLeft + childWidth, childTop + childHeight);
                childLeft += childWidth + mHorizontalSpace;
            }

        }

    }

}
