package com.zihai.shop.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import com.zihai.shop.R;
import com.zihai.shop.bean.GoodsEval;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/8/1.
 */
public class GoodsEvalAdapter extends BaseAdapter{
    private Context mContext;
    private List<GoodsEval> mGoodsEvalList;
    private LayoutInflater mInflater;
    public GoodsEvalAdapter(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);

    }
    public void setData(List<GoodsEval> goodsEvalList){
        if(mGoodsEvalList != null && !goodsEvalList.isEmpty()){
            mGoodsEvalList.addAll(goodsEvalList);
        }else{
            mGoodsEvalList = goodsEvalList;
        }
    }
    @Override
    public int getCount() {
        return mGoodsEvalList == null ? 0 : mGoodsEvalList.size();
    }

    @Override
    public Object getItem(int position) {
        return mGoodsEvalList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = mInflater.inflate(R.layout.goods_eval_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.evalUserName = (TextView) convertView.findViewById(R.id.evalUserName);
            viewHolder.evalContent = (TextView) convertView.findViewById(R.id.evalContent);
            viewHolder.evalDate = (TextView) convertView.findViewById(R.id.evalDate);
            viewHolder.evalRating = (RatingBar) convertView.findViewById(R.id.evalRating);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GoodsEval goodsEval = mGoodsEvalList.get(position);
        viewHolder.evalUserName.setText(goodsEval.getGevalFrommembername());
        viewHolder.evalContent.setText(goodsEval.getGevalContent());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
        Date date = new Date(Integer.parseInt(goodsEval.getGevalAddtime()));
        viewHolder.evalDate.setText(sdf.format(date));
        viewHolder.evalRating.setRating(Float.parseFloat(goodsEval.getGevalScores()));
        return convertView;
    }

    class ViewHolder{
        TextView evalUserName;
        TextView evalContent;
        TextView evalDate;
        RatingBar evalRating;
    }
}
