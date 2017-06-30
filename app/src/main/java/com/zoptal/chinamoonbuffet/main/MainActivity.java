package com.zoptal.chinamoonbuffet.main;

import android.Manifest;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_Category;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_Logout;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_ProfileData;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_RestaurentContact;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_ScanQRCode;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_Weblink;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_sliderimages;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.adapter.CustomPagerAdapter;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Checkout;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Coupons;
import com.zoptal.chinamoonbuffet.fragment.Fragment_LoyalityProgram;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Menu;
import com.zoptal.chinamoonbuffet.fragment.Fragment_Notification;
import com.zoptal.chinamoonbuffet.fragment.Fragment_ReserveTable;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    String TITLES[] = {"Home", "My Profile", "My Punches","Loyalty Program","About", "Contact us", "Feedback", "Facebook", "Yelp", "Yellow Pages"};
    int ICONS[] = {R.mipmap.home, R.mipmap.profile, R.mipmap.punches, R.mipmap.loyalty, R.mipmap.about, R.mipmap.call, R.mipmap.feedback, R.mipmap.fbicon, R.mipmap.yelp_icon, R.mipmap.yellowpages};
    public Toolbar toolbar;
    private int _xDelta;
    private int _yDelta;
    ViewGroup _root;
    public RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    public DrawerLayout Drawer;
    public ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle
    public Drawable menu_drawable;
    public TextView tv_signin, tv_main, tv_logout, tv_username, total_noti11;
    public ImageView img_user, img_bck, img_cart, circleView_image;
    public LinearLayout linear_b1, linear_b2, linear_b3, linear_b4, linear_b5;
    public RelativeLayout rel_slider, rel_notification;
    String MyPREFERENCES = "MyPrefs1";
    SharedPreferences sharedpreferences1;
    public FloatingActionButton fab;
    String access_tokn;
    private static final int CAMERA_REQUEST = 1888;
    public ViewPager mViewPager;
    public CustomPagerAdapter mCustomPagerAdapter;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    public static String regId;
    public NotificationCompat.Builder notificationBuilder;
    public static String message = null;
    public static String tkn;
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        if (!isDeviceSupportCamera()) {
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn't support camera",
                    Toast.LENGTH_LONG).show();
            // will close the app if the device does't have camera
            finish();
        }

        sharedpreferences1 = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String userid = sharedpreferences1.getString("id", "");
        String name = sharedpreferences1.getString("name", "");
        access_tokn = sharedpreferences1.getString("access_token", "");
        String image = sharedpreferences1.getString("image", "");

        Log.e("userid---", "" + userid);
        Log.e("name---", "" + name);
        Log.e("access_tokn---", "" + access_tokn);
        Log.e("image---", "" + image);


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);

                    displayFirebaseRegId();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    message = intent.getStringExtra("message");


                    // Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();

                    //final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    //     + "://" +getPackageName() + "/raw/notification");


                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    notificationBuilder = new NotificationCompat.Builder(MainActivity.this)
                            .setSmallIcon(R.drawable.app_icon)
                            .setContentTitle("ChinaMoonbuffet Notification")
                            .setContentText(message)
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri);


                    NotificationManager notificationManager =
                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                    notificationManager.notify(0, notificationBuilder.build());


                }
            }
        };


