package com.zoptal.chinamoonbuffet.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ListView;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_Tablelist;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.main.MainActivity;


public class Fragment_ReserveTable extends Fragment {

    MainActivity activity1;
    ListView listView;

    Boolean flag=false;
    int pagenum=1;
    //DatePicker

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_reservetable, container, false);
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.tv_main.setVisibility(View.VISIBLE);
        activity1.tv_main.setText("RESERVE TABLE");
        activity1.img_cart.setVisibility(View.VISIBLE);
        activity1.tv_signin.setTextColor(Color.parseColor("#000000"));

        activity1.img_user.setBackgroundDrawable(getResources().getDrawable(R.mipmap.user_iconblck));
        activity1.img_cart.setBackgroundDrawable(getResources().getDrawable(R.drawable.cartblck));




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
     //   listView.setAdapter(new ReserveTableAdapter(getActivity(),img));

        new Json_Tablelist(getActivity(),listView).execute("10","1");



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

                    new Json_Tablelist(getActivity(),listView).execute("10",newpage);

                    Log.e("Scroll=======", "Ended");
                }
            }
        });


        activity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        activity1.img_bck.setVisibility(View.GONE);


        // activity1.mDrawerToggle.setHomeAsUpIndicator(activity1.menu_drawable);

    //    activity1.tv_signin1.setVisibility(View.GONE);
      //  activity1.tv_signin.setVisibility(View.VISIBLE);
        //activity1.img_user1.setVisibility(View.GONE);
     //   activity1.img_user.setVisibility(View.VISIBLE);
        activity1.toolbar.setBackgroundColor(Color.TRANSPARENT);
        activity1.tv_signin.setTextColor(Color.parseColor("#000000"));
        activity1.img_user.setBackgroundDrawable(getResources().getDrawable(R.mipmap.user_iconblck));
        activity1.img_cart.setBackgroundDrawable(getResources().getDrawable(R.drawable.cartblck));

        activity1.menu_drawable = ResourcesCompat.getDrawable(getResources(),R.mipmap.menublck, getActivity().getTheme());
        activity1.mDrawerToggle.setHomeAsUpIndicator(activity1.menu_drawable);

      //  dateFormatter = new SimpleDateFormat("yyyy-MM-dd" , Locale.US);
        //setDateTimeField();




        return view;

    }


}
