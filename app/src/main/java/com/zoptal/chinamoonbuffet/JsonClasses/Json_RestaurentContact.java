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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Json_RestaurentContact extends AsyncTask<String, String, String> {

     ProgressDialog loading;
     Context context;

     //ArrayList<RestaurentContact> al_conatct= new ArrayList<RestaurentContact>();
    public  static RestaurentContact f;
    public Json_RestaurentContact(Context context) {
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
                RegisterUrl.contctus);


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

                JSONObject  obj=main_obj.getJSONObject("data");

                String phone =obj.getString("phone");
                String email =obj.getString("email");
                String address =obj.getString("address");
                String latitude =obj.getString("latitude");
                String longitude =obj.getString("longitude");

            f = new RestaurentContact();

               f.setPhone(phone);
                f.setEmail(email);
                f.setAddress(address);
                f.setLatitude(latitude);
                f.setLongitude(longitude);


              //  al_conatct.add(f);


             //   Log.e("result contact data-------",""+result);


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
