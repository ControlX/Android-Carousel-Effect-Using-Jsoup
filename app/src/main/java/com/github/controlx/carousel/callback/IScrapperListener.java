package com.github.controlx.carousel.callback;

import com.github.controlx.carousel.model.ItemData;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Abhishek on 12/4/2016.
 */
public interface IScrapperListener {
        void handleResponse(LinkedHashMap<Integer, ArrayList<ItemData>> responseMap);
        void handleError(String exception);
}
