package com.zoptal.chinamoonbuffet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zoptal.chinamoonbuffet.JsonClasses.OrderHistorytest;
import com.zoptal.chinamoonbuffet.R;

import java.util.ArrayList;

public class OrderHistoryAdapterDetail extends BaseAdapter {

	LayoutInflater inflater;
	Context context ;

	public ArrayList<OrderHistorytest> al_orderhistory;

	public OrderHistoryAdapterDetail(Context context, ArrayList<OrderHistorytest> al_orderhistory) {

		this.context=context;
		this.al_orderhistory=al_orderhistory;
		inflater=LayoutInflater.from(context);

		// TODO Auto-generated constructor stub
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub

//
//       if(al_orderhistory.size()==0){
//
//	Fragment_MyProfile.tv_msg.setVisibility(View.VISIBLE);
//	Fragment_MyProfile.listView.setVisibility(View.GONE);
//	}
//	   else {
//     Fragment_MyProfile.tv_msg.setVisibility(View.GONE);
//	Fragment_MyProfile.listView.setVisibility(View.VISIBLE);
//	}
		return al_orderhistory.size();
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

		HoldlerOrderHistoryDetail holder=new HoldlerOrderHistoryDetail();
		convertView=inflater.inflate(R.layout.customview_orderhistorytest, null);

		holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
		holder.tv2=(TextView)convertView.findViewById(R.id.tv2);
		holder.tv_qtyvalue=(TextView)convertView.findViewById(R.id.tv_qtyvalue);
		holder.tv_amountvalue=(TextView)convertView.findViewById(R.id.tv_amountvalue);
		holder.img=(ImageView)convertView.findViewById(R.id.img);


		holder.tv_name.setText(al_orderhistory.get(position).getProduct_name());
		holder.tv2.setText(al_orderhistory.get(position).getCategory_name());
		holder.tv_qtyvalue.setText(al_orderhistory.get(position).getQty());
		holder.tv_amountvalue.setText(al_orderhistory.get(position).getTotal_amt());


		if(al_orderhistory.get(position).getProduct_image().isEmpty()){

		}
		else {
			Picasso.with(context).load(al_orderhistory.get(position).getProduct_image()).into(holder.img);
		}

		return convertView;
		

	}
	
}class HoldlerOrderHistoryDetail
{

	ImageView img;
	TextView tv_name;
	TextView tv2;
	TextView tv_qtyvalue;
	TextView tv_amountvalue;


}
