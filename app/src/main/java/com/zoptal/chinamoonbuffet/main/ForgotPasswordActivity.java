package com.zoptal.chinamoonbuffet.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_forgotpw;
import com.zoptal.chinamoonbuffet.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {


    EditText ed_email;
    ImageView img_bck;
    Button btn_send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_forgotpw);

        ed_email=(EditText)findViewById(R.id.ed_email);
        btn_send=(Button)findViewById(R.id.btn_send);
        img_bck=(ImageView)findViewById(R.id.img_bck);

        btn_send.setOnClickListener(this);
        img_bck.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.img_bck:
                Log.e("click on signin","clicked");
                Intent i = new Intent(ForgotPasswordActivity.this,SigninActivity.class);
                startActivity(i);
                break;

            case R.id.btn_send:

                String email=ed_email.getText().toString().trim();

                if(email.equals("")){
                    ed_email.setError("Email should not be empty");
                    return;
                }
                else if (!isValidEmail(email)) {
                    ed_email.setError("Invalid Email");

                }
                else {
                    new Json_forgotpw(ForgotPasswordActivity.this).execute(email);
                    final InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                }
                break;


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
