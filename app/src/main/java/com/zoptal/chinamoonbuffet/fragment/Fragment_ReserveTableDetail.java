package com.zoptal.chinamoonbuffet.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zoptal.chinamoonbuffet.JsonClasses.Model_TableList;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.main.MainActivity;


public class Fragment_ReserveTableDetail extends Fragment {

    MainActivity activity1;
    Button btn_reserve,btn_reserved;
     ImageView rel_img;
    TextView tv_name,desc;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_reservetabledetail, container, false);
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        activity1.mDrawerToggle.setHomeAsUpIndicator(null);

        activity1.tv_main.setVisibility(View.VISIBLE);
        activity1.tv_main.setText("    RESERVE TABLE DETAILS ");
     //   activity1.tv_signin.setVisibility(View.GONE);
        activity1.toolbar.setBackgroundColor(Color.WHITE);
        activity1.rel_slider.setVisibility(View.GONE);
        activity1.fab.setVisibility(View.GONE);
       // activity1.tv_signin1.setVisibility(View.VISIBLE);

       // activity1.img_user1.setVisibility(View.VISIBLE);

        activity1.img_bck.setVisibility(View.VISIBLE);

        btn_reserve=(Button)view.findViewById(R.id.btn_reserve);
        btn_reserved=(Button)view.findViewById(R.id.btn_reserved);
        rel_img=(ImageView)view.findViewById(R.id.rel_img);
        tv_name=(TextView)view.findViewById(R.id.tv_name);
        desc=(TextView)view.findViewById(R.id.desc);


        Bundle bundle = getArguments();
        final Model_TableList m=(Model_TableList)bundle.getSerializable("tablelist");

         tv_name.setText(m.getTitle());
         desc.setText(m.getDescription());

       Picasso.with(getActivity()).load(m.getImage()).resize(1200,500).into(rel_img);
        btn_reserve.setVisibility(View.VISIBLE);
//        if(m.getBooked().equals("0")){
//            btn_reserve.setVisibility(View.VISIBLE);
//            btn_reserved.setVisibility(View.GONE);
//        }
//        else {
//            btn_reserve.setVisibility(View.GONE);
//            btn_reserved.setVisibility(View.VISIBLE);
//
//        }

        activity1.img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment=new Fragment_ReserveTable();
                FragmentManager fragmentManager =getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();


            }
        });

     btn_reserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // tableid=al_tablelist.get(position).getId();

                if(Fragment_Menu.access_tokn.isEmpty()){

                    alertmsglogin();


                }
                else {
                    FragmentManager fragmentManager1 =getFragmentManager();
                    Fragment_RequestDetail requestdetail = new Fragment_RequestDetail();
                    fragmentManager1.beginTransaction().replace(R.id.activity_main_content_fragment, requestdetail).commit();
                }

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







        return view;

    }


    public void alertmsglogin(){


        final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_msg);

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        TextView tv_msg=(TextView)dialog.findViewById(R.id.tv_msg);

        tv_msg.setText("Please signin to Reserve A Table!");

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
