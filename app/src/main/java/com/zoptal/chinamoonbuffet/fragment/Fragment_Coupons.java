package com.zoptal.chinamoonbuffet.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_CouponsList;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_Earncoupn1;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.adapter.CouponsAdapter;
import com.zoptal.chinamoonbuffet.adapter.EarnCouponAdapter;
import com.zoptal.chinamoonbuffet.common.GPSTracker;
import com.zoptal.chinamoonbuffet.main.MainActivity;


public class Fragment_Coupons extends Fragment {

    MainActivity activity1;
    ListView listView;
    public static ListView listView1;
    CouponsAdapter adapter_coupon;
    EarnCouponAdapter earnCouponAdapter;
    Boolean flag = false;
    int pagenum = 1;
    Button btn_dailyoffer, btn_earncoupon;
    GPSTracker gps;
    double latitude, longitude;
    Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_coupons, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.tv_signin.setTextColor(Color.parseColor("#000000"));
        activity1.img_user.setBackgroundDrawable(getResources().getDrawable(R.mipmap.user_iconblck));
        activity1.img_cart.setBackgroundDrawable(getResources().getDrawable(R.drawable.cartblck));


        if (Fragment_Menu.access_tokn.isEmpty()) {
            activity1.fab.setVisibility(View.GONE);
            activity1.tv_signin.setVisibility(View.VISIBLE);
            activity1.img_user.setVisibility(View.VISIBLE);
        }
        else {
            activity1.tv_signin.setVisibility(View.GONE);
            activity1.img_user.setVisibility(View.GONE);
            activity1.fab.setVisibility(View.GONE);

        }

        mContext = getActivity();
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            // Toast.makeText(mContext,"You need have granted permission",Toast.LENGTH_SHORT).show();
            gps = new GPSTracker(mContext, getActivity());

            // Check if GPS enabled
            if (gps.canGetLocation()) {

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

                Log.e("lat---",""+latitude);
                Log.e("long---",""+longitude);

                // \n is for new line
                // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gps.showSettingsAlert();
            }
        }


        listView = (ListView) view.findViewById(R.id.listView);
        listView1 = (ListView) view.findViewById(R.id.listView1);

        btn_dailyoffer = (Button) view.findViewById(R.id.btn_dailyoffer);
        btn_earncoupon = (Button) view.findViewById(R.id.btn_earncoupon);

        btn_dailyoffer.setBackgroundColor(Color.parseColor("#FE0000"));
        btn_earncoupon.setBackgroundColor(Color.parseColor("#711508"));

//        adapter_coupon= new CouponsAdapter(getActivity(), Json_CouponsList.model_couponsData);
//        listView.setAdapter(adapter_coupon);


        new Json_CouponsList(getActivity(), listView).execute("20", "1", Fragment_Menu.access_tokn, String.valueOf(latitude), String.valueOf(longitude));

        btn_dailyoffer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                listView1.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                btn_dailyoffer.setBackgroundColor(Color.parseColor("#FE0000"));
                btn_earncoupon.setBackgroundColor(Color.parseColor("#711508"));

//                adapter_coupon= new CouponsAdapter(getActivity(), Json_CouponsList.model_couponsData);
//                listView.setAdapter(adapter_coupon);


                new Json_CouponsList(getActivity(), listView).execute("20", "1", Fragment_Menu.access_tokn, String.valueOf(latitude), String.valueOf(longitude));


            }
        });


        btn_earncoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btn_earncoupon.setBackgroundColor(Color.parseColor("#FE0000"));
                btn_dailyoffer.setBackgroundColor(Color.parseColor("#711508"));

                if (Fragment_Menu.access_tokn.isEmpty()) {
                } else {

                    listView.setVisibility(View.GONE);
                    listView1.setVisibility(View.VISIBLE);

//                    earnCouponAdapter= new EarnCouponAdapter(getActivity(),Json_Earncoupn.model_earnCoupon);
//                    listView1.setAdapter(earnCouponAdapter);

                    new Json_Earncoupn1(getActivity(), listView1).execute(Fragment_Menu.access_tokn, "1", "20");


                }

            }
        });

//        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//                if (scrollState == 2)
//                    flag = true;
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//
//                // TODO Auto-generated method stub
//                if ((visibleItemCount == (totalItemCount - firstVisibleItem))
//                        && flag) {
//                    flag = false;
//                    pagenum++;
//                    String newpage= String.valueOf(pagenum);
//
//                    Log.e("pagenum------",""+pagenum);
//
//                    new Json_CouponsList(getActivity(),listView).execute("2",newpage,Fragment_Menu.access_tokn,String.valueOf(latitude),String.valueOf(longitude));
//
//                }
//            }
//        });


        return view;

    }

}
