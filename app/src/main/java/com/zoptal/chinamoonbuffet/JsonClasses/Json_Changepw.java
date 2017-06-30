package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.Dialog;
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


public class Json_Changepw extends AsyncTask<String, String, String> {

    Context context;
    String msg,coderesponse;
    ProgressDialog loading;

      public Json_Changepw(Context context) {
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
                RegisterUrl.changepw);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("old_password",params[0]));
            nameValuePairs.add(new BasicNameValuePair("password",params[1]));
            nameValuePairs.add(new BasicNameValuePair("cpassword",params[2]));
            nameValuePairs.add(new BasicNameValuePair("access_token",params[3]));



            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
                Log.e("changepw result------", "" + result1.trim());

                JSONObject main_obj = new JSONObject(result1);
                 msg= main_obj.getString("message");
                coderesponse = main_obj.getString("code");
            }

        } catch (Exception e) {
            Log.e("==+result changepw===", "======" + e);

        }

        return coderesponse;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
       loading.dismiss();

       if (coderesponse.equals("200")) {
           alertmsg();

          // Toast.makeText(context, msg, Toast.LENGTH_LONG).show();

//           Fragment fragment = new Fragment_MyProfile();
//           FragmentManager fragmentManager =((Activity)context).getFragmentManager();
//           fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();
        //  Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
           //Intent intent = new Intent(context,MainActivity.class);
           //context.startActivity(intent);
       }
        else if(coderesponse.equals("201")){

           Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
       }
   }

    public void alertmsg(){


        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_msg);

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        TextView tv_msg=(TextView)dialog.findViewById(R.id.tv_msg);

        tv_msg.setText("Password changed successfully!");

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


        // dialog.dismiss();



        dialog.show();

    }

}
