package com.zoptal.chinamoonbuffet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class CheckoutAdapter1 extends BaseAdapter {

	LayoutInflater inflater;
	Context context ;
	int img[];
	public CheckoutAdapter1(Context context) {

		this.context=context;
	 this.img=img;
		inflater=LayoutInflater.from(context);
		
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
      return 5;
		
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

		//HoldlerCheckout holder=new HoldlerCheckout();
		//convertView=inflater.inflate(R.layout.customview_recommdation, null);

//		 holder.spinner = (Spinner)convertView.findViewById(R.id.spinner);
//		String[] years = {"1","2","3","4","5","6","7"};
//		ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(context, R.layout.spinner_text, years );
//		langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//		holder.spinner.setAdapter(langAdapter);

		//holder.img=(ImageView) convertView.findViewById(R.id.img);
		//holder.img.setBackgroundResource(img[position]);
		return convertView;
		

	}
	
}
