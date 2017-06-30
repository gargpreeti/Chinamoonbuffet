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
import android.widget.TextView;

import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.main.MainActivity;
import com.zoptal.chinamoonbuffet.url.RegisterUrl;


public class Fragment_Aboutus extends Fragment {


    MainActivity activity1;
       public  static TextView tv_desc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_aboutus, container, false);
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.menu_drawable = ResourcesCompat.getDrawable(getResources(),R.mipmap.menublck, getActivity().getTheme());
        activity1.mDrawerToggle.setHomeAsUpIndicator(activity1.menu_drawable);
        activity1.tv_signin.setVisibility(View.GONE);
        activity1.tv_main.setText("   ABOUT US");
        activity1.img_user.setVisibility(View.GONE);
        activity1.fab.setVisibility(View.GONE);
        activity1.img_cart.setVisibility(View.INVISIBLE);

      //  tv_desc=(TextView)view.findViewById(R.id.tv_desc);

    //    new Json_Aboutus(getActivity()).execute();

        WebView webView= (WebView)view.findViewById(R.id.webView1);
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(RegisterUrl.aboutus);

        return view;

    }

    private class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(RegisterUrl.aboutus);
            return true;
        }
    }


}
