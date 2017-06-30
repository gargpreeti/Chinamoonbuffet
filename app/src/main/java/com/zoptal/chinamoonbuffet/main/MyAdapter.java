package com.zoptal.chinamoonbuffet.main;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_MyPunchsside;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_ProfileData;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_RestaurentContact;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_Weblink;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Aboutus;
import com.zoptal.chinamoonbuffet.fragment.Fragment_ContactUs;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Loyaltyprgram;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Menu;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    // IF the view under inflation and population is header or Item
    private static final int TYPE_ITEM = 1;
    private String mNavTitles[]; // String Array to store the passed titles Value from MainActivity.java
    private int mIcons[];       // Int Array to store the passed icons resource value from MainActivity.java
    Context context;
    MainActivity activity1;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView;
        LinearLayout root;

        public ViewHolder(final View itemView, int ViewType) {
            // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.rowText); // Creating TextView object with the id of textView from item_row.xml
            imageView = (ImageView) itemView.findViewById(R.id.rowIcon);// Creating ImageView object with the id of ImageView from item_row.xml
            root = (LinearLayout) itemView.findViewById(R.id.root);
            // setting holder id as 1 as the object being populated are of type item row

        }
    }


    public MyAdapter(Context context, String Titles[], int Icons[]) { // MyAdapter Constructor with titles and icons parameter
        // titles, icons, name, email, profile pic are passed from the main activity as we
        mNavTitles = Titles;                //have seen earlier
        mIcons = Icons;
        this.context = context;
        activity1=(MainActivity) context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row, parent, false);
        ViewHolder vhItem = new ViewHolder(v, viewType);
        return vhItem;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // as the list view is going to be called after the header view so we decrement the
        // position by 1 and pass it to the holder while setting the text and image
        holder.textView.setText(mNavTitles[position]); // Setting the Text with the array of our Titles
        holder.imageView.setImageResource(mIcons[position]);// Settimg the image with array of our icons



        holder.root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, "The Item Clicked is: " + "1", Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 0:
//                        Fragment fragment=new Fragment_listenlive();
//                        FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
//                        fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();
//                        MainActivity1.Drawer.closeDrawer(Gravity.LEFT);
//                        MainActivity1.b1_linear.setBackgroundColor(Color.parseColor("#CE3630"));
//                        MainActivity1.b2_linear.setBackgroundColor(Color.parseColor("#E04740"));
//                        MainActivity1.b3_linear.setBackgroundColor(Color.parseColor("#E04740"));
//                        MainActivity1.b4_linear.setBackgroundColor(Color.parseColor("#E04740"));

                        activity1.linear_b1.setBackgroundColor(Color.parseColor("#FE0000"));
                        activity1.linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b4.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b5.setBackgroundColor(Color.parseColor("#711508"));

                        activity1.tv_main.setVisibility(View.GONE);
                        activity1. toolbar.setBackgroundColor(Color.TRANSPARENT);

                        //activity1.tv_signin1.setVisibility(View.GONE);
                        activity1.tv_signin.setVisibility(View.VISIBLE);
                        //activity1.img_user1.setVisibility(View.GONE);
                        activity1.img_user.setVisibility(View.VISIBLE);
                        Log.e("menu clicked","clicked");

                        Fragment fragment1=new Fragment_Menu();
                        FragmentManager fragmentManager =((Activity)context).getFragmentManager();
                        fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment1).commit();
                        activity1.Drawer.closeDrawer(Gravity.LEFT);

                        break;
                    case 1:
                        activity1.linear_b1.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b4.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b5.setBackgroundColor(Color.parseColor("#711508"));



                        if(Fragment_Menu.access_tokn.isEmpty()){

                            alertmsg();

                        }
                        else {
                            activity1.img_bck.setVisibility(View.GONE);
                            activity1.tv_main.setVisibility(View.VISIBLE);
                            activity1.tv_main.setText("MY PROFILE");
                            activity1.tv_signin.setVisibility(View.GONE);
                            activity1.img_user.setVisibility(View.GONE);
                            activity1.toolbar.setBackgroundColor(Color.WHITE);
                            activity1.rel_slider.setVisibility(View.GONE);
                            activity1.Drawer.closeDrawer(Gravity.LEFT);
                            new Json_ProfileData(context).execute(Fragment_Menu.access_tokn);
                        }

                        break;

                    case 2:
                        activity1.linear_b1.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b4.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b5.setBackgroundColor(Color.parseColor("#711508"));



                        if(Fragment_Menu.access_tokn.isEmpty()){

                            final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setCancelable(true);
                            dialog.setContentView(R.layout.dialog_msg);

                            Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
                            TextView tv_msg=(TextView)dialog.findViewById(R.id.tv_msg);
                            tv_msg.setText(" Please sign in to see My Punches!");

                            btn_ok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    dialog.dismiss();

                                }
                            });
                            // dialog.dismiss();

                            dialog.show();

                        }
                        else {
                            activity1.img_bck.setVisibility(View.GONE);
                            activity1.tv_main.setVisibility(View.VISIBLE);
                            activity1.tv_signin.setVisibility(View.GONE);
                            activity1.img_user.setVisibility(View.GONE);
                            activity1.toolbar.setBackgroundColor(Color.WHITE);
                            activity1.rel_slider.setVisibility(View.GONE);


                            new Json_MyPunchsside(context).execute(Fragment_Menu.access_tokn,"");
                        }

                        break;

                    case 3:
