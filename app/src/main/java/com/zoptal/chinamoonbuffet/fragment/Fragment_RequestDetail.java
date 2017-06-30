package com.zoptal.chinamoonbuffet.fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_RequestTable;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.adapter.ReserveTableAdapter;
import com.zoptal.chinamoonbuffet.main.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Fragment_RequestDetail extends Fragment {

    MainActivity activity1;
    Button btn_continue;
    EditText ed_name,ed_email,ed_phone,ed_numofpeoplle,ed_desc;
    RelativeLayout rel_cal,rel_time;
    TextView tv_date,tv_time;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    public String selecteddate,selectedtime;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_requestdetails, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.mDrawerToggle.setDrawerIndicatorEnabled(false);
        activity1.mDrawerToggle.setHomeAsUpIndicator(null);

        activity1.tv_main.setVisibility(View.VISIBLE);
        activity1.tv_main.setText("RESERVE REQUEST DETAILS");
        activity1.tv_signin.setVisibility(View.GONE);
        activity1.toolbar.setBackgroundColor(Color.WHITE);
        activity1.rel_slider.setVisibility(View.GONE);
        activity1.img_user.setVisibility(View.GONE);
        activity1.img_bck.setVisibility(View.VISIBLE);
        activity1.img_cart.setVisibility(View.GONE);
        activity1.fab.setVisibility(View.GONE);

        btn_continue=(Button)view.findViewById(R.id.btn_continue);

        ed_name=(EditText)view.findViewById(R.id.ed_name);
        ed_email=(EditText)view.findViewById(R.id.ed_email);
        ed_phone=(EditText)view.findViewById(R.id.ed_phone);
        ed_numofpeoplle=(EditText)view.findViewById(R.id.ed_numofpeoplle);
        ed_desc=(EditText)view.findViewById(R.id.ed_desc);



        ed_phone.addTextChangedListener(new TextWatcher() {

            int length_before = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                length_before = s.length();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (length_before < s.length()) {
                    if (s.length() == 3 || s.length() == 7)
                        s.append("-");
                    if (s.length() > 3) {
                        if (Character.isDigit(s.charAt(3)))
                            s.insert(3, "-");
                    }
                    if (s.length() > 7) {
                        if (Character.isDigit(s.charAt(7)))
                            s.insert(7, "-");
                    }
                }
            }
        });
       dateFormatter = new SimpleDateFormat("yyyy-MM-dd" , Locale.US);
         setDateTimeField();



        activity1.img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Fragment fragment=new Fragment_ReserveTable();
                FragmentManager fragmentManager =getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();


            }
        });
        rel_cal=(RelativeLayout)view.findViewById(R.id.rel_date);
        rel_time=(RelativeLayout)view.findViewById(R.id.rel_time);
        tv_time=(TextView)view.findViewById(R.id.tv_time);
        tv_date=(TextView)view.findViewById(R.id.tv_date);

         rel_cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fromDatePickerDialog.show();

            }
        });

        rel_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;



          mTimePicker = new TimePickerDialog(getActivity(),  AlertDialog.THEME_DEVICE_DEFAULT_LIGHT,new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {


                        String aMpM = "AM";
                        if(selectedHour >11)
                        {
                            aMpM = "PM";
                        }

                        int currentHour;
                        if(selectedHour>11)
                        {
                            currentHour = selectedHour - 12;
                        }
                        else
                        {
                            currentHour = selectedHour;
                        }

                        if (currentHour >= mcurrentTime.get(Calendar.HOUR) && selectedMinute >= mcurrentTime.get(Calendar.MINUTE)) {

                        } else{

                            Toast.makeText(getActivity(), "Please select future time", Toast.LENGTH_SHORT).show();
                            return;
                        //Display a toast or something to inform the user that he can't pick a past time.
                    }





//                        if (selectedDateTime.before(mcurrentTime.getTime())) {
//                            // prevent user from selecting time
//                            Log.e("mcuuurururuu","ok");
//                        }

                        tv_time.setText(String.format("%02d:%02d %s", currentHour, selectedMinute,
                                aMpM + "\n"));
                    }
                }, hour, minute,false);//Yes 24 hour time


                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });


        btn_continue.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

       // dialogdeliverymap();

        selectedtime=tv_time.getText().toString().trim();
        String name=ed_name.getText().toString().trim();
        String email=ed_email.getText().toString().trim();
        String phone=ed_phone.getText().toString().trim();
        String numpeople=ed_numofpeoplle.getText().toString().trim();
        String desc=ed_desc.getText().toString().trim();

       if (!isValidEmail(email)) {
            ed_email.setError("Invalid Email");
              return;
        }

//        if (!isValidPhone(phone)) {
//            final Dialog dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setCancelable(true);
//            dialog.setContentView(R.layout.dialog_msg);
//
//            Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
//            TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);
//            tv_msg.setText("Phone Number should be like - xxx-xxx-xxxx");
//            btn_ok.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    dialog.dismiss();
//                }
//            });
//            dialog.show();
//
//            // ed_phone.setError("Invalid Phone Number");
//return;
//        }

        new Json_RequestTable(getActivity()).execute(Fragment_Menu.access_tokn, ReserveTableAdapter.tableid,selecteddate,selectedtime,name,email,phone,numpeople,desc);


        //  new Json_AddDeliveryAddress(getActivity()).execute(Fragment_Menu.access_tokn,name,email,address,zipcode,phn);

             }
});
      return view;

    }
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPhone(String phone)
    {
        String PHONE_PATTERN = "\\d{3}-\\d{3}-\\d{4}";

        Pattern pattern = Pattern.compile(PHONE_PATTERN);
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
    private void setDateTimeField() {

        Calendar newCalendar = Calendar.getInstance();
       fromDatePickerDialog = new DatePickerDialog(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT, new DatePickerDialog.OnDateSetListener() {


            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();

                newDate.set(year, monthOfYear, dayOfMonth);
                tv_date.setText(dateFormatter.format(newDate.getTime()));
                selecteddate=tv_date.getText().toString().trim();

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));


    }
}
