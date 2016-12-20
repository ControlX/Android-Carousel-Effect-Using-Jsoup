package com.github.controlx.carousel.scrapper;

import android.os.AsyncTask;

import com.github.controlx.carousel.callback.IScrapperListener;
import com.github.controlx.carousel.helper.Constants;
import com.github.controlx.carousel.model.ItemData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Abhishek on 12/4/2016.
 */
public class LinkScrapper extends AsyncTask<Void, Void, LinkedHashMap<Integer, ArrayList<ItemData>>>{

    private String mUrlLink;
    private LinkedHashMap<Integer, ArrayList<ItemData>> mItemMap;
    private IScrapperListener mIScrapperListener;
    private String mExceptionMessage;
    private ArrayList<ItemData> mArrayListData;

    public LinkScrapper(String urlLink, IScrapperListener iScrapperListener){
        this.mUrlLink = urlLink;
        this.mIScrapperListener = iScrapperListener;

        mItemMap = new LinkedHashMap<>();
    }

    @Override
    protected LinkedHashMap<Integer, ArrayList<ItemData>> doInBackground(Void... params) {
        int iMainLoop = 0;
        int jInnerLoop = 0;
        try {
            Document document  = Jsoup.connect(mUrlLink).get();
            Elements divElements = document.select(Constants.ELEMENT_DIV_PARENT);
            Element divElem = divElements.get(0);
            Elements liElements = divElem.select(Constants.ELEMENT_LI_PARENT);

            for(Element liElement : liElements){
                Elements itemImgInfoElements = liElement.select(Constants.ELEMENT_IMG_PARENT);
                Elements itemPriceInfoElements = liElement.select(Constants.ELEMENT_SPAN);
                Element imgTagInfoElement = itemImgInfoElements.get(0);

                String url = imgTagInfoElement.attr(Constants.ATTRIBUTE_DATA_SRC);
                String name = imgTagInfoElement.attr(Constants.ATTRIBUTE_TITLE);
                String price;
                try {
                    price = itemPriceInfoElements.get(2).select(Constants.ELEMENT_SPAN).attr(Constants.ATTRIBUTE_CONTENT);
                }
                catch (IndexOutOfBoundsException e){
                    price = itemPriceInfoElements.get(1).select(Constants.ELEMENT_SPAN).attr(Constants.ATTRIBUTE_CONTENT);
                }
                price = price.replace("AED","AED "); price = price.replace(".0","");

                if(iMainLoop % 2 == 0){
                    mArrayListData = new ArrayList<>();
                    mArrayListData.add(new ItemData(url, name, price));
                }
                else{
                    mArrayListData.add(new ItemData(url, name, price));
                    mItemMap.put(jInnerLoop, mArrayListData);
                    jInnerLoop++;
                }
                iMainLoop++;
            }
            return mItemMap;
        }
        catch(Exception e) {
            mExceptionMessage = e.getMessage();
            return null;
        }
    }

    @Override
    protected void onPostExecute(LinkedHashMap<Integer, ArrayList<ItemData>> mapData) {
        super.onPostExecute(mapData);

        if(mIScrapperListener != null) {
            if(mapData != null)
                mIScrapperListener.handleResponse(mItemMap);
            else
                mIScrapperListener.handleError(mExceptionMessage);
        }
    }
}