//                        Fragment fragment=new Fragment_listenlive();
//                        FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
//                        fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();
//                        MainActivity1.Drawer.closeDrawer(Gravity.LEFT);
//                        MainActivity1.b1_linear.setBackgroundColor(Color.parseColor("#CE3630"));
//                        MainActivity1.b2_linear.setBackgroundColor(Color.parseColor("#E04740"));
//                        MainActivity1.b3_linear.setBackgroundColor(Color.parseColor("#E04740"));
//                        MainActivity1.b4_linear.setBackgroundColor(Color.parseColor("#E04740"));

                        activity1.linear_b1.setBackgroundColor(Color.parseColor("#FE0000"));
                        activity1.linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b4.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b5.setBackgroundColor(Color.parseColor("#711508"));

                        activity1.img_bck.setVisibility(View.GONE);
                        activity1.tv_main.setVisibility(View.VISIBLE);
                        activity1.tv_signin.setVisibility(View.GONE);
                        activity1.img_user.setVisibility(View.GONE);
                        activity1.toolbar.setBackgroundColor(Color.WHITE);
                        activity1.rel_slider.setVisibility(View.GONE);

                        Fragment fragment3=new Fragment_Loyaltyprgram();
                        FragmentManager fragmentManager3 =((Activity)context).getFragmentManager();
                        fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment3).commit();
                        activity1.Drawer.closeDrawer(Gravity.LEFT);

                        break;
                    case 4:
                        activity1.linear_b1.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b4.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b5.setBackgroundColor(Color.parseColor("#711508"));

                        activity1.img_bck.setVisibility(View.GONE);
                        activity1.rel_slider.setVisibility(View.GONE);
                        //activity1.tv_signin1.setVisibility(View.GONE);
                       // activity1.img_user1.setVisibility(View.GONE);
                        activity1.tv_signin.setVisibility(View.GONE);
                        activity1.img_user.setVisibility(View.GONE);
                        activity1.tv_main.setVisibility(View.VISIBLE);
                        activity1.tv_main.setText("ABOUT US");
                        activity1.toolbar.setBackgroundColor(Color.WHITE);
                        Fragment fragment2=new Fragment_Aboutus();
                        FragmentManager fragmentManager2 = ((Activity)context).getFragmentManager();
                        fragmentManager2.beginTransaction().replace(R.id.activity_main_content_fragment, fragment2).commit();
                        activity1.Drawer.closeDrawer(Gravity.LEFT);

                        break;


