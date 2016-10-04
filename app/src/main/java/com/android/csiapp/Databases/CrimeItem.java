package com.android.csiapp.Databases;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by AnitaLin on 2016/9/9.
 */
public class CrimeItem implements Serializable {
    // 編號、日期時間、顏色、標題、內容、檔案名稱、經緯度、修改、已選擇
    //Page 1
    private long id;
    private String mCasetype;
    private String mArea;
    private String mLocation;
    private String mOccurred_start_time;
    private String mOccurred_end_time;
    private String mGet_access_time;
    private String mUnitsAssigned;
    private String mAccessPolicemen;
    private String mAccess_start_time;
    private String mAccess_end_time;
    private String mAccessLocation;
    private String mCaseOccurProcess;
    private String mSceneCondition;
    private String mWeatherCondition;
    private String mWindDirection;
    private String mTemperature;
    private String mHumidity;
    private String mAccessReason;

    //Page 2 (New people)
    private List<RelatedPeopleItem> mReleatedPeopleItem;

    //Page 2 (New Item)
    private List<LostItem> mLostItem;

    //Page 2 (New Tool)
    private List<CrimeToolItem> mCrimeToolItem;

    //Page 7
    private String mCrimePeopleNumber;
    private String mCrimeMeans;
    private String mCrimeCharacter;
    private String mCrimeEntrance;
    private String mCrimeTiming;
    private String mSelectObject;
    private String mCrimeExport;
    private String mCrimePeopleFeature;
    private String mCrimeFeature;
    private String mIntrusiveMethod;
    private String mSelectLocation;
    private String mCrimePurpose;

    //Page 8 (New Witness)
    private List<WitnessItem> mWitnessItem;

    public CrimeItem() {
        this.id = 0;
        this.mCasetype = "";
        this.mArea = "";
        this.mLocation = "";
        this.mOccurred_start_time = "";
        this.mOccurred_end_time = "";
        this.mGet_access_time = "";
        this.mUnitsAssigned = "";
        this.mAccessPolicemen = "";
        this.mAccess_start_time = "";
        this.mAccess_end_time = "";
        this.mAccessLocation = "";
        this.mCaseOccurProcess = "";
        this.mSceneCondition = "";
        this.mWeatherCondition = "";
        this.mWindDirection = "";
        this.mTemperature = "";
        this.mHumidity = "";
        this.mAccessReason = "";

        this.mReleatedPeopleItem = new ArrayList<RelatedPeopleItem>();
        this.mLostItem = new ArrayList<LostItem>();
        this.mCrimeToolItem = new ArrayList<CrimeToolItem>();

        this.mCrimePeopleNumber = "";
        this.mCrimeMeans = "";
        this.mCrimeCharacter = "";
        this.mCrimeEntrance = "";
        this.mCrimeTiming = "";
        this.mSelectObject = "";
        this.mCrimeExport = "";
        this.mCrimePeopleFeature = "";
        this.mCrimeFeature = "";
        this.mIntrusiveMethod = "";
        this.mSelectLocation = "";
        this.mCrimePurpose = "";

        this.mWitnessItem = new ArrayList<WitnessItem>();
    }