//
//        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//
//                // checking for type intent filter
//                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
//                    // gcm successfully registered
//                    // now subscribe to `global` topic to receive app wide notifications
//                    FirebaseMessaging.getInstance().subscribeToTopic(Config.TOPIC_GLOBAL);
//
//                    displayFirebaseRegId();
//
//                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
//                    // new push notification is received
//                    message = intent.getStringExtra("message");
//
//
//                    // Toast.makeText(getApplicationContext(), "Push notification: " + message, Toast.LENGTH_LONG).show();
//
//                    //final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
//                    //     + "://" +getPackageName() + "/raw/notification");
//                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                    notificationBuilder = new NotificationCompat.Builder(MainActivity.this)
//                            .setSmallIcon(R.drawable.app_icon)
//                            .setContentTitle("ChinaMoonbuffet Notification")
//                            .setContentText(message)
//                            .setAutoCancel(true)
//                            .setSound(defaultSoundUri);
//
//
//                    NotificationManager notificationManager =
//                            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//                    notificationManager.notify(0, notificationBuilder.build());
//                    //txtMessage.setText(message);
//                }
//            }
//        };

        displayFirebaseRegId();

        new Json_RestaurentContact(MainActivity.this).execute();
        new Json_Weblink(MainActivity.this).execute();

        rel_slider = (RelativeLayout) findViewById(R.id.rel_slider);
        linear_b1 = (LinearLayout) findViewById(R.id.linear_b1);
        linear_b2 = (LinearLayout) findViewById(R.id.linear_b2);
        linear_b3 = (LinearLayout) findViewById(R.id.linear_b3);
        linear_b4 = (LinearLayout) findViewById(R.id.linear_b4);
        linear_b5 = (LinearLayout) findViewById(R.id.linear_b5);

        tv_logout = (TextView) findViewById(R.id.tv_logout);
        tv_username = (TextView) findViewById(R.id.tv_username);
        circleView_image = (ImageView) findViewById(R.id.circleView_image);
        tv_username.setText(name);

        if (image.isEmpty()) {

        } else {
            Picasso.with(MainActivity.this).load(image).into(circleView_image);
        }

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                new Json_Logout(MainActivity.this).execute(access_tokn);
//                Drawer.closeDrawer(Gravity.LEFT);

                alertmsg();

            }
        });

        tv_main = (TextView) toolbar.findViewById(R.id.tv_main);
        tv_signin = (TextView) toolbar.findViewById(R.id.tv_signin);
        img_user = (ImageView) toolbar.findViewById(R.id.img_user);
        img_bck = (ImageView) toolbar.findViewById(R.id.img_bck);
        img_cart = (ImageView) toolbar.findViewById(R.id.img_cart);
     //   total_noti11 = (TextView) toolbar.findViewById(R.id.total_noti11);

        img_cart.setBackgroundDrawable(getResources().getDrawable(R.drawable.cart));
        fab = (FloatingActionButton) toolbar.findViewById(R.id.fabtool);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage(Json_RestaurentContact.f.getPhone());

                alertDialogBuilder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + Json_RestaurentContact.f.getPhone()));
                        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(callIntent);

                    }
                });

                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //finish();
                    }
                });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });

//        MultiTouchListener touchListener=new MultiTouchListener(this,fab);
//        fab.setOnTouchListener(touchListener);

        _root = (ViewGroup) toolbar.findViewById(R.id.root);
//
////        _view = new TextView(this);
////        _view.setText("TextView!!!!!!!!");
//
//       Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(200, 200);
//        layoutParams.leftMargin = 100;
//        layoutParams.topMargin = 50;
//        layoutParams.bottomMargin = -250;
//        layoutParams.rightMargin = -250;
//        fab.setLayoutParams(layoutParams);
//
        //fab.setOnTouchListener(this);

//                if(fab.getParent()!=null) {
//                    ((ViewGroup) fab.getParent()).removeView(fab);
//                    _root.addView(fab);
//                }


//        _root.addView(fab);

//        fab.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                _root.addView(fab);
//                return true;
//            }
//        });


//        fab.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                AlphaAnimation animation1 = new AlphaAnimation(1, 0);
//                animation1.setDuration(1000);
//                animation1.setStartOffset(1000);
//                animation1.setFillAfter(true);
//                fab.startAnimation(animation1);
//              return false;
//            }
//        });


