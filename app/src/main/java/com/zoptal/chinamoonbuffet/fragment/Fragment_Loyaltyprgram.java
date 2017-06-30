package com.zoptal.chinamoonbuffet.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.main.MainActivity;


public class Fragment_Loyaltyprgram extends Fragment implements View.OnClickListener {


    MainActivity activity1;
     Button btn_what,btn_how;
    RelativeLayout rel,rel1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_loyalityprgrm, container, false);
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.menu_drawable = ResourcesCompat.getDrawable(getResources(),R.mipmap.menublck, getActivity().getTheme());
        activity1.mDrawerToggle.setHomeAsUpIndicator(activity1.menu_drawable);
        activity1.img_bck.setVisibility(View.GONE);
        activity1.tv_main.setText("Loyalty Program");
        activity1.img_user.setVisibility(View.GONE);
        activity1.img_cart.setVisibility(View.INVISIBLE);
        activity1.tv_signin.setVisibility(View.INVISIBLE);
        activity1.fab.setVisibility(View.GONE);
        activity1.tv_signin.setVisibility(View.GONE);
        activity1.Drawer.closeDrawer(Gravity.LEFT);


        btn_what=(Button)view.findViewById(R.id.btn_what);
        btn_how=(Button)view.findViewById(R.id.btn_how);

        rel=(RelativeLayout)view.findViewById(R.id.rel);
        rel1=(RelativeLayout)view.findViewById(R.id.rel1);

        btn_what.setOnClickListener(this);
        btn_how.setOnClickListener(this);

        rel.setOnClickListener(this);
        rel1.setOnClickListener(this);


        return view;

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.btn_what:

                Fragment fragment2=new Fragment_WhatLoyaltyProgram();
                FragmentManager fragmentManager2 = getFragmentManager();
                fragmentManager2.beginTransaction().replace(R.id.activity_main_content_fragment, fragment2).commit();


                break;

            case R.id.btn_how:
                Fragment fragment21=new Fragment_howwrksLoyaltyProgram();
                FragmentManager fragmentManager21 = getFragmentManager();
                fragmentManager21.beginTransaction().replace(R.id.activity_main_content_fragment, fragment21).commit();



                break;

            case R.id.rel:

                Fragment fragment22=new Fragment_WhatLoyaltyProgram();
                FragmentManager fragmentManager22 = getFragmentManager();
                fragmentManager22.beginTransaction().replace(R.id.activity_main_content_fragment, fragment22).commit();


                break;

            case R.id.rel1:
                Fragment fragment211=new Fragment_howwrksLoyaltyProgram();
                FragmentManager fragmentManager211 = getFragmentManager();
                fragmentManager211.beginTransaction().replace(R.id.activity_main_content_fragment, fragment211).commit();



                break;

      }
    }
}
