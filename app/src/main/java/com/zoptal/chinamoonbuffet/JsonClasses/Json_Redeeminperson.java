package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Coupons;
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


public class Json_Redeeminperson extends AsyncTask<String, String, String> {

    Context context;
    String msg,coderesponse;
    ProgressDialog loading;

   public Json_Redeeminperson(Context context) {
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
                RegisterUrl.personinredeem);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("access_token",params[0]));
            nameValuePairs.add(new BasicNameValuePair("code_id",params[1]));
            nameValuePairs.add(new BasicNameValuePair("type",params[2]));


           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);

              JSONObject main_obj = new JSONObject(result1);
                coderesponse=main_obj.getString("code");
                msg=main_obj.getString("message");

             Log.e("redeem in person===",""+result1);
            }

        } catch (Exception e) {
            Log.e("==+result redeem===", "======" + e);

        }

        return coderesponse;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub

        super.onPostExecute(result);
        loading.dismiss();

        Toast.makeText(context,"Coupon Successfully Redeemed", Toast.LENGTH_SHORT).show();
      //  Coupon Successfully Redeemed

        Fragment fragment = new Fragment_Coupons();
        FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();


   }

  }