//        tv_signin1.setOnClickListener(this);
        tv_signin.setOnClickListener(this);
        if (name.isEmpty()) {
            tv_logout.setVisibility(View.GONE);
            tv_signin.setEnabled(true);
            tv_signin.setVisibility(View.VISIBLE);
            tv_signin.setText("Sign in");
            fab.setVisibility(View.GONE);
        } else {
            tv_logout.setVisibility(View.VISIBLE);
            tv_signin.setEnabled(false);
            tv_signin.setVisibility(View.GONE);
            img_user.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
        }
        // tv_signin1.setVisibility(View.GONE);
        rel_notification = (RelativeLayout) findViewById(R.id.rel_notification);

        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View
        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size
        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager
        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager
        mAdapter = new MyAdapter(MainActivity.this, TITLES, ICONS);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView
        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }
        }; // Drawer Toggle Object Made
        toolbar.setVisibility(View.VISIBLE);
        mDrawerToggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        menu_drawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.menu, getTheme());
        mDrawerToggle.setHomeAsUpIndicator(menu_drawable);
        // Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Drawer.isDrawerVisible(GravityCompat.START)) {
                    Drawer.closeDrawer(GravityCompat.START);
                } else {
                    Drawer.openDrawer(GravityCompat.START);
                }
            }
        });
        mDrawerToggle.syncState();


        rel_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                linear_b1.setBackgroundColor(Color.parseColor("#711508"));
                linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                linear_b4.setBackgroundColor(Color.parseColor("#711508"));
                linear_b5.setBackgroundColor(Color.parseColor("#711508"));


                //    total_noti11.setVisibility(View.GONE);

                Log.e("notification access token---", "" + access_tokn);

                if (Fragment_Menu.access_tokn.isEmpty()) {

                    alertmsgloginnotification();

                } else {
                    img_bck.setVisibility(View.VISIBLE);
                    rel_slider.setVisibility(View.GONE);
                    //  tv_signin1.setVisibility(View.GONE);
                    //img_user1.setVisibility(View.GONE);
                    tv_signin.setVisibility(View.GONE);
                    img_user.setVisibility(View.GONE);
                    tv_main.setVisibility(View.VISIBLE);
                    tv_main.setText("            Notifications  ");
                    toolbar.setBackgroundColor(Color.WHITE);
                    Fragment fragment7 = new Fragment_Notification();
                    FragmentManager fragmentManager7 = getFragmentManager();
                    fragmentManager7.beginTransaction().replace(R.id.activity_main_content_fragment, fragment7).commit();
                    Drawer.closeDrawer(Gravity.LEFT);
                }
            }
        });

        img_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Fragment_Menu.access_tokn.isEmpty()) {
                    alertmsglogin();

                    //  Toast.makeText(MainActivity.this,"Please login", Toast.LENGTH_LONG).show();
                } else {


                    final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Translucent);
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setCancelable(true);
                    dialog.setContentView(R.layout.dialog_msg);

                    Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
                    TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);
                    tv_msg.setText("To pay for order at pick-up, order must be placed over the phone. Orders placed in-app, need to be paid for in-app.");
                    btn_ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Fragment fragment = new Fragment_Checkout();
                            FragmentManager fragmentManager = getFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();
                            dialog.dismiss();
                        }
                    });
                              // new Json_MyCartList(MainActivity.this).execute(access_tokn);

                    dialog.show();

                }
            }


        });

        new Json_Category(MainActivity.this).execute();

        new Json_sliderimages(MainActivity.this).execute();
        mCustomPagerAdapter = new CustomPagerAdapter(MainActivity.this);

        mViewPager = (ViewPager) findViewById(R.id.pager);

        linear_b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_main.setVisibility(View.GONE);
                toolbar.setBackgroundColor(Color.TRANSPARENT);
                rel_slider.setVisibility(View.VISIBLE);
                menu_drawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.menu, getTheme());
                mDrawerToggle.setHomeAsUpIndicator(menu_drawable);

                img_user.setBackgroundDrawable(getResources().getDrawable(R.mipmap.signin_icon));

