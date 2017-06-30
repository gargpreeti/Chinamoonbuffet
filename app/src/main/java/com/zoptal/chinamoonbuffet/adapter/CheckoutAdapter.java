package com.zoptal.chinamoonbuffet.adapter;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_DeleteItem;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_UpdateItem;
import com.zoptal.chinamoonbuffet.JsonClasses.Model_MyCart;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Checkout;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Menu;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

public class CheckoutAdapter extends BaseAdapter {

	LayoutInflater inflater;
	Context context;
	ArrayList<Model_MyCart> al_mycartlist;

	public CheckoutAdapter(Context context, ArrayList<Model_MyCart> al_mycartlist) {
		this.context = context;
		this.al_mycartlist = al_mycartlist;
		inflater = LayoutInflater.from(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub


		if (al_mycartlist.size() == 0) {

			Fragment_Checkout.tv_msg.setVisibility(View.VISIBLE);
			Fragment_Checkout.listView.setVisibility(View.GONE);
			Fragment_Checkout.linear.setVisibility(View.GONE);

		}
		else if (al_mycartlist.size() > 0) {
			Fragment_Checkout.listView.setVisibility(View.VISIBLE);
			Fragment_Checkout.linear.setVisibility(View.VISIBLE);
			Fragment_Checkout.tv_msg.setVisibility(View.GONE);
		}

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

		final HoldlerCheckout holder = new HoldlerCheckout();

		convertView = inflater.inflate(R.layout.customview_checkout, parent, false);

//		 holder.spinner = (Spinner)convertView.findViewById(R.id.spinner);
//		String[] years = {"1","2","3","4","5","6","7"};
//		ArrayAdapter<CharSequence> langAdapter = new ArrayAdapter<CharSequence>(context, R.layout.spinner_text, years );
//		langAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
//		holder.spinner.setAdapter(langAdapter);

		holder.img = (ImageView) convertView.findViewById(R.id.img);
		holder.img_cross = (ImageView) convertView.findViewById(R.id.img_cross);
		holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
		holder.tv2 = (TextView) convertView.findViewById(R.id.tv2);
		holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);

		holder.tv_name.setText(al_mycartlist.get(position).getTitle());

		holder.tv2.setText(al_mycartlist.get(position).getCategoryname());
		holder.tv_price.setText("$" + " " + al_mycartlist.get(position).getTotal_amt());


		if(al_mycartlist.get(position).getImage().isEmpty()){

		}
		else {
			Picasso.with(context).load(al_mycartlist.get(position).getImage()).into(holder.img);
		}
		//al_mycartlist.get(position).getQty()

		final String selectvalue = al_mycartlist.get(position).getQty();
		final String productid = al_mycartlist.get(position).getProduct_id();
		final String cardid = al_mycartlist.get(position).getId();

		//String ITEMS[] = { , "  1","  2","  3","  4","  5","  6","  7","  8","  9","  10","  11","  12","  13","  14","  15","  16", "  17", "  18", "  19", "  20", "  21", "  22", "  23", "  24", "  25", "  26", "  27", "  28", "  29", "  30"};


		ArrayList<String> listcat = new ArrayList<String>();
		listcat.add(al_mycartlist.get(position).getQty());
		for(int i=1;i<=50;i++){

			listcat.add(  String.valueOf(i)  );
		}

		List catlist = new ArrayList(new LinkedHashSet(listcat));
//
//		List<String> species = Arrays.asList(ITEMS);
//		//List<String> uniqueWords = (List<String>) new HashSet<String>(Arrays.asList(ITEMS));
//		// List<String> dlist = new ArrayList<>(new TreeSet<>(Arrays.asList(ITEMS)));
//
//		List userlist = new ArrayList(new LinkedHashSet(species));
		holder.spinner = (Spinner) convertView.findViewById(R.id.spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
		context, R.layout.view_spinner_item, catlist
		);
		adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item1);
		holder.spinner.setAdapter(adapter);
		//spinner.setSelection(Integer.parseInt(al_mycartlist.get(position).getQty()));

		holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

				int pos = position;

				String qtyvalue = holder.spinner.getSelectedItem().toString();
				String oldvalue = holder.spinner.getSelectedItem().toString().trim();

				if (!oldvalue.equals(selectvalue.trim())) {

					new Json_UpdateItem(context).execute(Fragment_Menu.access_tokn, productid, holder.spinner.getSelectedItem().toString(), cardid);

				}


				//new Json_UpdateItem(context).execute(Fragment_Menu.access_tokn,al_mycartlist.get(position).getProduct_id(),qtyvalue);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});


		holder.img_cross.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {


				final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setCancelable(true);
				dialog.setContentView(R.layout.dialog_alert);

				Button btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
				Button btn_no = (Button) dialog.findViewById(R.id.btn_no);
				TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);
				tv_msg.setText("Are you sure want to Delete this Item?");


				btn_yes.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						new Json_DeleteItem(context).execute(Fragment_Menu.access_tokn, al_mycartlist.get(position).getId());
						dialog.dismiss();

					}
				});
				btn_no.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						dialog.dismiss();
					}
				});

				// dialog.dismiss();

				dialog.show();


			}
		});

		return convertView;

	}
}
	class HoldlerCheckout {

		ImageView img;
		ImageView img_cross;
		TextView tv_name;
		TextView tv2;
		TextView tv_price;
		Spinner spinner;
		TextView tv_qtyvalue;
	}

