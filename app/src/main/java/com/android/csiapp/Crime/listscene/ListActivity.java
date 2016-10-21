package com.android.csiapp.Crime.listscene;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.csiapp.Databases.CrimeProvider;
import com.android.csiapp.Databases.CrimeItem;
import com.android.csiapp.R;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    private Context context = null;
    private CrimeProvider mCrimeProvider;
    private List<CrimeItem> items_list;
    private ListView mListV;
    private ListAdapter mAdapter;

    final int LIST_DELETE = 0;

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_search:
                    msg += "Search";
                    Intent it1 = new Intent(ListActivity.this, ListSearchActivity.class);
                    startActivityForResult(it1, 1);
                    break;
                case R.id.action_delete:
                    msg += "Delete";
                    Intent it2 = new Intent(ListActivity.this, ListDeleteActivity.class);
                    startActivityForResult(it2, 2);
                    break;
            }

            if(!msg.equals("")) {
                //Toast.makeText(ListActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);

        context = this.getApplicationContext();

        mCrimeProvider = new CrimeProvider(context);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
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

        items_list = new ArrayList<CrimeItem>();
        items_list = mCrimeProvider.getAll();
        mListV=(ListView)findViewById(R.id.listView);
        mAdapter = new ListAdapter(ListActivity.this,items_list);
        mListV.setAdapter(mAdapter);
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // 讀取選擇的記事物件
                CrimeItem item = (CrimeItem) mAdapter.getItem(position);
                Intent intent = new Intent("com.android.csiapp.EDIT_SCENE");
                // 設定記事編號與記事物件
                intent.putExtra("CrimeItem", item);
                startActivityForResult(intent, 0);
            }
        };
        mListV.setOnItemClickListener(itemListener);
        registerForContextMenu(mListV);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        String delete = context.getResources().getString(R.string.list_delete);
        if (v.getId()==R.id.listView) {
            menu.add(0, LIST_DELETE, 0, delete);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch(item.getItemId()) {
            case LIST_DELETE:
                mCrimeProvider.delete(items_list.get(info.position).getId());
                items_list.remove(info.position);
                mAdapter.notifyDataSetChanged();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                // 新增記事資料到資料庫
                CrimeItem item = (CrimeItem) data.getExtras().getSerializable("com.android.csiapp.CrimeItem");
                boolean result = mCrimeProvider.update(item);
            }else if(requestCode == 1){
                //Search
            }else if(requestCode == 2){
                //Delete
            }
            items_list = mCrimeProvider.getAll();
            mAdapter = new ListAdapter(ListActivity.this,items_list);
            mListV.setAdapter(mAdapter);
        }
    }
}
