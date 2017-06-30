package com.zoptal.chinamoonbuffet.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_Applycouponcode;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_MyCartList;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_Removecoupon;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.adapter.CheckoutAdapter1;
import com.zoptal.chinamoonbuffet.main.MainActivity;


public class Fragment_Checkout extends Fragment {

     MainActivity activity1;
     Button btn_chkout;
     public static EditText ed_coupon;
     public static ListView listView;
     ViewPager mViewPager;
    //RecommdationAdapter recommdationAdapter;
    CheckoutAdapter1 adptr;
    public  static LinearLayout linear;
    public  static TextView tv_msg;
   public static TextView total_value,discountvalue,discountvaluetotal;
    public static ImageView img_chk,img_crs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_checkout, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            activity1 = (MainActivity) getActivity();
            activity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
            activity1.mDrawerToggle.setHomeAsUpIndicator(null);

            activity1.tv_main.setVisibility(View.VISIBLE);
            activity1.tv_main.setText("                   My Cart");
            activity1.tv_signin.setVisibility(View.GONE);
            activity1.toolbar.setBackgroundColor(Color.WHITE);
            activity1.rel_slider.setVisibility(View.GONE);

//        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) activity1.toolbar.getLayoutParams();
//        layoutParams.height =200;
//        activity1.toolbar.setLayoutParams(layoutParams);
        //activity1.tv_signin1.setVisibility(View.GONE);
        activity1.img_user.setVisibility(View.GONE);
      //  activity1.img_user1.setVisibility(View.GONE);
        activity1.fab.setVisibility(View.GONE);

        activity1.img_bck.setVisibility(View.VISIBLE);

        btn_chkout=(Button)view.findViewById(R.id.btn_chkout);

        listView=(ListView)view.findViewById(R.id.listView);
        ed_coupon=(EditText)view.findViewById(R.id.ed_coupon);
      //  listView.setAdapter(new CheckoutAdapter(getActivity(),img));

         tv_msg=(TextView)view.findViewById(R.id.tv_msg);
        linear=(LinearLayout)view.findViewById(R.id.linear);

        total_value=(TextView)view.findViewById(R.id.total_value);
        discountvalue=(TextView)view.findViewById(R.id.discountvalue);
        discountvaluetotal=(TextView) view.findViewById(R.id.discountvaluetotal);
        img_chk=(ImageView)view.findViewById(R.id.img_chk);
        img_crs=(ImageView)view.findViewById(R.id.img_crs);


        new Json_MyCartList(getActivity(),listView).execute(Fragment_Menu.access_tokn);

//        if(Json_MyCartList.al_mycart.size()==0)
//        {
//            tv_msg.setVisibility(View.VISIBLE);
//            listView.setVisibility(View.GONE);
//            linear.setVisibility(View.GONE);
//        }
        adptr=new CheckoutAdapter1(getActivity());

        //listView1=(ListView)view.findViewById(R.id.listView1);
//        TwoWayView lvTest = (TwoWayView)view.findViewById(R.id.lvItems);
//        lvTest.setAdapter(adptr);

       // listView1.setAdapter(adptr);
      //  mViewPager = (ViewPager)view.findViewById(R.id.pager);
        //recommdationAdapter = new RecommdationAdapter(getActivity());

       // mViewPager.setAdapter(recommdationAdapter);
//
        activity1.img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment=new Fragment_Menu();
                FragmentManager fragmentManager =getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

            }
        });

        btn_chkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Fragment fragment=new Fragment_Orderdetail();
//                FragmentManager fragmentManager =getFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

                Fragment fragment=new Fragment_OrderType();
                FragmentManager fragmentManager =getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

            }
        });

        img_chk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codevalue=ed_coupon.getText().toString().trim();

                new Json_Applycouponcode(getActivity()).execute(Fragment_Menu.access_tokn,Json_MyCartList.uniq,codevalue,total_value.getText().toString());

            }
        });

        ed_coupon.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    String codevalue=ed_coupon.getText().toString().trim();

                new Json_Applycouponcode(getActivity()).execute(Fragment_Menu.access_tokn,Json_MyCartList.uniq,codevalue,total_value.getText().toString());

                    return true;
                }
                return false;
            }

        });

        img_crs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Json_Removecoupon(getActivity()).execute(Fragment_Menu.access_tokn,Json_MyCartList.uniq);


            }
        });

        return view;

    }




}
