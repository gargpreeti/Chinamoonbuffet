package com.zoptal.chinamoonbuffet.fragment;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_AddDeliveryAddress;
import com.zoptal.chinamoonbuffet.JsonClasses.Model_DeliveryAddress;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.common.GooglePlacesAutocompleteAdapter;
import com.zoptal.chinamoonbuffet.main.MainActivity;


public class Fragment_DeliveryAddress extends Fragment implements AdapterView.OnItemClickListener {

    MainActivity activity1;
    Button btn_continue;
    EditText ed_name,ed_email,ed_zipcode,ed_phnnum;
    AutoCompleteTextView ed_loc;
    Dialog  dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_deliveryaddress, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        activity1.mDrawerToggle.setHomeAsUpIndicator(null);

        activity1.tv_main.setVisibility(View.VISIBLE);
        activity1.tv_main.setText("        DELIVERY ADDRESS");
        activity1.tv_signin.setVisibility(View.GONE);
        activity1.toolbar.setBackgroundColor(Color.WHITE);
        activity1.rel_slider.setVisibility(View.GONE);
      //  activity1.tv_signin1.setVisibility(View.GONE);
        activity1.img_user.setVisibility(View.GONE);
       // activity1.img_user1.setVisibility(View.GONE);
        activity1.img_bck.setVisibility(View.VISIBLE);
        activity1.fab.setVisibility(View.GONE);

        btn_continue=(Button)view.findViewById(R.id.btn_continue);

        ed_name=(EditText)view.findViewById(R.id.ed_name);
        ed_email=(EditText)view.findViewById(R.id.ed_email);
       // ed_address=(EditText)view.findViewById(R.id.ed_address);
        ed_loc = (AutoCompleteTextView) view.findViewById(R.id.autoCompleteTextView);
        ed_loc.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.list_item));

        ed_zipcode=(EditText)view.findViewById(R.id.ed_zipcode);
        ed_phnnum=(EditText)view.findViewById(R.id.ed_phnnum);


        Bundle bundle = getArguments();
        Model_DeliveryAddress m=(Model_DeliveryAddress)bundle.getSerializable("deliveryaddress");

        ed_name.setText(m.getName());
        ed_email.setText(m.getEmail());
        ed_loc.setText(m.getAddress());
        ed_zipcode.setText(m.getZipcode());
        ed_phnnum.setText(m.getPhone());


        activity1.img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment=new Fragment_OrderType();
                FragmentManager fragmentManager =getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();


            }
        });



        btn_continue.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

       // dialogdeliverymap();

        String name=ed_name.getText().toString().trim();
        String email=ed_email.getText().toString().trim();
        String address=ed_loc.getText().toString().trim();
        String zipcode=ed_zipcode.getText().toString().trim();
        String phn=ed_phnnum.getText().toString().trim();

        new Json_AddDeliveryAddress(getActivity()).execute(Fragment_Menu.access_tokn,name,email,address,zipcode,phn);

             }
});
      return view;

    }




    public void dialogdeliverymap() {

//        dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent) {
//            @Override
//            public boolean onTouchEvent(MotionEvent event) {
//                // Tap anywhere to close dialog.
//                dialog.dismiss();
//                return true;
//            }
//        };
//        √

        dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_deliverymap);


         TextView tv_cncl = (TextView) dialog.findViewById(R.id.tv_cncl);

       tv_cncl.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        dialog.dismiss();

    }
});
        // dialog.dismiss();

        dialog.show();

    }


    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long id) {
        String str = (String) adapterView.getItemAtPosition(position);

    }
}