//                    case 3:
//                        activity1.linear_b1.setBackgroundColor(Color.parseColor("#711508"));
//                        activity1.linear_b2.setBackgroundColor(Color.parseColor("#711508"));
//                        activity1.linear_b3.setBackgroundColor(Color.parseColor("#711508"));
//                        activity1.linear_b4.setBackgroundColor(Color.parseColor("#711508"));
//                        activity1.linear_b5.setBackgroundColor(Color.parseColor("#711508"));
//
//                        activity1.img_bck.setVisibility(View.GONE);
//                        activity1.rel_slider.setVisibility(View.GONE);
//                        activity1.tv_signin1.setVisibility(View.GONE);
//                        activity1.img_user1.setVisibility(View.GONE);
//                        activity1.tv_signin.setVisibility(View.GONE);
//                        activity1.img_user.setVisibility(View.GONE);
//                        activity1.tv_main.setVisibility(View.VISIBLE);
//                        activity1.tv_main.setText("PRIVACY POLICY");
//                        activity1.toolbar.setBackgroundColor(Color.WHITE);
//                        Fragment fragment3=new Fragment_PrivacyPolicy();
//                        FragmentManager fragmentManager3 = ((Activity)context).getFragmentManager();
//                        fragmentManager3.beginTransaction().replace(R.id.activity_main_content_fragment, fragment3).commit();
//                        activity1.Drawer.closeDrawer(Gravity.LEFT);
//
//                        break;
                    case 5:
                        activity1.linear_b1.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b4.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b5.setBackgroundColor(Color.parseColor("#711508"));

                        activity1.img_bck.setVisibility(View.GONE);
                        activity1.rel_slider.setVisibility(View.GONE);
                       // activity1.tv_signin1.setVisibility(View.GONE);
                      // activity1.img_user1.setVisibility(View.GONE);
                        activity1.tv_signin.setVisibility(View.GONE);
                        activity1.img_user.setVisibility(View.GONE);
                        activity1.tv_main.setVisibility(View.VISIBLE);
                        activity1.tv_main.setText("Contact Us");
                        activity1.toolbar.setBackgroundColor(Color.WHITE);
                        Fragment_ContactUs fragment4=new Fragment_ContactUs();
                       FragmentManager fragmentManager4 = ((Activity)context).getFragmentManager();
                        fragmentManager4.beginTransaction().replace(R.id.activity_main_content_fragment, fragment4).commit();
                        activity1.Drawer.closeDrawer(Gravity.LEFT);

                        break;
//                    case 5:
//                        activity1.linear_b1.setBackgroundColor(Color.parseColor("#711508"));
//                        activity1.linear_b2.setBackgroundColor(Color.parseColor("#711508"));
//                        activity1.linear_b3.setBackgroundColor(Color.parseColor("#711508"));
//                        activity1.linear_b4.setBackgroundColor(Color.parseColor("#711508"));
//                        activity1.linear_b5.setBackgroundColor(Color.parseColor("#711508"));
//
//                        activity1.img_bck.setVisibility(View.GONE);
//                        activity1.rel_slider.setVisibility(View.GONE);
//                        activity1.tv_signin1.setVisibility(View.GONE);
//                        activity1.img_user1.setVisibility(View.GONE);
//                        activity1.tv_signin.setVisibility(View.GONE);
//                        activity1.img_user.setVisibility(View.GONE);
//                        activity1.tv_main.setVisibility(View.VISIBLE);
//                        activity1.tv_main.setText("Terms & Conditions");
//                        activity1.toolbar.setBackgroundColor(Color.WHITE);
//                        Fragment fragment5=new Fragment_TermsConditions();
//                        FragmentManager fragmentManager5 = ((Activity)context).getFragmentManager();
//                        fragmentManager5.beginTransaction().replace(R.id.activity_main_content_fragment, fragment5).commit();
//                        activity1.Drawer.closeDrawer(Gravity.LEFT);
//
//                        break;

