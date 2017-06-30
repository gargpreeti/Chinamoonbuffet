package com.zoptal.chinamoonbuffet.adapter;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_Redeeminperson;
import com.zoptal.chinamoonbuffet.JsonClasses.Model_CouponsData;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Menu;

public class CouponsAdapter extends BaseAdapter {

	LayoutInflater inflater;
	Context context ;
	Model_CouponsData m;
	//ArrayList<CouponData> al_coupondata= new ArrayList<CouponData>();

	public CouponsAdapter(Context context,Model_CouponsData m) {

		this.context=context;
		this.m=m;
		//this.al_coupondata = new ArrayList<>();
		//al_coupondata.addAll(m.getAl_coupondata());
		inflater=LayoutInflater.from(context);
		
		// TODO Auto-generated constructor stub
	}
//	public void setAddList(ArrayList<CouponData> list) {
//		m.getAl_coupondata().addAll(list);
//	this.al_coupondata.addAll(list);
//	notifyDataSetChanged();
//}

//	public ArrayList<CouponData> getCurrentList() {
//		return al_coupondata;
//	}



	@Override
	public int getCount() {
		// TODO Auto-generated method stub


		return  m.getAl_coupondata().size();

	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		final HoldlerCoupons holder=new HoldlerCoupons();
		convertView=inflater.inflate(R.layout.customview_coupons, null);

		holder.img=(ImageView)convertView.findViewById(R.id.img);
		holder.tv_off=(TextView)convertView.findViewById(R.id.tv_off);
		holder.tv=(TextView)convertView.findViewById(R.id.tv);
		holder.tv_code=(TextView)convertView.findViewById(R.id.tv_code);
		holder.tv1=(TextView)convertView.findViewById(R.id.tv1);
		holder.btn_redeem=(ImageView)convertView.findViewById(R.id.btn_redeem);

		holder.tv_off.setText(m.getAl_coupondata().get(position).getDiscount()+"%");
		holder.tv.setText(m.getAl_coupondata().get(position).getTitle());
		holder.tv_code.setText(m.getAl_coupondata().get(position).getCode());
		holder.tv1.setText(m.getAl_coupondata().get(position).getDescription());

	  if(m.getAl_coupondata().get(position).getImage().isEmpty()){

		}else {
			Picasso.with(context).load(m.getAl_coupondata().get(position).getImage()).resize(2000,1200).into(holder.img);
		}
		holder.tv_code.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ClipboardManager clipboard = (ClipboardManager)context.getSystemService(context.CLIPBOARD_SERVICE);
				clipboard.setText(holder.tv_code.getText());
				Toast.makeText(context,"Code has been copied", Toast.LENGTH_SHORT).show();

			}
		});


		holder.btn_redeem.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setCancelable(true);
				dialog.setContentView(R.layout.dialog_alert);

				Button btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
				Button btn_no = (Button) dialog.findViewById(R.id.btn_no);
				TextView tv_msg=(TextView)dialog.findViewById(R.id.tv_msg);

				tv_msg.setText("This is for in-person coupon use only. Are you sure you want to proceed?");

				btn_yes.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						new Json_Redeeminperson(context).execute(Fragment_Menu.access_tokn,m.getAl_coupondata().get(position).getId(),"daily_offer");
						dialog.dismiss();

					}
				});
				btn_no.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						dialog.dismiss();
					}
				});


				dialog.show();





			}
		});

		return convertView;
		

	}
	
}
class HoldlerCoupons
{

	ImageView img;
	TextView tv_off;
	TextView tv;
	TextView tv_code;
	TextView tv1;
	ImageView btn_redeem;
}
