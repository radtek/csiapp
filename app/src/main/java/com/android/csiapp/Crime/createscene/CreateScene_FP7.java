package com.android.csiapp.Crime.createscene;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.csiapp.Crime.utils.ClearableEditText;
import com.android.csiapp.Crime.utils.CreateSceneUtils;
import com.android.csiapp.Crime.utils.DictionaryInfo;
import com.android.csiapp.Crime.utils.tree.TreeViewListActivity;
import com.android.csiapp.Databases.CrimeItem;
import com.android.csiapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CreateScene_FP7 extends Fragment{

    private Context context = null;
    private CrimeItem mItem;
    private int mEvent;

    private TextView mPeopleNumberText, mCrimeMeansText, mCrimeCharacterText, mCrimeEntranceText, mCrimeTimingText, mSelectObjectText;
    private TextView mCrimeExportText, mCrimeFeatureText, mIntrusiveMethodText, mSelectLocationText, mCrimePurposeText;

    private ClearableEditText mPeopleFeature;

    public CreateScene_FP7() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_scene_fp7, container, false);
        CreateSceneActivity activity  = (CreateSceneActivity) getActivity();
        if(savedInstanceState == null){
            mItem = activity.getItem();
            mEvent = activity.getEvent();
        }else{
            mItem = (CrimeItem) savedInstanceState.getSerializable("CrimeItem");
            mEvent = (int) savedInstanceState.getSerializable("Event");
        }
        context = getActivity().getApplicationContext();

        initView(view);
        initData();

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("CrimeItem",mItem);
        outState.putSerializable("Event",mEvent);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            savedInstanceState.putSerializable("CrimeItem", mItem);
            savedInstanceState.putSerializable("Event", mEvent);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = "";
        if (resultCode == Activity.RESULT_OK) {
            if(requestCode == CreateSceneUtils.EVENT_PEOPLE_NUMBER_SELECT_ITEM){
                result = (String) data.getStringExtra("Select");
                mItem.setCrimePeopleNumber(result);
                result = DictionaryInfo.getDictValue(DictionaryInfo.mPeopleNumberKey, result);
                mPeopleNumberText.setText(result);
            }else if(requestCode == CreateSceneUtils.EVENT_CRIME_MEANS_SELECT_ITEM){
                result = (String) data.getStringExtra("Select");
                mItem.setCrimeMeans(result);
                result = DictionaryInfo.getDictValue(DictionaryInfo.mCrimeMeansKey, result);
                mCrimeMeansText.setText(result);
            }else if(requestCode == CreateSceneUtils.EVENT_CRIME_CHARACTER_SELECT_ITEM){
                result = (String) data.getStringExtra("Select");
                mItem.setCrimeCharacter(result);
                result = DictionaryInfo.getDictValue(DictionaryInfo.mCrimeCharacterKey, result);
                mCrimeCharacterText.setText(result);
            }else if(requestCode == CreateSceneUtils.EVENT_CRIME_ENTRANCE_SELECT_ITEM){
                result = (String) data.getStringExtra("Select");
                mItem.setCrimeEntrance(result);
                result = DictionaryInfo.getDictValue(DictionaryInfo.mCrimeEntranceExportKey, result);
                mCrimeEntranceText.setText(result);
            }else if(requestCode == CreateSceneUtils.EVENT_CRIME_TIMING_SELECT_ITEM){
                result = (String) data.getStringExtra("Select");
                mItem.setCrimeTiming(result);
                result = DictionaryInfo.getDictValue(DictionaryInfo.mCrimeTimingKey, result);
                mCrimeTimingText.setText(result);
            }else if(requestCode == CreateSceneUtils.EVENT_SELECT_OBJECT_SELECT_ITEM){
                result = (String) data.getStringExtra("Select");
                mItem.setSelectObject(result);
                result = DictionaryInfo.getDictValue(DictionaryInfo.mSelectObjectKey, result);
                mSelectObjectText.setText(result);
            }else if(requestCode == CreateSceneUtils.EVENT_CRIME_EXPORT_SELECT_ITEM){
                result = (String) data.getStringExtra("Select");
                mItem.setCrimeExport(result);
                result = DictionaryInfo.getDictValue(DictionaryInfo.mCrimeEntranceExportKey, result);
                mCrimeExportText.setText(result);
            }else if(requestCode == CreateSceneUtils.EVENT_CRIME_FEATURE_SELECT_ITEM){
                result = (String) data.getStringExtra("Select");
                mItem.setCrimeFeature(result);
                result = DictionaryInfo.getDictValue(DictionaryInfo.mCrimeFeatureKey, result);
                mCrimeFeatureText.setText(result);
            }else if(requestCode == CreateSceneUtils.EVENT_INTRUSIVE_METHOD_SELECT_ITEM){
                result = (String) data.getStringExtra("Select");
                mItem.setIntrusiveMethod(result);
                result = DictionaryInfo.getDictValue(DictionaryInfo.mIntrusiveMethodKey, result);
                mIntrusiveMethodText.setText(result);
            }else if(requestCode == CreateSceneUtils.EVENT_SELECT_LOCATION_SELECT_ITEM) {
                result = (String) data.getStringExtra("Select");
                mItem.setSelectLocation(result);
                result = DictionaryInfo.getDictValue(DictionaryInfo.mSelectLocationKey, result);
                mSelectLocationText.setText(result);
            }else if(requestCode == CreateSceneUtils.EVENT_CRIME_PURPOSE_SELECT_ITEM) {
                result = (String) data.getStringExtra("Select");
                mItem.setCrimePurpose(result);
                result = DictionaryInfo.getDictValue(DictionaryInfo.mCrimePurposeKey, result);
                mCrimePurposeText.setText(result);
            }
        }
    }

    private void initView(View view){
        mPeopleNumberText = (TextView) view.findViewById(R.id.peopleNumber);
        mPeopleNumberText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), TreeViewListActivity.class);
                it.putExtra("Key",DictionaryInfo.mPeopleNumberKey);
                it.putExtra("Selected", mItem.getCrimePeopleNumber());
                startActivityForResult(it, CreateSceneUtils.EVENT_PEOPLE_NUMBER_SELECT_ITEM);
            }
        });

        mCrimeMeansText = (TextView) view.findViewById(R.id.crimeMeans);
        mCrimeMeansText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), TreeViewListActivity.class);
                it.putExtra("Key",DictionaryInfo.mCrimeMeansKey);
                it.putExtra("Selected", mItem.getCrimeMeans());
                startActivityForResult(it, CreateSceneUtils.EVENT_CRIME_MEANS_SELECT_ITEM);
            }
        });

        mCrimeCharacterText = (TextView) view.findViewById(R.id.crimeCharacter);
        mCrimeCharacterText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), TreeViewListActivity.class);
                it.putExtra("Key",DictionaryInfo.mCrimeCharacterKey);
                it.putExtra("Selected", mItem.getCrimeCharacter());
                startActivityForResult(it, CreateSceneUtils.EVENT_CRIME_CHARACTER_SELECT_ITEM);
            }
        });

        mCrimeEntranceText = (TextView) view.findViewById(R.id.crimeEntrance);
        mCrimeEntranceText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), TreeViewListActivity.class);
                it.putExtra("Key",DictionaryInfo.mCrimeEntranceExportKey);
                it.putExtra("Selected", mItem.getCrimeEntrance());
                startActivityForResult(it, CreateSceneUtils.EVENT_CRIME_ENTRANCE_SELECT_ITEM);
            }
        });

        mCrimeTimingText = (TextView) view.findViewById(R.id.crimeTiming);
        mCrimeTimingText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), TreeViewListActivity.class);
                it.putExtra("Key",DictionaryInfo.mCrimeTimingKey);
                it.putExtra("Selected", mItem.getCrimeTiming());
                startActivityForResult(it, CreateSceneUtils.EVENT_CRIME_TIMING_SELECT_ITEM);
            }
        });

        mSelectObjectText = (TextView) view.findViewById(R.id.selectObject);
        mSelectObjectText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), TreeViewListActivity.class);
                it.putExtra("Key",DictionaryInfo.mSelectObjectKey);
                it.putExtra("Selected", mItem.getSelectObject());
                startActivityForResult(it, CreateSceneUtils.EVENT_SELECT_OBJECT_SELECT_ITEM);
            }
        });

        mCrimeExportText = (TextView) view.findViewById(R.id.crimeExport);
        mCrimeExportText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), TreeViewListActivity.class);
                it.putExtra("Key",DictionaryInfo.mCrimeEntranceExportKey);
                it.putExtra("Selected", mItem.getCrimeExport());
                startActivityForResult(it, CreateSceneUtils.EVENT_CRIME_EXPORT_SELECT_ITEM);
            }
        });

        mCrimeFeatureText = (TextView) view.findViewById(R.id.crimeFeature);
        mCrimeFeatureText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), TreeViewListActivity.class);
                it.putExtra("Key",DictionaryInfo.mCrimeFeatureKey);
                it.putExtra("Selected", mItem.getCrimeFeature());
                startActivityForResult(it, CreateSceneUtils.EVENT_CRIME_FEATURE_SELECT_ITEM);
            }
        });

        mIntrusiveMethodText = (TextView) view.findViewById(R.id.intrusiveMethod);
        mIntrusiveMethodText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), TreeViewListActivity.class);
                it.putExtra("Key",DictionaryInfo.mIntrusiveMethodKey);
                it.putExtra("Selected", mItem.getIntrusiveMethod());
                startActivityForResult(it, CreateSceneUtils.EVENT_INTRUSIVE_METHOD_SELECT_ITEM);
            }
        });

        mSelectLocationText = (TextView) view.findViewById(R.id.selectLocation);
        mSelectLocationText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), TreeViewListActivity.class);
                it.putExtra("Key",DictionaryInfo.mSelectLocationKey);
                it.putExtra("Selected", mItem.getSelectLocation());
                startActivityForResult(it, CreateSceneUtils.EVENT_SELECT_LOCATION_SELECT_ITEM);
            }
        });

        mCrimePurposeText = (TextView) view.findViewById(R.id.crimePurpose);
        mCrimePurposeText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent it = new Intent(getActivity(), TreeViewListActivity.class);
                it.putExtra("Key",DictionaryInfo.mCrimePurposeKey);
                it.putExtra("Selected", mItem.getCrimePurpose());
                startActivityForResult(it, CreateSceneUtils.EVENT_CRIME_PURPOSE_SELECT_ITEM);
            }
        });

        mPeopleFeature = (ClearableEditText) view.findViewById(R.id.peopleFeature);
    }

    private void initData(){
        mPeopleNumberText.setText(DictionaryInfo.getDictValue(DictionaryInfo.mPeopleNumberKey, mItem.getCrimePeopleNumber()));
        mCrimeMeansText.setText(DictionaryInfo.getDictValue(DictionaryInfo.mCrimeMeansKey, mItem.getCrimeMeans()));
        mCrimeCharacterText.setText(DictionaryInfo.getDictValue(DictionaryInfo.mCrimeCharacterKey, mItem.getCrimeCharacter()));
        mCrimeEntranceText.setText(DictionaryInfo.getDictValue(DictionaryInfo.mCrimeEntranceExportKey, mItem.getCrimeEntrance()));
        mCrimeTimingText.setText(DictionaryInfo.getDictValue(DictionaryInfo.mCrimeTimingKey, mItem.getCrimeTiming()));
        mSelectObjectText.setText(DictionaryInfo.getDictValue(DictionaryInfo.mSelectObjectKey, mItem.getSelectObject()));
        mCrimeExportText.setText(DictionaryInfo.getDictValue(DictionaryInfo.mCrimeEntranceExportKey, mItem.getCrimeExport()));
        mCrimeFeatureText.setText(DictionaryInfo.getDictValue(DictionaryInfo.mCrimeFeatureKey, mItem.getCrimeFeature()));
        mIntrusiveMethodText.setText(DictionaryInfo.getDictValue(DictionaryInfo.mIntrusiveMethodKey, mItem.getIntrusiveMethod()));
        mSelectLocationText.setText(DictionaryInfo.getDictValue(DictionaryInfo.mSelectLocationKey, mItem.getSelectLocation()));
        mCrimePurposeText.setText(DictionaryInfo.getDictValue(DictionaryInfo.mCrimePurposeKey, mItem.getCrimePurpose()));

        mPeopleFeature.setText(mItem.getCrimePeopleFeature());
    }

    public void saveData(){
        mItem.setCrimePeopleFeature(mPeopleFeature.getText());
    }

    @Override
    public void onResume(){
        super.onResume();
        initData();
    }

    @Override
    public void onPause(){
        super.onPause();
        releaseFocusEditText();
        saveData();
    }

    private void releaseFocusEditText(){
        mPeopleFeature.clearFocus();
    }
}
