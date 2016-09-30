package com.android.csiapp.Databases;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by AnitaLin on 2016/9/9.
 */
public class CrimeItem implements Serializable {
    // 編號、日期時間、顏色、標題、內容、檔案名稱、經緯度、修改、已選擇
    private long id;
    private String casetype;
    private String area;
    private String time;

    //Page 2 (New people)
    private String mPeople_releation;
    private String mPeople_name;
    private String mPeople_sex;
    private String mPeople_id;
    private String mPeople_number;
    private String mPeople_address;

    //Page 2 (New Item)
    private String mItem_name;
    private String mItem_brand;
    private String mItem_amount;
    private String mItem_value;
    private String mItem_feature;

    //Page 2 (New Tool)
    private String mTool_name;
    private String mTool_category;
    private String mTool_source;

    //Page 8 (New Witness)
    private String mWitness_name;
    private String mWitness_sex;
    private String mWitness_birthday;
    private String mWitness_number;
    private String mWitness_address;

    public CrimeItem() {
        this.id = 0;
        this.casetype = "";
        this.area = "";
        this.time = "";

        this.mPeople_releation = "";
        this.mPeople_name = "";
        this.mPeople_sex = "";
        this.mPeople_id = "";
        this.mPeople_number = "";
        this.mPeople_address = "";

        this.mItem_name = "";
        this.mItem_brand = "";
        this.mItem_amount = "";
        this.mItem_value = "";
        this.mItem_feature = "";

        this.mTool_name = "";
        this.mTool_category = "";
        this.mTool_source = "";

        this.mWitness_name = "";
        this.mWitness_sex = "";
        this.mWitness_birthday = "";
        this.mWitness_number = "";
        this.mWitness_address = "";
    }

    public CrimeItem(long id, String casetype, String area, String time) {
        this.id = id;
        this.casetype = casetype;
        this.area = area;
        this.time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCasetype() {
        return casetype;
    }

    public void setCasetype(String casetype) {
        this.casetype = casetype;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    //Page 2 (New People)
    public String getPeopleReleation() {return mPeople_releation; }

    public void setPeopleReleation(String people_releation) {this.mPeople_releation = people_releation; }

    public String getPeopleName() {return mPeople_name; }

    public void setPeopleName(String people_name) {this.mPeople_name = people_name; }

    public String getPeopleSex() {return mPeople_sex; }

    public void setPeopleSex(String people_sex) {this.mPeople_sex = people_sex; }

    public String getPeopleId() {return mPeople_id; }

    public void setPeopleId(String people_id) {this.mPeople_id = people_id; }

    public String getPeopleNumber() {return mPeople_number; }

    public void setPeopleNumber(String people_number) {this.mPeople_number = people_number; }

    public String getPeopleAddress() {return mPeople_address; }

    public void setPeopleAddress(String people_address) {this.mPeople_address = people_address; }

    //Page 2 (New Item)
    public String getItemName() {return mItem_name; }

    public void setItemName(String item_name) {this.mItem_name = item_name; }

    public String getItemBrand() {return mItem_brand; }

    public void setItemBrand(String item_brand) {this.mItem_brand = item_brand; }

    public String getItemAmount() {return mItem_amount; }

    public void setItemAmount(String item_amount) {this.mItem_amount = item_amount; }

    public String getItemValue() {return mItem_value; }

    public void setItemVlaue(String item_value) {this.mItem_value = item_value; }

    public String getItemFeature() {return mItem_feature; }

    public void setItemFeatue(String item_feature) {this.mItem_feature = item_feature; }

    //Page 2  (New Tool)
    public String getToolName() {return mTool_name; }

    public void setToolName(String tool_name) {this.mTool_name = tool_name; }

    public String getToolCategory() {return mTool_category; }

    public void setToolCategory(String tool_category) {this.mTool_category = tool_category; }

    public String getToolSource() {return mTool_source; }

    public void setToolSource(String tool_source) {this.mTool_source = tool_source; }

    //Page 8 (New Witness)
    public String getWitnessName() {return mWitness_name; }

    public void setWitnessName(String witness_name) {this.mWitness_name = witness_name; }

    public String getWitnessSex() {return mWitness_sex; }

    public void setWitnessSex(String witness_sex) {this.mWitness_sex = witness_sex; }

    public String getWitnessBirthday() {return mWitness_birthday; }

    public void setWitnessBirthday(String witness_birthday) {this.mWitness_birthday = witness_birthday; }

    public String getWitnessNumber() {return mWitness_number; }

    public void setWitnessNumber(String witness_number) {this.mWitness_number = witness_number; }

    public String getWitnessAddress() {return mWitness_address; }

    public void setWitnessAddress(String witness_address) {this.mWitness_address = witness_address; }


    public static String getCurrentTime(Calendar c) { //輸出格式製作
        int[] a={c.get(Calendar.YEAR),
                c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH),
                c.get(Calendar.HOUR_OF_DAY),
                c.get(Calendar.MINUTE),
                c.get(Calendar.SECOND)
        };
        StringBuffer sb=new StringBuffer();
        sb.append(a[0]);
        sb.append("年");
        if (a[1]<9) {sb.append("0" + (a[1] + 1));}   //加 1 才會得到實際月份
        else {sb.append("" + (a[1] + 1));}
        sb.append("月");
        if (a[2]<10) {sb.append("0" + (a[2]));}
        else {sb.append("" + (a[2]));}
        sb.append("日\n");
        if (a[3]<10) {sb.append(" 0" + (a[3]));}
        else {sb.append("" + (a[3]));}
        sb.append("時");
        if (a[4]<10) {sb.append("0" + a[4]);}
        else {sb.append("" + a[4]);}
        sb.append("分");
        //if (a[5]<10) {sb.append(":0" + a[5]);}
        //selse {sb.append(":" + a[5]);}
        return sb.toString();
    }
}