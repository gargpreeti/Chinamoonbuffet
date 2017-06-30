package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Checkout;
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


public class Json_Removecoupon extends AsyncTask<String, String, String> {


    Context context;
    String coderesponse,msg;
    ProgressDialog loading;
    String d_amt;
       public Json_Removecoupon(Context context) {
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
                RegisterUrl.removecoupon);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("access_token",params[0]));;
            nameValuePairs.add(new BasicNameValuePair("uniq",params[1]));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
             Log.e("apply coupon  result------",""+result1.trim());

              JSONObject main_obj = new JSONObject(result1);

                 coderesponse=main_obj.getString("code");
             //   if(coderesponse.equals("201")){
                    msg= main_obj.getString("message");
             //   }

//                JSONObject  obj=main_obj.getJSONObject("data");
//
//                String discount =obj.getString("discount");
//                d_amt =obj.getString("d_amt");
//                String total_amt=obj.getString("total_amt");
//                String uniq=obj.getString("uniq");
//
//



            }

        } catch (Exception e) {
            Log.e("==+rapply coupon==", "======" + e);

        }
        return coderesponse;
    }

    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();

     if (coderesponse.equals("200")){

         Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
         new Json_MyCartList(context, Fragment_Checkout.listView).execute(Fragment_Menu.access_tokn);

      Fragment_Checkout.img_chk.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.coupon_code_disable));
//          Fragment_Checkout.discountvalue.setText("$"+d_amt);
         Fragment_Checkout.img_crs.setVisibility(View.GONE);

   }
//        else{
//          Fragment_Checkout.img_chk.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.coupon_code_disable));
//
//          Fragment_Checkout.img_crs.setVisibility(View.VISIBLE);
//          Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
//
//      }
    }
}
