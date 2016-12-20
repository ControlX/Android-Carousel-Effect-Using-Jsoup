/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.controlx.carousel.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.controlx.R;
import com.github.controlx.carousel.helper.DataSet;
import com.github.controlx.carousel.helper.Utils;
import com.github.controlx.carousel.model.ItemData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ScreenSlidePageFragment extends Fragment {
    public static final String ARG_PAGE = "page";
    private int mPageNumber;
    private LinkedHashMap<Integer, ArrayList<ItemData>> mItemMap;

    public static ScreenSlidePageFragment create(int pageNumber) {
        ScreenSlidePageFragment fragment = new ScreenSlidePageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, pageNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public ScreenSlidePageFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageNumber = getArguments().getInt(ARG_PAGE);
        mItemMap = DataSet.getItemDataMap();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        // Inflate the layout containing a title and body text.
        ViewGroup rootView = (ViewGroup) inflater
                .inflate(R.layout.fragment_screen_slide_page, container, false);

        ImageView leftImageView = (ImageView)rootView.findViewById(R.id.item_image_left_iv_holder);
        ImageView rightImageView = (ImageView)rootView.findViewById(R.id.item_image_right_iv_holder);

        Picasso.with(getActivity())
                .load(mItemMap.get(getPageNumber()).get(0).getUrl())
                .placeholder(android.R.color.transparent)
                .error(android.R.drawable.ic_menu_gallery)
                .into(leftImageView);
        ((TextView) rootView.findViewById(R.id.item_price_left_tv)).setText(
                mItemMap.get(getPageNumber()).get(0).getPrice());
        ((TextView) rootView.findViewById(R.id.item_name_left_tv)).setText(
                mItemMap.get(getPageNumber()).get(0).getName());
        ((TextView) rootView.findViewById(R.id.item_name_left_tv)).setTypeface(Utils.getTypeFaceTitillium(getActivity()));

        Picasso.with(getActivity())
                .load(mItemMap.get(getPageNumber()).get(1).getUrl())
                .placeholder(android.R.color.transparent)
                .error(android.R.drawable.ic_menu_gallery)
                .into(rightImageView);

        ((TextView) rootView.findViewById(R.id.item_price_right_tv)).setText(
                mItemMap.get(getPageNumber()).get(1).getPrice());
        ((TextView) rootView.findViewById(R.id.item_name_right_tv)).setText(
                mItemMap.get(getPageNumber()).get(1).getName());
        ((TextView) rootView.findViewById(R.id.item_name_right_tv)).setTypeface(Utils.getTypeFaceTitillium(getActivity()));

        return rootView;
    }

    /**
     * Returns the page number represented by this fragment object.
     */
    public int getPageNumber() {
        return mPageNumber;
    }
}
