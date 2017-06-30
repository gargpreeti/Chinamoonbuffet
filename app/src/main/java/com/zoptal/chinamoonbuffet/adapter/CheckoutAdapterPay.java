package com.zoptal.chinamoonbuffet.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_DeleteItem;
import com.zoptal.chinamoonbuffet.JsonClasses.Model_MyCart;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Menu;

import java.util.ArrayList;

public class CheckoutAdapterPay extends BaseAdapter {

	LayoutInflater inflater;
	Context context ;
	ArrayList<Model_MyCart> al_mycartlist;

	public CheckoutAdapterPay(Context context, ArrayList<Model_MyCart> al_mycartlist) {
		this.context=context;
	    this.al_mycartlist=al_mycartlist;
		inflater=LayoutInflater.from(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
      Log.e("cart size--",""+al_mycartlist.size());

//			if(al_mycartlist.size()==0){
//
//			Fragment_Checkout.tv_msg.setVisibility(View.VISIBLE);
//			Fragment_Checkout.listView.setVisibility(View.GONE);
//			Fragment_Checkout.linear.setVisibility(View.GONE);
//		}
//		else if(al_mycartlist.size()>0){
//			Fragment_Checkout.listView.setVisibility(View.VISIBLE);
//			Fragment_Checkout.linear.setVisibility(View.VISIBLE);
//			Fragment_Checkout.tv_msg.setVisibility(View.GONE);
//		}

			return al_mycartlist.size();

		
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

		final HoldlerCheckout holder=new HoldlerCheckout();

		convertView=inflater.inflate(R.layout.customview_checkout,parent,false);

//		 holder.spinner = (Spinner)convertView.findViewById(R.id.spinner);
//		String[] years = {"1","2","3","4","5","6","7"};
//		ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(context, R.layout.spinner_text, years );
//		langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//		holder.spinner.setAdapter(langAdapter);

		holder.img=(ImageView) convertView.findViewById(R.id.img);
		holder.img_cross=(ImageView) convertView.findViewById(R.id.img_cross);
		holder.tv_name=(TextView)convertView.findViewById(R.id.tv_name);
		holder.tv2=(TextView)convertView.findViewById(R.id.tv2);
		holder.tv_price=(TextView)convertView.findViewById(R.id.tv_price);

		holder.tv_name.setText(al_mycartlist.get(position).getTitle());

		holder.tv2.setText(al_mycartlist.get(position).getCategoryname());
		Log.e("holder.tv2",""+holder.tv2.getText());
		holder.tv_price.setText("$"+" "+al_mycartlist.get(position).getTotal_amt());

		Picasso.with(context).load(al_mycartlist.get(position).getImage()).into(holder.img);

		//al_mycartlist.get(position).getQty()

		final String selectvalue=al_mycartlist.get(position).getQty();
		final String productid=al_mycartlist.get(position).getProduct_id();
		final  String cardid=al_mycartlist.get(position).getId();

      	String ITEMS[]={al_mycartlist.get(position).getQty(),"  1","  2","  3","  4","  5","  6","  7","  8","  9","  10"};

		//final Spinner spinner = (Spinner)convertView.findViewById(R.id.spinner);

		holder.spinner= (Spinner)convertView.findViewById(R.id.spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				context, R.layout.view_spinner_item, ITEMS
		);

		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		holder.spinner.setAdapter(adapter);

		holder.img_cross.setVisibility(View.GONE);
		holder.spinner.setEnabled(false);

		double total=0;

		for (int i = 0; i <al_mycartlist.size(); i++) {

			Double pri= Double.parseDouble(al_mycartlist.get(position).getTotal_amt());
		//	Double qty= Double.valueOf(m.getAl_cart().get(i).getItem_qty());
		//	val_qty= String.valueOf(m.getAl_cart().get(i).getItem_qty());
			total+=(pri);
			Log.e("total price-----",""+total);

		}
       //   Log.e("after total---",""+total);
	//	PayPalIntegrationActivity.total_chrges.setText(String.valueOf(total));



		//spinner.setSelection(Integer.parseInt(al_mycartlist.get(position).getQty()));

//		holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//		@Override
//		public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//			int pos=position;
//	     Log.e("Spinner value item selected----",""+holder.spinner.getSelectedItem().toString());
//
//			String qtyvalue=holder.spinner.getSelectedItem().toString();
//			String oldvalue=holder.spinner.getSelectedItem().toString().trim();
//
//				if(!oldvalue.equals(selectvalue.trim())){
//					Log.e("new value---",""+holder.spinner.getSelectedItem().toString());
//
//			new Json_UpdateItem(context).execute(Fragment_Menu.access_tokn,productid,holder.spinner.getSelectedItem().toString(),cardid);
//
//			}
//
//			Log.e("qty value---",""+qtyvalue);
//			//new Json_UpdateItem(context).execute(Fragment_Menu.access_tokn,al_mycartlist.get(position).getProduct_id(),qtyvalue);
//		}
//		@Override
//		public void onNothingSelected(AdapterView<?> parent) {
//
//		}
//	});


	  holder.img_cross.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {

			new Json_DeleteItem(context).execute(Fragment_Menu.access_tokn,al_mycartlist.get(position).getId());

		}
		 });

		return convertView;

	}


}
