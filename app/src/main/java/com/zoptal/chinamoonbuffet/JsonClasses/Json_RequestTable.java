package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_ReserveTable;
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


public class Json_RequestTable extends AsyncTask<String, String, String> {

    Context context;
    String msg,coderesponse,name,email,address,zipcode,phone;
    ProgressDialog loading;

       public Json_RequestTable(Context context) {
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
                RegisterUrl.booktable);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("access_token",params[0]));
            nameValuePairs.add(new BasicNameValuePair("table_id",params[1]));
            nameValuePairs.add(new BasicNameValuePair("booking_date",params[2]));
            nameValuePairs.add(new BasicNameValuePair("booking_time",params[3]));
            nameValuePairs.add(new BasicNameValuePair("name",params[4]));
            nameValuePairs.add(new BasicNameValuePair("email",params[5]));
            nameValuePairs.add(new BasicNameValuePair("phone",params[6]));
            nameValuePairs.add(new BasicNameValuePair("no_of_people",params[7]));
            nameValuePairs.add(new BasicNameValuePair("discription",params[8]));

            Log.e("namevalue pair----",""+nameValuePairs);

             httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);

              JSONObject main_obj = new JSONObject(result1);
                coderesponse=main_obj.getString("code");
                msg=main_obj.getString("message");


             Log.e("request table address....===",""+result1);
            }

        } catch (Exception e) {
            Log.e("==+request table===", "======" + e);

        }

        return coderesponse;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();

     if (coderesponse.equals("200")) {

         final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
         dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
         dialog.setCancelable(true);
         dialog.setContentView(R.layout.dialog_msg);

         Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
         TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);
         tv_msg.setText("Request sent successfully. Our team will contact you shortly.");

         btn_ok.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

           FragmentManager fragmentManager1 = ((Activity) context).getFragmentManager();
                 Fragment_ReserveTable f=new Fragment_ReserveTable();


                 fragmentManager1
                         .beginTransaction()
                         .replace(R.id.activity_main_content_fragment,
                                 f).commit();
                 dialog.dismiss();
             }
         });
         dialog.show();



     }
        else {

         Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
     }

   }


}
