package com.zoptal.chinamoonbuffet.fragment;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.main.MainActivity;


public class Fragment_Facebook extends Fragment {


    MainActivity activity1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_facebook, container, false);
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.menu_drawable = ResourcesCompat.getDrawable(getResources(),R.mipmap.menublck, getActivity().getTheme());
        activity1.mDrawerToggle.setHomeAsUpIndicator(activity1.menu_drawable);
        activity1.img_bck.setVisibility(View.GONE);
        activity1.tv_main.setText("   Facebook");
        activity1.tv_signin.setTextColor(Color.parseColor("#000000"));
        activity1.img_user.setBackgroundDrawable(getResources().getDrawable(R.mipmap.user_iconblck));
        activity1.img_cart.setBackgroundDrawable(getResources().getDrawable(R.drawable.cartblck));
        activity1.img_cart.setVisibility(View.INVISIBLE);


        WebView webView= (WebView)view.findViewById(R.id.webView1);
        webView.setWebViewClient(new MyWebViewClient());

//        Log.e("facebook link---",""+Json_Weblink.f.getFacebook());
        webView.loadUrl("https://www.facebook.com/China-Moon-Restaurant-508085472645664/");

        return view;

    }
   // http://www.gaantori.com/pages/PrivacyPolicy

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl("https://www.facebook.com/China-Moon-Restaurant-508085472645664/");
            return true;
        }
    }

}