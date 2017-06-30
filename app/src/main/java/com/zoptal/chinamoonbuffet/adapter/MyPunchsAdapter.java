package com.zoptal.chinamoonbuffet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zoptal.chinamoonbuffet.JsonClasses.MyPunchsList;
import com.zoptal.chinamoonbuffet.R;

import java.util.ArrayList;

public class MyPunchsAdapter extends BaseAdapter {

	LayoutInflater inflater;
	Context context ;
	int img[];
	ArrayList<MyPunchsList> al_mypunchlist;

	public MyPunchsAdapter(Context context, ArrayList<MyPunchsList> al_mypunchlist) {

		this.context=context;
		this.al_mypunchlist=al_mypunchlist;
		inflater=LayoutInflater.from(context);
		
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub

//		if(al_mypunchlist.size()>0){
//			Fragment_MyPunchesP.tv_msg.setVisibility(View.GONE);
//
//		}
//		else{
//			Fragment_MyPunchesP.tv_msg.setVisibility(View.VISIBLE);
//
//		}
			return al_mypunchlist.size();
		
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		HoldlerPunchs holder=new HoldlerPunchs();
		convertView=inflater.inflate(R.layout.customview_mypunchs, null);
		holder.tv_punch=(TextView)convertView.findViewById(R.id.tv_punch);

		holder.tv_punch.setText(al_mypunchlist.get(position).getTitle());

		//holder.img=(ImageView) convertView.findViewById(R.id.img);
	//	holder.img.setBackgroundResource(img[position]);
		return convertView;
		

	}
	
}
class HoldlerPunchs
{

	ImageView img;
	TextView tv_punch;
}
