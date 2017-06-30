package com.zoptal.chinamoonbuffet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_CategoryDetail;
import com.zoptal.chinamoonbuffet.JsonClasses.MenuData;
import com.zoptal.chinamoonbuffet.JsonClasses.Model_MenuData;
import com.zoptal.chinamoonbuffet.R;

import java.util.ArrayList;

public class CustomListAdapter extends BaseAdapter {

	LayoutInflater inflater;
	Context context ;
	Model_MenuData m;
	ArrayList<MenuData> al_menudata = new ArrayList<MenuData>();

	public CustomListAdapter(Context context,Model_MenuData m) {

		this.context=context;
		this.m=m;
		this.al_menudata = new ArrayList<>();
		al_menudata.addAll(m.getAl_menudata());
		inflater=LayoutInflater.from(context);
		
		// TODO Auto-generated constructor stub
	}
	public void setAddList(ArrayList<MenuData> list) {
		m.getAl_menudata().addAll(list);
		this.al_menudata.addAll(list);
		notifyDataSetChanged();
	}

	public ArrayList<MenuData> getCurrentList() {
		return al_menudata;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub

		return al_menudata.size();
		
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
		
		Holdler holder=new Holdler();
		convertView=inflater.inflate(R.layout.activity_customview, null);
		holder.img=(ImageView) convertView.findViewById(R.id.img);
		holder.tv=(TextView)convertView.findViewById(R.id.tv);
		holder.item_price=(TextView)convertView.findViewById(R.id.item_price);
		holder.tv_catname=(TextView)convertView.findViewById(R.id.tv_catname);
		holder.btn_view=(Button)convertView.findViewById(R.id.btn_view);


		holder.tv.setText(al_menudata.get(position).getTitle());
		holder.item_price.setText("$"+al_menudata.get(position).getPrice());
		holder.tv_catname.setText(al_menudata.get(position).getCategoryname());

			if(al_menudata.get(position).getImage().isEmpty()){

		}
		else {
			Picasso.with(context).load(al_menudata.get(position).getImage()).resize(160,100).into(holder.img);
		}

		holder.img.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				new Json_CategoryDetail(context).execute(al_menudata.get(position).getId());

//
//				Fragment fragment=new Fragment_ItemDetail();
//				FragmentManager fragmentManager =((Activity)context).getFragmentManager();
//				fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();
			}
		});

		holder.btn_view.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new Json_CategoryDetail(context).execute(al_menudata.get(position).getId());

//				Fragment fragment=new Fragment_ItemDetail();
//				FragmentManager fragmentManager =((Activity)context).getFragmentManager();
//				fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();
			}
		});


		return convertView;
		
		

	}

	
	
	
}
class Holdler
{
	ImageView img;
	Button btn_view;
	TextView tv;
	TextView item_price;
	TextView tv_catname;
}
