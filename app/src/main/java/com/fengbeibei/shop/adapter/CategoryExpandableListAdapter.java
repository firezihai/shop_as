package com.fengbeibei.shop.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.bean.Category;
import com.fengbeibei.shop.widget.MyGridView;

import java.util.List;

/**
 * @author zihai(https://github.com/firezihai)
 * @created 2016-08-07 22:12
 */
public class CategoryExpandableListAdapter extends BaseExpandableListAdapter {
    private String TAG = "CateExpandableListAdapter";
    private List<Category> mParentCategory;
    private List<List<Category>> mChildCategory;
    private LayoutInflater mInflater;
    private Context mContext;

    public CategoryExpandableListAdapter(Context context,List<Category> parentCategory, List<List<Category>> childCategory) {
        mParentCategory = parentCategory;
        mChildCategory = childCategory;
        mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getGroupCount() {
        return mParentCategory.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
      //  Log.i(TAG,"childrenCount:"+mChildCategory.size()+" groupPosition  "+groupPosition+" mParentCategory "+mParentCategory + " "+mChildCategory);
   //     Log.i(TAG,"mChildCategory:"+mChildCategory);
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mParentCategory.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mChildCategory.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView textView;
        if(convertView == null){
            textView = new TextView(mContext);
        }else{
            textView = (TextView)convertView;
        }
        Category category = mParentCategory.get(groupPosition);
        textView.setText(category.getGcName());
        textView.setTextSize(12);
        textView.setPadding(36, 10, 0, 10);
        return textView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.child_category_item,null);
            viewHolder.myGridView = (MyGridView)convertView.findViewById(R.id.categoryGridView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }

        List<Category> categoryList = mChildCategory.get(groupPosition);
        CategoryGridViewAdapter  categoryGridViewAdapter = new CategoryGridViewAdapter(mContext,categoryList);
        viewHolder.myGridView.setAdapter(categoryGridViewAdapter);
        categoryGridViewAdapter.notifyDataSetChanged();
        Log.i(TAG,"getChildView"+categoryList);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    class ViewHolder {
        MyGridView myGridView;
    }

}
