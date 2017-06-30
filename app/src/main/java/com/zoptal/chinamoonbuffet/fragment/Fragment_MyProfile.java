package com.zoptal.chinamoonbuffet.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soundcloud.android.crop.Crop;
import com.squareup.picasso.Picasso;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_Changepw;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_MyPunchsP;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_OrderHistory;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_UpdateProfile;
import com.zoptal.chinamoonbuffet.JsonClasses.Model_ProfileData;
import com.zoptal.chinamoonbuffet.R;
import com.zoptal.chinamoonbuffet.common.GooglePlacesAutocompleteAdapter;
import com.zoptal.chinamoonbuffet.main.MainActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Fragment_MyProfile extends Fragment implements View.OnClickListener,AdapterView.OnItemClickListener {

    ImageView user_img;
    EditText ed_name,ed_email,ed_phone,ed_zipcode;
    Button btn_cancel,btn_save;
    AutoCompleteTextView ed_address;
    MainActivity activity1;
    RelativeLayout rel_account, rel_chngepw, rel_ordrhistory;
    public Dialog dialog, dialog1;
    private Bitmap bitmap;
    Model_ProfileData m;
    public static   TextView  tv_msg;
    public static ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub

        View view = inflater.inflate(R.layout.activity_myprofile, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        activity1 = (MainActivity) getActivity();
        activity1.menu_drawable = ResourcesCompat.getDrawable(getResources(), R.mipmap.menublck, getActivity().getTheme());
        activity1.mDrawerToggle.setHomeAsUpIndicator(activity1.menu_drawable);
        activity1.fab.setVisibility(View.GONE);


        activity1.Drawer.closeDrawer(Gravity.LEFT);
        activity1.img_bck.setVisibility(View.GONE);
        activity1.tv_main.setText("  MY PROFILE");
        rel_account = (RelativeLayout) view.findViewById(R.id.rel_account);
        rel_chngepw = (RelativeLayout) view.findViewById(R.id.rel_chngepw);
        rel_ordrhistory = (RelativeLayout) view.findViewById(R.id.rel_ordrhistory);

        bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.male_profile);

        ed_name=(EditText)view.findViewById(R.id.ed_name);
        ed_email=(EditText)view.findViewById(R.id.ed_email);
        ed_phone=(EditText)view.findViewById(R.id.ed_phone);
        ed_address=(AutoCompleteTextView)view.findViewById(R.id.ed_address);
        ed_zipcode=(EditText)view.findViewById(R.id.ed_zipcode);
        btn_cancel=(Button)view.findViewById(R.id.btn_cancel);
        btn_save=(Button)view.findViewById(R.id.btn_save);
        user_img=(ImageView)view.findViewById(R.id.user_img);
        activity1.img_cart.setVisibility(View.INVISIBLE);
       // activity1.img_cart.setBackgroundDrawable(getResources().getDrawable(R.drawable.cartblck));

         ed_address.setAdapter(new GooglePlacesAutocompleteAdapter(getActivity(), R.layout.list_item));

         Bundle bundle = getArguments();
         m=(Model_ProfileData)bundle.getSerializable("profiledata");

        ed_name.setText(m.getName());
        ed_email.setText(m.getEmail());
        ed_phone.setText(m.getPhone());
        ed_address.setText(m.getAddress());
        ed_zipcode.setText(m.getZipcode());

            if(m.getImage().isEmpty()){

            }
                    else {
                Picasso.with(getActivity()).load(m.getImage()).into(user_img);
            }
        rel_account.setOnClickListener(this);
        rel_chngepw.setOnClickListener(this);
        rel_ordrhistory.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        user_img.setOnClickListener(this);

        return view;

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rel_account:

//                Fragment fragment = new Fragment_MyPunches();
//                FragmentManager fragmentManager = getFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).addToBackStack(null).commit();

                new Json_MyPunchsP(getActivity()).execute(Fragment_Menu.access_tokn,"");


                break;

            case R.id.rel_chngepw:

                dialogchangepw();

                break;
            case R.id.rel_ordrhistory:

                Fragment fragment = new Fragment_orderhistory();
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.activity_main_content_fragment, fragment).addToBackStack(null).commit();

         //dialogorderhistory();
                break;
            case R.id.btn_save:

                updateprofile();

                break;

            case R.id.btn_cancel:

                ed_name.setText(m.getName());
                ed_email.setText(m.getEmail());
                ed_phone.setText(m.getPhone());
                ed_address.setText(m.getAddress());
                ed_zipcode.setText(m.getZipcode());

                if(m.getImage().isEmpty()){

                }
                else {
                    Picasso.with(getActivity()).load(m.getImage()).into(user_img);
                }

                break;
            case R.id.user_img:

               Crop.pickImage(getActivity(), 1);

               // selectImage();

                break;
        }
    }

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));

                            //   intent.putExtra("outputY", com.app.controller.Constants.MAX_HEIGHT);

                    startActivityForResult(intent, 2);


                }
                else if (options[item].equals("Choose from Gallery"))

                {

                    Crop.pickImage(getActivity(), 1);
                   // Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   // startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    public  void updateprofile(){

        String name=ed_name.getText().toString().trim();
        String email=ed_email.getText().toString().trim();
        String phone=ed_phone.getText().toString().trim();
        String address=ed_address.getText().toString().trim();
        String zipcode=ed_zipcode.getText().toString().trim();
        String userimg=getStringImage(bitmap);

//        if(name.equals("")||email.equals("")||phone.equals("")||address.equals("")||zipcode.equals("")){
//
//            Toast.makeText(getActivity(),"All fields are complusory", Toast.LENGTH_LONG).show();
//        }
      if (!isValidEmail(email)) {
            ed_email.setError("Invalid Email");
        }

        else{

            new Json_UpdateProfile(getActivity()).execute(Fragment_Menu.access_tokn,name,email,address,zipcode,phone,userimg);
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
    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getActivity().getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(getActivity(),1);
    }

    private void handleCrop(int resultCode, Intent result) {
        if (resultCode ==getActivity().RESULT_OK) {

            user_img.setImageURI(null);

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),Crop.getOutput(result));
            } catch (IOException e) {
                e.printStackTrace();
            }
            user_img.setImageURI(Crop.getOutput(result));
        }
        else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(getActivity(), Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void onActivityResult1(int requestCode, int resultCode, Intent data) {

        try {
            if (requestCode ==1) {
                beginCrop(data.getData());

            }
            if (requestCode == 1) {

                handleCrop(resultCode, data);
            }

        } catch (Exception e) {

        }

//        if(requestCode==2){
//            //create instance of File with same name we created before to get image from storage
//            File file = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
//            //Crop the captured image using an other intent
//            try {
//    /*the user's device may not support cropping*/
//                cropCapturedImage(Uri.fromFile(file));
//            }
//            catch(ActivityNotFoundException aNFE){
//                //display an error message if user device doesn't support
//                String errorMessage = "Sorry - your device doesn't support the crop action!";
//
//                Log.e("error msg---",""+errorMessage);
//               // Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
//               // toast.show();
//            }
//        }
//        if(requestCode==3){
//            //Create an instance of bundle and get the returned data
//            Bundle extras = data.getExtras();
//            //get the cropped bitmap from extras
//            Bitmap thePic = extras.getParcelable("data");
//            //set image bitmap to image view
//            user_img.setImageBitmap(thePic);
//        }

    }


    public void cropCapturedImage(Uri picUri){
        //call the standard crop action intent
        Intent cropIntent = new Intent("com.android.camera.action.CROP");
        //indicate image type and Uri of image
        cropIntent.setDataAndType(picUri, "image/*");
        //set crop properties
        cropIntent.putExtra("crop", "true");
        //indicate aspect of desired crop
        cropIntent.putExtra("aspectX", 1);
        cropIntent.putExtra("aspectY", 1);
        //indicate output X and Y
        cropIntent.putExtra("outputX", 256);
        cropIntent.putExtra("outputY", 256);
        //retrieve data on return
        cropIntent.putExtra("return-data", true);
        //start the activity - we handle returning in onActivityResult
        startActivityForResult(cropIntent, 3);
    }
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,20, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }



    public void dialogchangepw() {

//        dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent) {
//            @Override
//            public boolean onTouchEvent(MotionEvent event) {
//                // Tap anywhere to close dialog.
//                dialog.dismiss();
//                return true;
//            }
//        };
        dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
        dialog.setCanceledOnTouchOutside(true);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_changepw);

        ImageView img_cross = (ImageView) dialog.findViewById(R.id.img_cross);
        final EditText ed_oldpw = (EditText) dialog.findViewById(R.id.ed_oldpw);
        final EditText ed_newpw = (EditText) dialog.findViewById(R.id.ed_newpw);
        final EditText ed_confirmpw = (EditText) dialog.findViewById(R.id.ed_confirmpw);
        Button btn_clear = (Button) dialog.findViewById(R.id.btn_clear);
        Button btn_save = (Button) dialog.findViewById(R.id.btn_save);

        img_cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldpw = ed_oldpw.getText().toString().trim();
                String newpw = ed_newpw.getText().toString().trim();
                String confirmpw = ed_confirmpw.getText().toString().trim();

                if (oldpw.equals("")) {
                    ed_oldpw.setError("Old password should not be empty");
                    return;
                } else if (newpw.equals("")) {
                    ed_newpw.setError("New password should not be empty");
                    return;
                } else if (confirmpw.equals("")) {
                    ed_confirmpw.setError("Confirm password should not be empty");
                    return;
                } else if (!confirmpw.equals(newpw)) {
                    ed_confirmpw.setError("New password and Confirm password should be same");
                } else {
                    Log.e("access token----", "" + Fragment_Menu.access_tokn);
                    new Json_Changepw(getActivity()).execute(oldpw, newpw, confirmpw, Fragment_Menu.access_tokn);
                }
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ed_oldpw.setText("");
                ed_newpw.setText("");
                ed_confirmpw.setText("");
            }
        });

        dialog.show();

    }


    public void dialogorderhistory() {

        dialog1 = new Dialog(getActivity(), android.R.style.Theme_Translucent) {
            @Override
            public boolean onTouchEvent(MotionEvent event) {
                // Tap anywhere to close dialog.
                dialog1.dismiss();
                return true;
            }
        };

        dialog1.setCanceledOnTouchOutside(true);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog1.setCancelable(true);
        dialog1.setContentView(R.layout.dialog_orderhistory);

        listView = (ListView) dialog1.findViewById(R.id.listView);
        tv_msg=(TextView) dialog1.findViewById(R.id.tv_msg);
     //   listView.setAdapter(new OrderHistoryAdapter(getActivity()));

        new Json_OrderHistory(getActivity(),listView).execute(Fragment_Menu.access_tokn);

        // TextView tv_cncl = (TextView) dialog.findViewById(R.id.tv_cncl);


        // dialog.dismiss();

        dialog1.show();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
