package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.zoptal.chinamoonbuffet.adapter.OrderHistoryAdapter;
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


public class Json_OrderHistorytest extends AsyncTask<String, String, String> {

    Context context;
   public String  coderesponse,requiredpunch,needpunch,btnvalue;
    String msg;
   ProgressDialog loading;
   ListView list;
   public  ArrayList<OrderHistory> al_orderhistory= new ArrayList<OrderHistory>();
  public  OrderHistory orderHistorylist;



    public Json_OrderHistorytest(Context context, ListView list) {
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
                RegisterUrl.orderhistory);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("access_token", params[0]));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
                Log.e("order history result------", "" + result1.trim());

                JSONObject main_obj = new JSONObject(result1);
               // msg = main_obj.getString("message");
                coderesponse = main_obj.getString("code");


                if(coderesponse.equals("201")){
                    msg= main_obj.getString("message");

                }

                JSONArray ary_products =main_obj.getJSONArray("data");

                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);

                    String order_type = obj.getString("order_type");
                    String time=obj.getString("time");

                    JSONArray ary_products1 =obj.getJSONArray("products");

                    Log.e("arraylength----",""+ary_products1.length());

                    for (int j= 0; j < ary_products1.length(); j++) {
                        JSONObject obj1 = ary_products1.getJSONObject(i);

//                        String product_id = obj1.getString("product_id");
//                        String product_name = obj1.getString("product_name");
//                        String product_image = obj1.getString("product_image");
//                        String category_id = obj1.getString("category_id");
//                        String category_name = obj1.getString("category_name");
//                        String price = obj1.getString("price");
//                        String qty = obj1.getString("qty");
//                        String total_amt = obj1.getString("total_amt");
//                        String status = obj1.getString("status");
//
//                        orderHistorylist = new OrderHistory();
//
//                         orderHistorylist.setProduct_id(product_id);
//                        orderHistorylist.setProduct_name(product_name);
//                        orderHistorylist.setOrder_type(order_type);
//                        orderHistorylist.setTime(time);
//                        orderHistorylist.setCategory_id(category_id);
//                        orderHistorylist.setCategory_name(category_name);
//                        orderHistorylist.setPrice(price);
//                        orderHistorylist.setQty(qty);
//                        orderHistorylist.setTotal_amt(total_amt);
//                        orderHistorylist.setStatus(status);
//                        orderHistorylist.setProduct_image(product_image);

                    }
                    al_orderhistory.add(orderHistorylist);
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

        if (coderesponse.equals("200")) {

            list.setAdapter(new OrderHistoryAdapter(context,al_orderhistory));
        }
        else{

            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }
}