package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Menu;
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


public class Json_GoogleLogin extends AsyncTask<String, String, String> {

    public static final String MyPREFERENCES = "MyPrefs1" ;
    SharedPreferences sharedpreferences1;
    Context context;
    String msg,coderesponse;
    ProgressDialog loading;
    String access_token;
  public Json_GoogleLogin(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;

    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        loading = new ProgressDialog(context);
        loading.setMessage("loading");
        loading.show();
        sharedpreferences1 = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }
    @Override
    protected String doInBackground(String... params) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                RegisterUrl.googlelogin);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("g_id",params[0]));
            nameValuePairs.add(new BasicNameValuePair("name",params[1]));
            nameValuePairs.add(new BasicNameValuePair("email",params[2]));
            nameValuePairs.add(new BasicNameValuePair("address",params[3]));
            nameValuePairs.add(new BasicNameValuePair("latitude",params[4]));
            nameValuePairs.add(new BasicNameValuePair("longitude",params[5]));
            nameValuePairs.add(new BasicNameValuePair("device_token",params[6]));
            nameValuePairs.add(new BasicNameValuePair("device_type",params[7]));
            nameValuePairs.add(new BasicNameValuePair("firebase_registration_id",params[8]));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
             Log.e("google login result------",""+result1.trim());

              JSONObject main_obj = new JSONObject(result1);
                msg= main_obj.getString("message");
                coderesponse=main_obj.getString("code");

                JSONObject  obj=main_obj.getJSONObject("data");

                    String id =obj.getString("id");
                    String name=obj.getString("name");
                  access_token=obj.getString("access_token");


                SharedPreferences.Editor editor1 = sharedpreferences1.edit();
                editor1.putString("id",id);
                editor1.putString("name",name);
                editor1.putString("access_token",access_token);
                editor1.commit();
            }

        } catch (Exception e) {
            Log.e("==+result google login===", "======" + e);

        }

        return coderesponse;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();

       if (coderesponse.equals("200")) {

           if(Fragment_Menu.access_tokn.isEmpty()) {
               final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
               dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
               dialog.setCancelable(true);
               dialog.setContentView(R.layout.dialog_msg);

               Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
               TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);
               tv_msg.setText("To pay for order at pick-up, order must be placed over the phone. Orders placed in-app, need to be paid for in-app.");
               btn_ok.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
                       Intent intent = new Intent(context, MainActivity.class);
                       context.startActivity(intent);
                       dialog.dismiss();
                   }
               });
               dialog.show();
           }
           else {
//       //   Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(context,MainActivity.class);
         context.startActivity(intent);
           }
       }
        else if(coderesponse.equals("201")){

           Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
       }
    }
}
