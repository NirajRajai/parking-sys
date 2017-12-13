package com.example.android.park.utilities;

/**
 * Created by dell on 19-Nov-17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import com.example.android.park.models.ParkInfo;
import com.example.android.park.models.PlotInfo;

public final class JsonUtils{

    private static final String TAG = "JsonUtils";

    public static ParkInfo getInfo(Context context, String parkStr)
        throws JSONException{

        Log.d(TAG, "getInfo: reading json");

        JSONObject parkJson = new JSONObject(parkStr);
//        JSONArray parkArray = parkJson.getJSONArray("");
//        ParkInfo[] parsedPark = new ParkInfo[parkArray.length()];

        ParkInfo parsedPark = new ParkInfo();
//        for(int i=0; i<parkArray.length(); i++){

            JSONObject park = //parkArray.getJSONObject(i);
                    new JSONObject(parkStr);
//
        parsedPark.setParkId(park.getString("parkid"));
        parsedPark.setLat(park.getString("let"));
        parsedPark.setLon(park.getString("lon"));
        parsedPark.setTotal(park.getString("tot"));
        parsedPark.setAvail(park.getString("avail"));
        parsedPark.setParkCity(park.getString("city"));
        parsedPark.setParkAddress(park.getString("address"));


        Log.d(TAG, "getInfo: value of parsedPark" + parsedPark.toString());
//        }


        return parsedPark;
    }

    public static PlotInfo getPlot(Context context, String plotStr)
            throws JSONException{

        Log.d(TAG, "getInfo: reading json");

        JSONObject parkJson = new JSONObject(plotStr);
//        JSONArray parkArray = parkJson.getJSONArray("");
//        ParkInfo[] parsedPark = new ParkInfo[parkArray.length()];

        PlotInfo parsedPlot = new PlotInfo();
//        for(int i=0; i<parkArray.length(); i++){

        JSONObject plot = //parkArray.getJSONObject(i);
                new JSONObject(plotStr);

        parsedPlot.setPin(plot.getString("userpin"));
        parsedPlot.setPlotNo(plot.getString("plotid"));



        Log.d(TAG, "getInfo: value of parsedPark" + parsedPlot.toString());
//        }


        return parsedPlot;
    }
}

