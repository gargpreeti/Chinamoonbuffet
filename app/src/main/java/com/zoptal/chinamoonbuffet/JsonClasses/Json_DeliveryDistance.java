package com.zoptal.chinamoonbuffet.JsonClasses;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import com.zoptal.chinamoonbuffet.main.MapActivity;
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

public class Json_DeliveryDistance extends AsyncTask<String, String, String> {

	Context context;
	public  static Model_DeliveryDistance deliveryDistance;
	ProgressDialog loading;

	public Json_DeliveryDistance(Context context) {

		this.context = context;
		loading = new ProgressDialog(context);
		loading.setMessage("loading");
		loading.show();
	}

	@Override
	protected String doInBackground(String... params) {

		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(
				RegisterUrl.deliverydistance);

		try {
			// Add your data
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("access_token", params[0]));
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Execute HTTP Post Request
			HttpResponse response = httpclient.execute(httppost);

			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String result = EntityUtils.toString(entity);

				JSONObject main_obj = new JSONObject(result);
				JSONObject obj = main_obj.getJSONObject("data");

				String image = obj.getString("image");
				String name = obj.getString("name");
				String email = obj.getString("email");
				String address = obj.getString("address");
				String zip_code = obj.getString("zip_code");
				String phone = obj.getString("phone");
				String distance = obj.getString("distance");
				String delivery_fee = obj.getString("delivery_fee");
				String latitude = obj.getString("latitude");
				String longitude = obj.getString("longitude");
				String hotel_latitude = obj.getString("hotel_latitude");
				String hotel_longitude = obj.getString("hotel_longitude");

				deliveryDistance = new Model_DeliveryDistance();

				deliveryDistance.setImage(image);
				deliveryDistance.setName(name);
				deliveryDistance.setEmail(email);
				deliveryDistance.setAddress(address);
				deliveryDistance.setZip_code(zip_code);
				deliveryDistance.setPhone(phone);
				deliveryDistance.setDistance(distance);
				deliveryDistance.setDelivery_fee(delivery_fee);
				deliveryDistance.setUserlat(latitude);
				deliveryDistance.setUserlong(longitude);
				deliveryDistance.setHotel_latitude(hotel_latitude);
				deliveryDistance.setHotel_longitude(hotel_longitude);


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

		Intent i = new Intent(context, MapActivity.class);
		context.startActivity(i);


//		FragmentManager fragmentManager1 = ((Activity) context).getFragmentManager();
//		Fragment_DeliveryMap f = new Fragment_DeliveryMap();
//		fragmentManager1
//				.beginTransaction()
//				.replace(R.id.activity_main_content_fragment,
//						f).commit();


//		final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
//		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//		dialog.setCancelable(true);
//		dialog.setContentView(R.layout.dialog_deliverymap);
//
//
//		TextView tv_cncl = (TextView) dialog.findViewById(R.id.tv_cncl);
//		TextView tv_address = (TextView) dialog.findViewById(R.id.tv_address);
//		TextView tv_name = (TextView) dialog.findViewById(R.id.tv_name);
//		ImageView user_img=(ImageView)dialog.findViewById(R.id.user_img);
//		TextView tv_distancevalue = (TextView) dialog.findViewById(R.id.tv_distancevalue);
//		TextView tv_chrge = (TextView) dialog.findViewById(R.id.tv_chrge);
//		Button btn_continue = (Button) dialog.findViewById(R.id.btn_continue);
//
//        tv_address.setText(deliveryDistance.getAddress());
//		tv_name.setText(deliveryDistance.getName());
//		tv_distancevalue.setText(deliveryDistance.getDistance());
//		tv_chrge.setText(deliveryDistance.getDelivery_fee());
//
//
//			Picasso.with(context).load(deliveryDistance.getImage()).into(user_img);
//
//			tv_cncl.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dialog.dismiss();
//
//			}
//		});
//		btn_continue.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//
//			}
//		});
//
//		// dialog.dismiss();
//
//		dialog.show();
//
//	}


//
//
//
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
}
