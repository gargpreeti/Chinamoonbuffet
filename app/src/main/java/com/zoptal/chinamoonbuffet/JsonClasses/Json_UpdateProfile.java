package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import com.zoptal.chinamoonbuffet.fragment.Fragment_Menu;
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


public class Json_UpdateProfile extends AsyncTask<String, String, String> {

    public static final String MyPREFERENCES = "MyPrefs1" ;
    SharedPreferences sharedpreferences1;
    Context context;
    String msg,coderesponse;
    ProgressDialog loading;

       public Json_UpdateProfile(Context context) {
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
                RegisterUrl.updateprofile);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("access_token",params[0]));;
            nameValuePairs.add(new BasicNameValuePair("name",params[1]));
            nameValuePairs.add(new BasicNameValuePair("email",params[2]));
            nameValuePairs.add(new BasicNameValuePair("address",params[3]));
            nameValuePairs.add(new BasicNameValuePair("zip_code",params[4]));
            nameValuePairs.add(new BasicNameValuePair("phone",params[5]));
            nameValuePairs.add(new BasicNameValuePair("image",params[6]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
             Log.e("update result------",""+result1.trim());

             JSONObject main_obj = new JSONObject(result1);
//                msg= main_obj.getString("message");
             coderesponse=main_obj.getString("code");
//
                JSONObject obj=main_obj.getJSONObject("data");

                    String id =obj.getString("id");
                    String name=obj.getString("name");
                    String access_token=obj.getString("access_token");
                    String image=obj.getString("image");

                Log.e("image====",""+image);
                SharedPreferences.Editor editor1 = sharedpreferences1.edit();
                editor1.putString("id",id);
                editor1.putString("name",name);
                editor1.putString("access_token",access_token);
                editor1.putString("image",image);
                editor1.commit();
            }

        } catch (Exception e) {
            Log.e("==+result update===", "======" + e);

        }

        return coderesponse;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();

      if (coderesponse.equals("200")) {
          new Json_ProfileData(context).execute(Fragment_Menu.access_tokn);
      }
//        else if(coderesponse.equals("201")){
//
//           Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
//       }
    }
}