    public CrimeItem(long id, String casetype, String area, String time) {
        this.id = id;
        this.mCasetype = casetype;
        this.mArea = area;
        this.mOccurred_start_time = time;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    //Page 1
    public String getCasetype() {
        return mCasetype;
    }

    public void setCasetype(String casetype) {
        this.mCasetype = casetype;
    }

    public String getArea() {
        return mArea;
    }

    public void setArea(String area) {
        this.mArea = area;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocationa(String location) {
        this.mLocation = location;
    }

    public String getOccurredStartTime() {
        return mOccurred_start_time;
    }

    public void setOccurredStartTime(String time) {
        this.mOccurred_start_time = time;
    }

    public String getOccurredEndTime() {
        return mOccurred_end_time;
    }

    public void setOccurredEndTime(String time) {
        this.mOccurred_end_time = time;
    }

    public String getGetAccessTime() {
        return mGet_access_time;
    }

    public void setGetAccessTime(String time) {
        this.mGet_access_time = time;
    }

    public String getUnitsAssigned () {
        return mUnitsAssigned;
    }

    public void setUnitsAssigned (String unitsAssigned ) {
        this.mUnitsAssigned = unitsAssigned ;
    }

    public String getAccessPolicemen () {
        return mAccessPolicemen;
    }

    public void setAccessPolicemen (String accessPolicemen ) {
        this.mAccessPolicemen = accessPolicemen ;
    }

    public String getAccessStartTime () {
        return mAccess_start_time;
    }

    public void setAccessStartTime (String time ) {
        this.mAccess_start_time = time ;
    }

    public String getAccessEndTime () {
        return mAccess_end_time;
    }

    public void setAccessEndTime (String time ) {
        this.mAccess_end_time = time ;
    }

    public String getAccessLocation () {
        return mAccessLocation;
    }

    public void setAccessLocation (String accessLocation ) {
        this.mAccessLocation = accessLocation ;
    }

    public String getCaseOccurProcess () {
        return mCaseOccurProcess;
    }

    public void setCaseOccurProcess(String caseOccurProcess ) {
        this.mCaseOccurProcess = caseOccurProcess ;
    }

    public String getSceneCondition () {
        return mSceneCondition;
    }

    public void setSceneCondition (String sceneCondition ) {
        this.mSceneCondition = sceneCondition ;
    }

    public String getWeatherCondition () {
        return mWeatherCondition;
    }

    public void setWeatherCondition (String weatherCondition ) {
        this.mWeatherCondition = weatherCondition ;
    }

    public String getWindDirection () {
        return mWindDirection;
    }

    public void setWindDirection (String windDirection ) {
        this.mWindDirection = windDirection ;
    }

    public String getTemperature() {
        return mTemperature;
    }

    public void setTemperature (String temperature ) {
        this.mTemperature = temperature ;
    }

    public String getHumidity () {
        return mHumidity;
    }

    public void setHumidity (String humidity ) {
        this.mHumidity = humidity ;
    }

    public String getAccessReason () {
        return mAccessReason;
    }

    public void setAccessReason (String accessReason ) {
        this.mAccessReason = accessReason ;
    }

    //Page 2 (New People)
    public List<RelatedPeopleItem> getReleatedPeople() {return mReleatedPeopleItem; }

    public void setReleatedPeople(List<RelatedPeopleItem> relatedPeopleItem) {this.mReleatedPeopleItem = relatedPeopleItem; }

    //Page 2 (New Item)
    public List<LostItem> getLostItem() {return mLostItem; }

    public void setLostItem(List<LostItem> lostItem) {this.mLostItem = lostItem; }

    //Page 2  (New Tool)
    public List<CrimeToolItem>  getCrimeTool() {return mCrimeToolItem; }

    public void setCrimeTool(List<CrimeToolItem>  crimeTool) {this.mCrimeToolItem = crimeTool; }

    //Page 7
    public String getCrimePeopleNumber() {return mCrimePeopleNumber; }

    public void setCrimePeopleNumber(String crimePeopleNumber) {this.mCrimePeopleNumber = crimePeopleNumber; }

    public String getCrimeMeans() {return mCrimeMeans; }

    public void setCrimeMeans(String crimeMeans) {this.mCrimeMeans = crimeMeans; }

    public String getCrimeCharacter() {return mCrimeCharacter; }

    public void setCrimeCharacter(String crimeCharacter) {this.mCrimeCharacter = crimeCharacter; }

    public String getCrimeEntrance() {return mCrimeEntrance; }

    public void setCrimeEntrance(String crimeEntrance) {this.mCrimeEntrance = crimeEntrance; }

    public String getCrimeTiming() {return mCrimeTiming; }

    public void setCrimeTiming(String crimeTiming) {this.mCrimeTiming = crimeTiming; }

    public String getSelectObject() {return mSelectObject; }

    public void setSelectObject(String selectObject) {this.mSelectObject = selectObject; }

    public String getCrimeExport() {return mCrimeExport; }

    public void setCrimeExport(String crimeExport) {this.mCrimeExport = crimeExport; }

    public String getCrimePeopleFeature() {return mCrimePeopleFeature; }

    public void setCrimePeopleFeature(String peopleFeature) {this.mCrimePeopleFeature = peopleFeature; }

    public String getCrimeFeature() {return mCrimeFeature; }

    public void setCrimeFeature(String crimeFeature) {this.mCrimeFeature = crimeFeature; }

    public String getIntrusiveMethod() {return mIntrusiveMethod; }

    public void setIntrusiveMethod(String intrusiveMethod) {this.mIntrusiveMethod = intrusiveMethod; }

    public String getSelectLocation() {return mSelectLocation; }

    public void setSelectLocation(String selectLocation) {this.mSelectLocation = selectLocation; }

    public String getCrimePurpose() {return mCrimePurpose; }

    public void setCrimePurpose(String crimePurpose) {this.mCrimePurpose = crimePurpose; }

    //Page 8 (New Witness)
    public List<WitnessItem> getWitness() {return mWitnessItem; }

    public void setWitness(List<WitnessItem> witness) {this.mWitnessItem = witness; }

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

    public static String getCurrentDate(Calendar c) { //輸出格式製作
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
        sb.append("日");
        return sb.toString();
    }
}
