package com.zoptal.chinamoonbuffet.JsonClasses;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_ItemDetail;
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

public class Json_CategoryDetail extends AsyncTask<String, String, String> {

	  Context context;
      MenuData menuDataDetail;
	  ProgressDialog loading;

	public Json_CategoryDetail(Context context) {

		this.context = context;
		loading = new ProgressDialog(context);
		loading.setMessage("loading");
		loading.show();
	}

	@Override
	protected String doInBackground(String... params) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				RegisterUrl.product_detail);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

			nameValuePairs.add(new BasicNameValuePair("id", params[0]));


			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);

				JSONObject main_obj = new JSONObject(result);
				JSONObject data = main_obj.getJSONObject("data");

				String id=data.getString("id");
				String title = data.getString("title");
				String category = data.getString("category");
				String price = data.getString("price");
				String category_name = data.getString("category_name");
				String image = data.getString("image");
				String description = data.getString("description");
				String posted = data.getString("posted");


				//list.add(username);

				menuDataDetail = new MenuData();

				menuDataDetail.setId(id);
				menuDataDetail.setTitle(title);
				menuDataDetail.setCategory(category);
				menuDataDetail.setCategoryname(category_name);
				menuDataDetail.setImage(image);
				menuDataDetail.setDescription(description);
				menuDataDetail.setPosted(posted);
				menuDataDetail.setPrice(price);

			}

		} catch (Exception e) {
			Log.e("==+Error===", "Error===" + e);

		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		loading.dismiss();

	    Bundle bundle = new Bundle();
		bundle.putSerializable("itemdetail", menuDataDetail);

		FragmentManager fragmentManager1 = ((Activity) context).getFragmentManager();
		Fragment_ItemDetail f=new Fragment_ItemDetail();
		f.setArguments(bundle);


		fragmentManager1
				.beginTransaction()
				.replace(R.id.activity_main_content_fragment,
						f).commit();



	}
}