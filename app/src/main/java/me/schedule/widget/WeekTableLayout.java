package me.schedule.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.schedule.R;

/**
 * Created by caowenhua on 2015/12/5.
 */
public class WeekTableLayout extends ViewGroup {

    private int columnCount;
    private List<ViewModel> viewList;
    private int[] columnWidth;
    private int[] columnHeight;
    private int[] computeColumnHeight;
    private ColumnGravity gravity;


    public WeekTableLayout(Context context) {
        this(context, null);
    }

    public WeekTableLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public WeekTableLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

        viewList = new ArrayList<>();
        if(attrs != null){
            TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.WeekTableLayout);
            columnCount = ta.getInt(R.attr.columnCount, 1);
            gravity = getColumnGravity(ta.getInt(R.attr.columnGravity, 0));
            ta.recycle();
        }
        else{
            columnCount = 1;
        }
        columnHeight = new int[columnCount];
        columnWidth = new int[columnCount];
        for (int i = 0; i < columnHeight.length; i++) {
            columnHeight[i] = 0;
            columnWidth[i] = 0;
        }
    }

    private void init(){
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(sizeWidth/columnCount, modeWidth);

        int width = 0;
        int height = 0;

        for (int i = 0; i < viewList.size(); i++) {
//            if(viewList.get(i).view.getVisibility() == VISIBLE){
//            }

            //TODO 减少width
            measureChild(viewList.get(i).view, childWidthMeasureSpec, heightMeasureSpec);

            int childWidth, childHeight;
            try {
                MarginLayoutParams lp = (MarginLayoutParams) viewList.get(i).view
                        .getLayoutParams();
                childWidth = viewList.get(i).view.getMeasuredWidth() + lp.leftMargin
                        + lp.rightMargin;
                childHeight = viewList.get(i).view.getMeasuredHeight() + lp.topMargin
                        + lp.bottomMargin;
            }
            catch (Exception e){
                childWidth = viewList.get(i).view.getMeasuredWidth();
                childHeight = viewList.get(i).view.getMeasuredHeight();
            }

            if(viewList.get(i).spanColumn == 1){
                columnHeight[viewList.get(i).column] += childHeight;
                if(columnWidth[viewList.get(i).column] < childWidth){
                    columnWidth[viewList.get(i).column] = childWidth;
                }
            }
            else{
                int index = max(viewList.get(i).column, viewList.get(i).spanColumn);
                columnHeight[index] += childHeight;
                for (int j = viewList.get(i).column; j < viewList.get(i).column + viewList.get(j).spanColumn; j++) {
                    columnHeight[j] = columnHeight[index];
                    if(columnWidth[j] < childWidth / viewList.get(j).spanColumn){
                        columnWidth[j] = childWidth / viewList.get(j).spanColumn;
                    }
                }
            }

        }

        for (int i = 0; i < columnWidth.length; i++) {
            width += columnWidth[i];
        }

        height = maxHeight();

        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width + getPaddingLeft() + getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height + getPaddingTop() + getPaddingBottom()
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        computeColumnHeight = new int[columnCount];
        for (int i = 0; i < computeColumnHeight.length; i++) {
            computeColumnHeight[i] = getPaddingTop();
        }

        for (int i = 0; i < viewList.size(); i++) {
            if(viewList.get(i).view.getVisibility() == GONE){
                continue;
            }

            int childWidth = viewList.get(i).view.getMeasuredWidth();
            int childHeight = viewList.get(i).view.getMeasuredHeight();

            try{
                MarginLayoutParams lp = (MarginLayoutParams) viewList.get(i).view
                        .getLayoutParams();
                childWidth += lp.leftMargin + lp.rightMargin;
                childHeight += lp.topMargin + lp.bottomMargin;

            }catch (Exception e){
            }

            if(viewList.get(i).spanColumn == 1){
                switch (gravity){
                    case left:
                        viewList.get(i).view.layout(getColumnX(viewList.get(i).column),
                                computeColumnHeight[viewList.get(i).column],
                                childWidth, childHeight);
                        break;
                    case center:
                        break;
                    case right:
                        break;
                }
                computeColumnHeight[viewList.get(i).column] += childHeight;
            }
            else{

            }

        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if(w > 0){
            int totalWidth = 0;
            for (int i = 0; i < columnWidth.length; i++) {
                totalWidth += columnWidth[i];
            }

            totalWidth = totalWidth - getPaddingLeft() - getPaddingRight();

            for (int i = 0; i < columnWidth.length; i++) {
                columnWidth[i] = columnWidth[i]/totalWidth * w;
            }
        }

    }

    @Override
    public void addView(View child) {
        addView(child, 1);
    }

    @Override
    public void addView(View child, int columnCount) {
        addView(child, columnCount, 1);
    }

    public void addView(View child, int columnCount, int spanColumn){
        if (child == null) {
            throw new IllegalArgumentException("Cannot add a null child view to a ViewGroup");
        }
        LayoutParams params = child.getLayoutParams();
        if (params == null) {
            params = generateDefaultLayoutParams();
            if (params == null) {
                throw new IllegalArgumentException("generateDefaultLayoutParams() cannot return null");
            }
        }
        addView(child, columnCount, spanColumn, params);
    }

    @Override
    public void addView(View child, int columnCount, LayoutParams params) {
        addView(child, 1, 1, params);
    }

    public void addView(View child, int columnCount, int spanColumn, LayoutParams params) {
        if (child == null) {
            throw new IllegalArgumentException("Cannot add a null child view to a ViewGroup");
        }

        // addViewInner() will call child.requestLayout() when setting the new LayoutParams
        // therefore, we call requestLayout() on ourselves before, so that the child's request
        // will be blocked at our level
        ViewModel viewmodel = new ViewModel();
        viewmodel.column = columnCount;
        viewmodel.spanColumn = spanColumn;
        viewmodel.view = child;
        viewList.add(viewmodel);

        requestLayout();
        invalidate();
    }

    @Override
    public void addView(View child, LayoutParams params) {
        addView(child, 1, params);
    }

    @Override
    public int getChildCount() {
        return viewList.size();
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams()
    {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p)
    {
        return new MarginLayoutParams(p);
    }

    private int getColumnX(int column){
        int tmp = getPaddingLeft();
        for (int i = 0; i < column; i++) {
            tmp += columnWidth[i];
        }
        return tmp;
    }

    private int max(int column, int span){
        int tmp = column;
        for (int i = column; i < column + span - 1; i++) {
            if(i < columnHeight.length){
                if(columnHeight[i] > columnHeight[tmp]){
                    tmp = i;
                }
            }
        }
        return tmp;
    }

    private int maxHeight(){
        int tmp = columnHeight[0];
        for (int i = 1; i < columnHeight.length; i++) {
            if(columnHeight[i] > tmp){
                tmp = columnHeight[i];
            }
        }
        return tmp;
    }

    protected ColumnGravity getColumnGravity(int value){
        switch (value){
            case 0:
                return ColumnGravity.left;
            case 1:
                return ColumnGravity.center;
            case 2:
                return ColumnGravity.right;
        }
        return ColumnGravity.left;
    }

    protected enum ColumnGravity{
        left, center, right
    }

    private class ViewModel{
        View view;
        int column;
        int spanColumn;
    }
}
