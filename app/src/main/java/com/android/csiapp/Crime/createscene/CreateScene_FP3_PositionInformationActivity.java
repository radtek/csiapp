package com.android.csiapp.Crime.createscene;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.csiapp.R;

public class CreateScene_FP3_PositionInformationActivity extends AppCompatActivity {

    private Context context = null;
    private Button mNew_Position;
    private String mFilepath;

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_camera:
                    msg += "Camera";
                    Intent it = new Intent(CreateScene_FP3_PositionInformationActivity.this, CreateScene_FP3_NewPositionActivity.class);
                    startActivityForResult(it, 0);
                    break;
                case R.id.action_click:
                    msg += "Save";
                    Intent result = getIntent().putExtra("BaiduMap_ScreenShot", mFilepath);
                    setResult(Activity.RESULT_OK, result);
                    finish();
                    break;
            }

            if(!msg.equals("")) {
                Toast.makeText(CreateScene_FP3_PositionInformationActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_scene_fp3_position_information);

        context = this.getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(context.getResources().getString(R.string.title_activity_position_information));
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

        mNew_Position = (Button) findViewById(R.id.new_position);
        mNew_Position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(CreateScene_FP3_PositionInformationActivity.this, CreateScene_FP3_NewPositionActivity.class);
                startActivityForResult(it, 0);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_fp3_position_information, menu);
        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("BaiduMap","onActivityResult");
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            mFilepath = data.getStringExtra("BaiduMap_ScreenShot");
            Log.d("BaiduMap","onActivityResult, filepath: " + mFilepath);
            Bitmap Bitmap = BitmapFactory.decodeFile(mFilepath);
            BitmapDrawable bDrawable = new BitmapDrawable(getResources(), Bitmap);
            mNew_Position.setBackground(bDrawable);
            mNew_Position.setVisibility(View.VISIBLE);
        }
    }
}
