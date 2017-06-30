package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_MyPunchesP;
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


public class Json_MyPunchsP extends AsyncTask<String, String, String> {

    Context context;
   public static String  coderesponse,requiredpunch,needpunch,btnvalue,code_id;
    String msg;
  ProgressDialog loading;
  //  ListView list;
  // public  ArrayList<Model_MyCart> al_mycart= new ArrayList<Model_MyCart>();
 //   public  Model_MyCart mycart;
//public static String tamt;


    public Json_MyPunchsP(Context context) {
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
                RegisterUrl.mypunchs);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("access_token", params[0]));
            nameValuePairs.add(new BasicNameValuePair("id", params[1]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
                Log.e("punch result------", "" + result1.trim());

                JSONObject main_obj = new JSONObject(result1);
               // msg = main_obj.getString("message");
                coderesponse = main_obj.getString("code");
                requiredpunch = main_obj.getString("require_punch");
                needpunch = main_obj.getString("punch");
                btnvalue = main_obj.getString("show_btn");
                code_id = main_obj.getString("code_id");

                if(coderesponse.equals("201")){
                    msg= main_obj.getString("message");
                    Log.e("msg===",""+msg);
                }



            }

        } catch (Exception e) {
            Log.e("==+result punch===", "======" + e);

        }

        return coderesponse;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
         loading.dismiss();

        if (coderesponse.equals("200")) {

                Fragment fragment=new Fragment_MyPunchesP();
				FragmentManager fragmentManager =((Activity)context).getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).addToBackStack(null).commit();

//            Fragment fragment = new Fragment_Checkout();
//            FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
//            fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

        }
        else{

            Toast.makeText(context, "Error: No Punch Found", Toast.LENGTH_LONG).show();
        }
    }
}