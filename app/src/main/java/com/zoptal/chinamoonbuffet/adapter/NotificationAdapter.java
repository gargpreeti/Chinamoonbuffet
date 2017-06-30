package com.zoptal.chinamoonbuffet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.zoptal.chinamoonbuffet.JsonClasses.NotificationList;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Notification;

import java.util.ArrayList;

public class NotificationAdapter extends BaseAdapter {

	LayoutInflater inflater;
	Context context;
	int img[];

	ArrayList<NotificationList> al_notificationlist;

	public NotificationAdapter(Context context,ArrayList<NotificationList> al_notificationlist) {

		this.context = context;
		this.al_notificationlist=al_notificationlist;
		inflater = LayoutInflater.from(context);

		// TODO Auto-generated constructor stub
	}


	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(al_notificationlist.size()==0){
			Fragment_Notification.tv_msg.setVisibility(View.VISIBLE);
		}
		else{
			Fragment_Notification.tv_msg.setVisibility(View.GONE);
		}
		return al_notificationlist.size();

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

		HolderNotification holder = new HolderNotification();
		convertView = inflater.inflate(R.layout.customview_notification, null);
		holder.tv_name=(TextView) convertView.findViewById(R.id.tv_name);
		holder.tv2=(TextView) convertView.findViewById(R.id.tv2);

		holder.tv_name.setText(al_notificationlist.get(position).getMessage());
		holder.tv2.setText(al_notificationlist.get(position).getDescription());

		return convertView;


	}
}
class HolderNotification{

	TextView tv_name;
	TextView tv2;

}
