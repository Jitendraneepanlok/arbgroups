package com.arv.groups;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class FoxFun {

    private static final String TAG = "";
    private static String OPERATION_NAME = "";
    private static String ServerExtras="API/PDAPI/";
    private static String Serverip="http://api.arbgroups.in/";
    private static String api_key="api_key";
    private static String api_secret="api_secret";
    private static String appname="appname";
    private static String query="query";

    public static void getdatamod(Context context, String psquery, String srchtext,String orderby,String startrow,
                                  String nextrows,final Callback callback) {

        class GetDataNow extends AsyncTask<String, Void, JSONObject> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(JSONObject s) {
                super.onPostExecute(s);
                if (s.length() > 0) {
                    callback.onSuccess(s);
                } else {
                    callback.onError("Error");
                }
            }

            @Override
            protected JSONObject doInBackground(String... params) {
                OPERATION_NAME = FoxFun.Serverip + FoxFun.ServerExtras + "multigetresult";
                JSONObject jsonObject = new JSONObject();
                try {

                    String OffsetVal="";
                    if(params[2].trim().length()>1){
                        OffsetVal+=" order by "+params[2]+" ";
                    }
                    if(!params[3].trim().equals("")&& !params[4].trim().equals("")) {
                        OffsetVal += " OFFSET " + params[3] + " ROWS FETCH NEXT " + params[4] + " ROWS ONLY";
                    }
                    jsonObject.accumulate(api_key, params[1]);
                    jsonObject.accumulate(appname, "");
                    jsonObject.accumulate(api_secret, OffsetVal);
                    jsonObject.accumulate(query, params[0]);
                    jsonObject.accumulate("cocd","ram");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String json = jsonObject.toString();
                String result = Get_Base(OPERATION_NAME, json);
//do {
                result = result.replaceAll("[\\\\]{1}[\"]{1}", "\"");
                result = result.replaceAll("(\\\\r\\\\n|\\\\n|\\\\r)", "");
                result = result.replaceAll("(\\\"/)", "#");
                result = result.replaceAll("(\\\\\")", "#");
//}
//while (result.contains("\\"));
//                result = result.substring(result.indexOf("["), result.lastIndexOf("]") + 1);

                ArrayList<HashMap<String, String>> fed = new ArrayList<>();
                JSONObject jobj=new JSONObject();
                try {
                    jobj = new JSONObject(result.substring(result.indexOf("{"), result.lastIndexOf("}")+1));

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                return jobj;
            }
        }
        //creating asynctask object and executing it

        GetDataNow GetData = new GetDataNow();
        GetData.execute(psquery, srchtext, orderby, startrow, nextrows);
    }
    public static JSONObject Savedatamod(Context context, String psquery) {

        OPERATION_NAME = Serverip + FoxFun.ServerExtras + "saveresult";
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject.accumulate(api_key, "");
            jsonObject.accumulate(appname, "");
            jsonObject.accumulate(api_secret, "");
            jsonObject.accumulate(query, psquery);
            jsonObject.accumulate("cocd","ram");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String json = jsonObject.toString();
        Log.d(TAG, "Message JSON1: " + json);

        String result = Get_Base(OPERATION_NAME, json);
        Log.d(TAG, "Message JSON2: " + json);
        if(result==null) {
            Log.d("TTTT", "Null result");
        }
        else     Log.d("TTTT", result);
        //do {
        result = result.replaceAll("[\\\\]{1}[\"]{1}", "\"");
        result = result.replaceAll("(\\\\r\\\\n|\\\\n|\\\\r)", "");
        result = result.replaceAll("(\\\"/)", "#");
        result = result.replaceAll("(\\\\\")", "#");
//}
//while (result.contains("\\"));
//                result = result.substring(result.indexOf("["), result.lastIndexOf("]") + 1);

        ArrayList<HashMap<String, String>> fed = new ArrayList<>();
        JSONObject jobj=new JSONObject();
        try {
            Log.d("TTTT", result);
            jobj = new JSONObject(result.substring(result.indexOf("{"), result.lastIndexOf("}")+1));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jobj;
    }





    public  static String Get_Base(String Operation_Name, String json) {
        InputStream inputStream = null;

        String result = "";
        boolean myval = false;
        OPERATION_NAME = Operation_Name;

        try {
            URL url = new URL(OPERATION_NAME);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
            httpURLConnection.setRequestProperty("Accept", "application/json");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
            bufferedWriter.write(json);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }

        } catch (Exception err) {
            Log.d(TAG, "Message error: " + err);
            String mmm = err.getMessage();
        }
        return result;
    }



    public  static  String getColVal(JSONArray jsonArray,int index,String ColName) {

        String Result = "";
        try {
            JSONObject explrObject = jsonArray.getJSONObject(index);
            Result = explrObject.getString(ColName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return Result;

    }

    public interface Callback {
        void onSuccess(JSONObject Result1);

        void onError(String Error);
    }

}
