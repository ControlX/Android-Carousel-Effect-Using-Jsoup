package com.github.controlx.carousel.activity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.controlx.R;
import com.github.controlx.carousel.adapter.ScreenSlidePagerAdapter;
import com.github.controlx.carousel.callback.IScrapperListener;
import com.github.controlx.carousel.helper.Constants;
import com.github.controlx.carousel.helper.DataSet;
import com.github.controlx.carousel.helper.Utils;
import com.github.controlx.carousel.model.ItemData;
import com.github.controlx.carousel.scrapper.LinkScrapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class DashboardActivity extends AppCompatActivity {

    private RelativeLayout mParentLayout;
    private ProgressBar mProgressBar;
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        initToolBar();
        initViews();

        if(Utils.isNetworkAvailable(this)) {
            new LinkScrapper(Constants.URL_TO_PARSE, iScrapperListener).execute();
        }
        else {
            mProgressBar.setVisibility(View.GONE);

            Snackbar snackbar = Snackbar
                    .make(mParentLayout, R.string.no_network, Snackbar.LENGTH_LONG);

            View view = snackbar.getView();
            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.YELLOW);
            snackbar.show();
        }

    }

    private void initToolBar(){
        SpannableString spannableString = new SpannableString(getString(R.string.app_name));
        spannableString.setSpan(new Utils.TypefaceSpan(this, Constants.FONT_NAME), 0, spannableString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(spannableString);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.BLACK));
    }

    private void initViews(){
        mParentLayout = (RelativeLayout)findViewById(R.id.parent_layout);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBarIndeterminate);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void initPagerAdapter(int count){
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(), count);
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {

            }
        });

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabDots);
        tabLayout.setupWithViewPager(mPager, true);
    }

    IScrapperListener iScrapperListener = new IScrapperListener() {
        @Override
        public void handleResponse(LinkedHashMap<Integer, ArrayList<ItemData>> responseMap) {

            mProgressBar.setVisibility(View.GONE);
            DataSet.setItemDataMap(responseMap);
            initPagerAdapter(responseMap.size());
        }

        @Override
        public void handleError(String exception) {

            mProgressBar.setVisibility(View.GONE);
            Snackbar snackbar = Snackbar
                    .make(mParentLayout, "Exception: "+exception, Snackbar.LENGTH_LONG);

            View view = snackbar.getView();
            TextView tv = (TextView) view.findViewById(android.support.design.R.id.snackbar_text);
            tv.setTextColor(Color.YELLOW);
            snackbar.show();
        }
    };
}
