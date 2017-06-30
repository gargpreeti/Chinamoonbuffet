package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.zoptal.chinamoonbuffet.adapter.MyPunchsAdapter;
import com.zoptal.chinamoonbuffet.fragment.Fragment_MyPunches;
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


public class Json_MyPunchsList extends AsyncTask<String, String, String> {

    Context context;
   public String  coderesponse,requiredpunch,needpunch,btnvalue;
    String msg;
   ProgressDialog loading;
   ListView list;
   public  ArrayList<MyPunchsList> al_mypunchlist= new ArrayList<MyPunchsList>();
  public  MyPunchsList mypunchlist;



    public Json_MyPunchsList(Context context,ListView list) {
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
                RegisterUrl.mypunchs);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("access_token", params[0]));
            nameValuePairs.add(new BasicNameValuePair("id", params[1]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
                Log.e("punch result------", "" + result1.trim());

                JSONObject main_obj = new JSONObject(result1);
               // msg = main_obj.getString("message");
                coderesponse = main_obj.getString("code");
                requiredpunch = main_obj.getString("require_punch");
                needpunch = main_obj.getString("punch");
                btnvalue = main_obj.getString("show_btn");


                if(coderesponse.equals("201")){
                    msg= main_obj.getString("message");
                }
                JSONArray ary_products =main_obj.getJSONArray("data");
                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);

                    String id = obj.getString("id");
                  //  String relation=obj.getString("relation");
                    String title = obj.getString("title");
                  //  String discount = obj.getString("discount");
                    String description = obj.getString("description");
                    String status = obj.getString("status");
                    String punch_date = obj.getString("punch_date");

                    mypunchlist = new MyPunchsList();

                    mypunchlist.setId(id);
                    mypunchlist.setTitle(title);
                 //   mypunchlist.setRelation(relation);
                 //   mypunchlist.setDiscount(discount);
                    mypunchlist.setDescription(description);
                    mypunchlist.setStatus(status);
                    mypunchlist.setPunchdate(punch_date);


                  al_mypunchlist.add(mypunchlist);

                }




            }

        } catch (Exception e) {
            Log.e("==+result punch===", "======" + e);

        }

        return coderesponse;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
         loading.dismiss();

        if (coderesponse.equals("200")) {
            if(requiredpunch.isEmpty()){

                Fragment_MyPunches.tv_need.setVisibility(View.GONE);
                Fragment_MyPunches.tv_total.setVisibility(View.GONE);
               Fragment_MyPunches.tv_msg.setVisibility(View.VISIBLE);
            }
     else{
                Fragment_MyPunches.tv_need.setVisibility(View.VISIBLE);
                Fragment_MyPunches.tv_total.setVisibility(View.VISIBLE);
                Fragment_MyPunches.tv_msg.setVisibility(View.GONE);

            }

            list.setAdapter(new MyPunchsAdapter(context,al_mypunchlist));
        }
        else{

            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }
}