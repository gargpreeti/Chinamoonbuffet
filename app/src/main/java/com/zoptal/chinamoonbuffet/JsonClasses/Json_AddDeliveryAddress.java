package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Json_AddDeliveryAddress extends AsyncTask<String, String, String> {

    Context context;
    String msg,coderesponse,name,email,address,zipcode,phone;
    ProgressDialog loading;

   public Json_AddDeliveryAddress(Context context) {
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
                RegisterUrl.adddeliveryaddress);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("access_token",params[0]));
            nameValuePairs.add(new BasicNameValuePair("name",params[1]));
            nameValuePairs.add(new BasicNameValuePair("email",params[2]));
            nameValuePairs.add(new BasicNameValuePair("address",params[3]));
            nameValuePairs.add(new BasicNameValuePair("zip_code",params[4]));
            nameValuePairs.add(new BasicNameValuePair("phone",params[5]));

           httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);

              JSONObject main_obj = new JSONObject(result1);
                coderesponse=main_obj.getString("code");
                msg=main_obj.getString("message");

             Log.e("delivery address===",""+result1);
            }

        } catch (Exception e) {
            Log.e("==+result address===", "======" + e);

        }

        return coderesponse;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();

      if (coderesponse.equals("200")) {
          new Json_DeliveryDistance(context).execute(Fragment_Menu.access_tokn);

          //  dialogdeliverymap();
       }
        else{

          Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
      }

   }

    public void dialogdeliverymap() {

//        dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent) {
//            @Override
//            public boolean onTouchEvent(MotionEvent event) {
//                // Tap anywhere to close dialog.
//                dialog.dismiss();
//                return true;
//            }
//        };
//        √

     final Dialog   dialog = new Dialog(context, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_deliverymap);


        TextView tv_cncl = (TextView) dialog.findViewById(R.id.tv_cncl);

        tv_cncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        // dialog.dismiss();

        dialog.show();

    }
}
