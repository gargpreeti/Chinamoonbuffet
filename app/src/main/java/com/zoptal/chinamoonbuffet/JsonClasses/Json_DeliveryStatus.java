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

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Menu;
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


public class Json_DeliveryStatus extends AsyncTask<String, String, String> {

     ProgressDialog loading;
     Context context;
    String del_status,coderesponse;

    public Json_DeliveryStatus(Context context) {
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
                RegisterUrl.getdeliverystatus);


        try {
            // Add your data
             List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
             httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);

                Log.e("delivery status----",""+result);
                JSONObject main_obj = new JSONObject(result);
                coderesponse=main_obj.getString("code");

                JSONObject  obj=main_obj.getJSONObject("data");

                del_status =obj.getString("delivery_status");



            }

        } catch (Exception e) {
            Log.e("==+Error===", "Error===" + e);

        }
        return coderesponse;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();

        if(coderesponse.equals("200")){

            if(del_status.equals("0")){

                final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_msg);

                Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
                TextView tv_msg=(TextView)dialog.findViewById(R.id.tv_msg);
                tv_msg.setText("Sorry, we do not offer delivery.");
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
            else{

                new Json_GetDeliveryAddress(context).execute(Fragment_Menu.access_tokn);

            }


        }

           }
}
