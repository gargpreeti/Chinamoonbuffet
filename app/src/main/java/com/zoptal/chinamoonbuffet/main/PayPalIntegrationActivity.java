package com.zoptal.chinamoonbuffet.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_MyCartList;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_MyCartList1;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Checkout;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Menu;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.List;

public class PayPalIntegrationActivity extends ActionBarActivity {

	public  List<String> seller_id;
	JSONObject jsonObject;
	String result2;
	ImageView img_bck;
	public  TextView total_chrges,tv_delivery,discount_value,gtotal_value;

	public static String grandtotal;
	Button paynow;
	//private PayPal mPayPal;
	public  ListView listView;
	double gtotal=0;
	double dchrge=0;

	private static PayPalConfiguration config = new PayPalConfiguration()

			// Start with mock environment.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
			// or live (ENVIRONMENT_PRODUCTION)
			.environment(PayPalConfiguration.ENVIRONMENT_PRODUCTION)

			.clientId("ASXiUF4yjsZA76L8e5ZdaSo6IqteEp0yh6nv4PPvHfRpZU0pfxZITK8hsUvSD0kZ8svBILcIowtj_KW2").merchantName("ChinaMoonBuffet");
			//.clientId("Ae6QnxsdQgyUZSzuMSA306aEbaAf6RFe4ea934EgWtow7knriA_O3qcLI1exXEUgnng3zFZ4HyLhFkfT").merchantName("ChinaMoonBuffet");



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_pal_integration);

	//	txt_name=(EditText)findViewById(R.id.ed_name);
	//	txt_address=(EditText)findViewById(R.id.ed_address);
		//paynow=(Button)findViewById(R.id.paynow);
		img_bck=(ImageView)findViewById(R.id.back);

		total_chrges=(TextView)findViewById(R.id.total_chrges);
		gtotal_value=(TextView)findViewById(R.id.gtotal_value);
		discount_value=(TextView)findViewById(R.id.discount_value);

		discount_value.setText(Fragment_Checkout.discountvalue.getText().toString());

		listView=(ListView)findViewById(R.id.listView);

		new Json_MyCartList1(PayPalIntegrationActivity.this,listView).execute(Fragment_Menu.access_tokn);

		total_chrges.setText("$"+Json_MyCartList.tamt);


          gtotal=Double.parseDouble(Json_MyCartList.tamt)+dchrge-Double.parseDouble(Fragment_Checkout.discountvalue.getText().toString());

			gtotal_value.setText(String.valueOf(gtotal));
		grandtotal=String.valueOf(gtotal);

		Log.e("grandtotal=====",""+grandtotal);

		img_bck.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

//				Fragment fragment=new Fragment_OrderType();
//				FragmentManager fragmentManager =getFragmentManager();
//				fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

				Intent i = new Intent(PayPalIntegrationActivity.this, MainActivity.class);
				startActivity(i);

				finish();

			}
		});

//		mPayPal=PayPal.initWithAppID(this,Constants.PAYPAL_APP_ID,PayPal.ENV_SANDBOX);
//
//		initUI();

		Intent intent = new Intent(this, PayPalService.class);

		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

		startService(intent);


	}
	@Override
	public void onDestroy() {
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}
	private void initUI() {

		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);

		params.addRule(RelativeLayout.CENTER_IN_PARENT);

		params.bottomMargin = 10;


//		paynow.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//
//					payWithPaypal();
//
//			}
//		});
	}
	public void onBuyPressed(View pressed) {

		// PAYMENT_INTENT_SALE will cause the payment to complete immediately.
		// Change PAYMENT_INTENT_SALE to
		//   - PAYMENT_INTENT_AUTHORIZE to only authorize payment and capture funds later.
		//   - PAYMENT_INTENT_ORDER to create a payment for authorization and capture
		//     later via calls from your server.

//		PayPalPayment payment = new PayPalPayment(new BigDecimal("1.75"), "USD", "sample item",
//				PayPalPayment.PAYMENT_INTENT_SALE);
//
//		Intent intent = new Intent(this, PaymentActivity.class);
//
//		// send the same configuration for restart resiliency
//		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//
//		intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
//
//		startActivityForResult(intent, 0);



		PayPalPayment payment = new PayPalPayment(new BigDecimal(grandtotal), "USD", "items",
				PayPalPayment.PAYMENT_INTENT_SALE);

//		PayPalPayment payment = new PayPalPayment(new BigDecimal(0.01), "USD", "items",
//				PayPalPayment.PAYMENT_INTENT_SALE);

		Intent intent = new Intent(this, PaymentActivity.class);

		// send the same configuration for restart resiliency
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);

		intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

		startActivityForResult(intent, 0);





