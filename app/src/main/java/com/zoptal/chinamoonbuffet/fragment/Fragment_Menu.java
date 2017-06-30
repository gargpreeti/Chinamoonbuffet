package com.zoptal.chinamoonbuffet.fragment;

import android.Manifest;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_Category;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_Del_St;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_MenuData;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_MenuData_Srch;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_RestaurentContact;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.adapter.CustomListAdapter;
import com.zoptal.chinamoonbuffet.main.MainActivity;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;


public class Fragment_Menu extends Fragment implements View.OnTouchListener {

    MainActivity activity1;
    GridView gridView;
    String MyPREFERENCES = "MyPrefs1";
    SharedPreferences sharedpreferences1;
    public static String access_tokn="";
    ImageView img_filter,img_srch;
    private int _xDelta;
    private int _yDelta;
    ViewGroup _root;
    Boolean flag=false;
    int pagenum=1;
    CustomListAdapter adapter_customlist;
    public static FloatingActionButton fab1;
    EditText ed_srch;


    float dX;
    float dY;
    int lastAction;
    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        final View view = inflater.inflate(R.layout.activity_home, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        activity1.img_bck.setVisibility(View.GONE);
        activity1.menu_drawable = ResourcesCompat.getDrawable(getResources(),R.mipmap.menu, getActivity().getTheme());
        activity1.mDrawerToggle.setHomeAsUpIndicator(activity1.menu_drawable);
        activity1.tv_main.setVisibility(View.GONE);
        activity1.rel_slider.setVisibility(View.VISIBLE);
        activity1.toolbar.setBackgroundColor(Color.TRANSPARENT);
        activity1.img_cart.setBackgroundDrawable(getResources().getDrawable(R.drawable.cart));
        activity1.img_cart.setVisibility(View.VISIBLE);
        activity1.tv_signin.setTextColor(Color.parseColor("#ffffff"));
        activity1.img_user.setBackgroundDrawable(getResources().getDrawable(R.mipmap.signin_icon));
        sharedpreferences1 = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        activity1.linear_b1.setBackgroundColor(Color.parseColor("#FE0000"));
        activity1.linear_b2.setBackgroundColor(Color.parseColor("#711508"));
        activity1.linear_b3.setBackgroundColor(Color.parseColor("#711508"));
        activity1.linear_b4.setBackgroundColor(Color.parseColor("#711508"));
        activity1.linear_b5.setBackgroundColor(Color.parseColor("#711508"));


        final View dragView = view.findViewById(R.id.draggable_view);
        dragView.setOnTouchListener(this);

        gridView=(GridView)view.findViewById(R.id.gridView);
        img_filter=(ImageView)view.findViewById(R.id.img_filter);
        img_srch=(ImageView)view.findViewById(R.id.img_srch);
        ed_srch=(EditText)view.findViewById(R.id.ed_srch);


        String userid = sharedpreferences1.getString("id", "");
        String name = sharedpreferences1.getString("name", "");
        access_tokn= sharedpreferences1.getString("access_token", "");
        String img= sharedpreferences1.getString("image","");

        Log.e("userid menu---",""+userid);
        Log.e("name menu---",""+name);
        Log.e("access_tokn menu---",""+access_tokn);
        new Json_Del_St(getActivity());

        if(img.isEmpty()){

            activity1.circleView_image.setVisibility(View.GONE);

        }else {
            activity1.circleView_image.setVisibility(View.VISIBLE);
            Picasso.with(getActivity()).load(img).into(activity1.circleView_image);
        }
        activity1.tv_username.setText(name);

        if(access_tokn.isEmpty()){
            activity1.rel_notification.setVisibility(View.GONE);
        }
else{
            activity1.rel_notification.setVisibility(View.VISIBLE);
        }

        fab1 = (FloatingActionButton)view.findViewById(R.id.fab);

//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//             Log.e("fab button---","ok");
//            }
//        });
        fab1.setVisibility(View.GONE);


        if(name.isEmpty()){
           // fab1.setVisibility(View.VISIBLE)
            activity1.fab.setVisibility(View.GONE);
            activity1.tv_signin.setVisibility(View.VISIBLE);
            activity1.img_user.setVisibility(View.VISIBLE);
        }
        else{
            activity1.tv_signin.setVisibility(View.GONE);
            activity1.img_user.setVisibility(View.GONE);
            activity1.fab.setVisibility(View.GONE);
         //  fab1.setVisibility(View.GONE);
        }

 // fab1.setOnTouchListener(this);

     //   MultiTouchListener touchListener=new MultiTouchListener();
       // fab1.setOnTouchListener(touchListener);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

        _root = (ViewGroup)view.findViewById(R.id.root);


        Log.e("ed_srch",""+ed_srch.getText());

          final Spinner dropdown = (Spinner)view.findViewById(R.id.spinner);

//        List categorylist = new ArrayList(new LinkedHashSet(Json_Category.categorylist));
//        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),R.layout.dropdownitem,categorylist);
//        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        dropdown.setAdapter(dataAdapter);



        Log.e("category list item---",""+Json_Category.categorylist);
        dropdown.setVisibility(View.VISIBLE);
        List categorylist = new ArrayList(new LinkedHashSet(Json_Category.categorylist));
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),R.layout.dropdownitem,categorylist);
        dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        dropdown.setAdapter(dataAdapter);

        img_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dropdown.setVisibility(View.VISIBLE);
                List categorylist = new ArrayList(new LinkedHashSet(Json_Category.categorylist));
                ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(),R.layout.dropdownitem,categorylist);
                dataAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                dropdown.setAdapter(dataAdapter);

              //  ((TextView) v).setGravity(Gravity.RIGHT);
                Log.e("filter ed_srch",""+ed_srch.getText().toString());

            }
        });

        dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setGravity(Gravity.RIGHT);
                String item = parent.getItemAtPosition(position).toString();

              //  ((TextView) view).setGravity(Gravity.RIGHT);
                Log.e("drop down item---",""+item);
                Log.e("drop down id---",""+Json_Category.al_category.get(dropdown.getSelectedItemPosition()).getId());

                String catid=Json_Category.al_category.get(dropdown.getSelectedItemPosition()).getId();

               adapter_customlist= new CustomListAdapter(getActivity(),Json_MenuData.model_menudata);
              gridView.setAdapter(adapter_customlist);

                new Json_MenuData_Srch(getActivity(),gridView).execute(catid,ed_srch.getText().toString(),"1","40");

                if(item.toString().trim().equals("All")){
                    Log.e("all items---",""+item.toString().trim());
                    new Json_MenuData_Srch(getActivity(),gridView).execute("All",ed_srch.getText().toString(),"1","40");
                }

                Log.e("ed_srch",""+ed_srch.getText().toString());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ed_srch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                 //   adapter_customlist= new CustomListAdapter(getActivity(),Json_MenuData.model_menudata);
                 //   gridView.setAdapter(adapter_customlist);

                //    new Json_MenuData_Srch(getActivity(),gridView).execute("All",ed_srch.getText().toString(),"1","40");




                   // new Json_MenuData(getActivity(),adapter_customlist).execute("All",ed_srch.getText().toString(),"1","10");

                 if(dropdown.getSelectedItem()!=null){
                     String catid=Json_Category.al_category.get(dropdown.getSelectedItemPosition()).getId();

                     Log.e("drop dwon selected itemmmmmm",""+dropdown.getSelectedItem().toString());
                    // adapter_customlist= new CustomListAdapter(getActivity(),Json_MenuData.model_menudata);
                   //  gridView.setAdapter(adapter_customlist);


                     if(dropdown.getSelectedItem().toString().trim().equals("All")){
                         Log.e("in if===","test");

                         new Json_MenuData_Srch(getActivity(),gridView).execute("All",ed_srch.getText().toString(),"1","40");
                     }
                     else{
                         Log.e("inelse===","test");
                         new Json_MenuData_Srch(getActivity(),gridView).execute(catid,ed_srch.getText().toString(),"1","40");


                     }
                    // new Json_MenuData(getActivity(),adapter_customlist).execute(catid,ed_srch.getText().toString(),"1","10");

                 }
                    else{

                  //   adapter_customlist= new CustomListAdapter(getActivity(),Json_MenuData.model_menudata);
                  //   gridView.setAdapter(adapter_customlist);

                   //  new Json_MenuData(getActivity(),adapter_customlist).execute("All",ed_srch.getText().toString(),"1","10");

                     new Json_MenuData_Srch(getActivity(),gridView).execute("All",ed_srch.getText().toString(),"1","40");

                 }

                        return true;
                }
                return false;
            }

        });

        img_srch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  new Json_MenuData_Srch(getActivity(),gridView).execute("All",ed_srch.getText().toString(),"1","10");

             if(dropdown.getSelectedItem()!=null){
                    String catid=Json_Category.al_category.get(dropdown.getSelectedItemPosition()).getId();

                    Log.e("drop dwon selected itemmmmmm",""+dropdown.getSelectedItem().toString());
                   new Json_MenuData_Srch(getActivity(),gridView).execute(catid,ed_srch.getText().toString(),"1","40");

                }
                else{


                    new Json_MenuData_Srch(getActivity(),gridView).execute("All",ed_srch.getText().toString(),"1","40");


                }

            }
        });

        adapter_customlist= new CustomListAdapter(getActivity(),Json_MenuData.model_menudata);
        gridView.setAdapter(adapter_customlist);

        new Json_MenuData(getActivity(),adapter_customlist).execute("All","","1","30");


        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
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

                    new Json_MenuData(getActivity(),adapter_customlist).execute("All","",newpage,"30");

                }
            }
        });

        return view;

    }

