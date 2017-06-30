package com.zoptal.chinamoonbuffet.main;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.soundcloud.android.crop.Crop;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_FbLogin;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_GoogleLogin;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_SignUp;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.common.GPSTracker;
import com.zoptal.chinamoonbuffet.common.GooglePlacesAutocompleteAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener,AdapterView.OnItemClickListener{


    TextView tv_sign;
    ImageView img_bck,user_img;
    Button btn_signup;
    EditText ed_name,ed_email,ed_pw,ed_zipcode,ed_phone;
    String name,email,pw,address,zipcode,phone;
    AutoCompleteTextView ed_address;
    double latitude, longitude;
    GPSTracker gps;
    Context mContext;
    private Bitmap bitmap;
    LoginButton btn_fb;
    CallbackManager callbackManager;


    //Signin button
    private SignInButton signInButton;

    //Signing Options
    private GoogleSignInOptions gso;

    //google api client
    private GoogleApiClient mGoogleApiClient;

    //Signin constant to check the activity result
    private int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        facebookSDKInitialize();
        setContentView(R.layout.activity_signup);


        tv_sign=(TextView)findViewById(R.id.tv_sign);
        img_bck=(ImageView)findViewById(R.id.img_bck);
        btn_signup=(Button)findViewById(R.id.btn_signup);
        ed_name=(EditText)findViewById(R.id.ed_name);
        ed_email=(EditText)findViewById(R.id.ed_email);
        ed_pw=(EditText)findViewById(R.id.ed_pw);
        ed_address=(AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        ed_zipcode=(EditText)findViewById(R.id.ed_zipcode);
        ed_phone=(EditText)findViewById(R.id.ed_phone);
        user_img=(ImageView)findViewById(R.id.user_img);

        ed_address.setAdapter(new GooglePlacesAutocompleteAdapter(SignupActivity.this, R.layout.list_item));


        btn_fb=(LoginButton)findViewById(R.id.connectWithFbButton);
        btn_fb.setReadPermissions("email");

        tv_sign.setOnClickListener(this);
        img_bck.setOnClickListener(this);
        btn_signup.setOnClickListener(this);
        user_img.setOnClickListener(this);

        //Initializing google signin option
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        //Initializing signinbutton
        signInButton = (SignInButton) findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_WIDE);
        signInButton.setScopes(gso.getScopeArray());

        //Initializing google api client
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        //Setting onclick listener to signing button
        signInButton.setOnClickListener(this);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.male_profile);

        mContext = this;
        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SignupActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            // Toast.makeText(mContext,"You need have granted permission",Toast.LENGTH_SHORT).show();
            gps = new GPSTracker(mContext, SignupActivity.this);

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

        btn_fb.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    getLoginDetails(btn_fb);
                }
            });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tv_sign:
                Intent i = new Intent(SignupActivity.this,SigninActivity.class);
                startActivity(i);
                break;

            case R.id.user_img:

                Crop.pickImage(SignupActivity.this, 1);
                break;

            case R.id.img_bck:
                Intent intent = new Intent(SignupActivity.this,MainActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_signup:

                signup();
                break;

            case R.id.sign_in_button:
                signIn();
                break;

        }
    }

    private void signIn() {
        //Creating an intent




        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);

        //Starting intent for result
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    public void signup(){

        name=ed_name.getText().toString().trim();
        email= ed_email.getText().toString();
        pw=ed_pw.getText().toString().trim();
        address=ed_address.getText().toString().trim();
        zipcode=ed_zipcode.getText().toString().trim();
        phone=ed_phone.getText().toString().trim();
        String userimg=getStringImage(bitmap);
        String regid=MainActivity.regId;


        if(name.equals("") ) {
            ed_name.setError("Username should not be empty");
            return;
        }
        else if(email.equals("")) {
            ed_email.setError("Email should not be empty");
            return;
        }
        else if(pw.equals("")) {
            ed_pw.setError("Password should not be empty");
            return;
        }
//        else if (address.equals("")) {
//            ed_address.setError("Address should not be empty");
//
//        }
//        else if (zipcode.equals("")) {
//            ed_zipcode.setError("Zipcode should not be empty");
//
//        }
//        else if (phone.equals("")) {
//            ed_phone.setError("Phone Number should not be empty");
//
//        }
        else if (!isValidEmail(email)) {
            ed_email.setError("Invalid Email");

        }

   else{

          new Json_SignUp(SignupActivity.this).execute(name,email,pw,address,zipcode,phone,String.valueOf(latitude),String.valueOf(longitude),"123456789","android",userimg,regid);


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
    // validating password with retype password
    private boolean isValidPassword(String password) {
        if (password != null && password.length() > 7) {
            return true;
        }
        return false;
    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,20, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(SignupActivity.this, 1);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode ==RESULT_OK) {

            user_img.setImageURI(null);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),Crop.getOutput(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
            user_img.setImageURI(Crop.getOutput(result));
        }
        else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(SignupActivity.this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
//            beginCrop(result.getData());
//        } else if (requestCode == Crop.REQUEST_CROP) {
//            handleCrop(resultCode, result);
//        }
        //If signin
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //Calling a new function to handle signin
            handleSignInResult(result);
        }
        callbackManager.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {

            onActivityResult1(requestCode, resultCode, data);
        }
    }

    public void onActivityResult1(int requestCode, int resultCode, Intent data) {

        try {
            if (requestCode == 1) {
                beginCrop(data.getData());

            }
            if (requestCode == 1) {

                handleCrop(resultCode, data);
            }

        } catch (Exception e) {

        }
    }

    //After the signing we are calling this function
    private void handleSignInResult(GoogleSignInResult result) {
        //If the login succeed
        if (result.isSuccess()) {
            //Getting google account
            GoogleSignInAccount acct = result.getSignInAccount();
            String regid=MainActivity.regId;
            //Displaying name and email


            Log.e("google id--",""+acct.getId());
            Log.e("google id Token--",""+acct.getIdToken());
            Log.e("google name--",""+acct.getDisplayName());
            Log.e("google email--",""+acct.getEmail());

            new Json_GoogleLogin(SignupActivity.this).execute(acct.getId(),acct.getDisplayName(),acct.getEmail(),"",String.valueOf(latitude),String.valueOf(longitude),"123456789","android",regid);


        }
        else {
            //If login fails
            Toast.makeText(this, "Login Failed", Toast.LENGTH_LONG).show();
        }
    }
    protected void facebookSDKInitialize() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AppEventsLogger.deactivateApp(this);
    }

    protected void getLoginDetails(LoginButton login_button){

        // Callback registration
        Log.e("in login detail---","test");

        login_button.setReadPermissions("user_birthday");
        login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult login_result) {

                Log.e("success----", "test");

              LoginManager.getInstance().logInWithPublishPermissions(SignupActivity.this, Arrays.asList("publish_actions"));
//
//
               LoginManager.getInstance().logInWithReadPermissions(SignupActivity.this,
                   Arrays.asList("user_friends", "email", "public_profile", "user_birthday"));
//
//
//                GraphRequest request = GraphRequest.newMeRequest(
//                        login_result.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(JSONObject object, GraphResponse response) {
//                                try {
//                                    Log.e("Response",response.toString());
//
//                                    String email = response.getJSONObject().getString("email");
//                                    String firstName = response.getJSONObject().getString("first_name");
//                                    String lastName = response.getJSONObject().getString("last_name");
//                                    String gender = response.getJSONObject().getString("gender");
//                                    String bday= response.getJSONObject().getString("birthday");
//
//                                    Profile profile = Profile.getCurrentProfile();
//                                    try {
//                                        String id = profile.getId();
//
//                                        String link = profile.getLinkUri().toString();
//                                        // Log.i("Link",link);
//                                        if (Profile.getCurrentProfile()!=null)
//                                        {
//                                             Log.e("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
//                                        }
//
//                                        Log.e("Login" + "ID", id);
//                                        Log.e("Login" + "Email", email);
//                                        //  Log.e("Login" + "Bday", bday);
//                                        Log.e("Login"+ "FirstName", firstName);
//                                        Log.e("Login" + "LastName", lastName);
//                                        Log.e("Login" + "Gender", gender);
//
//
////                                        SharedPreferences.Editor editor1 = sharedpreferences1.edit();
////
////                                        editor1.putString(Tokn,id);
////                                        editor1.putString(Username,firstName);
////                                        editor1.putString(Username1,lastName);
////                                        editor1.putString(Email,email);
////                                        editor1.putString(Usertype,utype);
////                                        editor1.commit();
//
//                                       // new Json_FbLogin(SignupActivity.this).execute(id,firstName,email,"",String.valueOf(latitude),String.valueOf(longitude),"123456789","android");
//                                    }catch (NullPointerException e){}
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//
//                        });
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,email,first_name,last_name,gender, birthday");
//                request.setParameters(parameters);
//                request.executeAsync();
                LoginManager.getInstance().registerCallback(callbackManager,
                        new FacebookCallback<LoginResult>() {
                            @Override
                            public void onSuccess(LoginResult loginResult) {
                                Log.e("in sucesss","test");
                                setFacebookData(loginResult);
                            }

                            @Override
                            public void onCancel() {
                            }

                            @Override
                            public void onError(FacebookException exception) {

                                Log.e("exception===",""+exception);
                            }
                        });
                //sharePhotoToFacebook();
                // Intent intent = new Intent(LoginSignup.this,MainActivity1.class);
                //startActivity(intent);

            }

            @Override
            public void onCancel() {
                // code for cancellation
            }

            @Override
            public void onError(FacebookException exception) {
                //  code to handle error
            }
        });

    }

    private void setFacebookData(final LoginResult loginResult)
    {
        GraphRequest request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(),
                new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            Log.e("Response",response.toString());

                            String email = response.getJSONObject().getString("email");
                            String firstName = response.getJSONObject().getString("first_name");
                            String lastName = response.getJSONObject().getString("last_name");
                            String gender = response.getJSONObject().getString("gender");
                            //  String bday= response.getJSONObject().getString("birthday");

                            Profile profile = Profile.getCurrentProfile();
                            String id = profile.getId();
                            String regid=MainActivity.regId;

                            String link = profile.getLinkUri().toString();
                            // Log.i("Link",link);
                            if (Profile.getCurrentProfile()!=null)
                            {
                                // Log.e("Login", "ProfilePic" + Profile.getCurrentProfile().getProfilePictureUri(200, 200));
                            }

                            Log.e("Login" + "ID", id);
                            Log.e("Login" + "Email", email);
                            //  Log.e("Login" + "Bday", bday);
                            Log.e("Login"+ "FirstName", firstName);
                            Log.e("Login" + "LastName", lastName);
                            Log.e("Login" + "Gender", gender);


                            new Json_FbLogin(SignupActivity.this).execute(id,firstName,email,"",String.valueOf(latitude),String.valueOf(longitude),"123456789","android",regid);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,email,first_name,last_name,gender, birthday");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onItemClick(AdapterView adapterView, View view, int position, long id) {
        String str = (String) adapterView.getItemAtPosition(position);

    }
}