//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
//                layoutParams.height = 400;
//                toolbar.setLayoutParams(layoutParams);
//                 tv_signin1.setVisibility(View.GONE);
//                tv_signin.setVisibility(View.VISIBLE);
//                img_user1.setVisibility(View.GONE);
//                img_user.setVisibility(View.VISIBLE);

                linear_b1.setBackgroundColor(Color.parseColor("#FE0000"));
                linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                linear_b4.setBackgroundColor(Color.parseColor("#711508"));
                linear_b5.setBackgroundColor(Color.parseColor("#711508"));
                Fragment fragment = new Fragment_Menu();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();
            }
        });
        linear_b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (access_tokn.isEmpty()) {

                    alertmsglogincoupon();

                }
                else {
                    img_bck.setVisibility(View.GONE);
                    tv_main.setVisibility(View.VISIBLE);
                    tv_main.setText("    Coupons");
                    // tv_signin.setVisibility(View.GONE);
                    // img_user.setVisibility(View.GONE);
                    toolbar.setBackgroundColor(Color.WHITE);
                    menu_drawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.menublck, getTheme());
                    mDrawerToggle.setHomeAsUpIndicator(menu_drawable);
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
//                layoutParams.height =200;
//                toolbar.setLayoutParams(layoutParams);
                    rel_slider.setVisibility(View.GONE);
                    //  tv_signin1.setVisibility(View.VISIBLE);
                    // img_user1.setVisibility(View.VISIBLE);
                    tv_signin.setTextColor(Color.parseColor("#000000"));
                    img_user.setBackgroundDrawable(getResources().getDrawable(R.mipmap.user_iconblck));
                    img_cart.setBackgroundDrawable(getResources().getDrawable(R.drawable.cartblck));
                    Log.e("daily clicked", "clicked");
                    linear_b2.setBackgroundColor(Color.parseColor("#FE0000"));
                    linear_b1.setBackgroundColor(Color.parseColor("#711508"));
                    linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                    linear_b4.setBackgroundColor(Color.parseColor("#711508"));
                    linear_b5.setBackgroundColor(Color.parseColor("#711508"));

                    Fragment fragment = new Fragment_Coupons();
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();
                }
            }
        });
        linear_b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   toolbar.setVisibility(View.VISIBLE);

//                Intent cameraIntent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);

                if(isReadStorageAllowed()){
                    //If permission is already having then showing the toast
                   // Toast.makeText(MainActivity.this,"You already have the permission",Toast.LENGTH_LONG).show();

                    if (access_tokn.isEmpty()) {

                        alertmsgloginscan();

                    } else {
                        Log.e("table clicked", "clicked");
                        linear_b3.setBackgroundColor(Color.parseColor("#FE0000"));
                        linear_b1.setBackgroundColor(Color.parseColor("#711508"));
                        linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                        linear_b4.setBackgroundColor(Color.parseColor("#711508"));
                        linear_b5.setBackgroundColor(Color.parseColor("#711508"));
                        tv_signin.setTextColor(Color.parseColor("#000000"));
                        img_user.setBackgroundDrawable(getResources().getDrawable(R.mipmap.user_iconblck));
                        img_cart.setBackgroundDrawable(getResources().getDrawable(R.drawable.cartblck));

                        tkn = access_tokn;

                        qrScan = new IntentIntegrator(MainActivity.this);
                  //  qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                    qrScan.setOrientationLocked(false);
                 // qrScan.setBeepEnabled(true);
            // qrScan.setCaptureActivity(CaptureActivity.class);
                   //qrScan.setCaptureLayout(R.layout.custom_layout);
                                              qrScan.initiateScan();

                        // Intent cameraIntent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //   startActivityForResult(cameraIntent, CAMERA_REQUEST);

                       // Intent i = new Intent(MainActivity.this, ScanActivity.class);
                       // startActivity(i);
                    }
                    //Existing the method with return
                    return;
                }

                //If the app has not the permission then asking for the permission
                else {
                    requestStoragePermission();
                }




                //    captureImage();

