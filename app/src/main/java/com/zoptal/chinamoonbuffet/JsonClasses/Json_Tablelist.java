package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.zoptal.chinamoonbuffet.adapter.ReserveTableAdapter;
import com.zoptal.chinamoonbuffet.url.RegisterUrl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Json_Tablelist extends AsyncTask<String, String, String> {

    Context context;
    String  coderesponse;
    ProgressDialog loading;
    ListView list;
    public  ArrayList<Model_TableList> al_tablelist= new ArrayList<Model_TableList>();
    public  Model_TableList tablelist;

    public Json_Tablelist(Context context, ListView list) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list=list;

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
                RegisterUrl.tablelist);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

          nameValuePairs.add(new BasicNameValuePair("no_of_post", params[0]));
            nameValuePairs.add(new BasicNameValuePair("page_no", params[1]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
                Log.e("table list result------", "" + result1.trim());

                JSONObject main_obj = new JSONObject(result1);
               // msg = main_obj.getString("message");
                coderesponse = main_obj.getString("code");

                JSONArray ary_products =main_obj.getJSONArray("data");
                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);

                    String id = obj.getString("id");
                    String title = obj.getString("title");
                    String image = obj.getString("image");
                    String description = obj.getString("description");
                    String booked = obj.getString("booked");

                    tablelist = new Model_TableList();

                    tablelist.setId(id);
                    tablelist.setTitle(title);
                    tablelist.setImage(image);
                    tablelist.setDescription(description);
                    tablelist.setBooked(booked);


                    al_tablelist.add(tablelist);

                }


            }

        } catch (Exception e) {
            Log.e("==+result cart===", "======" + e);

        }

        return coderesponse;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();

        if (coderesponse.equals("200")) {

            list.setAdapter(new ReserveTableAdapter(context,al_tablelist));
//            Fragment fragment = new Fragment_Checkout();
//            FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

        }
    }
}