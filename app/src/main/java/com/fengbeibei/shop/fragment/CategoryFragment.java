package com.fengbeibei.shop.fragment;

import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.adapter.CategoryAdapter;
import com.fengbeibei.shop.adapter.CategoryExpandableListAdapter;
import com.fengbeibei.shop.adapter.CategoryGridViewAdapter;
import com.fengbeibei.shop.bean.Category;
import com.fengbeibei.shop.common.Constants;
import com.fengbeibei.shop.common.HttpClientHelper;
import com.fengbeibei.shop.common.HttpClientHelper.CallBack;
import com.fengbeibei.shop.fragment.Base.BaseFragment;
import com.fengbeibei.shop.widget.MyGridView;

import org.apache.http.HttpStatus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CategoryFragment extends BaseFragment {
    @BindView(R.id.parentCategory)
	ListView mParentCategoryLayout;
    @BindView(R.id.childCategory)
	ExpandableListView mChildCategoryLayout;
	private CategoryAdapter mCategoryAdapter;

	private CategoryExpandableListAdapter mExpandableListAdapter;
	private List<List<Category>> mChildCategory = new ArrayList<List<Category>>();
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View categoryLayout = inflater.inflate(getLayoutId(),container, false);
        mInflater = inflater;
		return categoryLayout;
	}

    @Override
    protected int getLayoutId() {
        return R.layout.category;
    }

    @Override
    public void initData() {
        initParentCategory();
        getChildCategory("1");
    }

    @Override
    public void initView() {
     	initData();
    }

    public void initParentCategory(){
		HttpClientHelper.asynGet(Constants.PARENT_CATEGORY_URL, new CallBack(){

			@Override
			public void onFinish(Message response) {
				// TODO Auto-generated method stub
				if(response.what == HttpStatus.SC_OK){
					String json = (String) response.obj;
					try{
						JSONObject obj = new JSONObject(json);
						String categoryJson= obj.getString("class_list");
						ArrayList<Category> parentCategory = Category.newIntance(categoryJson);
						mCategoryAdapter = new CategoryAdapter(CategoryFragment.this.getContext());
						mCategoryAdapter.setSelectedPosition(0);
						mCategoryAdapter.setCategoryData(parentCategory);
						
						mParentCategoryLayout.setAdapter(mCategoryAdapter);
						mParentCategoryLayout.setOnItemClickListener(new OnItemClickListener(){

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								int itemPosition = mCategoryAdapter.getSelectedPosition();
								Category category =(Category) mParentCategoryLayout.getItemAtPosition(arg2);
								mCategoryAdapter.setSelectedPosition(arg2);
						        mCategoryAdapter.notifyDataSetInvalidated();
								if(category != null && !(itemPosition == arg2) ){
									getChildCategory(category.getGcId());
								}
							}
							
						});
				
					} catch(JSONException e){
						e.printStackTrace();
					}
					
				}
			}

			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	
	public void getChildCategory(String gcId){
		String url = Constants.CHILD_CATEGORY_URL+"&gc_id="+gcId;
		HttpClientHelper.asynGet(url, new CallBack(){

			@Override
			public void onFinish(Message response) {
				// TODO Auto-generated method stub
				if(response.what == HttpStatus.SC_OK){

					String json = (String)response.obj;
					try{
						JSONObject obj = new JSONObject(json);
						if(!obj.isNull("class_list")){
							JSONArray arr = new JSONArray(obj.getString("class_list"));
							int size = arr == null ? 0 : arr.length();
							for( int i = 0 ; i < size ; i ++){
								JSONObject cateObj = arr.getJSONObject(i);
								JSONObject cateJsonObj = new JSONObject(cateObj.toString());
								String parentId = cateJsonObj.getString("gc_id");
								String parentName = cateJsonObj.getString("gc_name");

							//	View childCategoryView =	getActivity().getLayoutInflater().inflate(R.layout.child_category_item, null);
							//	TextView childCategoryTitle = (TextView) childCategoryView.findViewById(R.id.categoryName);
							//	childCategoryTitle.setText(parentName);

						
								if(!cateJsonObj .isNull("child")){
										String childJson = cateJsonObj.optString("child");
										MyGridView categoryGridView = (MyGridView)childCategoryView.findViewById(R.id.categoryGridView);
										List<Category> childCategoryData = Category.newIntance(childJson);
										CategoryGridViewAdapter  categoryGridViewAdapter = new CategoryGridViewAdapter(CategoryFragment.this.getContext(),childCategoryData);
										categoryGridView.setAdapter(categoryGridViewAdapter);
								}
								mChildCategoryLayout.addView(childCategoryView);
							}
						}
						
					} catch (JSONException e){
						e.printStackTrace();
					}
				}
			}

			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				
			}
			
		});
	}
	

}
