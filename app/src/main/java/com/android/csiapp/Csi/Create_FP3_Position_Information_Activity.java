package com.android.csiapp.Csi;

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

public class Create_FP3_Position_Information_Activity extends AppCompatActivity {

    private Context context = null;
    private Button new_position;
    private String filepath;

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_camera:
                    msg += "Camera";
                    Intent it = new Intent(Create_FP3_Position_Information_Activity.this, Create_FP3_NewPosition_Activity.class);
                    startActivityForResult(it, 0);
                    break;
                case R.id.action_click:
                    msg += "Save";
                    Intent result = getIntent().putExtra("BaiduMap_ScreenShot", filepath);
                    setResult(Activity.RESULT_OK, result);
                    finish();
                    break;
            }

            if(!msg.equals("")) {
                Toast.makeText(Create_FP3_Position_Information_Activity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_fp3_position_information);

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

        new_position = (Button) findViewById(R.id.new_position);
        new_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(Create_FP3_Position_Information_Activity.this, Create_FP3_NewPosition_Activity.class);
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
            filepath = data.getStringExtra("BaiduMap_ScreenShot");
            Log.d("BaiduMap","onActivityResult, filepath: " + filepath);
            Bitmap Bitmap = BitmapFactory.decodeFile(filepath);
            BitmapDrawable bDrawable = new BitmapDrawable(getResources(), Bitmap);
            new_position.setBackground(bDrawable);
            new_position.setVisibility(View.VISIBLE);
        }
    }
}
