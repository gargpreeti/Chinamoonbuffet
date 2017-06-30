package com.zoptal.chinamoonbuffet.JsonClasses;


import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_LoyalityProgram;
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

public class Json_Redeem extends AsyncTask<String, String, String> {

	Context context;
	public  static Model_Redeem Model_Redeem;
	ProgressDialog loading;

	public Json_Redeem(Context context) {

		this.context = context;
		loading = new ProgressDialog(context);
		loading.setMessage("loading");
		loading.show();
	}

	@Override
	protected String doInBackground(String... params) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				RegisterUrl.redeempunchs);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("access_token", params[0]));
			nameValuePairs.add(new BasicNameValuePair("code_id", params[1]));

			Log.e("nameparis----",""+nameValuePairs);

			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);
				Log.e("result----",""+result);
				JSONObject main_obj = new JSONObject(result);


				JSONObject obj = main_obj.getJSONObject("data");

				String user_id = obj.getString("user_id");
				String title = obj.getString("title");
				String discount = obj.getString("discount");
				String code = obj.getString("code");
				String description = obj.getString("description");


				Model_Redeem = new Model_Redeem();

				Model_Redeem.setUser_id(user_id);
				Model_Redeem.setTitle(title);
				Model_Redeem.setDiscount(discount);
				Model_Redeem.setCode(code);
				Model_Redeem.setDescription(description);

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

		alertmsgcode();
//		android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(context);
//		//  builder1.setTitle(Html.fromHtml("<font color='#000000'>" +"Pl"));
//		builder1.setMessage((Html.fromHtml("<font color='#000000'>" +"  Use Code:-" +Model_Redeem.getCode())));
//		builder1.setCancelable(false);
//
//		builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//			public void onClick(DialogInterface dialog, int which) {
////                                Toast.makeText(MainActivity1.this, "You exit from app",
////                                        Toast.LENGTH_LONG).show();
//				dialog.dismiss();
//
//			}
//		});
//
//		android.app.AlertDialog alert = builder1.create();
//		alert.show();
//		Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
//		pbutton.setBackgroundColor(Color.WHITE);
//		pbutton.setTextColor(Color.BLACK);


//
//	    Bundle bundle = new Bundle();
//		bundle.putSerializable("deliverydistance", deliveryDistance);
//
//		FragmentManager fragmentManager1 = ((Activity) context).getFragmentManager();
//		Fragment_ReserveTableDetail f=new Fragment_ReserveTableDetail();
//		f.setArguments(bundle);
//
//
//		fragmentManager1
//				.beginTransaction()
//				.replace(R.id.activity_main_content_fragment,
//						f).commit();
//
//
//
	}

	public void alertmsgcode(){


		final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.dialog_msgloyality);

		Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
		TextView tv_msg=(TextView)dialog.findViewById(R.id.tv_msg);

//Log.e("model redeem---",""+Model_Redeem.getCode());
		tv_msg.setText("  Use Code:-" +Model_Redeem.getCode());

		btn_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				Fragment fragment = new Fragment_LoyalityProgram();
				FragmentManager fragmentManager =((Activity)context).getFragmentManager();
				fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

				dialog.dismiss();

			}
		});


		// dialog.dismiss();

		dialog.show();

	}
}