//                    case 6:
//                        activity1.linear_b1.setBackgroundColor(Color.parseColor("#711508"));
//                        activity1.linear_b2.setBackgroundColor(Color.parseColor("#711508"));
//                        activity1.linear_b3.setBackgroundColor(Color.parseColor("#711508"));
//                        activity1.linear_b4.setBackgroundColor(Color.parseColor("#711508"));
//                        activity1.linear_b5.setBackgroundColor(Color.parseColor("#711508"));
//
//                        activity1.img_bck.setVisibility(View.GONE);
//                        activity1.rel_slider.setVisibility(View.GONE);
//                        activity1.tv_signin1.setVisibility(View.GONE);
//                        activity1.img_user1.setVisibility(View.GONE);
//                        activity1.tv_signin.setVisibility(View.GONE);
//                        activity1.img_user.setVisibility(View.GONE);
//                        activity1.tv_main.setVisibility(View.VISIBLE);
//                        activity1.tv_main.setText("      FAQ   ");
//                        activity1.toolbar.setBackgroundColor(Color.WHITE);
//                        Fragment fragment6=new Fragment_FAQ();
//                        FragmentManager fragmentManager6 = ((Activity)context).getFragmentManager();
//                        fragmentManager6.beginTransaction().replace(R.id.activity_main_content_fragment, fragment6).commit();
//                        activity1.Drawer.closeDrawer(Gravity.LEFT);
//
//                        break;

                    case 6:
                        activity1.linear_b1.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b4.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b5.setBackgroundColor(Color.parseColor("#711508"));

                        Intent email = new Intent(Intent.ACTION_SEND);
                        email.putExtra(Intent.EXTRA_EMAIL, new String[]{Json_RestaurentContact.f.getEmail()});
                        email.putExtra(Intent.EXTRA_SUBJECT,"Feedback");
                        email.putExtra(Intent.EXTRA_TEXT, "");
                        email.setType("message/rfc822");
                        context.startActivity(Intent.createChooser(email, "Select Email Client"));


                        break;

                    case 7:

                        activity1.linear_b1.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b4.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b5.setBackgroundColor(Color.parseColor("#711508"));

//                        activity1.img_bck.setVisibility(View.GONE);
//                        activity1.rel_slider.setVisibility(View.GONE);
//                        // activity1.tv_signin1.setVisibility(View.GONE);
//                        // activity1.img_user1.setVisibility(View.GONE);
//                        activity1.tv_signin.setVisibility(View.GONE);
//                        activity1.img_user.setVisibility(View.GONE);
//                        activity1.tv_main.setVisibility(View.VISIBLE);
//                        activity1.tv_main.setText("");
//                        activity1.toolbar.setBackgroundColor(Color.WHITE);


                       // getOpenFacebookIntent(context);

//                        Fragment_Facebook fragment5=new Fragment_Facebook();
//                        FragmentManager fragmentManager5 = ((Activity)context).getFragmentManager();
//                        fragmentManager5.beginTransaction().replace(R.id.activity_main_content_fragment, fragment5).commit();
//                        activity1.Drawer.closeDrawer(Gravity.LEFT);

                        Intent internetIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(Json_Weblink.facebook));
                        internetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(internetIntent);
                      //  activity1.Drawer.closeDrawer(Gravity.LEFT);

                        break;

                    case 8:

                        activity1.linear_b1.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b4.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b5.setBackgroundColor(Color.parseColor("#711508"));

