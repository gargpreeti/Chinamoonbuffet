package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_MyProfile;
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


public class Json_ProfileData extends AsyncTask<String, String, String> {

  public static final String MyPREFERENCES = "MyPrefs1" ;
  SharedPreferences sharedpreferences1;
    Context context;
    String msg,coderesponse;
    ProgressDialog loading;
    Model_ProfileData profiledata;
        public static Fragment_MyProfile myProfile;
  // public
  public Json_ProfileData(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;

    }
    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        loading = new ProgressDialog(context);
        loading.setMessage("loading");
        loading.show();

     sharedpreferences1 = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

    }
    @Override
    protected String doInBackground(String... params) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                RegisterUrl.profile);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("access_token",params[0]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
             Log.e("profile result------",""+result1.trim());

           JSONObject main_obj = new JSONObject(result1);

             coderesponse=main_obj.getString("code");

                if(coderesponse.equals("201")){
                    msg= main_obj.getString("message");
                }

               JSONObject  obj=main_obj.getJSONObject("data");

                    String id =obj.getString("id");
                    String name=obj.getString("name");
                    String access_token=obj.getString("access_token");
                    String email=obj.getString("email");
                    String image=obj.getString("image");
                    String address=obj.getString("address");
                    String zipcode=obj.getString("zip_code");
                    String phone=obj.getString("phone");

                      Log.e("image====",""+image);

                        SharedPreferences.Editor editor1 = sharedpreferences1.edit();
                        editor1.putString("id",id);
                        editor1.putString("name",name);
                        editor1.putString("access_token",access_token);
                        editor1.putString("image",image);
                        editor1.commit();

                profiledata=new Model_ProfileData();

                profiledata.setId(id);
                profiledata.setName(name);
                profiledata.setAccess_token(access_token);
                profiledata.setEmail(email);
                profiledata.setImage(image);
                profiledata.setAddress(address);
                profiledata.setZipcode(zipcode);
                profiledata.setPhone(phone);

            }

        } catch (Exception e) {
            Log.e("==+result profile===", "======" + e);

        }
        return coderesponse;
    }
    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();

       if (coderesponse.equals("200")) {

           Bundle bundle = new Bundle();
           bundle.putSerializable("profiledata", profiledata);

           FragmentManager fragmentManager1 = ((Activity)context).getFragmentManager();
           myProfile=new Fragment_MyProfile();
           myProfile.setArguments(bundle);

           fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, myProfile).commit();
       }
        else if(coderesponse.equals("201")){

           Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
       }
   }
}
