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
import android.widget.Toast;

import com.zoptal.chinamoonbuffet.R;
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


public class Json_OrderHistory extends AsyncTask<String, String, String> {

    Context context;
   public String  coderesponse;
    String msg;
   ProgressDialog loading;
   ListView list;
   public  ArrayList<OrderHistory> al_orderhistory= new ArrayList<OrderHistory>();
  public  OrderHistory orderHistorylist;



    public Json_OrderHistory(Context context, ListView list) {
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

                    String order_id= obj.getString("order_id");
                    String txn_id= obj.getString("txn_id");
                    String grand_total= obj.getString("grand_total");
                    String order_type= obj.getString("order_type");
                    String order_status= obj.getString("order_status");
                    String time=obj.getString("time");

                    orderHistorylist = new OrderHistory();

                    orderHistorylist.setOrder_id(order_id);
                    orderHistorylist.setTxn_id(txn_id);
                    orderHistorylist.setOrder_type(order_type);
                    orderHistorylist.setTime(time);
                    orderHistorylist.setGrand_total(grand_total);
                    orderHistorylist.setOrder_status(order_status);



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

            if(al_orderhistory.size()==0){


                final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
                dialog.setContentView(R.layout.dialog_msg);

                Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
                TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);
                tv_msg.setText("You have not placed any Order yet!");
                btn_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();

                    }
                });

                dialog.show();
                return;




            }
else {
                list.setAdapter(new OrderHistoryAdapter(context, al_orderhistory));
            }
        }
        else{

            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }
}