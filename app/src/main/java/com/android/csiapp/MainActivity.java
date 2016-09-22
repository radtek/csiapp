package com.android.csiapp;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.android.csiapp.Cell.SceneActivity;
import com.android.csiapp.Cell.SignalActivity;
import com.android.csiapp.Csi.ListActivity;
import com.android.csiapp.Csi.SettingActivity;

public class MainActivity extends AppCompatActivity {
    private Context context = null;

    private Csi_provider csi_item;

    private Button mCreate;
    private Button mList;
    //private Button mScene;
    //private Button mSignal;

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_setting:
                    msg += "Setting";
                    Intent it = new Intent(MainActivity.this, SettingActivity.class);
                    startActivity(it);
                    break;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this.getApplicationContext();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        // 建立資料庫物件
        csi_item = new Csi_provider(context);

        // 如果資料庫是空的，就建立一些範例資料
        // 這是為了方便測試用的，完成應用程式以後可以拿掉
        if (csi_item.getCount() == 0) {
            csi_item.sample();
        }

        mCreate = (Button) findViewById(R.id.imageButton_create);
        mCreate.setOnClickListener(new OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                Intent it = new Intent("com.android.csiapp.ADD_SCENE");
                startActivityForResult(it,0);
            }
        });

        mList = (Button) findViewById(R.id.imageButton_list);
        mList.setOnClickListener(new OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, ListActivity.class);
                startActivity(it);
            }
        });

        /*mScene= (Button) findViewById(R.id.imageButton_cell_scene);
        mScene.setOnClickListener(new OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, SceneActivity.class);
                startActivity(it);
            }
        });

        mSignal= (Button) findViewById(R.id.imageButton_cell_signal);
        mSignal.setOnClickListener(new OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            public void onClick(View view) {
                Intent it = new Intent(MainActivity.this, SignalActivity.class);
                startActivity(it);
            }
        });*/
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Item item = (Item) data.getExtras().getSerializable("com.android.csiapp.Item");
            if (requestCode == 0) {
                // 新增記事資料到資料庫
                item = csi_item.insert(item);
            }
        }
    }
}