//                Intent intent = new Intent(
//                        "com.google.zxing.client.android.SCAN");
//                intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
////                startActivityForResult(intent, 0);
//                try {
//
//                    Intent intent = new Intent("com.google.zxing.client.android.SCAN");
//                    intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes
//                    startActivityForResult(intent, 0);
//
//                } catch (Exception e) {
//
//                    Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
//                    Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
//                    startActivity(marketIntent);
//
//                }
            }
        });
        linear_b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setVisibility(View.VISIBLE);
                Log.e("cart clicked", "clicked");
                linear_b4.setBackgroundColor(Color.parseColor("#FE0000"));
                linear_b1.setBackgroundColor(Color.parseColor("#711508"));
                linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                linear_b5.setBackgroundColor(Color.parseColor("#711508"));

                tv_signin.setTextColor(Color.parseColor("#000000"));
                img_user.setBackgroundDrawable(getResources().getDrawable(R.mipmap.user_iconblck));
                img_cart.setBackgroundDrawable(getResources().getDrawable(R.drawable.cartblck));

                img_bck.setVisibility(View.GONE);
                tv_main.setVisibility(View.VISIBLE);
                tv_main.setText("RESERVE TABLE");
                tv_signin.setVisibility(View.GONE);
                img_user.setVisibility(View.GONE);
                toolbar.setBackgroundColor(Color.WHITE);
                menu_drawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.menublck, getTheme());
                mDrawerToggle.setHomeAsUpIndicator(menu_drawable);
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
//                layoutParams.height =200;
//                toolbar.setLayoutParams(layoutParams);
                rel_slider.setVisibility(View.GONE);
                // tv_signin1.setVisibility(View.GONE);
                //img_user1.setVisibility(View.GONE);


                Fragment fragment = new Fragment_ReserveTable();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();
            }
        });
        linear_b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbar.setVisibility(View.VISIBLE);
                linear_b5.setBackgroundColor(Color.parseColor("#FE0000"));
                linear_b1.setBackgroundColor(Color.parseColor("#711508"));
                linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                linear_b4.setBackgroundColor(Color.parseColor("#711508"));

                tv_signin.setTextColor(Color.parseColor("#000000"));
                img_user.setBackgroundDrawable(getResources().getDrawable(R.mipmap.user_iconblck));
                img_cart.setBackgroundDrawable(getResources().getDrawable(R.drawable.cartblck));
                img_bck.setVisibility(View.GONE);
                tv_main.setVisibility(View.VISIBLE);
                tv_main.setText("Loyalty Program");
                tv_signin.setVisibility(View.GONE);
                img_user.setVisibility(View.GONE);
                toolbar.setBackgroundColor(Color.WHITE);
                menu_drawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.menublck, getTheme());
                mDrawerToggle.setHomeAsUpIndicator(menu_drawable);
