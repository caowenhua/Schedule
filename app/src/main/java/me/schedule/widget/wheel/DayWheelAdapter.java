package me.schedule.widget.wheel;

/**
 * Created by caowenhua on 2015/11/9.
 */
public class DayWheelAdapter implements WheelAdapter{

    private int maxValue;

    public DayWheelAdapter(int year, int mouth) {
        refreshData(year, mouth);
    }

    @Override
    public int getItemsCount() {
        return maxValue;
    }

    @Override
    public String getItem(int index) {
        return (index+1) + "";
    }

    @Override
    public int getMaximumLength() {
        return 2;
    }

    public int getIndex(int index){
        return index+1;
    }

    public void refreshData(int year, int mouth){
        switch (mouth){
            case 1:
                maxValue = 31;
                break;
            case 2:
                if(year % 4 == 0){
                    maxValue = 28;
                }
                else{
                    maxValue = 29;
                }
                break;
            case 3:
                maxValue = 31;
                break;
            case 4:
                maxValue = 30;
                break;
            case 5:
                maxValue = 31;
                break;
            case 6:
                maxValue = 30;
                break;
            case 7:
                maxValue = 31;
                break;
            case 8:
                maxValue = 31;
                break;
            case 9:
                maxValue = 30;
                break;
            case 10:
                maxValue = 31;
                break;
            case 11:
                maxValue = 30;
                break;
            case 12:
                maxValue = 31;
                break;
        }
    }
}
