package com.zoptal.chinamoonbuffet.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.main.MainActivity;


public class Fragment_Feedback extends Fragment implements View.OnClickListener {


    MainActivity activity1;
   // RelativeLayout rel_account,rel_chngepw,rel_ordrhistory;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_feedback, container, false);
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.menu_drawable = ResourcesCompat.getDrawable(getResources(),R.mipmap.menublck, getActivity().getTheme());
        activity1.mDrawerToggle.setHomeAsUpIndicator(activity1.menu_drawable);
        activity1.tv_signin.setVisibility(View.GONE);
      //  activity1.tv_signin1.setVisibility(View.GONE);
        activity1.img_user.setVisibility(View.GONE);
    //    activity1.img_user1.setVisibility(View.GONE);
        activity1.fab.setVisibility(View.GONE);

        Intent email = new Intent(Intent.ACTION_SEND);
        email.putExtra(Intent.EXTRA_EMAIL, new String[]{"admin@zoptal.com"});
        email.putExtra(Intent.EXTRA_SUBJECT,"Chinamoonbuffet-Feedback");
        email.putExtra(Intent.EXTRA_TEXT, "");
        email.setType("message/rfc822");
        startActivity(Intent.createChooser(email, "Select Email Client"));
//        rel_account=(RelativeLayout)view.findViewById(R.id.rel_account);
//        rel_chngepw=(RelativeLayout)view.findViewById(R.id.rel_chngepw);
//        rel_ordrhistory=(RelativeLayout)view.findViewById(R.id.rel_ordrhistory);
//
//        rel_account.setOnClickListener(this);
//        rel_chngepw.setOnClickListener(this);
//        rel_ordrhistory.setOnClickListener(this);

        return view;

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){

//            case R.id.rel_account:
//
//
//                break;
//
//            case R.id.rel_chngepw:
//
//
//                break;
//            case R.id.rel_ordrhistory:
//
//
//                break;
      }
    }
}