//    @Override
//    public boolean onTouch(View view, MotionEvent event) {
//        final int X = (int) event.getRawX();
//        final int Y = (int) event.getRawY();
//        switch (event.getAction() & MotionEvent.ACTION_MASK) {
//            case MotionEvent.ACTION_DOWN:
//                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
//                _xDelta = X - lParams.leftMargin;
//                _yDelta = Y - lParams.topMargin;
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//            case MotionEvent.ACTION_POINTER_DOWN:
//                break;
//            case MotionEvent.ACTION_POINTER_UP:
//                break;
//            case MotionEvent.ACTION_MOVE:
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
//                layoutParams.leftMargin = X - _xDelta;
//                layoutParams.topMargin = Y - _yDelta;
//                layoutParams.rightMargin = -250;
//                layoutParams.bottomMargin = -250;
//                view.setLayoutParams(layoutParams);
//                break;
//        }
//        _root.invalidate();
//        return true;
//    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;
                break;

            case MotionEvent.ACTION_MOVE:
                view.setY(event.getRawY() + dY);
                view.setX(event.getRawX() + dX);
                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN)


                    alrt();



                    //Toast.makeText(getActivity(), "Clicked!", Toast.LENGTH_SHORT).show();
                break;

            default:
                return false;
        }
        return true;
    }



    public void alrt(){


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(Json_RestaurentContact.f.getPhone());

        alertDialogBuilder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + Json_RestaurentContact.f.getPhone()));
                if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(callIntent);

            }
        });

        alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }
}
