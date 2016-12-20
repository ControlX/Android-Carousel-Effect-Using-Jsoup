package com.github.controlx.carousel.helper;

import com.github.controlx.carousel.model.ItemData;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Abhishek on 12/5/2016.
 */
public class DataSet {
    private static LinkedHashMap<Integer, ArrayList<ItemData>> itemDataHashMap;

    public static void setItemDataMap(LinkedHashMap<Integer, ArrayList<ItemData>> itemDataHashMap){
        DataSet.itemDataHashMap = itemDataHashMap;
    }

    public static LinkedHashMap<Integer, ArrayList<ItemData>> getItemDataMap(){
        return DataSet.itemDataHashMap;
    }
}
