package com.zoptal.chinamoonbuffet.fragment;

import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_RestaurentContact;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.main.MainActivity;


public class Fragment_ContactUs extends Fragment implements View.OnClickListener {


    MainActivity activity1;
    TextView tv_address,tv_num,tv_email;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_contactus, container, false);
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.menu_drawable = ResourcesCompat.getDrawable(getResources(),R.mipmap.menublck, getActivity().getTheme());
        activity1.mDrawerToggle.setHomeAsUpIndicator(activity1.menu_drawable);
        activity1.img_bck.setVisibility(View.GONE);
        activity1.tv_main.setText("  Contact Us");
        activity1.img_user.setVisibility(View.GONE);
        activity1.img_cart.setVisibility(View.INVISIBLE);
        activity1.tv_signin.setVisibility(View.INVISIBLE);
        activity1.fab.setVisibility(View.GONE);
        activity1.tv_signin.setVisibility(View.GONE);




        tv_address=(TextView)view.findViewById(R.id.tv_address);
        tv_num=(TextView)view.findViewById(R.id.tv_num);
        tv_email=(TextView)view.findViewById(R.id.tv_emailid);

        tv_num.setText(Json_RestaurentContact.f.getPhone());
        tv_address.setText(Json_RestaurentContact.f.getAddress());
        tv_email.setText(Json_RestaurentContact.f.getEmail());

        tv_address.setOnClickListener(this);
        tv_num.setOnClickListener(this);
        tv_email.setOnClickListener(this);


        return view;

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.tv_address:

                         Fragment_LocMap fragment5=new Fragment_LocMap();
                         FragmentManager fragmentManager5 =getFragmentManager();
                        fragmentManager5.beginTransaction().replace(R.id.activity_main_content_fragment, fragment5).commit();

                break;

            case R.id.tv_num:

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
                alertDialogBuilder.setMessage(tv_num.getText().toString().trim());


                alertDialogBuilder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + tv_num.getText().toString().trim()));
                        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
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

                break;

        case R.id.tv_emailid:

            Intent email = new Intent(Intent.ACTION_SEND);
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{tv_email.getText().toString()});
            email.putExtra(Intent.EXTRA_SUBJECT,"Chinamoonbuffet-Contact us");
            email.putExtra(Intent.EXTRA_TEXT, "");
            email.setType("message/rfc822");
            startActivity(Intent.createChooser(email, "Select Email Client"));
            Log.e("email clicked--",""+tv_email.getText().toString().trim());
//            Intent emailIntent = new Intent(Intent.ACTION_SEND);
//            //emailIntent.setType("text/plain");
//            emailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{tv_email.getText().toString()});
//            emailIntent.putExtra(Intent.EXTRA_SUBJECT,"Chinamoonbuffet-Contact us");
//            emailIntent.putExtra(Intent.EXTRA_TEXT, "");
//            emailIntent.setType("message/rfc822");
//            getActivity().startActivity(emailIntent);

            break;
      }
    }
}
