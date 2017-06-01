package com.zihai.shop.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.zihai.shop.R;
import com.zihai.shop.activity.SearchResultActivity;
import com.zihai.shop.bean.Category;
import com.zihai.shop.common.AnimateFirstDisplayListener;
import com.zihai.shop.common.SystemHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

public class CategoryGridViewAdapter extends BaseAdapter {
	private List<Category> mCategoryList;
	private Context mContext;
	private ImageLoader mImageLoader = ImageLoader.getInstance();
	private DisplayImageOptions mOptions = SystemHelper.getDisplayImageOptions();
	private ImageLoadingListener mAnimateFirstListener = new AnimateFirstDisplayListener();
	class ViewHolder{
		TextView gcName;
		ImageView gcImage;
	}

	public CategoryGridViewAdapter(Context context,List<Category> categoryList) {

		mContext = context;
		mCategoryList = categoryList;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		return mCategoryList.size();
	}

	@Override
	public Object getItem(int position) {
		return mCategoryList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		ViewHolder holder;
		if ( convertView == null){
			convertView = LayoutInflater.from(mContext).inflate(R.layout.category_child_grid, null);
			holder = new ViewHolder();
			holder.gcName = (TextView)convertView.findViewById(R.id.gcName);
			holder.gcImage = (ImageView)convertView.findViewById(R.id.gcImage);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		Category category = mCategoryList.get(position);
		holder.gcName.setText(category.getGcName());
		mImageLoader.displayImage(category.getGcImage(), holder.gcImage, mOptions,  mAnimateFirstListener );
		setOnClickListener(convertView,category.getGcId());
		return convertView;
	}

	public void setOnClickListener(View view,final String data){
		view.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(mContext,SearchResultActivity.class);
				intent.putExtra("gcId", data);
				mContext.startActivity(intent);
			}
			
		});
	}

}
