package com.zoptal.chinamoonbuffet.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zoptal.chinamoonbuffet.JsonClasses.Json_MyCartList;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_PaymentSuccess;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Menu;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmationActivity extends AppCompatActivity {
    ImageView img_bck;
   Button btn_ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmationpaypal);

        img_bck=(ImageView)findViewById(R.id.back);
        btn_ok=(Button)findViewById(R.id.btn_ok);
        img_bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent i = new Intent(ConfirmationActivity.this, MainActivity.class);
                startActivity(i);

                finish();

            }
        });
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ConfirmationActivity.this, MainActivity.class);
               startActivity(i);

            }
        });
        //Getting Intent
        Intent intent = getIntent();


        try {
            JSONObject jsonDetails = new JSONObject(intent.getStringExtra("PaymentDetails"));

            //Displaying payment details
            showDetails(jsonDetails.getJSONObject("response"), intent.getStringExtra("PaymentAmount"));
        } catch (JSONException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showDetails(JSONObject jsonDetails, String paymentAmount) throws JSONException {
        //Views
        TextView textViewId = (TextView) findViewById(R.id.paymentId);
        TextView textViewStatus= (TextView) findViewById(R.id.paymentStatus);
        TextView textViewAmount = (TextView) findViewById(R.id.paymentAmount);

        //Showing the details from json object
        textViewId.setText(jsonDetails.getString("id"));
        textViewStatus.setText(jsonDetails.getString("state"));
        textViewAmount.setText(paymentAmount+" USD");

        String create_tym=jsonDetails.getString("create_time");
        String id=jsonDetails.getString("id");
        String status=textViewStatus.getText().toString();
        Log.e("tym----",""+create_tym);
        Log.e("id----",""+id);

        new Json_PaymentSuccess(ConfirmationActivity.this).execute(Fragment_Menu.access_tokn, Json_MyCartList.uniq,create_tym,id,status,PayPalIntegrationActivity.grandtotal,"Pickup");
    }





}

