package com.arny.myapidemo.utils;

import com.arny.arnylib.utils.MathUtils;
import com.arny.arnylib.utils.generators.Generator;
import com.arny.myapidemo.models.GoodItem;

import java.util.ArrayList;
public class Local {
    public static ArrayList<GoodItem> generateItems(int min, int max1, long id) throws Exception {
        ArrayList<GoodItem> items = new ArrayList<>();
        int max = MathUtils.randInt(min, max1);
        for (int i = 0; i < max; i++) {
            GoodItem e = new GoodItem(MathUtils.randDouble(5, 10000), Generator.getString(MathUtils.randInt(3, 5)));
            e.setDbID(id);
            items.add(e);
        }
        return items;
    }
}
