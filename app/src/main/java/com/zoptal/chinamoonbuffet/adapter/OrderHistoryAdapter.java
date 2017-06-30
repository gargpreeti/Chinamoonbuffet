package com.zoptal.chinamoonbuffet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_OrderHistoryDetail;
import com.zoptal.chinamoonbuffet.JsonClasses.OrderHistory;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Menu;

import java.util.ArrayList;

public class OrderHistoryAdapter extends BaseAdapter {

	LayoutInflater inflater;
	Context context ;
	int img[];
	public ArrayList<OrderHistory> al_orderhistory;

	public OrderHistoryAdapter(Context context,ArrayList<OrderHistory> al_orderhistory) {

		this.context=context;
		this.al_orderhistory=al_orderhistory;
		inflater=LayoutInflater.from(context);
		
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub


//       if(al_orderhistory.size()>0){
//
//
//	}
//	   else {
//
//
//
//
//
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		HoldlerOrderHistory holder=new HoldlerOrderHistory();
		convertView=inflater.inflate(R.layout.customview_orderhistory, null);

	holder.tv_grandtotal=(TextView)convertView.findViewById(R.id.tv_grandtotal);
		holder.tv_paypalid=(TextView)convertView.findViewById(R.id.tv_paypalid);
		holder.tv_ordername=(TextView)convertView.findViewById(R.id.tv_ordername);
		holder.tv_orderstatus=(TextView)convertView.findViewById(R.id.tv_orderstatus);
		holder.tv_date=(TextView)convertView.findViewById(R.id.tv_date);
		holder.rel=(RelativeLayout)convertView.findViewById(R.id.rel);

	holder.tv_grandtotal.setText(al_orderhistory.get(position).getGrand_total());
		holder.tv_paypalid.setText(al_orderhistory.get(position).getOrder_id());
		holder.tv_ordername.setText(al_orderhistory.get(position).getOrder_type());
		holder.tv_date.setText(al_orderhistory.get(position).getTime());
		holder.tv_orderstatus.setText(al_orderhistory.get(position).getOrder_status());


		holder.rel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				new Json_OrderHistoryDetail(context).execute(Fragment_Menu.access_tokn,al_orderhistory.get(position).getOrder_id());

//				FragmentManager fragmentManager1 = ((Activity) context).getFragmentManager();
//				Fragment_orderhistoryDetail f=new Fragment_orderhistoryDetail();
//					fragmentManager1
//						.beginTransaction()
//						.replace(R.id.activity_main_content_fragment,
//								f).commit();


			}
		});


		return convertView;
		

	}
	
}
class HoldlerOrderHistory
{
RelativeLayout rel;
	TextView tv_grandtotal;
	TextView tv_paypalid;
	TextView tv_ordername;
	TextView tv_orderstatus;
	TextView tv_date;

}