//                        activity1.img_bck.setVisibility(View.GONE);
//                        activity1.rel_slider.setVisibility(View.GONE);
//                        // activity1.tv_signin1.setVisibility(View.GONE);
//                        // activity1.img_user1.setVisibility(View.GONE);
//                        activity1.tv_signin.setVisibility(View.GONE);
//                        activity1.img_user.setVisibility(View.GONE);
//                        activity1.tv_main.setVisibility(View.VISIBLE);
//                        activity1.tv_main.setText("");
//                        activity1.toolbar.setBackgroundColor(Color.WHITE);

                        Intent internetIntent1 = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(Json_Weblink.yelp));
                        internetIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(internetIntent1);

//                        Fragment_Yelp fragment6=new Fragment_Yelp();
//                        FragmentManager fragmentManager6 = ((Activity)context).getFragmentManager();
//                        fragmentManager6.beginTransaction().replace(R.id.activity_main_content_fragment, fragment6).commit();
//                        activity1.Drawer.closeDrawer(Gravity.LEFT);



//                        Intent internetIntent1 = new Intent(Intent.ACTION_VIEW,
//                                Uri.parse("https:\\/\\/www.yelp.com\\/"));
//                        internetIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(internetIntent1);


                                         break;

                    case 9:

                        activity1.linear_b1.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b4.setBackgroundColor(Color.parseColor("#711508"));
                        activity1.linear_b5.setBackgroundColor(Color.parseColor("#711508"));

//                        activity1.img_bck.setVisibility(View.GONE);
//                        activity1.rel_slider.setVisibility(View.GONE);
//                        // activity1.tv_signin1.setVisibility(View.GONE);
//                        // activity1.img_user1.setVisibility(View.GONE);
//                        activity1.tv_signin.setVisibility(View.GONE);
//                        activity1.img_user.setVisibility(View.GONE);
//                        activity1.tv_main.setVisibility(View.VISIBLE);
//                        activity1.tv_main.setText("");
//                        activity1.toolbar.setBackgroundColor(Color.WHITE);

//                        Fragment_Yellowpages fragment7=new Fragment_Yellowpages();
//                        FragmentManager fragmentManager7 = ((Activity)context).getFragmentManager();
//                        fragmentManager7.beginTransaction().replace(R.id.activity_main_content_fragment, fragment7).commit();
//                        activity1.Drawer.closeDrawer(Gravity.LEFT);


                        Intent internetIntent2 = new Intent(Intent.ACTION_VIEW,
                                Uri.parse(Json_Weblink.yellow_pages));
                        internetIntent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(internetIntent2);

//                        Intent internetIntent2 = new Intent(Intent.ACTION_VIEW,
//                                Uri.parse("  http:\\/\\/www.yellowpages.com\\/"));
//                        internetIntent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        context.startActivity(internetIntent2);

                        break;

//                    default:
//                        Fragment fragment11=new Fragment_listenlive();
//                        FragmentManager fragmentManager11 = ((Activity)context).getFragmentManager();
//                        fragmentManager11.beginTransaction().replace(R.id.activity_main_content_fragment, fragment11).commit();
//                        MainActivity1.Drawer.closeDrawer(Gravity.LEFT);
//                        break;
                }
            }
        });

        Fragment fragment=new Fragment_Menu();
        FragmentManager fragmentManager =((Activity)context).getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

    }

    @Override
    public int getItemCount() {
        return mNavTitles.length; // the number of items in the list will be +1 the titles including the header view.
    }

    // Witht the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        return TYPE_ITEM;
    }



    public void alertmsg(){


        final Dialog dialog = new Dialog(context, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_msg);

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        TextView tv_msg=(TextView)dialog.findViewById(R.id.tv_msg);
        tv_msg.setText(" Please sign in to see User Profile!");

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


        // dialog.dismiss();

        dialog.show();

    }


    public static Intent getOpenFacebookIntent(Context context)
    {
        try
        {
            context.getPackageManager().getPackageInfo("com.facebook.katana",0);
            return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/<id_here>"));
        }catch (Exception e)
        {
            return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/China-Moon-Restaurant-508085472645664/"));
        }
    }








}

