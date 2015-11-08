package me.schedule.widget.wheel;

/**
 * Created by caowenhua on 2015/11/8.
 */
public class IntegerWheelAdapter implements WheelAdapter {

    /** The default min value */
    public static final int DEFAULT_MAX_VALUE = 60;

    /** The default max value */
    private static final int DEFAULT_MIN_VALUE = 0;

    // Values
    private int minValue;
    private int maxValue;

    /**
     * Default constructor
     */
    public IntegerWheelAdapter() {
        this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
    }

    /**
     * Constructor
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     */
    public IntegerWheelAdapter(int minValue, int maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
    }

    @Override
    public int getItemsCount() {
        return maxValue - minValue + 1;
    }

    @Override
    public String getItem(int index) {
        if (index >= 0 && index < getItemsCount()) {
            int value = minValue + index;
            return value + "";
        }
        return null;
    }

    @Override
    public int getMaximumLength() {
        int max = Math.max(Math.abs(maxValue), Math.abs(minValue));
        int maxLen = Integer.toString(max).length();
        if (minValue < 0) {
            maxLen++;
        }
        return maxLen;
    }

    public int getIndex(int index){
        if (index >= 0 && index < getItemsCount()) {
            int value = minValue + index;
            return value;
        }
        return -1;
    }
}