//
//									PayPalPayment newPayment = new PayPalPayment();
//		BigDecimal bigDecimal=new BigDecimal(10);
//		newPayment.setSubtotal(bigDecimal);
//		newPayment.setCurrencyType("USD");
//		newPayment.setRecipient("technical-facilitator7@gmail.com");
//		newPayment.setMerchantName("My Merchant");
//		Intent paypalIntent = PayPal.getInstance().checkout(newPayment, this);
//         startActivityForResult(paypalIntent,1);
//

	}


//
//	private void payWithPaypal() {
//
//		PayPalPayment newPayment = new PayPalPayment();
//		BigDecimal bigDecimal=new BigDecimal(10);
//		newPayment.setSubtotal(bigDecimal);
//		newPayment.setCurrencyType("USD");
//		newPayment.setRecipient("technical-facilitator7@gmail.com");
//		newPayment.setMerchantName("My Merchant");
//		Intent paypalIntent = PayPal.getInstance().checkout(newPayment, this);
//         startActivityForResult(paypalIntent,1);
//
////		double pricePrimary = 0.0;
////		Double admin_price=0.0;
////
////		seller_id=new ArrayList<String>();
////		PayPalAdvancedPayment payment = new PayPalAdvancedPayment();
////
////		payment.setCurrencyType("USD");
////
////		pricePrimary=Double.parseDouble("100");
////
////		BigDecimal bigDecimalPrimary = new BigDecimal(pricePrimary);
////		PayPalReceiverDetails receiverPrimary = new PayPalReceiverDetails();
////
////
////		receiverPrimary.setRecipient("admin-facilitator@zoptal.com");
////
////		receiverPrimary.setMerchantName("chinamoon");
////
////		receiverPrimary.setSubtotal(bigDecimalPrimary);
////		receiverPrimary.setIsPrimary(true);
////
////		payment.getReceivers().add(receiverPrimary);
////
//////		for (int i=0;i<CustomCart.mrchntlist.size();i++) {
//////
//////
//////			Double item_price=0.0;
//////			Double Tprice = 0.0;
//////			Double Seller_Price=0.0;
//////			for (int j = 0; j < CustomCart.m.getAl_cart().size(); j++) {
//////
//////				if (CustomCart.m.getAl_cart().get(j).getMrchntid().equals(CustomCart.mrchntlist.get(i).toString())) {
//////
//////					Tprice += Double.parseDouble(CustomCart.m.getAl_cart().get(j).getItem_price());
//////					item_price = Tprice / 10;
//////				}
//////
//////			}
////		  Double Seller_Price=10.0;
////		//	Seller_Price=Tprice-item_price;
////			PayPalReceiverDetails receiver0;
////			receiver0 = new PayPalReceiverDetails();
////			BigDecimal bigDecimalSecondary = new BigDecimal(Seller_Price);
////			receiver0.setSubtotal(bigDecimalSecondary);
////			receiver0.setRecipient("technical-facilitator2@zoptal.com");// add correct object of email of receiver
////
////
////				payment.getReceivers().add(receiver0);
////
////		Intent checkoutIntent = PayPal.getInstance().checkout(payment,PayPalIntegrationActivity.this);
////		startActivityForResult(checkoutIntent, 1);
//
//	}
//




	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == Activity.RESULT_OK) {
			PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
			if (confirm != null) {
				try {
					String paymentDetails = confirm.toJSONObject().toString(4);
				 String paymentAmount=grandtotal;
					Log.e("paymentExample", confirm.toJSONObject().toString(4));
					Log.e("paymentExample1", confirm.getPayment().toJSONObject().toString(4));

					startActivity(new Intent(this, ConfirmationActivity.class)
							.putExtra("PaymentDetails", paymentDetails)
							.putExtra("PaymentAmount", paymentAmount));
					// TODO: send 'confirm' to your server for verification.
					// see https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
					// for more details.

				} catch (JSONException e) {
					Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
				}
			}
		}
		else if (resultCode == Activity.RESULT_CANCELED) {
			Log.e("paymentExample", "The user canceled.");
		}
		else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
			Log.e("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
		}

	}


}
