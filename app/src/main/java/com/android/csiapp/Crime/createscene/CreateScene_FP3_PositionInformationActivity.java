package com.android.csiapp.Crime.createscene;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.display.VirtualDisplay;
import android.media.Image;
import android.media.ImageReader;
import android.media.projection.MediaProjection;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.android.csiapp.Crime.utils.DateTimePicker;
import com.android.csiapp.Crime.utils.DictionaryInfo;
import com.android.csiapp.Crime.utils.PriviewPhotoActivity;
import com.android.csiapp.Databases.CrimeItem;
import com.android.csiapp.Databases.CrimeProvider;
import com.android.csiapp.Databases.PhotoItem;
import com.android.csiapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CreateScene_FP3_PositionInformationActivity extends AppCompatActivity {

    private Context context = null;
    private CrimeItem mItem;
    private PhotoItem mPositionItem;
    private int mEvent;
    private String mAdd;
    private ImageView mNew_Position;
    private TableLayout mTablePosition1, mTablePosition2, mTableFlat;
    private TextView mIncidentTime, mIncidentLocation, mCreateUnit, mCreatePeople, mCreateTime;
    private static final int REQUEST_POSITION = 0;
    private static final int REQUEST_FLAT = 1;

    private String gpsLat = "", gpsLon = "";

    private String path;
    private int mResultCode;
    private Intent mResultData;
    private static final int SCREENSHOTS_STOP_DELAY = 100;
    private static final int MAX_IMAGES = 5;
    private boolean mIsToastFirst = true;
    private int mCount = 0;
    private int mSaveCount = 0;
    private Object mLock = new Object();
    private int mIndex = 0;
    private int mDensityDpi;
    private int mWidth;
    private int mHeight;
    private MediaProjection mMediaProjection;
    private VirtualDisplay mVirtualDisplay;
    private ImageReader mImageReader;
    private Image mImage;
    private MediaProjectionManager mMediaProjectionManager;
    private static final int REQUEST_MEDIA_PROJECTION = 2;

    public Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            //Log.d("Anita","mHandler");
            super.handleMessage(msg);
        }
    };

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            String msg = "";
            switch (menuItem.getItemId()) {
                case R.id.action_camera:
                    msg += "Camera";
                    if(mAdd.equalsIgnoreCase("Position")) {
                        Intent it = new Intent(CreateScene_FP3_PositionInformationActivity.this, CreateScene_FP3_NewPositionActivity_Amap.class);
                        startActivityForResult(it, REQUEST_POSITION);
                    }else if(mAdd.equalsIgnoreCase("Flat")){
                        Intent it = new Intent(CreateScene_FP3_PositionInformationActivity.this, CreateScene_FP3_NewFlatActivity.class);
                        startActivityForResult(it, REQUEST_FLAT);
                    }
                    break;
                case R.id.action_click:
                    msg += "Save";
                    createVirtualEnvironment();
                    break;
                default:
                    break;
            }

            if(!msg.equals("")) {
                //Toast.makeText(CreateScene_FP3_PositionInformationActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
            return true;
        }
    };

    private void createVirtualEnvironment() {
        File mediaStorageDir = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "Report");
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return;
            }
        }
        mMediaProjectionManager = (MediaProjectionManager) this.getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        path = new File(mediaStorageDir.getPath() + File.separator + "POSITION_"+ timeStamp + ".jpg").toString();

        mDensityDpi = getResources().getDisplayMetrics().densityDpi;
        Display display = getWindowManager().getDefaultDisplay();

        Rect frame = new Rect();
        getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        int toolsBarHeight = (int) getResources().getDimension(R.dimen.toolbar_size);
        int statusBarHeight = frame.top;
        int removeHeight = toolsBarHeight+statusBarHeight;
        //Log.d("Anita","toolsBarHeight ="+toolsBarHeight+", statusBarHeight ="+statusBarHeight+", removeHeight = "+removeHeight);
        Point point = new Point();
        display.getSize(point);
        //Log.d("Anita","point x ="+point.x+", y ="+point.y);
        this.mWidth = point.x;
        this.mHeight = point.y;

        startScreenCapture();
        //Log.i("Anita", "prepared the virtual environment");
    }

    private void startScreenCapture(){
        if (mMediaProjection != null) {
            setUpVirtualDisplay();
        } else if (mResultCode != 0 && mResultData != null) {
            setUpMediaProjection();
            setUpVirtualDisplay();
        } else {
            //Log.i("Anita", "Requesting confirmation");
            startActivityForResult(mMediaProjectionManager.createScreenCaptureIntent(), REQUEST_MEDIA_PROJECTION);
        }
    }

    private void setUpVirtualDisplay() {
        //Log.i("Anita", "Setting up a VirtualDisplay");
        mVirtualDisplay = mMediaProjection.createVirtualDisplay("ScreenCapture",
                mWidth, mHeight, mDensityDpi, 9, mImageReader.getSurface(), null, mHandler);
    }

    private void setUpMediaProjection() {
        //Log.i("Anita", "Setting up MediaProjection");
        mMediaProjection = mMediaProjectionManager.getMediaProjection(mResultCode, mResultData);
    }

    private void setUpImageReader(){
        mIsToastFirst = true;
        mImageReader = ImageReader.newInstance(mWidth, mHeight, PixelFormat.RGBA_8888, MAX_IMAGES);
    }

    private void setImageReaderListener(){
        mImageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener() {
            @Override
            public void onImageAvailable(ImageReader reader) {
                try {
                    if(mCount >= MAX_IMAGES - 1){
                        //Log.w("Anita", "acquire image count(" + mCount + ") is more than MAX!");
                        return;
                    }

                    acquireImageCount();
                    //Log.d("Anita", " index: " + mIndex + " count: " + mCount);
                    mImage = reader.acquireLatestImage();
                    if(null != mImage){
                        savePicture(mIndex);
                    }else{
                        //Log.e("Anita", " acquire Latest image is null!" + " >>index: " + mIndex);
                        destoryImageCount();
                    }
                } catch (Exception e) {
                    //Log.e("Anita", "acquire error" + " >>index: " + mIndex, e);
                }

                // 結束整個界面，但是延遲一定時間停止截屏，以便截屏時去掉系統彈出框
                finishScreenShots();
            }
        }, mHandler);
    }

    private void finishScreenShots(){
        if(mIsToastFirst){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    stopScreenCapture();
                    tearDownMediaProjection();
                }
            }, SCREENSHOTS_STOP_DELAY);
            mIsToastFirst = false;
        }
        //finish();
    }

    private void acquireImageCount(){
        synchronized (mLock) {
            mCount ++;
            mIndex ++;
            mSaveCount ++;
        }
    }

    private void destoryImageCount(){
        synchronized (mLock) {
            mCount --;
        }
    }

    private void savePicture(int index){
        //Log.d("Anita", " savePicture " + " >>index: " + index);
        new Thread(new SavePictureTask(index,mImage, path)).start();
    }

    private class SavePictureTask implements Runnable {

        private int index;
        private Image image;
        private String path;

        public SavePictureTask(int index, Image image, String path){
            this.index = index;
            this.image = image;
            this.path = path;
        }

        @Override
        public void run() {
            long sTime = System.currentTimeMillis();
            if(image == null){
                //Log.e("Anita", "image is null!!!" + " >>index: " + index);
                return;
            }
            Bitmap bitmap = null;
            try {
                Image.Plane[] planes = image.getPlanes();
                if(planes == null || planes.length < 1){
                    //Log.e("Anita", "planes is null!!!" + " >>index: " + index);
                    return;
                }

                Image.Plane plane = planes[0];
                if(plane == null){
                    //Log.e("Anita", " plane 0 is null" + " >>index: " + index);
                    return;
                }

                ByteBuffer buffer = plane.getBuffer();
                int pixelStride = plane.getPixelStride();
                int rowStride = plane.getRowStride();
                int rowPadding = rowStride - pixelStride * mWidth;

                bitmap = Bitmap.createBitmap(mWidth+rowPadding/pixelStride, mHeight, Bitmap.Config.ARGB_8888);
                bitmap.copyPixelsFromBuffer(buffer);
                buffer.position(0);
                if(image != null){
                    image.close();
                    image = null;
                    destoryImageCount();
                }

                long iTime = System.currentTimeMillis();
                //Log.d("Anita", " CREATE BITMAP TIME： " + (iTime - sTime) + " >>index: " + index);
            } catch (Exception e) {
                //Log.e("Anita", " get bitmap error" + " >>index: " + index, e);
            } catch (OutOfMemoryError e){
                //Log.e("Anita", " Out Of Memory error" + " >>index: " + index, e);
            }

            FileOutputStream out = null;
            try {
                if(bitmap == null || bitmap.isRecycled()){
                    //Log.w("Anita", " bitmap is null or Recycled" + " >>index: " + index);
                    return;
                }
                long iTime = System.currentTimeMillis();
                File file = new File(path);
                if(!file.exists() || file.delete()){
                    file.createNewFile();
                    out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        //Log.i("Anita", "save file success!!!" + " >>index: " + index);
                } else {
                    //Log.e("Anita", "create save file failed!!!" + " >>index: " + index+", path :"+path);
                }
                //Log.d("Anita", " Save file time： " + (System.currentTimeMillis() - iTime) + " >>index: " + index);
            } catch (Exception e) {
                //Log.e("Anita", " save file error" + " >>index: " + index, e);
            } catch (OutOfMemoryError e){
                //Log.e("Anita", " Out Of Memory error" + " >>index: " + index, e);
            } finally {
                if(out != null){
                    try {
                        out.close();
                    } catch (IOException e) {
                    }
                }
                if(bitmap != null && !bitmap.isRecycled()){
                    bitmap.recycle();
                }
                //Log.d("Anita","mSaveCount = "+mSaveCount);
                mSaveCount--;
                if(mSaveCount==0) onSave();
            }
        }
    }

    private void stopScreenCapture(){
        if(mVirtualDisplay == null){
            return ;
        }
        //Log.i("Anita", " release Virtual Display ");
        mVirtualDisplay.release();
        mVirtualDisplay = null;
    }

    private void tearDownMediaProjection() {
        if (mMediaProjection != null) {
            //Log.i("Anita", "stop MediaProjection");
            mMediaProjection.stop();
            mMediaProjection = null;
        }
    }

    private void onSave(){
        //String path = ScreenShot.shoot(CreateScene_FP3_PositionInformationActivity.this);
        mPositionItem.setPhotoPath(path);
        mPositionItem.setUuid(CrimeProvider.getUUID());
        Intent result = getIntent();
        result.putExtra("com.android.csiapp.Databases.PhotoItem", mPositionItem);
        result.putExtra("Event",mEvent);
        result.putExtra("gpsLat", gpsLat);
        result.putExtra("gpsLon", gpsLon);
        setResult(Activity.RESULT_OK, result);
        //Log.d("Anita","onSave");
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_scene_fp3_position_information);

        context = this.getApplicationContext();
        mItem = (CrimeItem) getIntent().getSerializableExtra("com.android.csiapp.Databases.CrimeItem");
        mEvent = (int) getIntent().getIntExtra("Event", 1);
        mAdd = (String) getIntent().getStringExtra("Add");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        String title = (IsPositionInformation())
                ?context.getResources().getString(R.string.title_activity_position_information)
                :context.getResources().getString(R.string.title_activity_flat_information);
        toolbar.setTitle(title);
        toolbar.setTitleTextColor(context.getResources().getColor(R.color.titleBar));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.btn_back_mini);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });
        toolbar.setOnMenuItemClickListener(onMenuItemClick);

        initView();

        if(mEvent == 1) {
            mPositionItem = new PhotoItem();
            if(IsPositionInformation()) {
                Intent it = new Intent(CreateScene_FP3_PositionInformationActivity.this, CreateScene_FP3_NewPositionActivity_Amap.class);
                startActivityForResult(it, REQUEST_POSITION);
            }else if(!IsPositionInformation()){
                Intent it = new Intent(CreateScene_FP3_PositionInformationActivity.this, CreateScene_FP3_NewFlatActivity.class);
                startActivityForResult(it, REQUEST_FLAT);
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_create_fp3_position_information, menu);
        return true;
    }

    private void initView(){
        mNew_Position = (ImageView) findViewById(R.id.new_position);
        mNew_Position.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent it = new Intent(CreateScene_FP3_PositionInformationActivity.this, PriviewPhotoActivity.class);
                it.putExtra("Path",mPositionItem.getPhotoPath());
                startActivityForResult(it, 100);
            }
        });

        mTablePosition1 = (TableLayout) findViewById(R.id.table_position1);
        mTablePosition2 = (TableLayout) findViewById(R.id.table_position2);
        mTableFlat = (TableLayout) findViewById(R.id.table_flat);

        if(IsPositionInformation()) {
            mTablePosition1.setVisibility(View.VISIBLE);
            mTablePosition2.setVisibility(View.VISIBLE);
        }else if(!IsPositionInformation()) {
            mTableFlat.setVisibility(View.VISIBLE);
        }

        mIncidentTime = (TextView) findViewById(R.id.incident_time);
        mIncidentLocation = (TextView) findViewById(R.id.incident_location);
        mCreateUnit = (TextView) findViewById(R.id.create_unit);
        mCreatePeople = (TextView) findViewById(R.id.create_people);
        mCreateTime = (TextView) findViewById(R.id.create_time);
        getInformation();
    }

    private void setPhoto(String path){
        Bitmap Bitmap = BitmapFactory.decodeFile(path);
        mNew_Position.setImageBitmap(Bitmap);
        mNew_Position.setVisibility(View.VISIBLE);
    }

    private void getInformation(){
        long time = mItem.getOccurredStartTime();
        mIncidentTime.setText(DateTimePicker.getCurrentDate(time));
        mIncidentLocation.setText(mItem.getLocation());
        mCreateUnit.setText(DictionaryInfo.getDictValue(DictionaryInfo.mAreaKey,mItem.getArea()));
        mCreatePeople.setText(mItem.getAccessPolicemen());
        mCreateTime.setText(DateTimePicker.getCurrentDate(Calendar.getInstance().getTimeInMillis()));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_POSITION) {
                //Add lat and lon
                gpsLat = (String) data.getStringExtra("gpsLat");
                gpsLon = (String) data.getStringExtra("gpsLon");

                mPositionItem.setPhotoPath(data.getStringExtra("Map_ScreenShot"));
                setPhoto(mPositionItem.getPhotoPath());
            } else if(requestCode == REQUEST_FLAT) {
                mPositionItem.setPhotoPath(data.getStringExtra("Map_ScreenShot"));
                setPhoto(mPositionItem.getPhotoPath());
            } else if (requestCode == REQUEST_MEDIA_PROJECTION) {
                //Log.i("Anita", "Starting screen capture");
                mResultCode = resultCode;
                mResultData = data;
                //Log.d("Anita", " save path： " + path);

                setUpMediaProjection();
                setUpImageReader();
                setImageReaderListener();
                setUpVirtualDisplay();
            }
        }
    }

    private boolean IsPositionInformation(){
        return mAdd.equalsIgnoreCase("Position");
    }
}