//                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) toolbar.getLayoutParams();
//                layoutParams.height =200;
//                toolbar.setLayoutParams(layoutParams);
                rel_slider.setVisibility(View.GONE);
                //   tv_signin1.setVisibility(View.GONE);
                //img_user1.setVisibility(View.GONE);


                Fragment fragment = new Fragment_LoyalityProgram();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).commit();

            }
        });
        linear_b1.setBackgroundColor(Color.parseColor("#FE0000"));
    }

    //We are calling this method to check the permission status
    private boolean isReadStorageAllowed() {
        //Getting the permission status
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        //If permission is granted returning true
        if (result == PackageManager.PERMISSION_GRANTED) {
            Log.e("alredy granted---","yes");
            return true;
        }
        else {
            Log.e("no alredy granted---","no");
            //If permission is not granted returning false
            return false;
        }
    }

    //Requesting permission
    private void requestStoragePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }

        //And finally ask for the permission
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},22);
    }

    private boolean isDeviceSupportCamera() {
        if (getApplicationContext().getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA)) {
            // this device has a camera
            return true;
        } else {
            // no camera on this device
            return false;
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if(requestCode == 22){

            //If permission is granted
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                //Displaying a toast
              //  Toast.makeText(this,"Permission granted now you can read the storage",Toast.LENGTH_LONG).show();

                if (access_tokn.isEmpty()) {

                    alertmsgloginscan();

                } else {
                    tkn = access_tokn;
                    qrScan = new IntentIntegrator(MainActivity.this);
                    //  qrScan.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
                    qrScan.setOrientationLocked(false);
                    // qrScan.setBeepEnabled(true);
                    // qrScan.setCaptureActivity(CaptureActivity.class);
                    //qrScan.setCaptureLayout(R.layout.custom_layout);
                    qrScan.initiateScan();


                    // Intent cameraIntent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //   startActivityForResult(cameraIntent, CAMERA_REQUEST);

                    //Intent i = new Intent(MainActivity.this, CameraActivity.class);
                    //startActivity(i);
                }
            }else{
                //Displaying another toast if permission is not granted
                Toast.makeText(this,"Oops you just denied the permission",Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.tv_signin:

                Intent i = new Intent(MainActivity.this,SigninActivity.class);
                startActivity(i);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));

        // clear the notification area when the app is opened
        NotificationUtils.clearNotifications(getApplicationContext());
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }
    // Checking camera availability

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {

                alertmsgcode();

            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result.getContents());


                }
                catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast

                    Log.e("result----",""+result.getContents());
                 //   Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    new Json_ScanQRCode(MainActivity.this).execute(Fragment_Menu.access_tokn,result.getContents());
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }




        if (requestCode == 1 && resultCode == RESULT_OK) {

            Json_ProfileData.myProfile.onActivityResult1(requestCode, resultCode, data);
        }

        if (requestCode == 3 && resultCode == RESULT_OK) {

            Json_ProfileData.myProfile.onActivityResult1(requestCode, resultCode, data);
        }
//        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//
//            String uploadImage1 = getStringImage(photo);
//            Log.e("photo----", "" + uploadImage1);
//            Log.e("access_token---", "" + access_tokn);
//
//            new Json_ScanQRCode(MainActivity.this).execute(access_tokn, uploadImage1);
//
//        }
        }

        // Fetches reg id from shared preferences
        // and displays on the screen
    private void displayFirebaseRegId() {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        regId= pref.getString("regId", null);

        Log.e("regid server---",""+regId);
        if (!TextUtils.isEmpty(regId)) {
          //  Toast.makeText(getApplicationContext(), "Firebase Reg Id " + regId ,Toast.LENGTH_LONG).show();
        }
        else{
           // Toast.makeText(getApplicationContext(), "Firebase Reg Id is not received yet!" ,Toast.LENGTH_LONG).show();
        }


    }

