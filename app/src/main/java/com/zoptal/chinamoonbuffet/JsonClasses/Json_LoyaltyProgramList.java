package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zoptal.chinamoonbuffet.adapter.LoyalityProgramAdapter;
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


public class Json_LoyaltyProgramList extends AsyncTask<String, String, String> {


    Context context;
    String coderesponse;
    ProgressDialog loading;
    public static Model_LoyaltyProgram model_loyaltyProgram=new Model_LoyaltyProgram();

    public LoyalityProgramAdapter loyalityProgramAdapter;

    public Json_LoyaltyProgramList(Context context, LoyalityProgramAdapter loyalityProgramAdapter) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.loyalityProgramAdapter=loyalityProgramAdapter;

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
                RegisterUrl.loyaltyprogram);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("no_of_post",params[0]));
            nameValuePairs.add(new BasicNameValuePair("page_no",params[1]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
             Log.e("loyaltyprogram result------",""+result1.trim());


                model_loyaltyProgram = new Model_LoyaltyProgram();

                ArrayList<LoyaltyProgramData> loyaltyprogrm = new ArrayList<LoyaltyProgramData>();
                JSONObject main_obj = new JSONObject(result1);

                      coderesponse=main_obj.getString("code");


                JSONArray ary_products =main_obj.getJSONArray("data");

                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);
                    String id = obj.getString("id");
                    String title = obj.getString("title");
                 //   String image = obj.getString("image");
                    String discount = obj.getString("discount");
                    String punches = obj.getString("punches");
                    String description = obj.getString("description");
                    String status = obj.getString("status");
                    String posted = obj.getString("posted");

                    LoyaltyProgramData f = new LoyaltyProgramData();

                    f.setId(id);
                    f.setTitle(title);
                   // f.setImage(image);
                    f.setDiscount(discount);
                    f.setPunches(punches);
                    f.setDescription(description);
                    f.setStatus(status);
                    f.setPosted(posted);


                    loyaltyprogrm.add(f);

                }

                model_loyaltyProgram.setAl_loyaltyprogram(loyaltyprogrm);

            }

        } catch (Exception e) {
            Log.e("==+result loyaltyprogram===", "======" + e);

        }

        return coderesponse;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();


        ArrayList<LoyaltyProgramData> loyaltyProgramDataArrayList=new ArrayList<>();
        ArrayList<LoyaltyProgramData> loyaltyProgramlist= loyalityProgramAdapter.getCurrentList();
//karna open list
        for (int i = 0; i <model_loyaltyProgram.getAl_loyaltyprogram().size(); i++) {
            boolean check=true;
            for (int j = 0; j< loyaltyProgramlist.size() ; j++) {

                if (loyaltyProgramlist.get(j).getId().equalsIgnoreCase(model_loyaltyProgram.getAl_loyaltyprogram().get(i).getId()) )
                {
                    check=false;
                }
            }
            if(check)
            {
                loyaltyProgramDataArrayList.add(model_loyaltyProgram.getAl_loyaltyprogram().get(i));
            }
        }
        loyalityProgramAdapter.setAddList(loyaltyProgramDataArrayList);

    }
}
