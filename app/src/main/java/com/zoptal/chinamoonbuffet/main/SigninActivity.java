package com.zoptal.chinamoonbuffet.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_SignIn;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.common.GPSTracker;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SigninActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView img_bck;
    EditText ed_email,ed_pw;
    Button btn_signin;
    String email,pw;
    TextView tv_fpw,tv_signup;
    GPSTracker gps;
    double latitude, longitude;
    Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signin);

//        final InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);


        img_bck=(ImageView)findViewById(R.id.img_bck);
        ed_email=(EditText)findViewById(R.id.ed_email);
        ed_pw=(EditText)findViewById(R.id.ed_pw);
        tv_fpw=(TextView)findViewById(R.id.tv_fpw);
        tv_signup=(TextView)findViewById(R.id.tv_signup);
        btn_signin=(Button)findViewById(R.id.btn_signin);


        img_bck.setOnClickListener(this);
        btn_signin.setOnClickListener(this);
        tv_fpw.setOnClickListener(this);
        tv_signup.setOnClickListener(this);

        mContext = this;
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SigninActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            // Toast.makeText(mContext,"You need have granted permission",Toast.LENGTH_SHORT).show();
            gps = new GPSTracker(mContext, SigninActivity.this);

            // Check if GPS enabled
            if (gps.canGetLocation()) {

                latitude = gps.getLatitude();
                longitude = gps.getLongitude();

                Log.e("lat---",""+latitude);
                Log.e("long---",""+longitude);

                // \n is for new line
                // Toast.makeText(getApplicationContext(), "Your Location is - \nLat: " + latitude + "\nLong: " + longitude, Toast.LENGTH_LONG).show();
            } else {
                // Can't get location.
                // GPS or network is not enabled.
                // Ask user to enable GPS/network in settings.
                gps.showSettingsAlert();
            }
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.img_bck:
                Intent i = new Intent(SigninActivity.this,MainActivity.class);
                startActivity(i);
                finish();
                break;

            case R.id.btn_signin:

               signin();
                break;
            case R.id.tv_fpw:

                Intent intent = new Intent(SigninActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
                finish();

                break;

            case R.id.tv_signup:

                Intent intent1 = new Intent(SigninActivity.this,SignupActivity.class);
                startActivity(intent1);
                finish();

                break;
        }
    }

    public void signin(){

        email=ed_email.getText().toString().trim();
        pw=ed_pw.getText().toString().trim();
        String regid=MainActivity.regId;

       if(email.equals("")) {
            ed_email.setError("Email should not be empty");
            return;
        }
        else if(pw.equals("")) {
            ed_pw.setError("Password should not be empty");
            return;
        }
       else if (!isValidEmail(email)) {
           ed_email.setError("Invalid Email");

       }

       else{

           new Json_SignIn(SigninActivity.this).execute(email,pw,"123456789","android",String.valueOf(latitude),String.valueOf(longitude),regid);

           //new Json_SignUp(SignupActivity.this).execute(name,email,pw,address,zipcode,phone,String.valueOf(latitude),String.valueOf(longitude),"123456789","android",userimg);


           // Toast.makeText(SignupActivity.this, "Sign up successfully", Toast.LENGTH_LONG).show();
       }



    }


    // validating email id
    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
