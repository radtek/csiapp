package com.android.csiapp.Crime.createscene;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.csiapp.ClearableEditText;
import com.android.csiapp.Databases.CrimeItem;
import com.android.csiapp.R;

import java.util.ArrayList;
import java.util.Arrays;

public class CreateScene_FP2_NewToolActivity extends AppCompatActivity {

    private Context context = null;
    private CrimeItem mItem;
    private int mEvent;

    private ClearableEditText mName;

    private Spinner mTool_category_spinner;
    private ArrayList<String> mTool_category = new ArrayList<String>();
    private ArrayAdapter<String> mTool_category_adapter;

    private Spinner mTool_source_spinner;
    private ArrayList<String> mTool_source = new ArrayList<String>();
    private ArrayAdapter<String> mTool_source_adapter;

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_click:
                    msg += "Save";
                    saveMessage();
                    Intent result = getIntent();
                    result.putExtra("com.android.csiapp.CrimeItem", mItem);
                    setResult(Activity.RESULT_OK, result);
                    break;
            }

            if(!msg.equals("")) {
                Toast.makeText(CreateScene_FP2_NewToolActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            finish();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_scene_fp2_new_tool);

        context = this.getApplicationContext();
        mItem = (CrimeItem) getIntent().getSerializableExtra("com.android.csiapp.CrimeItem");
        mEvent = (int) getIntent().getIntExtra("Event", 1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(context.getResources().getString(R.string.title_activity_crimetool));
        toolbar.setTitleTextColor(context.getResources().getColor(R.color.titleBar));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_back_mini);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //What to do on back clicked
            }
        });
        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        mName = (ClearableEditText) findViewById(R.id.tool_name_editView);

        mTool_category = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.tool_category)));
        mTool_category_spinner= (Spinner) findViewById(R.id.tool_category_spinner);
        mTool_category_adapter = new ArrayAdapter<String>(CreateScene_FP2_NewToolActivity.this, R.layout.spinnerview, mTool_category);
        mTool_category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTool_category_spinner.setAdapter(mTool_category_adapter);
        mTool_category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                mItem.setToolCategory(mTool_category.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        mTool_source = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.tool_source)));
        mTool_source_spinner = (Spinner) findViewById(R.id.tool_source_spinner);
        mTool_source_adapter = new ArrayAdapter<String>(CreateScene_FP2_NewToolActivity.this, R.layout.spinnerview, mTool_source);
        mTool_source_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mTool_source_spinner.setAdapter(mTool_source_adapter);
        mTool_source_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long arg3) {
                mItem.setToolSource(mTool_source.get(position));
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        if(mEvent == 2) {
            getMessage();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_fp2_subactivity, menu);
        return true;
    }

    private int getCategory(String category){
        for(int i=0; i<mTool_category.size(); i++){
            if(category.equalsIgnoreCase(mTool_category.get(i))) return i;
        }
        return 0;
    }
    private int getSource(String source){
        for(int i=0; i<mTool_source.size(); i++){
            if(source.equalsIgnoreCase(mTool_source.get(i))) return i;
        }
        return 0;
    }

    public void getMessage(){
        mName.setText(mItem.getToolName());
        mTool_category_spinner.setSelection(getCategory(mItem.getToolCategory()));
        mTool_source_spinner.setSelection(getSource(mItem.getToolSource()));
    }

    public void saveMessage(){
        mItem.setToolName(mName.getText());
    }
}