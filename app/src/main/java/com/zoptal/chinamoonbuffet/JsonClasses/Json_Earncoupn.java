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
import com.zoptal.chinamoonbuffet.adapter.EarnCouponAdapter;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Json_Earncoupn extends AsyncTask<String, String, String> {


    Context context;
    String coderesponse;
    ProgressDialog loading;
       public static Model_EarnCoupon model_earnCoupon=new Model_EarnCoupon();

    public EarnCouponAdapter earnCouponAdapter;

    public Json_Earncoupn(Context context,EarnCouponAdapter earnCouponAdapter) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.earnCouponAdapter=earnCouponAdapter;


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
                RegisterUrl.earnedcoupon);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("access_token",params[0]));
            nameValuePairs.add(new BasicNameValuePair("page_no",params[1]));
            nameValuePairs.add(new BasicNameValuePair("no_of_post",params[2]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
                Log.e("earn coupon result------", "" + result1.trim());


                model_earnCoupon = new Model_EarnCoupon();

                ArrayList<Earncouponcode> earncoupon = new ArrayList<Earncouponcode>();
                JSONObject main_obj = new JSONObject(result1);

                coderesponse = main_obj.getString("code");



                JSONArray ary_products =main_obj.getJSONArray("data");

                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);
                    String id = obj.getString("id");
                    String user_id = obj.getString("user_id");
                    String title = obj.getString("title");
                    String discount = obj.getString("discount");
                    String code = obj.getString("code");
                    String description = obj.getString("description");
                    String status = obj.getString("status");
                    String earn_on = obj.getString("earn_on");

                    Earncouponcode f = new Earncouponcode();

                    f.setId(id);
                    f.setUser_id(user_id);
                    f.setTitle(title);
                    f.setDiscount(discount);
                    f.setCode(code);
                    f.setDescription(description);
                    f.setStatus(status);
                    f.setEarn_on(earn_on);

                    earncoupon.add(f);

                }

                model_earnCoupon.setAl_coupondata(earncoupon);

            }

        } catch (Exception e) {
            Log.e("==+result coupon===", "======" + e);

        }

        return coderesponse;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();

        Log.e("code repsonse---", "" + coderesponse);
        if (coderesponse.equals("201")) {

            final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.dialog_msg);

            Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
            TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);
            tv_msg.setText("No Earned Coupons");
            btn_ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            // tv_msg.setText(" Please sign in to see My Punches!");

//                    btn_ok.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//
//                            dialog.dismiss();
//
//                        }
//                    });
            // dialog.dismiss();

            dialog.show();
        }
        else {

            ArrayList<Earncouponcode> earncouponcodesArrayList = new ArrayList<>();
            ArrayList<Earncouponcode> earncouponcodeslist = earnCouponAdapter.getCurrentList();

            for (int i = 0; i < model_earnCoupon.getAl_earncoupon().size(); i++) {
                boolean check = true;
                for (int j = 0; j < earncouponcodeslist.size(); j++) {

                    if (earncouponcodeslist.get(j).getId().equalsIgnoreCase(model_earnCoupon.getAl_earncoupon().get(i).getId())) {
                        check = false;
                    }
                }
                if (check) {
                    earncouponcodesArrayList.add(model_earnCoupon.getAl_earncoupon().get(i));
                }
            }
            earnCouponAdapter.setAddList(earncouponcodesArrayList);

        }
    }
}
