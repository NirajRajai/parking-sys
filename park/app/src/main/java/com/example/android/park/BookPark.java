package com.example.android.park;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.park.*;
import com.example.android.park.models.ParkInfo;
import com.example.android.park.models.PlotInfo;
import com.example.android.park.utilities.JsonUtils;
import com.example.android.park.utilities.NetworkUtils;

import java.net.URL;

/**
 * Created by dell on 03-Dec-17.
 */

public class BookPark extends AppCompatActivity {

    private static final String TAG = "BookPark";

    private static final String usrName = "rahul";

    private TextView mAddress;
    private TextView mPin;
    private ImageView mTick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_page);

        mAddress = (TextView) findViewById(R.id.tv_park_address);
        mPin = (TextView) findViewById(R.id.tv_pin);

        mTick = (ImageView) findViewById(R.id.iv_tick);
        mTick.setVisibility(View.VISIBLE);
        plot();
    }

    private void plot(){

        URL url = NetworkUtils.buildUrl(usrName, "book");
        new Book().execute(url);
    }

    public class Book extends AsyncTask<URL, Void, PlotInfo>{


        @Override
        protected PlotInfo doInBackground(URL... urls) {
            URL searchUrl = urls[0];
            String plotJson = null;
            try {
                plotJson = NetworkUtils.getResponseFromHttpUrl(searchUrl);

                PlotInfo mPlotInfo = JsonUtils
                        .getPlot(BookPark.this, plotJson);

                return mPlotInfo;

            } catch (Exception e) {
                Log.e(TAG, "doInBackground: error between request n receive" + e.getMessage() );
            }
            return null;
        }

        @Override
        protected void onPostExecute(PlotInfo plotInfo) {
            mAddress.setText("Plot ID:" + plotInfo.getPlotNo());
            Log.d(TAG, "onPostExecute: PIN: " + plotInfo.getPin());
            mPin.setText(plotInfo.getPin());
        }
    }
}
