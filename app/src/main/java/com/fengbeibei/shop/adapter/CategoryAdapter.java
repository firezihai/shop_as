package com.fengbeibei.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.bean.Category;

import java.util.ArrayList;

public class CategoryAdapter extends BaseAdapter{
	private int selectedPosition = -1;
	private ArrayList<Category> mCategoryData;
	private LayoutInflater mInflater;
	private Context mContext;
	class ViewHolder {
		TextView gcName;
		View gcImage;
	}

	public CategoryAdapter(Context context) {
		super();
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
	}

	public void setCategoryData(ArrayList<Category> categoryData){
		mCategoryData = categoryData;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mCategoryData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mCategoryData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.category_parent_item, arg2,false);
			holder = new ViewHolder();
			holder.gcName = (TextView)convertView.findViewById(R.id.gcName);
			holder.gcImage = (View)convertView.findViewById(R.id.gcImage);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		Category categoryData = mCategoryData.get(position);
		holder.gcName.setText(categoryData.getGcName());
	  if (selectedPosition == position) {
			holder.gcName.setTextColor( mContext.getResources().getColor(R.color.orange));
        } else {
        	holder.gcName.setTextColor( mContext.getResources().getColor(R.color.black));
        }
		return convertView;
	}

	public void setSelectedPosition(int position) {
        selectedPosition = position;
    }

	public int getSelectedPosition(){
		return  selectedPosition;
	}
}
