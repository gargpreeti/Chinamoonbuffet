package com.zoptal.chinamoonbuffet.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.main.MainActivity;


public class Fragment_Orderdetail extends Fragment {
    MainActivity activity1;
    Button btn_paynow;
      ListView listView,listView1;
   // GridView gridView;

    ViewPager mViewPager;
    //RecommdationAdapter recommdationAdapter;
  //  CheckoutAdapter1 adptr;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_orderdetail, container, false);
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        activity1.mDrawerToggle.setHomeAsUpIndicator(null);

        activity1.tv_main.setVisibility(View.VISIBLE);
        activity1.tv_main.setText("  ORDER DETAILS");
        activity1.tv_signin.setVisibility(View.GONE);
        activity1.toolbar.setBackgroundColor(Color.WHITE);
        activity1.fab.setVisibility(View.GONE);
//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) activity1.toolbar.getLayoutParams();
//        layoutParams.height =200;
//        activity1.toolbar.setLayoutParams(layoutParams);
       // activity1.tv_signin1.setVisibility(View.GONE);
        activity1.img_user.setVisibility(View.GONE);
       // activity1.img_user1.setVisibility(View.GONE);

        activity1.img_bck.setVisibility(View.VISIBLE);

        btn_paynow=(Button)view.findViewById(R.id.btn_paynow);

        listView=(ListView)view.findViewById(R.id.listView);
      //  listView.setAdapter(new CheckoutAdapter(getActivity(),img));



        activity1.img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment=new Fragment_Checkout();
                FragmentManager fragmentManager =getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();


            }
        });

        btn_paynow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment=new Fragment_OrderType();
                FragmentManager fragmentManager =getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

            }
        });

        return view;

    }

}
