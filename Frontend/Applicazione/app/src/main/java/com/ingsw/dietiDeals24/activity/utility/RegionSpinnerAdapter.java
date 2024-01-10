package com.ingsw.dietiDeals24.activity.utility;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class RegionSpinnerAdapter extends ArrayAdapter<String> {
    public RegionSpinnerAdapter(Context context, int resource, String[] objects) {
        super(context, resource, objects);
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        return count > 0 ? count - 1 : count;
    }
}

