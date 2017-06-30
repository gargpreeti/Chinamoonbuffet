package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;

import com.zoptal.chinamoonbuffet.adapter.CheckoutAdapter;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Checkout;
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


public class Json_MyCartList extends AsyncTask<String, String, String> {

    Context context;
    String  coderesponse;
    ProgressDialog loading;
    ListView list;
    String msg;
    public ArrayList<Model_MyCart> al_mycart= new ArrayList<Model_MyCart>();
    public  Model_MyCart mycart;
    public static String tamt,uniq,discnt,discntper,d_amt,cupncode;

    public Json_MyCartList(Context context,ListView list) {
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
                RegisterUrl.mycart);

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
                Log.e("cart result------", "" + result1.trim());

                JSONObject main_obj = new JSONObject(result1);
               // msg = main_obj.getString("message");
                coderesponse = main_obj.getString("code");
                if(coderesponse.equals("201")){
                    msg= main_obj.getString("message");

                }

                JSONObject tdataobj = main_obj.getJSONObject("tdata");
                String total_qty = tdataobj.getString("total_qty");
                tamt = tdataobj.getString("t_amt");
                discnt=tdataobj.getString("discount");
                discntper=tdataobj.getString("discount_per");
                d_amt=tdataobj.getString("d_amt");
                cupncode=tdataobj.getString("cuopan_code");

               Log.e("total amount cart----",""+tamt);

                JSONArray ary_products =main_obj.getJSONArray("data");
                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);
                    String id = obj.getString("id");
                    String product_id=obj.getString("product_id");
                    String title = obj.getString("title");
                    String category = obj.getString("category");
                    String category_name = obj.getString("category_name");
                    String price = obj.getString("price");
                    String qty = obj.getString("qty");
                    String total_amt = obj.getString("total_amt");
                    String image = obj.getString("image");
                    String posted = obj.getString("posted");
                      uniq = obj.getString("uniq");

                    Log.e("uniq value===",""+uniq);
                    mycart = new Model_MyCart();

                    mycart.setId(id);
                    mycart.setTitle(title);
                    mycart.setCategory(category);
                    mycart.setCategoryname(category_name);
                    mycart.setPrice(price);
                    mycart.setQty(qty);
                    mycart.setTotal_amt(total_amt);
                    mycart.setImage(image);
                    mycart.setPosted(posted);
                    mycart.setProduct_id(product_id);
                    mycart.setUniq(uniq);


                    al_mycart.add(mycart);

                }


            }

        } catch (Exception e) {
            Log.e("==+result cart===", "======" + e);

        }

        return coderesponse;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
       loading.dismiss();

        if (coderesponse.equals("200")) {

            list.setAdapter(new CheckoutAdapter(context,al_mycart));
            Fragment_Checkout.total_value.setText(tamt);
            Fragment_Checkout.ed_coupon.setText(cupncode);
            Fragment_Checkout.discountvalue.setText(discnt);
            Fragment_Checkout.discountvaluetotal.setText(d_amt);

//            Fragment fragment = new Fragment_Checkout();
//            FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

        }
    }
}