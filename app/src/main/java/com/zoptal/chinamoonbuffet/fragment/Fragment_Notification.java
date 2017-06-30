package com.zoptal.chinamoonbuffet.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_NotificationList;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.main.MainActivity;


public class Fragment_Notification extends Fragment {

    MainActivity activity1;
    ListView listView;
  public static   TextView tv_msg;
   // GridView gridView;
   // int img[]={R.drawable.main_screendummy,R.drawable.main_screendummy,R.drawable.main_screendummy,R.drawable.main_screendummy,R.drawable.main_screendummy,R.drawable.main_screendummy,R.drawable.main_screendummy,R.drawable.main_screendummy,R.drawable.main_screendummy,R.drawable.main_screendummy,R.drawable.main_screendummy,R.drawable.main_screendummy};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_notification, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        activity1.mDrawerToggle.setHomeAsUpIndicator(null);

        activity1.fab.setVisibility(View.GONE);


        listView=(ListView)view.findViewById(R.id.listView);
        tv_msg=(TextView)view.findViewById(R.id.tv_msg);

        new Json_NotificationList(getActivity(),listView).execute(Fragment_Menu.access_tokn);

        activity1.img_bck.setVisibility(View.VISIBLE);


        activity1.img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment=new Fragment_Menu();
                FragmentManager fragmentManager =getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();


            }
        });

        return view;

    }

}
