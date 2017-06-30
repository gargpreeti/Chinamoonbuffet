package com.zoptal.chinamoonbuffet.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_AddtoCart;
import com.zoptal.chinamoonbuffet.JsonClasses.MenuData;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.main.MainActivity;


public class Fragment_ItemDetail extends Fragment {

    MainActivity activity1;
    Button btn_addcart;

    ImageView rel_price;
    TextView tv_price,tv_name,tv_name1,tv_desc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_itemdetail, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        activity1.mDrawerToggle.setHomeAsUpIndicator(null);

        activity1.tv_main.setVisibility(View.VISIBLE);
        activity1.tv_main.setText("            ITEM DETAILS");
      //  activity1.tv_signin.setVisibility(View.GONE);
        activity1.tv_signin.setTextColor(Color.parseColor("#000000"));
        activity1.toolbar.setBackgroundColor(Color.WHITE);
        activity1.rel_slider.setVisibility(View.GONE);
        activity1.fab.setVisibility(View.GONE);
       // activity1.tv_signin1.setVisibility(View.GONE);
       // activity1.img_user.setVisibility(View.GONE);
       // activity1.img_user1.setVisibility(View.GONE);

        activity1.img_bck.setVisibility(View.VISIBLE);
        btn_addcart=(Button)view.findViewById(R.id.btn_addcart);
        activity1.img_cart.setBackgroundDrawable(getResources().getDrawable(R.drawable.cartblck));
        activity1.img_user.setBackgroundDrawable(getResources().getDrawable(R.mipmap.user_iconblck));

        tv_price=(TextView)view.findViewById(R.id.tv_price);
        tv_name=(TextView)view.findViewById(R.id.tv_name);
        tv_name1=(TextView)view.findViewById(R.id.tv_name1);
        tv_desc=(TextView)view.findViewById(R.id.tv_desc);
        rel_price=(ImageView)view.findViewById(R.id.rel_price);

        Bundle bundle = getArguments();
        final MenuData m=(MenuData)bundle.getSerializable("itemdetail");


        Log.e("price---",""+m.getPrice());
        tv_name.setText(m.getTitle());
        tv_name1.setText("("+m.getCategoryname()+")");
        tv_desc.setText(m.getDescription());
        tv_price.setText("$"+m.getPrice());

      Picasso.with(getActivity()).load(m.getImage()).resize(1700,700).into(rel_price);


//BitmapDrawable(obj) convert Bitmap object into drawable object.

//        Picasso.with(getActivity())
//                .load("http://looksok.files.wordpress.com/2011/12/me.jpg")
//                .into(rel_price);


        activity1.img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment=new Fragment_Menu();
                FragmentManager fragmentManager =getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

          }
        });


        btn_addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Fragment_Menu.access_tokn.isEmpty()){
                    alertmsglogin();

//                    android.app.AlertDialog.Builder builder1 = new android.app.AlertDialog.Builder(getActivity());
//                    //  builder1.setTitle(Html.fromHtml("<font color='#000000'>" +"Pl"));
//                    builder1.setMessage((Html.fromHtml("<font color='#000000'>" + "Please Login to add item")));
//                    builder1.setCancelable(false);
//
//                    builder1.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int which) {
////                                Toast.makeText(MainActivity1.this, "You exit from app",
////                                        Toast.LENGTH_LONG).show();
//                            dialog.dismiss();
//
//                        }
//                    });
//
//                    android.app.AlertDialog alert = builder1.create();
//                    alert.show();
//                    Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
//                    pbutton.setBackgroundColor(Color.WHITE);
//                    pbutton.setTextColor(Color.BLACK);
                }
                else {

                    new Json_AddtoCart(getActivity()).execute(Fragment_Menu.access_tokn,m.getId(),"1");

                }


            }
        });

        return view;

    }
    public void alertmsglogin(){


        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_msg);

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        TextView tv_msg=(TextView)dialog.findViewById(R.id.tv_msg);

        tv_msg.setText("Please Sign in with your user account to Add Item in cart!");

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


        // dialog.dismiss();



        dialog.show();

    }



}
