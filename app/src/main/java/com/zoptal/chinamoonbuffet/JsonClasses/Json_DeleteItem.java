package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Checkout;
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

public class Json_DeleteItem extends AsyncTask<String, String, String> {

    Context context;
    String msg,coderesponse;
  //  ProgressDialog loading;
   MainActivity activity1;
       public Json_DeleteItem(Context context) {
        // TODO Auto-generated constructor stub
        this.context = context;
           activity1 = (MainActivity) context;
    }
    @Override
    protected void onPreExecute() {

        super.onPreExecute();
//        loading = new ProgressDialog(context);
//        loading.setMessage("Logging out");
//        loading.show();

    }
    @Override
    protected String doInBackground(String... params) {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(
                RegisterUrl.dltcartitem);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("access_token",params[0]));
            nameValuePairs.add(new BasicNameValuePair("cart_id",params[1]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
             Log.e("cart result------",""+result1.trim());

          JSONObject main_obj = new JSONObject(result1);
//                msg= main_obj.getString("message");
           coderesponse=main_obj.getString("code");

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
        //loading.dismiss();

   if (coderesponse.equals("200")) {
        //Toast.makeText(context, "item delted", Toast.LENGTH_LONG).show();
     //  new Json_MyCartList(context, Fragment_Checkout.listView).execute(Fragment_Menu.access_tokn);
       Fragment fragment = new Fragment_Checkout();
       FragmentManager fragmentManager = ((Activity) context).getFragmentManager();
       fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

//           Intent intent = new Intent(context,MainActivity.class);
//           context.startActivity(intent);
     }
//        else if(coderesponse.equals("201")){
//
//           Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
//       }
   }
}
