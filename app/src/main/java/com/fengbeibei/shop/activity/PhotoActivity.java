package com.fengbeibei.shop.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fengbeibei.shop.R;
import com.fengbeibei.shop.common.AnimateFirstDisplayListener;
import com.fengbeibei.shop.common.SystemHelper;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


import java.util.ArrayList;


import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Administrator on 2016/7/28.
 */
public class PhotoActivity extends Activity{
    @BindView(R.id.goodsImageViewPager) ViewPager mViewPager;
    @BindView(R.id.textIndex) TextView mTextView;
    /*图片缓存*/
    static ImageLoader mImageLoader = ImageLoader.getInstance();
    static DisplayImageOptions mOptions = SystemHelper.getDisplayImageOptions();
    static ImageLoadingListener mAnimateListener = new AnimateFirstDisplayListener();

    private PhotoViewAttacher mPhotoViewAttacher;

    private ArrayList<ImageView> mGoodsImageData = new ArrayList<ImageView>();
    private int mImageSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_photo);
        ButterKnife.bind(this);


        Intent intent = getIntent();
        String[] goodsImages = intent.getStringArrayExtra("images");
        int position = intent.getIntExtra("position", 0);


        if( goodsImages.length<=0){
            Toast.makeText(this,"内容错误",Toast.LENGTH_LONG).show();
            finish();
        }




    /*    for(int i=0 ;i<goodsImages.length;i++){

            String url = goodsImages[i];
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mImageLoader.displayImage(url, imageView, mOptions, );

            mGoodsImageData.add(imageView);
        }

        mImageSize = mGoodsImageData.size();*/
        mImageSize = goodsImages.length;
        GoodsImagePagerAdapter adapter = new GoodsImagePagerAdapter(goodsImages);
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(position);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                mTextView.setText((position + 1) + "/" + mImageSize);
                //mPhotoViewAttacher.update();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

      //  mPhotoViewAttacher = new PhotoViewAttacher(mGoodsImageData.get(position));
    ///    mPhotoViewAttacher.update();

        position = position+1;
        String text = position+"/"+ mImageSize;
        mTextView.setText(text);

    }

    /*private static class MyImageLoadingListener extends SimpleImageLoadingListener{

        private
        static final List<String> displayedImages = Collections.synchronizedList(new LinkedList<String>());
        @Override
        public void onLoadingComplete(String imageUri, View arg1, Bitmap loadedImage) {
            // TODO Auto-generated method stub
            if (loadedImage != null) {
                ImageView imageView = (ImageView) arg1;
                // 是否第一次显示
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    // 图片淡入效果
                    FadeInBitmapDisplayer.animate(imageView, 500);
                    displayedImages.add(imageUri);
                }
            }
        }

    }*/

    static class GoodsImagePagerAdapter extends PagerAdapter {

        private String[] mData;

        public GoodsImagePagerAdapter(String[] data) {
            mData = data;
        }

        @Override
        public int getCount() {
            return mData.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            //photoView.setImageResource(sDrawables[position]);
            String url = mData[position];
            url =url.replace("_360","_1280");
            mImageLoader.displayImage(url,photoView,mOptions,mAnimateListener );
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
