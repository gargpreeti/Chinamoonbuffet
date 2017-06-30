package com.zoptal.chinamoonbuffet.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_LoyaltyProgramList;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.adapter.LoyalityProgramAdapter;
import com.zoptal.chinamoonbuffet.main.MainActivity;


public class Fragment_LoyalityProgram extends Fragment {
    MainActivity activity1;
    ListView listView;
   LoyalityProgramAdapter adapter_loyaltyprogram;
    Boolean flag=false;
    int pagenum=1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_loyalityprogram, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.menu_drawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.menublck, getActivity().getTheme());
        activity1.mDrawerToggle.setHomeAsUpIndicator(activity1.menu_drawable);
        activity1.Drawer.closeDrawer(Gravity.LEFT);
        activity1.img_bck.setVisibility(View.GONE);
        activity1.tv_signin.setTextColor(Color.parseColor("#000000"));
        activity1.tv_main.setText("Loyalty Program");
        activity1.img_user.setBackgroundDrawable(getResources().getDrawable(R.mipmap.user_iconblck));
        activity1.img_cart.setBackgroundDrawable(getResources().getDrawable(R.drawable.cartblck));
        activity1.img_cart.setVisibility(View.VISIBLE);





        if(Fragment_Menu.access_tokn.isEmpty()){
            activity1.fab.setVisibility(View.GONE);
            activity1.tv_signin.setVisibility(View.VISIBLE);
            activity1.img_user.setVisibility(View.VISIBLE);
        }
        else{
            activity1.tv_signin.setVisibility(View.GONE);
            activity1.img_user.setVisibility(View.GONE);
            activity1.fab.setVisibility(View.GONE);

        }


        listView=(ListView)view.findViewById(R.id.listView);
        adapter_loyaltyprogram= new LoyalityProgramAdapter(getActivity(), Json_LoyaltyProgramList.model_loyaltyProgram);
        listView.setAdapter(adapter_loyaltyprogram);

        new Json_LoyaltyProgramList(getActivity(),adapter_loyaltyprogram).execute("10","1");

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                if (scrollState == 2)
                    flag = true;

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {


                // TODO Auto-generated method stub
                if ((visibleItemCount == (totalItemCount - firstVisibleItem))
                        && flag) {
                    flag = false;

                    pagenum++;
                    String newpage= String.valueOf(pagenum);

                    Log.e("pagenum------",""+pagenum);

                    new Json_LoyaltyProgramList(getActivity(),adapter_loyaltyprogram).execute("10",newpage);

                }
            }
        });


        return view;

    }

}
