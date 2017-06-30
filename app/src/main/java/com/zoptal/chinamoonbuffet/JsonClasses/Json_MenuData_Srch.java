package com.zoptal.chinamoonbuffet.JsonClasses;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;

import com.zoptal.chinamoonbuffet.adapter.CustomListAdapter;
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


public class Json_MenuData_Srch extends AsyncTask<String, String, String> {


    Context context;
    String coderesponse;
    ProgressDialog loading;
    public static Model_MenuData model_menudata;
   // public CustomListAdapter adapter_customlist;
   GridView gridView;

    public Json_MenuData_Srch(Context context,GridView gridView) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.gridView=gridView;

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
                RegisterUrl.menu_data);

        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

            nameValuePairs.add(new BasicNameValuePair("category",params[0]));
            nameValuePairs.add(new BasicNameValuePair("search",params[1]));
            nameValuePairs.add(new BasicNameValuePair("page_no",params[2]));
            nameValuePairs.add(new BasicNameValuePair("no_of_post",params[3]));

            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            Log.e("paramerts---",""+nameValuePairs);
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result1 = EntityUtils.toString(entity);
             Log.e("menu data result------",""+result1.trim());


                model_menudata = new Model_MenuData();

                ArrayList<MenuData> menudata = new ArrayList<MenuData>();
                JSONObject main_obj = new JSONObject(result1);

             coderesponse=main_obj.getString("code");


                JSONArray ary_products =main_obj.getJSONArray("data");
                for (int i = 0; i < ary_products.length(); i++) {
                    JSONObject obj = ary_products.getJSONObject(i);
                    String id = obj.getString("id");
                    String title = obj.getString("title");
                    String category = obj.getString("category");
                    String category_name = obj.getString("category_name");
                    String image = obj.getString("image");
                    String description = obj.getString("description");
                    String price = obj.getString("price");
                    String posted = obj.getString("posted");

                    MenuData f = new MenuData();
                    f.setId(id);
                    f.setTitle(title);
                    f.setCategory(category);
                    f.setCategoryname(category_name);
                    f.setImage(image);
                    f.setDescription(description);
                    f.setPrice(price);
                    f.setPosted(posted);


                    menudata.add(f);

                }

                model_menudata.setAl_menudata(menudata);

            }

        } catch (Exception e) {
            Log.e("==+result menudata===", "======" + e);

        }

        return coderesponse;
    }


    @Override
    protected void onPostExecute(String result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        loading.dismiss();


        gridView.setAdapter(new CustomListAdapter(context, model_menudata));


//        ArrayList<MenuData> menudataArrayList=new ArrayList<>();
//        ArrayList<MenuData> menudatalist= adapter_customlist.getCurrentList();
//
//        for (int i = 0; i <model_menudata.getAl_menudata().size(); i++) {
//            boolean check=true;
//            for (int j = 0; j< menudatalist.size() ; j++) {
//
//                if (menudatalist.get(j).getId().equalsIgnoreCase(model_menudata.getAl_menudata().get(i).getId()) )
//                {
//                    check=false;
//                }
//            }
//            if(check)
//            {
//                menudataArrayList.add(model_menudata.getAl_menudata().get(i));
//            }
//        }
//        adapter_customlist.setAddList(menudataArrayList);
//
   }
}
