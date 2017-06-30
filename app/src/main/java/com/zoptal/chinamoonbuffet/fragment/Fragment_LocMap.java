package com.zoptal.chinamoonbuffet.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_RestaurentContact;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.main.MainActivity;


public class Fragment_LocMap extends Fragment implements OnMapReadyCallback {

    MainActivity activity1;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.fragment__locate_us, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        activity1.mDrawerToggle.setHomeAsUpIndicator(null);
        activity1.tv_signin.setTextColor(Color.parseColor("#000000"));
        activity1.toolbar.setBackgroundColor(Color.WHITE);
        activity1.rel_slider.setVisibility(View.GONE);
        activity1.img_bck.setVisibility(View.VISIBLE);
        activity1.tv_main.setText("              Location");

        activity1.img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment=new Fragment_ContactUs();
                FragmentManager fragmentManager =getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

            }
        });


        initMap();

        return view;

    }

    private void initMap() {

        MapFragment mapFragment = (MapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap map) {
        LatLng latLng = new LatLng(Double.parseDouble(Json_RestaurentContact.f.getLatitude()),Double.parseDouble(Json_RestaurentContact.f.getLongitude()));
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        map.addMarker(new MarkerOptions().position(latLng).title("Restaurent"));
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

    }
}