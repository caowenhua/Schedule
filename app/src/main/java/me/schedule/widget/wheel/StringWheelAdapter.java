package me.schedule.widget.wheel;

import java.util.List;

/**
 * Created by caowenhua on 2015/11/8.
 */
public class StringWheelAdapter implements WheelAdapter {

    private List<String> list;

    public StringWheelAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public int getItemsCount() {
        return list.size();
    }

    @Override
    public String getItem(int index) {
        return list.get(index);
    }

    @Override
    public int getMaximumLength() {
        return 0;
    }
}
