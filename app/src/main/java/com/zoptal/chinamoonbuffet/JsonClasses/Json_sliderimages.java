package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.zoptal.chinamoonbuffet.main.MainActivity;
import com.zoptal.chinamoonbuffet.url.RegisterUrl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class Json_sliderimages extends AsyncTask<String, String, String> {

    Context context;
   public String  coderesponse,msg;
   ProgressDialog loading;
MainActivity activity1;
   public  static ArrayList<Sliderimages> al_sliderimages= new ArrayList<Sliderimages>();
  public  Sliderimages sliderimages;



    public Json_sliderimages(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
        activity1 = (MainActivity)context;


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
                RegisterUrl.imgslider);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

          httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
                Log.e("slider result------", "" + result1.trim());

                JSONObject main_obj = new JSONObject(result1);
               // msg = main_obj.getString("message");
                coderesponse = main_obj.getString("code");

//                if(coderesponse.equals("201")){
//                    msg= main_obj.getString("message");
//                }
                JSONArray ary_products =main_obj.getJSONArray("data");
                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);


                    String title = obj.getString("title");
                    String image = obj.getString("image");

                    sliderimages = new Sliderimages();

                    sliderimages.setTitle(title);
                    sliderimages.setImage(image);

                   al_sliderimages.add(sliderimages);

                }

            }

        } catch (Exception e) {
            Log.e("==+result slider===", "======" + e);

        }

        return coderesponse;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
         loading.dismiss();

        if (coderesponse.equals("200")) {
           activity1.mViewPager.setAdapter(activity1.mCustomPagerAdapter);

            //list.setAdapter(new MyPunchsAdapter(context,al_mypunchlist));
        }
        else{

            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        }
    }
}