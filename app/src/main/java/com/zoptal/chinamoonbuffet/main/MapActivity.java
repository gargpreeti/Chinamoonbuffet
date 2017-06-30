package com.zoptal.chinamoonbuffet.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_DeliveryDistance;
import com.zoptal.chinamoonbuffet.R;


public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    Button btn_continue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_deliverymap);


        TextView tv_cncl = (TextView)findViewById(R.id.tv_cncl);
        TextView tv_address = (TextView)findViewById(R.id.tv_address);
        TextView tv_name = (TextView)findViewById(R.id.tv_name);
        ImageView user_img=(ImageView)findViewById(R.id.user_img);
        TextView tv_distancevalue = (TextView)findViewById(R.id.tv_distancevalue);
        TextView tv_chrge = (TextView)findViewById(R.id.tv_chrge);
      btn_continue = (Button)findViewById(R.id.btn_continue);

       tv_address.setText(Json_DeliveryDistance.deliveryDistance.getAddress());
        tv_name.setText(Json_DeliveryDistance.deliveryDistance.getName());
      tv_distancevalue.setText(Json_DeliveryDistance.deliveryDistance.getDistance());
       tv_chrge.setText(Json_DeliveryDistance.deliveryDistance.getDelivery_fee());

      if(Json_DeliveryDistance.deliveryDistance.getImage().isEmpty()){

      }
        else {
          Picasso.with(MapActivity.this).load(Json_DeliveryDistance.deliveryDistance.getImage()).into(user_img);
      }
        tv_cncl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();

            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(MapActivity.this,PayPalIntegrationActivity1.class);
                startActivity(i);
                finish();

            }
        });

        initMap();
    }

    private void initMap() {

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap map) {

        LatLng latLng = new LatLng(Double.parseDouble(Json_DeliveryDistance.deliveryDistance.getHotel_latitude()),Double.parseDouble(Json_DeliveryDistance.deliveryDistance.getHotel_longitude()));
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        map.addMarker(new MarkerOptions().position(latLng).title("Hotel"));
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

        LatLng latLng1 = new LatLng(Double.parseDouble(Json_DeliveryDistance.deliveryDistance.getUserlat()),Double.parseDouble(Json_DeliveryDistance.deliveryDistance.getUserlong()));
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng1));
        map.addMarker(new MarkerOptions().position(latLng).title("User"));
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);

    }
}
