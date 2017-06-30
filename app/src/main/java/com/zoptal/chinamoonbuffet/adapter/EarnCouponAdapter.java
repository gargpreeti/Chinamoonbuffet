package com.zoptal.chinamoonbuffet.adapter;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.chinamoonbuffet.JsonClasses.Earncouponcode;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_Redeeminperson;
import com.zoptal.chinamoonbuffet.JsonClasses.Model_EarnCoupon;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Menu;

import java.util.ArrayList;

public class EarnCouponAdapter extends BaseAdapter {

	LayoutInflater inflater;
	Context context ;
	Model_EarnCoupon m;
	ArrayList<Earncouponcode> al_earncoupon= new ArrayList<Earncouponcode>();
	public static String id;

	public EarnCouponAdapter(Context context, Model_EarnCoupon m) {

		this.context=context;
		this.m=m;
	    this.al_earncoupon = new ArrayList<>();
		al_earncoupon.addAll(m.getAl_earncoupon());
		inflater=LayoutInflater.from(context);
		// TODO Auto-generated constructor stub
	}

	public void setAddList(ArrayList<Earncouponcode> list) {
		m.getAl_earncoupon().addAll(list);
		this.al_earncoupon.addAll(list);
		notifyDataSetChanged();
	}

	public ArrayList<Earncouponcode> getCurrentList() {
		return al_earncoupon;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
				return al_earncoupon.size();
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

		final HoldlerEarncoupon holder=new HoldlerEarncoupon();
		convertView=inflater.inflate(R.layout.customview_earncoupon, null);

		holder.tv_code=(TextView) convertView.findViewById(R.id.tv_code);
		holder.tv_main=(TextView) convertView.findViewById(R.id.tv_main);
		holder.tv_date=(TextView) convertView.findViewById(R.id.tv_date);
		holder.tv_percnt=(TextView) convertView.findViewById(R.id.tv_percnt);
		holder.disable_img=(ImageView) convertView.findViewById(R.id.disable_img);
		holder.disable_img1=(ImageView) convertView.findViewById(R.id.disable_img1);
		holder.disable_img2=(ImageView) convertView.findViewById(R.id.disable_img2);
		holder.rel=(RelativeLayout)convertView.findViewById(R.id.rel);
		holder.tv_punch=(TextView) convertView.findViewById(R.id.tv_percnt);
		holder.discount=(TextView) convertView.findViewById(R.id.tv_percnt);
		holder.tv_cupon=(TextView) convertView.findViewById(R.id.tv_percnt);
		holder.btn_redeem=(ImageView)convertView.findViewById(R.id.btn_redeem);


		holder.tv_main.setText(al_earncoupon.get(position).getTitle());
		holder.tv_date.setText(al_earncoupon.get(position).getEarn_on());
		holder.tv_percnt.setText(al_earncoupon.get(position).getDiscount()+"%");
	  holder.tv_code.setText(al_earncoupon.get(position).getCode());

		holder.disable_img.setVisibility(View.GONE);

		if(al_earncoupon.get(position).getStatus().equals("1")){
			holder.disable_img.setVisibility(View.GONE);
			holder.disable_img1.setVisibility(View.GONE);
			holder.disable_img2.setVisibility(View.GONE);
			holder.rel.setBackgroundColor(Color.parseColor("#f2f2f2"));
		}
        else{
			holder.disable_img.setVisibility(View.VISIBLE);
			holder.disable_img1.setVisibility(View.VISIBLE);
			holder.disable_img2.setVisibility(View.VISIBLE);
//			holder.tv_main.setText(al_earncoupon.get(position).getTitle());
//			holder.tv_main.setPaintFlags(holder.tv_main.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//			holder.tv_date.setText(al_earncoupon.get(position).getEarn_on());
//			holder.tv_date.setPaintFlags(holder.tv_date.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//			holder.tv_percnt.setText(al_earncoupon.get(position).getDiscount()+"%");
//			holder.tv_percnt.setPaintFlags(holder.tv_percnt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//			holder.tv_code.setText(al_earncoupon.get(position).getCode());
//			holder.tv_code.setPaintFlags(holder.tv_percnt.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//
//			holder.tv_punch.setText("Earn_on:");
//			holder.tv_punch.setPaintFlags(holder.tv_punch.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//			holder.discount.setText("Discount");
//			holder.discount.setPaintFlags(holder.discount.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
//			holder.discount.setText("Code");
//			holder.tv_cupon.setPaintFlags(holder.tv_cupon.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
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

						new Json_Redeeminperson(context).execute(Fragment_Menu.access_tokn,al_earncoupon.get(position).getId(),"earned");
						dialog.dismiss();
						notifyDataSetChanged();

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
      notifyDataSetChanged();

		return convertView;
		

	}
	
}
class HoldlerEarncoupon
{

	ImageView img;
	TextView tv_main;
	TextView tv_punch;
	TextView discount;
	TextView tv_cupon;
	TextView tv_date;
	TextView tv_percnt;
	TextView tv_code;
	ImageView disable_img;
	ImageView disable_img1;
	ImageView disable_img2;
	RelativeLayout rel;
	ImageView btn_redeem;

}