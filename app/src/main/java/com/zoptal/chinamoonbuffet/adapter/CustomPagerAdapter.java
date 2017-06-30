package com.zoptal.chinamoonbuffet.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.zoptal.chinamoonbuffet.JsonClasses.Json_sliderimages;
import com.zoptal.chinamoonbuffet.R;



public class CustomPagerAdapter extends PagerAdapter {

    Context mContext;
    LayoutInflater mLayoutInflater;
//    int[] mResources = {
//            R.drawable.img3,
//            R.drawable.img3,
//            R.drawable.img3,
//            R.drawable.img3,

  //  };
    public CustomPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return Json_sliderimages.al_sliderimages.size();
       // return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }



    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);


        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);


//        Log.e("ist img", "" + Json_ProductDetail1.img_array.get(0));
//        Log.e("2nd img", "" + Json_ProductDetail1.img_array.get(1));
//        Log.e("3rd img", "" + Json_ProductDetail1.img_array.get(2));
//        Log.e("4thimg", "" + Json_ProductDetail1.img_array.get(3));



        Picasso.with(mContext).load(Json_sliderimages.al_sliderimages.get(position).getImage()).into(imageView);
//        Picasso.with(mContext).load(Json_ProductDetail1.img_array.get(1)).into(imageView);
//        Picasso.with(mContext).load(Json_ProductDetail1.img_array.get(2)).into(imageView);
//        Picasso.with(mContext).load(Json_ProductDetail1.img_array.get(3)).into(imageView);
      // imageView.setImageResource(Integer.parseInt(Json_ProductDetail1.img_array.get(position)));
//      try{
//        Picasso.with(mContext).load(Json_ProductDetail1.model_postAdData.getProduct_img()).into(imageView.ge);
//        Picasso.with(mContext).load(Json_ProductDetail1.img_array.get(1)).into(imageView);
//        Picasso.with(mContext).load(Json_ProductDetail1.img_array.get(2)).into(imageView);
//        Picasso.with(mContext).load(Json_ProductDetail1.img_array.get(3)).into(imageView);
//    }
//    catch (Exception e){}

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}