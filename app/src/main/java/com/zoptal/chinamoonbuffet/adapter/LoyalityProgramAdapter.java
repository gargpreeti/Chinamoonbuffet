package com.zoptal.chinamoonbuffet.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_MyPunchs;
import com.zoptal.chinamoonbuffet.JsonClasses.LoyaltyProgramData;
import com.zoptal.chinamoonbuffet.JsonClasses.Model_LoyaltyProgram;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Menu;

import java.util.ArrayList;

public class LoyalityProgramAdapter extends BaseAdapter {

	LayoutInflater inflater;
	Context context ;
	Model_LoyaltyProgram m;
	ArrayList<LoyaltyProgramData> al_loyaltyprogram= new ArrayList<LoyaltyProgramData>();
    public static String id;

	public LoyalityProgramAdapter(Context context,Model_LoyaltyProgram m) {

		this.context=context;
		this.m=m;
		this.al_loyaltyprogram = new ArrayList<>();
		al_loyaltyprogram.addAll(m.getAl_loyaltyprogram());
		inflater=LayoutInflater.from(context);
		// TODO Auto-generated constructor stub
	}

	public void setAddList(ArrayList<LoyaltyProgramData> list) {

		m.getAl_loyaltyprogram().addAll(list);
		this.al_loyaltyprogram.addAll(list);
		notifyDataSetChanged();
	}

	public ArrayList<LoyaltyProgramData> getCurrentList() {
		return al_loyaltyprogram;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
				return al_loyaltyprogram.size();
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

		HoldlerLoyality holder=new HoldlerLoyality();
		convertView=inflater.inflate(R.layout.customview_loyalityprogram, null);
		holder.btn_getnow=(Button) convertView.findViewById(R.id.btn_getnow);
		holder.tv_main=(TextView) convertView.findViewById(R.id.tv_main);
		holder.tv_punchvalue=(TextView) convertView.findViewById(R.id.tv_punchvalue);
		holder.tv_percnt=(TextView) convertView.findViewById(R.id.tv_percnt);

		holder.tv_main.setText(al_loyaltyprogram.get(position).getTitle());
		holder.tv_punchvalue.setText(al_loyaltyprogram.get(position).getPunches());
		holder.tv_percnt.setText(al_loyaltyprogram.get(position).getDiscount()+"%");


	holder.btn_getnow.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				if(Fragment_Menu.access_tokn.isEmpty()){
					alertmsglogin();
				}

				else {
					id = al_loyaltyprogram.get(position).getId();

					new Json_MyPunchs(context).execute(Fragment_Menu.access_tokn, al_loyaltyprogram.get(position).getId());
				}
//				Fragment fragment=new Fragment_MyPunches();
//				FragmentManager fragmentManager =((Activity)context).getFragmentManager();
//				fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).addToBackStack(null).commit();
			}
		});



		return convertView;
		

	}

	public void alertmsglogin(){


		final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.dialog_msgloyality);

		Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
		TextView tv_msg=(TextView)dialog.findViewById(R.id.tv_msg);

		tv_msg.setText("Please Sign in to get Punches! ");

		btn_ok.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				dialog.dismiss();

			}
		});


		// dialog.dismiss();

		dialog.show();

	}
	
}
class HoldlerLoyality
{

	ImageView img;
	TextView tv_main;
	TextView tv_punchvalue;
	TextView tv_percnt;
	Button btn_getnow;
}
