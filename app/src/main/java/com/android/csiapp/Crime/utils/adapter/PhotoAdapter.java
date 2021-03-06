package com.android.csiapp.Crime.utils.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.csiapp.Crime.utils.CreateSceneUtils;
import com.android.csiapp.Databases.PhotoItem;
import com.android.csiapp.R;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * Created by user on 2016/10/7.
 */
public class PhotoAdapter extends BaseAdapter {
    private LayoutInflater myInflater;
    private List<PhotoItem> items;
    private Bitmap bp = null;
    private ViewHolder holder =null;

    public PhotoAdapter(Context context, List<PhotoItem> items){
        myInflater = LayoutInflater.from(context);
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return items.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = myInflater.inflate(R.layout.photoadapterview, null);
            holder = new ViewHolder(
                    (ImageView) convertView.findViewById(R.id.photo)
            );
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        PhotoItem item = (PhotoItem)getItem(position);
        String path = item.getPhotoPath();
        if(!path.isEmpty()){
            bp = CreateSceneUtils.loadBitmapFromFile(new File(path));
            if(bp!=null) holder.txtItemPhoto.setImageBitmap(bp);
        }

        return convertView;
    }

    private class ViewHolder {
        ImageView txtItemPhoto;
        public ViewHolder(ImageView txtItemPhoto){
            this.txtItemPhoto = txtItemPhoto;
        }
    }

}
