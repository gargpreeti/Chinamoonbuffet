package com.zoptal.chinamoonbuffet.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_DeliveryStatus;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.main.MainActivity;
import com.zoptal.chinamoonbuffet.main.PayPalIntegrationActivity;


public class Fragment_OrderType extends Fragment {

    MainActivity activity1;
    Button btn_addcart;
    TextView tv_delivery,tv_delivery1,tv_pickup1,tv_pickup;
    ImageView img_pickup,img_pickup1,img_delivery,img_delivery1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_ordertype, container, false);
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        activity1.mDrawerToggle.setHomeAsUpIndicator(null);
        activity1.tv_main.setVisibility(View.VISIBLE);
        activity1.tv_main.setText("            ORDER  TYPE");
        activity1.tv_signin.setVisibility(View.GONE);
        activity1.toolbar.setBackgroundColor(Color.WHITE);
        activity1.rel_slider.setVisibility(View.GONE);
       // activity1.tv_signin1.setVisibility(View.VISIBLE);
        activity1.img_user.setVisibility(View.GONE);
      //  activity1.img_user1.setVisibility(View.VISIBLE);

        activity1.img_bck.setVisibility(View.VISIBLE);
        activity1.fab.setVisibility(View.GONE);

        btn_addcart=(Button)view.findViewById(R.id.btn_addcart);
        tv_delivery=(TextView)view.findViewById(R.id.tv_delivery);
        tv_delivery1=(TextView)view.findViewById(R.id.tv_delivery1);
        tv_pickup1=(TextView)view.findViewById(R.id.tv_pickup1);
        tv_pickup=(TextView)view.findViewById(R.id.tv_pickup);

        img_pickup=(ImageView)view.findViewById(R.id.img_pickup);
        img_pickup1=(ImageView)view.findViewById(R.id.img_pickup1);
        img_delivery=(ImageView)view.findViewById(R.id.img_delivery);
        img_delivery1=(ImageView)view.findViewById(R.id.img_delivery1);


        tv_delivery1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_delivery.setVisibility(View.VISIBLE);
                tv_delivery1.setVisibility(View.GONE);
                img_delivery1.setVisibility(View.GONE);
                img_delivery.setVisibility(View.VISIBLE);

                tv_pickup1.setVisibility(View.GONE);
                img_pickup1.setVisibility(View.GONE);
                tv_pickup.setVisibility(View.VISIBLE);
                img_pickup.setVisibility(View.VISIBLE);

                Log.e("clicked on delivery","ok");

             //   new Json_GetDeliveryAddress(getActivity()).execute(Fragment_Menu.access_tokn);
                new Json_DeliveryStatus(getActivity()).execute();
            }
        });


        tv_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_delivery.setVisibility(View.GONE);
                tv_delivery1.setVisibility(View.VISIBLE);
                img_delivery1.setVisibility(View.VISIBLE);
                img_delivery.setVisibility(View.GONE);

                tv_pickup1.setVisibility(View.GONE);
                img_pickup1.setVisibility(View.GONE);
                tv_pickup.setVisibility(View.VISIBLE);
                img_pickup.setVisibility(View.VISIBLE);

            }
        });

        tv_pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_pickup.setVisibility(View.GONE);
                tv_pickup1.setVisibility(View.VISIBLE);
                img_pickup.setVisibility(View.GONE);
                img_pickup1.setVisibility(View.VISIBLE);

                Intent i = new Intent(getActivity(),PayPalIntegrationActivity.class);
                startActivity(i);


                //   new Json_GetDeliveryAddress(getActivity()).execute(Fragment_Menu.access_tokn);

            }
        });

        activity1.img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment=new Fragment_Checkout();
                FragmentManager fragmentManager =getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

            }
        });


//        btn_addcart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Fragment fragment=new Fragment_Checkout();
//                FragmentManager fragmentManager =getFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();
//            }
//        });

//        tv_delivery.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new Json_GetDeliveryAddress(getActivity()).execute(Fragment_Menu.access_tokn);
//
////                Fragment fragment=new Fragment_DeliveryAddress();
////                FragmentManager fragmentManager =getFragmentManager();
////                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();
//            }
//        });



        return view;

    }




}
