package com.android.csiapp.Csi;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.android.csiapp.Item;
import com.android.csiapp.R;

import java.io.File;
import java.io.FileInputStream;

/**
 * A simple {@link Fragment} subclass.
 */
public class Create_FragmentPage5 extends Fragment {
    private Context context = null;
    private Item item;
    private ImageButton add_scene_evidence;
    private ImageButton Scene_evidence;

    public Create_FragmentPage5() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_fragmentpage5, container, false);
        CreateSceneActivity activity  = (CreateSceneActivity) getActivity();
        item = activity.getItem();
        context = getActivity().getApplicationContext();

        add_scene_evidence = (ImageButton) view.findViewById(R.id.add_scene_evidence);
        add_scene_evidence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getActivity(), Create_FP5_NewEvidence_Activity.class);
                startActivityForResult(it, 0);
            }
        });
        Scene_evidence = (ImageButton) view.findViewById(R.id.Scene_evidence);
        return view;
    }

    public static Bitmap loadBitmapFromFile(File f) {
        Bitmap b = null;
        BitmapFactory.Options option = new BitmapFactory.Options();
        // Bitmap sampling factor, size = (Original Size)/(inSampleSize)
        option.inSampleSize = 4;

        try {
            FileInputStream fis = new FileInputStream(f);
            b = BitmapFactory.decodeStream(fis, null, option);
            fis.close();

            // Rotate Bitmap by 90 degree
            Matrix matrix = new Matrix();
            matrix.setRotate(0, (float)b.getWidth()/2, (float)b.getHeight()/2);
            Bitmap resultImage = Bitmap.createBitmap(b, 0, 0, b.getWidth(), b.getHeight(), matrix, true);

            return resultImage;
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("Photo","onActivityResult");
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            String filepath = data.getStringExtra("photo_uri");
            Log.d("Photo","onActivityResult, filepath: " + filepath);
            Bitmap Bitmap = loadBitmapFromFile(new File(filepath));
            BitmapDrawable bDrawable = new BitmapDrawable(context.getResources(), Bitmap);
            Scene_evidence.setBackground(bDrawable);
            Scene_evidence.setVisibility(View.VISIBLE);
        }
    }
}
