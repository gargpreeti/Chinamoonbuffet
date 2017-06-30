package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_orderhistoryDetail;
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


public class Json_OrderHistoryDetail extends AsyncTask<String, String, String> {

    Context context;
   public String  coderesponse;
    String msg;
   ProgressDialog loading;

   public static ArrayList<OrderHistorytest> al_orderhistory;
  public  OrderHistorytest orderHistorylisttest;



    public Json_OrderHistoryDetail(Context context) {
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
                RegisterUrl.orderhistorydetail);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("access_token", params[0]));
            nameValuePairs.add(new BasicNameValuePair("order_id", params[1]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
                Log.e("order history detail result------", "" + result1.trim());

                JSONObject main_obj = new JSONObject(result1);

                al_orderhistory= new ArrayList<OrderHistorytest>();
                JSONObject data = main_obj.getJSONObject("data");

               // String id=data.getString("id");
               // msg = main_obj.getString("message");
                coderesponse = main_obj.getString("code");


                if(coderesponse.equals("201")){
                    msg= main_obj.getString("message");

                }

                JSONArray ary_products =data.getJSONArray("products");

                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);

                    String product_id= obj.getString("product_id");
                    String product_name= obj.getString("product_name");
                    String product_image= obj.getString("product_image");
                    String category_id= obj.getString("category_id");
                    String category_name= obj.getString("category_name");
                    String price= obj.getString("price");
                    String qty= obj.getString("qty");
                    String total_amt= obj.getString("total_amt");
                    String time=obj.getString("time");

                    orderHistorylisttest = new OrderHistorytest();

                    orderHistorylisttest.setProduct_id(product_id);
                    orderHistorylisttest.setProduct_name(product_name);
                    orderHistorylisttest.setProduct_image(product_image);
                    orderHistorylisttest.setCategory_id(category_id);
                    orderHistorylisttest.setCategory_name(category_name);
                    orderHistorylisttest.setPrice(price);
                    orderHistorylisttest.setQty(qty);
                    orderHistorylisttest.setTotal_amt(total_amt);
                    orderHistorylisttest.setTime(time);




                    al_orderhistory.add(orderHistorylisttest);
                }

            }

        } catch (Exception e) {
            Log.e("==+result order===", "======" + e);

        }

        return coderesponse;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
         loading.dismiss();

 			FragmentManager fragmentManager1 = ((Activity) context).getFragmentManager();
			Fragment_orderhistoryDetail f=new Fragment_orderhistoryDetail();
					fragmentManager1
					.beginTransaction()
				.replace(R.id.activity_main_content_fragment,
						f).addToBackStack(null).commit();

//        if (coderesponse.equals("200")) {
//
//            list.setAdapter(new OrderHistoryAdapterDetail(context,al_orderhistory));
//        }
//        else{
//
//            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
//        }
    }
}