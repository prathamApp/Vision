package com.pratham.pravin.vision.google;

import android.content.Context;
import android.os.AsyncTask;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;


public class TranslatorBackGroundTask extends AsyncTask<String, Void, String> {

    //Declare Context
    public interface MyAsyncTaskListener{
        void onPostExecute(String s);
    }
    public static String resultString;
    Context ctx;
    //Set Context
    TranslatorBackGroundTask(MyAsyncTaskListener listener){
        this.listener=listener;
    }
    private MyAsyncTaskListener listener;
    @Override
    protected String doInBackground(String... params) {
        //String variables
        String textToBeTranslated = params[0];
        String languagePair = params[1];

        try {
            //Set up the translation call URL
            String yandexKey = "trnsl.1.1.20180113T102250Z.941f2a23f7d88084.0e9f4357f3500975fac5d2cbb11c2a946f72faa1";
            String yandexUrl = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=" + yandexKey
                    + "&text=" + textToBeTranslated + "&lang=" + languagePair;

            return yandexUrl;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        AndroidNetworking.get(result)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        String string = null;
                        try {
                            string = response.getString("text");
                            resultString=string.substring(string.indexOf('[')+2,string.indexOf(']')-1);
                            if(listener!=null){
                                listener.onPostExecute(resultString);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

