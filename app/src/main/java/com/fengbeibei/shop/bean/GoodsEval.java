package com.fengbeibei.shop.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/7/30.
 */
public class GoodsEval {

    /**
     * geval_goodsid : 9597
     * geval_scores : 5
     * geval_content : 皮质很好，实物和图片没什么差异
     * geval_addtime : 1452220308
     * geval_frommembername : 墨元
     * geval_frommemberid : 145
     * member_avatar : http://www.fengbeibei.com/data/upload/shop/common/default_user_portrait.gif
     */

    @SerializedName("geval_goodsid")
    private String gevalGoodsid;
    @SerializedName("geval_scores")
    private String gevalScores;
    @SerializedName("geval_content")
    private String gevalContent;
    @SerializedName("geval_addtime")
    private String gevalAddtime;
    @SerializedName("geval_frommembername")
    private String gevalFrommembername;
    @SerializedName("geval_frommemberid")
    private String gevalFrommemberid;
    @SerializedName("member_avatar")
    private String memberAvatar;

    public static GoodsEval objectFromData(String str) {

        return new Gson().fromJson(str, GoodsEval.class);
    }

    public static GoodsEval objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), GoodsEval.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<GoodsEval> arrayGoodsEvalFromData(String str) {

        Type listType = new TypeToken<ArrayList<GoodsEval>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<GoodsEval> arrayGoodsEvalFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<GoodsEval>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public String getGevalGoodsid() {
        return gevalGoodsid;
    }

    public void setGevalGoodsid(String gevalGoodsid) {
        this.gevalGoodsid = gevalGoodsid;
    }

    public String getGevalScores() {
        return gevalScores;
    }

    public void setGevalScores(String gevalScores) {
        this.gevalScores = gevalScores;
    }

    public String getGevalContent() {
        return gevalContent;
    }

    public void setGevalContent(String gevalContent) {
        this.gevalContent = gevalContent;
    }

    public String getGevalAddtime() {
        return gevalAddtime;
    }

    public void setGevalAddtime(String gevalAddtime) {
        this.gevalAddtime = gevalAddtime;
    }

    public String getGevalFrommembername() {
        return gevalFrommembername;
    }

    public void setGevalFrommembername(String gevalFrommembername) {
        this.gevalFrommembername = gevalFrommembername;
    }

    public String getGevalFrommemberid() {
        return gevalFrommemberid;
    }

    public void setGevalFrommemberid(String gevalFrommemberid) {
        this.gevalFrommemberid = gevalFrommemberid;
    }

    public String getMemberAvatar() {
        return memberAvatar;
    }

    public void setMemberAvatar(String memberAvatar) {
        this.memberAvatar = memberAvatar;
    }
}
