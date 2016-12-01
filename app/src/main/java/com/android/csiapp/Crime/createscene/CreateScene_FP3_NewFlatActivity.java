package com.android.csiapp.Crime.createscene;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.csiapp.Crime.utils.BackAlertDialog;
import com.android.csiapp.Crime.utils.LinePathView;
import com.android.csiapp.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by user on 2016/11/30.
 */
public class CreateScene_FP3_NewFlatActivity extends AppCompatActivity {

    private Context context = null;
    @Bind(R.id.view)
    LinePathView mPathView;
    @Bind(R.id.left)
    Button mLeft;
    @Bind(R.id.right)
    Button mRight;
    @Bind(R.id.clear)
    Button mClear;
    @Bind(R.id.ll)
    LinearLayout ll;

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_click:
                    msg += "Save";
                    if (mPathView.getTouched()) {
                        try {
                            File mediaStorageDir = new File( context.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Report");
                            // This location works best if you want the created images to be shared
                            // between applications and persist after your app has been uninstalled.
                            // Create the storage directory if it does not exist
                            if (!mediaStorageDir.exists()){
                                if (!mediaStorageDir.mkdirs()){
                                    finish();
                                }
                            }
                            // Create a media file name
                            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
                            mPathView.save(mediaStorageDir.getPath() + File.separator + "FLAT_"+ timeStamp + ".jpg", true, 10);
                            String path = mediaStorageDir.getPath() + File.separator + "FLAT_"+ timeStamp + ".jpg";
                            Intent result = getIntent().putExtra("Map_ScreenShot", path);
                            setResult(Activity.RESULT_OK, result);
                            finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(CreateScene_FP3_NewFlatActivity.this, "您没有画图~", Toast.LENGTH_SHORT).show();
                    }
                    break;
                default:
                    break;
            }

            if(!msg.equals("")) {
                //Toast.makeText(CreateScene_FP8_AddWitnessActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_scene_fp3_new_flat);

        context = this.getApplicationContext();

        ButterKnife.bind(this);
        setResult(50);
        mClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathView.clear();
            }
        });
        mLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathView.clear();
            }
        });
        mRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPathView.clear();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(context.getResources().getString(R.string.title_activity_flat_information));
        toolbar.setTitleTextColor(context.getResources().getColor(R.color.titleBar));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_back_mini);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                BackAlertDialog dialog = new BackAlertDialog(CreateScene_FP3_NewFlatActivity.this);
                dialog.onCreateDialog(false,null);
                dialog.setOwnerActivity(CreateScene_FP3_NewFlatActivity.this);
            }
        });
        toolbar.setOnMenuItemClickListener(onMenuItemClick);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_fp3_subactivity, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}