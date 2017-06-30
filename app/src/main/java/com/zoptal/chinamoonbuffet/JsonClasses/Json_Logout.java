package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Menu;
import com.zoptal.chinamoonbuffet.main.MainActivity;
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


public class Json_Logout extends AsyncTask<String, String, String> {

    public static final String MyPREFERENCES = "MyPrefs1" ;
    SharedPreferences sharedpreferences1;
    Context context;
    String msg,coderesponse;
    ProgressDialog loading;
     MainActivity activity1;
       public Json_Logout(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
           activity1 = (MainActivity) context;
    }
    @Override
    protected void onPreExecute() {

        super.onPreExecute();
        loading = new ProgressDialog(context);
        loading.setMessage("Logging out");
        loading.show();
        sharedpreferences1 = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }
    @Override
    protected String doInBackground(String... params) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                RegisterUrl.logout);

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
             Log.e("logout result------",""+result1.trim());

              JSONObject main_obj = new JSONObject(result1);
                msg= main_obj.getString("message");
                coderesponse=main_obj.getString("code");

//                JSONObject  obj=main_obj.getJSONObject("data");
//
//                    String id =obj.getString("id");
//                    String name=obj.getString("name");
//                    String access_token=obj.getString("access_token");
//
//
                SharedPreferences.Editor editor1 =sharedpreferences1.edit();
                editor1.putString("id","");
                editor1.putString("name","");
                editor1.putString("access_token","");
                editor1.putString("image","");
                editor1.commit();

            }

        } catch (Exception e) {
            Log.e("==+result logout===", "======" + e);

        }

        return coderesponse;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();

   if (coderesponse.equals("200")) {

       activity1.tv_logout.setVisibility(View.GONE);
       activity1.tv_username.setText("");
       activity1.tv_signin.setText("Sign in");
       activity1.tv_signin.setEnabled(true);
       activity1.tv_signin.setVisibility(View.VISIBLE);
       activity1.img_user.setVisibility(View.VISIBLE);
       activity1.fab.setVisibility(View.GONE);
       Fragment_Menu.fab1.setVisibility(View.VISIBLE);

       FragmentManager fragmentManager1 = ((Activity) context).getFragmentManager();
       Fragment_Menu f=new Fragment_Menu();
       fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, f).commit();
//          Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
//           Intent intent = new Intent(context,MainActivity.class);
//           context.startActivity(intent);
     }
//        else if(coderesponse.equals("201")){
//
//           Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
//       }
   }
}
