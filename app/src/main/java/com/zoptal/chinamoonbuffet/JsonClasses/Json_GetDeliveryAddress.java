package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_DeliveryAddress;
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
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Json_GetDeliveryAddress extends AsyncTask<String, String, String> {

    Context context;
    String msg,coderesponse,name,email,address,zipcode,phone;
    ProgressDialog loading;
    Model_DeliveryAddress deliveryAddress;
       public Json_GetDeliveryAddress(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
          // activity1 = (MainActivity) context;
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
                RegisterUrl.deliveryaddress);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("access_token",params[0]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);

              JSONObject main_obj = new JSONObject(result1);
                coderesponse=main_obj.getString("code");

                JSONObject  obj=main_obj.getJSONObject("data");

                name=obj.getString("name");
                email=obj.getString("email");
                address=obj.getString("address");
                zipcode=obj.getString("zip_code");
                phone=obj.getString("phone");

                deliveryAddress=new Model_DeliveryAddress();
                deliveryAddress.setName(name);
                deliveryAddress.setEmail(email);
                deliveryAddress.setAddress(address);
                deliveryAddress.setZipcode(zipcode);
                deliveryAddress.setPhone(phone);

            }

        } catch (Exception e) {
            Log.e("==+result logout===", "======" + e);

        }

        return coderesponse;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();

     if (coderesponse.equals("200")) {

           Bundle bundle = new Bundle();
           bundle.putSerializable("deliveryaddress", deliveryAddress);

           FragmentManager fragmentManager1 = ((Activity)context).getFragmentManager();
           Fragment_DeliveryAddress myaddress=new Fragment_DeliveryAddress();
           myaddress.setArguments(bundle);

           fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, myaddress).commit();
       }

   }
}
