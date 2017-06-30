package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.zoptal.chinamoonbuffet.main.MainActivity;
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


public class Json_PaymentSuccess extends AsyncTask<String, String, String> {


    Context context;
    String coderesponse,msg;
    ProgressDialog loading;

       public Json_PaymentSuccess(Context context) {
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
                RegisterUrl.paymentsuccess);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("access_token",params[0]));;
            nameValuePairs.add(new BasicNameValuePair("uniq",params[1]));
            nameValuePairs.add(new BasicNameValuePair("create_time",params[2]));
            nameValuePairs.add(new BasicNameValuePair("txn_id",params[3]));
            nameValuePairs.add(new BasicNameValuePair("delivery_charges",params[4]));
            nameValuePairs.add(new BasicNameValuePair("grand_total",params[5]));
            nameValuePairs.add(new BasicNameValuePair("order_type",params[6]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            Log.e("total values-----",""+nameValuePairs);

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
             Log.e("paymnet result------",""+result1.trim());

              JSONObject main_obj = new JSONObject(result1);

                 coderesponse=main_obj.getString("code");
                if(coderesponse.equals("200")){
                    msg= main_obj.getString("message");
                }

            }

        } catch (Exception e) {
            Log.e("==+result addtocart===", "======" + e);

        }
        return coderesponse;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();


//      if (coderesponse.equals("200")){
//          Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//          FragmentManager fragmentManager1 = ((Activity) context).getFragmentManager();
//          Fragment_Menu f=new Fragment_Menu();
//          fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, f).commit();
//
//      }
//        Fragment fragment=new Fragment_Checkout();
//        FragmentManager fragmentManager =getFragmentManager();
//        fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

//      if (coderesponse.equals("200")) {
//          new Json_ProfileData(context).execute(Fragment_Menu.access_tokn);
//      }
////        else if(coderesponse.equals("201")){
////
////           Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
////       }
    }
}
