package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.adapter.CouponsAdapter;
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


public class Json_CouponsList extends AsyncTask<String, String, String> {

    Context context;
    String coderesponse;
    ProgressDialog loading;
    public static Model_CouponsData model_couponsData;
  //  public CouponsAdapter couponsAdapter;
    ListView list;
    public Json_CouponsList(Context context,ListView list) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.list=list;

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
                RegisterUrl.couponslist);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("no_of_post",params[0]));
            nameValuePairs.add(new BasicNameValuePair("page_no",params[1]));
            nameValuePairs.add(new BasicNameValuePair("access_token",params[2]));
            nameValuePairs.add(new BasicNameValuePair("latitude",params[3]));
            nameValuePairs.add(new BasicNameValuePair("longitude",params[4]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
             Log.e("coupon result------",""+result1.trim());


                model_couponsData = new Model_CouponsData();

                ArrayList<CouponData> coupondata = new ArrayList<CouponData>();
                JSONObject main_obj = new JSONObject(result1);

                      coderesponse=main_obj.getString("code");


                JSONArray ary_products =main_obj.getJSONArray("data");

                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);
                    String id = obj.getString("id");
                    String title = obj.getString("title");
                    String image = obj.getString("image");
                    String discount = obj.getString("discount");
                    String code = obj.getString("code");
                    String description = obj.getString("description");
                    String status = obj.getString("status");
                    String posted = obj.getString("posted");

                    CouponData f = new CouponData();
                    f.setId(id);
                    f.setTitle(title);
                    f.setImage(image);
                    f.setDiscount(discount);
                    f.setCode(code);
                    f.setDescription(description);
                    f.setStatus(status);
                    f.setPosted(posted);


                    coupondata.add(f);

                }

                model_couponsData.setAl_coupondata(coupondata);
                Log.e("daily size---",""+model_couponsData.getAl_coupondata().size());

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

        if(model_couponsData.getAl_coupondata().size()==0){


		final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
			dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			dialog.setCancelable(true);
			dialog.setContentView(R.layout.dialog_msg);

			Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
			TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);
			tv_msg.setText("No daily offer at this time");
			btn_ok.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Log.e("ok","clicked");
					dialog.dismiss();

				}
			});

			dialog.show();
            return;
        }
else {

            list.setAdapter(new CouponsAdapter(context,model_couponsData));



//
//
//            ArrayList<CouponData> coupondataArrayList = new ArrayList<>();
//            ArrayList<CouponData> coupondatalist = couponsAdapter.getCurrentList();
//
//            for (int i = 0; i < model_couponsData.getAl_coupondata().size(); i++) {
//                boolean check = true;
//                for (int j = 0; j < coupondatalist.size(); j++) {
//
//                    if (coupondatalist.get(j).getId().equalsIgnoreCase(model_couponsData.getAl_coupondata().get(i).getId())) {
//                        check = false;
//                    }
//                }
//                if (check) {
//                    coupondataArrayList.add(model_couponsData.getAl_coupondata().get(i));
//                }
//            }
//            couponsAdapter.setAddList(coupondataArrayList);
//
    }
    }
}
