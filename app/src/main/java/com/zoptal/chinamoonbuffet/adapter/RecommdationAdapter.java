package com.zoptal.chinamoonbuffet.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zoptal.chinamoonbuffet.R;

public class RecommdationAdapter extends PagerAdapter {

	Context mContext;
	LayoutInflater mLayoutInflater;
	int[] mResources = {


	};
	public RecommdationAdapter(Context context) {
		mContext = context;
		mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {

	return 4;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == ((LinearLayout) object);
	}



	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		View itemView = mLayoutInflater.inflate(R.layout.customview_recommdation, container, false);


		//ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);



		//imageView.setImageResource(mResources[position]);
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