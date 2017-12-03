package com.example.android.park;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.park.*;
import com.example.android.park.models.ParkInfo;

/**
 * Created by dell on 03-Dec-17.
 */

public class BookPark extends AppCompatActivity {

    private static final String TAG = "BookPark";

    private TextView mAddress;
    private TextView mPin;
    private ImageView mTick;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booking_page);

        mAddress = (TextView) findViewById(R.id.tv_park_address);
        mPin = (TextView) findViewById(R.id.tv_pin);

        MapActivity temp = new MapActivity();

        ParkInfo parkTemp = temp.getParkInfo();

        mTick = (ImageView) findViewById(R.id.iv_tick);

        mTick.setVisibility(View.VISIBLE);
        mAddress.setText(parkTemp.getParkAddress());
        mPin.setText("2135");
    }
}
