/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.park.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    private static final String TAG = "NetworkUtils";

//    final static String GITHUB_BASE_URL =
//            "https://api.github.com/search/repositories";
//    final static String FB_BASE_URL =
//            "https://graph.facebook.com/search";

    final static String PARK_BASE_URL =
                "http://192.168.43.224:8000/mc";



//
//    final static String PARAM_QUERY = "q";
//    final static String PARAM_SORT = "sort";
//    final static String sortBy = "stars";
//    final static String PARAM_ACCESSTOKEN = "access_token";
//    final static String PARAM_TYPE = "type";
//    final static String type = "event";
//    final static String PARAM_FIELDS = "fields";
//    final static String field1 = "id,name,start_time";
//    final static String field2 = "name";
//    final static String field3 = "start_time";

    public static URL buildUrl(String usrName, String task) {
        Uri builtUri = Uri.parse(PARK_BASE_URL).buildUpon()
                .appendPath(usrName)
                .appendPath(task)
//                .appendQueryParameter(PARAM_QUERY, searchQuery)
//                .appendQueryParameter(PARAM_TYPE, type)
////                .appendQueryParameter(PARAM_FIELDS, field1)
////                .appendQueryParameter(PARAM_FIELDS, field2)
////                .appendQueryParameter(PARAM_FIELDS, field3)
//                .appendQueryParameter(PARAM_ACCESSTOKEN, accessToken)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.e(TAG, "buildUrl:" + e.getMessage() + builtUri.toString());
        }
        Log.d(TAG, "buildUrl: " + url);
        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            Log.d(TAG, "getResponseFromHttpUrl: requesting...");

            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        }catch (Exception e){
            Log.d(TAG, "getResponseFromHttpUrl: unable to setup http connection");
            Log.e(TAG, "getResponseFromHttpUrl: unable to setup http connection" + e.getMessage());
        } finally {
            urlConnection.disconnect();
        }
        return null;
    }
}