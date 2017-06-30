package com.zoptal.chinamoonbuffet.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_TableDetail;
import com.zoptal.chinamoonbuffet.JsonClasses.Model_TableList;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Menu;
import com.zoptal.chinamoonbuffet.fragment.Fragment_RequestDetail;

import java.util.ArrayList;

public class ReserveTableAdapter extends BaseAdapter {

	LayoutInflater inflater;
	Context context ;
	//int img[];

	ArrayList<Model_TableList> al_tablelist;
    public static String tableid;

	public ReserveTableAdapter(Context context,ArrayList<Model_TableList> al_tablelist) {

		this.context=context;
        this.al_tablelist=al_tablelist;
		inflater=LayoutInflater.from(context);
		
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
      return al_tablelist.size();
		
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

		HoldlerReservetable holder=new HoldlerReservetable();
		convertView=inflater.inflate(R.layout.customview_reservetable, null);

		holder.img=(ImageView) convertView.findViewById(R.id.img);
		holder.tv_title=(TextView)convertView.findViewById(R.id.tv_name);
		holder.tv_desc=(TextView)convertView.findViewById(R.id.tv2);
		holder.btn_reserve=(Button) convertView.findViewById(R.id.btn_reserve);
		holder.btn_reserved=(Button)convertView.findViewById(R.id.btn_reserved);

		holder.tv_title.setText(al_tablelist.get(position).getTitle());
		holder.tv_desc.setText(al_tablelist.get(position).getDescription());

		Picasso.with(context).load(al_tablelist.get(position).getImage()).resize(220,120).into(holder.img);




		holder.btn_reserve.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				tableid=al_tablelist.get(position).getId();

				if(Fragment_Menu.access_tokn.isEmpty()){

                alertmsglogin();


				}
else {
					FragmentManager fragmentManager1 = ((Activity) context).getFragmentManager();
					Fragment_RequestDetail requestdetail = new Fragment_RequestDetail();
					fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, requestdetail).commit();
				}

			}
		});



		holder.img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				new Json_TableDetail(context).execute(al_tablelist.get(position).getId());

//				Fragment fragment=new Fragment_ReserveTableDetail();
//				FragmentManager fragmentManager =((Activity)context).getFragmentManager();
//				fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();
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

		tv_msg.setText("Please Sign in to Reserve A Table!");

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
class HoldlerReservetable
{

	ImageView img;
    TextView tv_title;
	TextView tv_desc;
	Button btn_reserve;
	Button btn_reserved;
}