//        if (requestCode == 0) {
//
//            if (resultCode == RESULT_OK) {
//                String contents = data.getStringExtra("SCAN_RESULT");
//            }
//            if (resultCode == RESULT_CANCELED) {
//                //handle cancel
//            }
//        }


    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }
    @Override
    public boolean onTouch(View view, MotionEvent event) {
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams)view.getLayoutParams();
                _xDelta = X - lParams.leftMargin;
                _yDelta = Y - lParams.topMargin;
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                break;
            case MotionEvent.ACTION_POINTER_UP:
                break;
            case MotionEvent.ACTION_MOVE:
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                layoutParams.leftMargin = X - _xDelta;
                layoutParams.topMargin = Y - _yDelta;
                layoutParams.rightMargin = -250;
                layoutParams.bottomMargin = -250;
                view.setLayoutParams(layoutParams);
                break;
        }
        _root.invalidate();
        return true;
    }

    public void alertmsgcode() {


        final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_msg);

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        TextView tv_msg = (TextView) dialog.findViewById(R.id.tv_msg);

        tv_msg.setText("Result Not Found!");

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });
    }

    public void alertmsg(){


        final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Translucent);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setCancelable(true);
		dialog.setContentView(R.layout.dialog_alert);

		Button btn_yes = (Button) dialog.findViewById(R.id.btn_yes);
        Button btn_no = (Button) dialog.findViewById(R.id.btn_no);


        btn_yes.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

           new Json_Logout(MainActivity.this).execute(access_tokn);
           Drawer.closeDrawer(Gravity.LEFT);
				dialog.dismiss();

			}
		});
        btn_no.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

                CustomNotification();
                dialog.dismiss();
			}
		});

		// dialog.dismiss();

		dialog.show();

	}

    public void alertmsglogin(){


        final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_msg);

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        TextView tv_msg=(TextView)dialog.findViewById(R.id.tv_msg);

         tv_msg.setText("Please signin with your user account to see cart!");

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


        // dialog.dismiss();

        dialog.show();

    }

    public void alertmsgloginnotification(){


        final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_msg);

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        TextView tv_msg=(TextView)dialog.findViewById(R.id.tv_msg);

        tv_msg.setText("Please signin with your user account to see Notifications!");

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });


        // dialog.dismiss();

        dialog.show();

    }
    public void alertmsglogincoupon(){


        final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_msgloyality);

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        TextView tv_msg=(TextView)dialog.findViewById(R.id.tv_msg);

        tv_msg.setText("Please sign in to see Daily offer!");

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                linear_b1.setBackgroundColor(Color.parseColor("#FE0000"));
                linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                linear_b4.setBackgroundColor(Color.parseColor("#711508"));
                linear_b5.setBackgroundColor(Color.parseColor("#711508"));
            }
        });


        // dialog.dismiss();

        dialog.show();

    }

    public void alertmsgloginscan(){


        final Dialog dialog = new Dialog(MainActivity.this, android.R.style.Theme_Translucent);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_msgloyality);

        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        TextView tv_msg=(TextView)dialog.findViewById(R.id.tv_msg);

        tv_msg.setText("Please sign in to Scan Coupon Code!");

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                linear_b1.setBackgroundColor(Color.parseColor("#FE0000"));
                linear_b2.setBackgroundColor(Color.parseColor("#711508"));
                linear_b3.setBackgroundColor(Color.parseColor("#711508"));
                linear_b4.setBackgroundColor(Color.parseColor("#711508"));
                linear_b5.setBackgroundColor(Color.parseColor("#711508"));

            }
        });


        // dialog.dismiss();

        dialog.show();

    }


    public void CustomNotification() {
        // Using RemoteViews to bind custom layouts into Notification
        RemoteViews remoteViews = new RemoteViews(getPackageName(),
                R.layout.customnotification);

        // Set Notification Title
        String strtitle = getString(R.string.customnotificationtitle);
        // Set Notification Text
        String strtext = getString(R.string.customnotificationtext);

        // Open NotificationView Class on Notification Click
        Intent intent = new Intent(this, NotificationView.class);
        // Send data to NotificationView Class
        intent.putExtra("title", strtitle);
        intent.putExtra("text", strtext);
        // Open NotificationView.java Activity
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                // Set Icon
                .setSmallIcon(R.mipmap.logo)
                // Set Ticker Message
                .setTicker(getString(R.string.customnotificationticker))
                // Dismiss Notification
                .setAutoCancel(true)
                // Set PendingIntent into Notification
                .setContentIntent(pIntent)
                // Set RemoteViews into Notification
                .setContent(remoteViews);

        // Locate and set the Image into customnotificationtext.xml ImageViews
        // Locate and set the Image into customnotificationtext.xml ImageViews
        remoteViews.setImageViewResource(R.id.imagenotileft,R.mipmap.ic_launcher);
        remoteViews.setImageViewResource(R.id.imagenotiright,R.mipmap.logo);

        // Locate and set the Text into customnotificationtext.xml TextViews
        remoteViews.setTextViewText(R.id.title,"Notification test title1 ");
        remoteViews.setTextColor(R.id.title,Color.parseColor("#000000"));
        remoteViews.setTextViewText(R.id.text,"Notification test title2");
        remoteViews.setTextColor(R.id.text,Color.parseColor("#000000"));





        // Create Notification Manager
        NotificationManager notificationmanager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // Build Notification with Notification Manager
        notificationmanager.notify(0, builder.build());

    }


}
