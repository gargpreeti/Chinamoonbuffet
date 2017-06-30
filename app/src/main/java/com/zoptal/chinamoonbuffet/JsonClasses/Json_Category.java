package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zoptal.chinamoonbuffet.url.RegisterUrl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Json_Category extends AsyncTask<String, String, String> {

     ProgressDialog loading;
     Context context;
     public  static  ArrayList<Model_Categories> al_category= new ArrayList<Model_Categories>();
    public static  List<String> categorylist = new ArrayList<String>();

    public Json_Category(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;

    }

    @Override
    protected void onPreExecute() {


        super.onPreExecute();
        loading = new ProgressDialog(context);
        loading.setMessage("loading");
        loading.show();
    }

    @Override
    protected String doInBackground(String... params) {

        HttpClient httpclient = new DefaultHttpClient();

        HttpPost httppost = new HttpPost(
                RegisterUrl.categories);


        try {
            // Add your data
             List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
             httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

                JSONObject main_obj = new JSONObject(result);
                JSONArray ary_products =main_obj.getJSONArray("data");



                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);
                    String id = obj.getString("id");
                    String title = obj.getString("title");

                    categorylist.add(title);
                    Model_Categories f = new Model_Categories();

                    f.setId(id);
                    f.setTitle(title);


                    al_category.add(f);

                }
Log.e("category list json---",""+categorylist);

                Log.e("result category data-------",""+result);


            }

        } catch (Exception e) {
            Log.e("==+Error===", "Error===" + e);

        }
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();

           }

}
