package com.zoptal.chinamoonbuffet.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_Weblink;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.main.MainActivity;


public class Fragment_Yellowpages extends Fragment {

    MainActivity activity1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_yelp, container, false);
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.menu_drawable = ResourcesCompat.getDrawable(getResources(),R.mipmap.menublck, getActivity().getTheme());
        activity1.mDrawerToggle.setHomeAsUpIndicator(activity1.menu_drawable);
        activity1.tv_signin.setVisibility(View.GONE);
        activity1.tv_main.setText("   Yellow Pages");
        activity1.img_user.setVisibility(View.GONE);
        activity1.fab.setVisibility(View.GONE);
        activity1.img_cart.setVisibility(View.INVISIBLE);

        WebView webView= (WebView)view.findViewById(R.id.webView1);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(Json_Weblink.yellow_pages);

        return view;

    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(Json_Weblink.yellow_pages);
            return true;
        }
    }

}