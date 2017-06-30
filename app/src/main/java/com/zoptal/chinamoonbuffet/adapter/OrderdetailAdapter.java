package com.zoptal.chinamoonbuffet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.zoptal.chinamoonbuffet.R;

public class OrderdetailAdapter extends BaseAdapter {

	LayoutInflater inflater;
	Context context ;
	int img[];
	public OrderdetailAdapter(Context context, int img[]) {

		this.context=context;
	 this.img=img;
		inflater=LayoutInflater.from(context);
		
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
      return img.length;
		
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

		HoldlerOrder holder=new HoldlerOrder();
		convertView=inflater.inflate(R.layout.customview_orderdetail, null);

		holder.img=(ImageView) convertView.findViewById(R.id.img);
		holder.img.setBackgroundResource(img[position]);
		return convertView;

	}
	
}
class HoldlerOrder
{
	ImageView img;
	Spinner spinner;
}