package com.zoptal.chinamoonbuffet.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_MyPunchs;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_MyPunchsList;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_Redeem;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.adapter.LoyalityProgramAdapter;
import com.zoptal.chinamoonbuffet.main.MainActivity;


public class Fragment_MyPunches extends Fragment {

    MainActivity activity1;
    ListView listView;
    Button btn_redeem;
    TextView tv_totalvalue,tv_needvalue;
    public static TextView tv_need,tv_total,tv_msg;

   @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_mypunchs, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        activity1.mDrawerToggle.setHomeAsUpIndicator(null);
        activity1.tv_main.setVisibility(View.VISIBLE);
        activity1.tv_main.setText("            MY PUNCHES");
        activity1.tv_signin.setVisibility(View.GONE);
        activity1.toolbar.setBackgroundColor(Color.WHITE);
        activity1.rel_slider.setVisibility(View.GONE);
        activity1.img_user.setVisibility(View.GONE);
        activity1.img_bck.setVisibility(View.VISIBLE);
       activity1.img_cart.setVisibility(View.INVISIBLE);
       activity1.fab.setVisibility(View.GONE);

        btn_redeem=(Button)view.findViewById(R.id.btn_redeem);
        tv_totalvalue=(TextView)view.findViewById(R.id.tv_totalvalue);
        tv_needvalue=(TextView)view.findViewById(R.id.tv_needvalue);
       tv_need=(TextView)view.findViewById(R.id.tv_need);
       tv_total=(TextView)view.findViewById(R.id.tv_total);
       tv_msg=(TextView)view.findViewById(R.id.tv_msg);

       tv_totalvalue.setText(Json_MyPunchs.requiredpunch);
       tv_needvalue.setText(Json_MyPunchs.needpunch);

       if(Json_MyPunchs.btnvalue.equals("Yes")){
           btn_redeem.setVisibility(View.VISIBLE);
       }
       else{
           btn_redeem.setVisibility(View.GONE);

       }

       btn_redeem.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               new Json_Redeem(getActivity()).execute(Fragment_Menu.access_tokn,Json_MyPunchs.code_id);
           }
       });


       activity1.img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager1 = getFragmentManager();
                fragmentManager1.popBackStack();


//                Fragment fragment=new Fragment_Menu();
//                FragmentManager fragmentManager =getFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

            }
        });

       listView=(ListView)view.findViewById(R.id.listView);
      // listView.setAdapter(new MyPunchsAdapter(getActivity()));

           new Json_MyPunchsList(getActivity(),listView).execute(Fragment_Menu.access_tokn, LoyalityProgramAdapter.id);



        return view;

    }




}
