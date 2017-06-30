package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.zoptal.chinamoonbuffet.main.SigninActivity;
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


public class Json_forgotpw extends AsyncTask<String, String, String> {


    Context context;
    String msg,coderesponse;
    ProgressDialog loading;


       public Json_forgotpw(Context context) {
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
                RegisterUrl.forgotpw);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("email",params[0]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
             Log.e("forgot result------",""+result1.trim());

               JSONObject main_obj = new JSONObject(result1);
                msg= main_obj.getString("message");
                coderesponse=main_obj.getString("code");


            }

        } catch (Exception e) {
            Log.e("==+result forgot===", "======" + e);

        }

        return null;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();

     if (coderesponse.equals("200")) {
         Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
         Intent intent = new Intent(context,SigninActivity.class);
         context.startActivity(intent);
     }
        else if(coderesponse.equals("201")){

           Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
       }
   }
}
