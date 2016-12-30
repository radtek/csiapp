package com.android.csiapp.Crime.createscene;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.csiapp.Crime.utils.BackAlertDialog;
import com.android.csiapp.Crime.utils.ClearableEditText;
import com.android.csiapp.Crime.utils.DictionaryInfo;
import com.android.csiapp.Crime.utils.IdCardVerify;
import com.android.csiapp.Crime.utils.SaveAlertDialog;
import com.android.csiapp.Crime.utils.tree.TreeViewListDemo;
import com.android.csiapp.Databases.CrimeProvider;
import com.android.csiapp.Databases.RelatedPeopleItem;
import com.android.csiapp.R;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateScene_FP2_NewPeopleActivity extends AppCompatActivity {

    private Context context = null;
    private RelatedPeopleItem mRelatedPeopleItem;
    private int mEvent;
    private int mPosition;

    private Spinner mReleationPeople_spinner;
    private ArrayList<String> mReleationPeople = new ArrayList<String>();
    private ArrayAdapter<String> mReleationPeople_adapter;

    private ClearableEditText mName;

    private TextView mSexText;
    private int EVENT_SEX_SELECT_ITEM = 1;

    private ClearableEditText mId;
    private ClearableEditText mNumber;
    private ClearableEditText mAddress;

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_click:
                    //msg += "Save";
                    saveData();
                    Intent result = getIntent();
                    result.putExtra("com.android.csiapp.Databases.RelatedPeopleItem", mRelatedPeopleItem);
                    result.putExtra("Event", mEvent);
                    result.putExtra("Posiotion", mPosition);

                    if(!mRelatedPeopleItem.getPeopleId().isEmpty() && !IdCardVerify.validateIdCard(mRelatedPeopleItem.getPeopleId())){
                        msg = "身份证号码错误";
                        Toast.makeText(CreateScene_FP2_NewPeopleActivity.this, msg, Toast.LENGTH_SHORT).show();
                        break;
                    }

                    if(mRelatedPeopleItem.checkInformation()){
                        setResult(Activity.RESULT_OK, result);
                        finish();
                    }else{
                        SaveAlertDialog dialog = new SaveAlertDialog(CreateScene_FP2_NewPeopleActivity.this);
                        dialog.onCreateDialog(result,false,null);
                        dialog.setOwnerActivity(CreateScene_FP2_NewPeopleActivity.this);
                    }
                    break;
                default:
                    break;
            }

            if(!msg.equals("")) {
                //Toast.makeText(CreateScene_FP2_NewPeopleActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_scene_fp2_new_people);

        context = this.getApplicationContext();
        mRelatedPeopleItem = (RelatedPeopleItem) getIntent().getSerializableExtra("com.android.csiapp.Databases.RelatedPeopleItem");
        mEvent = (int) getIntent().getIntExtra("Event", 1);
        mPosition = (int) getIntent().getIntExtra("Position", 0);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(context.getResources().getString(R.string.title_activity_peopleinformation));
        toolbar.setTitleTextColor(context.getResources().getColor(R.color.titleBar));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_back_mini);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                BackAlertDialog dialog = new BackAlertDialog(CreateScene_FP2_NewPeopleActivity.this);
                dialog.onCreateDialog(false,null);
                dialog.setOwnerActivity(CreateScene_FP2_NewPeopleActivity.this);
            }
        });
        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        initView();
        initData();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            //What to do on back clicked
            BackAlertDialog dialog = new BackAlertDialog(CreateScene_FP2_NewPeopleActivity.this);
            dialog.onCreateDialog(false,null);
            dialog.setOwnerActivity(CreateScene_FP2_NewPeopleActivity.this);
        }
        return false;
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_fp2_subactivity, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result = "";
        if (resultCode == Activity.RESULT_OK) {
             if(requestCode == EVENT_SEX_SELECT_ITEM){
                result = (String) data.getStringExtra("Select");
                mRelatedPeopleItem.setPeopleSex(result);
                result = DictionaryInfo.getDictValue(DictionaryInfo.mSexKey, result);
                mSexText.setText(result);
            }
        }
        Log.d("Anita","result = "+result);
    }

    private void initView(){
        mReleationPeople = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.releation_people)));
        mReleationPeople_spinner = (Spinner) findViewById(R.id.releationPeople_spinner);
        mReleationPeople_adapter = new ArrayAdapter<String>(CreateScene_FP2_NewPeopleActivity.this, R.layout.spinnerview, mReleationPeople);
        mReleationPeople_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mReleationPeople_spinner.setAdapter(mReleationPeople_adapter);
        mReleationPeople_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                mRelatedPeopleItem.setPeopleRelation(mReleationPeople.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        mName = (ClearableEditText) findViewById(R.id.name_editView);

        mSexText = (TextView) findViewById(R.id.sex);
        mSexText.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent it = new Intent(CreateScene_FP2_NewPeopleActivity.this, TreeViewListDemo.class);
                it.putExtra("Key",DictionaryInfo.mSexKey);
                it.putExtra("Selected", mRelatedPeopleItem.getPeopleSex());
                startActivityForResult(it, EVENT_SEX_SELECT_ITEM);
            }
        });

        mId = (ClearableEditText) findViewById(R.id.identity_number_editView);
        mNumber = (ClearableEditText) findViewById(R.id.contact_number_editView);
        mAddress = (ClearableEditText) findViewById(R.id.address_editView);
        mId.setKeyListener(DigitsKeyListener.getInstance("abcdefghigklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"));
        mNumber.setKeyListener(DigitsKeyListener.getInstance("()-0123456789"));

        mId.addTextChangedListener(new TextWatcher(){
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length()==17) mId.setKeyListener(new DigitsKeyListener() {
                    @Override
                    public int getInputType() {
                        return InputType.TYPE_TEXT_VARIATION_PASSWORD;
                    }
                    @Override
                    protected char[] getAcceptedChars() {
                        char[] data = getResources().getString(R.string.login_only_can_input).toCharArray();
                        return data;
                    }
                });
                if(s.length()!=17) mId.setKeyListener(DigitsKeyListener.getInstance("0123456789"));
                if(s.length()>=18) mId.setKeyListener(DigitsKeyListener.getInstance(""));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void initData(){
        mReleationPeople_spinner.setSelection(getPeople(mRelatedPeopleItem.getPeopleRelation()));
        mName.setText(mRelatedPeopleItem.getPeopleName());
        mSexText.setText(DictionaryInfo.getDictValue(DictionaryInfo.mSexKey, mRelatedPeopleItem.getPeopleSex()));
        mId.setText(mRelatedPeopleItem.getPeopleId());
        mNumber.setText(mRelatedPeopleItem.getPeopleNumber());
        mAddress.setText(mRelatedPeopleItem.getPeopleAddress());
    }

    private void saveData(){
        mRelatedPeopleItem.setPeopleName(mName.getText());
        mRelatedPeopleItem.setPeopleId(mId.getText());
        mRelatedPeopleItem.setPeopleNumber(mNumber.getText());
        mRelatedPeopleItem.setPeopleAddress(mAddress.getText());
        mRelatedPeopleItem.setUuid(CrimeProvider.getUUID());
    }

    private int getPeople(String category){
        for(int i=0; i<mReleationPeople.size(); i++){
            if(category.equalsIgnoreCase(mReleationPeople.get(i))) return i;
        }
        return 0;
    }
}
