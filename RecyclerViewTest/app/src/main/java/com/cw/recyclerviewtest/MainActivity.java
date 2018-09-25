package com.cw.recyclerviewtest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initImageBitmaps();
    }

    private void initImageBitmaps() {
        Log.d(TAG, "initImageBitmaps: prepare image bitmaps. ");
        mNames.add("https://tse4.mm.bing.net/th?id=OIP.DCLySHcYEvP7Bkg3awZJmwHaEK&pid=Api");
        mImageUrls.add("https://tse4.mm.bing.net/th?id=OIP.DCLySHcYEvP7Bkg3awZJmwHaEK&pid=Api");

        mNames.add("http://weneedfun.com/wp-content/uploads/2016/01/Anemone-Flower-5.jpg");
        mImageUrls.add("http://weneedfun.com/wp-content/uploads/2016/01/Anemone-Flower-5.jpg");

        mNames.add("https://theviewsthroughmylens.files.wordpress.com/2014/05/dscf2809.jpg");
        mImageUrls.add("https://theviewsthroughmylens.files.wordpress.com/2014/05/dscf2809.jpg");

        mNames.add("http://2.bp.blogspot.com/-quGAgI1jY-4/Tj68dS3uFiI/AAAAAAAAFmY/BGSBWq3omnE/s1600/Lotus-Flower-1-.jpg");
        mImageUrls.add("http://2.bp.blogspot.com/-quGAgI1jY-4/Tj68dS3uFiI/AAAAAAAAFmY/BGSBWq3omnE/s1600/Lotus-Flower-1-.jpg");


        mNames.add("http://weneedfun.com/wp-content/uploads/2016/01/Pink-Flower-17.jpg");
        mImageUrls.add("http://weneedfun.com/wp-content/uploads/2016/01/Pink-Flower-17.jpg");

        mNames.add("http://latesthdwallpapers1.com/wp-content/uploads/2015/02/Crocus-Flower-Wallpaper-4.jpg");
        mImageUrls.add("http://latesthdwallpapers1.com/wp-content/uploads/2015/02/Crocus-Flower-Wallpaper-4.jpg");

        mNames.add("http://tracts4free.files.wordpress.com/2013/12/turnera-flower1.jpg");
        mImageUrls.add("http://tracts4free.files.wordpress.com/2013/12/turnera-flower1.jpg");

        mNames.add("http://images.fanpop.com/images/image_uploads/flower-power-flowers-247555_1024_768.jpg");
        mImageUrls.add("http://images.fanpop.com/images/image_uploads/flower-power-flowers-247555_1024_768.jpg");

        mNames.add("http://www.freeflowerpictures.net/image/flowers/peonies/peony-flower.jpg");
        mImageUrls.add("http://www.freeflowerpictures.net/image/flowers/peonies/peony-flower.jpg");

        mNames.add("http://www.maestronews.com/wallpapers/landscape/flower/flower_11.jpg");
        mImageUrls.add("http://www.maestronews.com/wallpapers/landscape/flower/flower_11.jpg");

        mNames.add("http://1.bp.blogspot.com/-H5fF0RLlpd4/UPKnMewCqqI/AAAAAAAAAJY/sMl6j0fdJNo/s1600/20090718074331!Tb_mecsek_yellow_flower.jpg");
        mImageUrls.add("http://1.bp.blogspot.com/-H5fF0RLlpd4/UPKnMewCqqI/AAAAAAAAAJY/sMl6j0fdJNo/s1600/20090718074331!Tb_mecsek_yellow_flower.jpg");

        mNames.add("http://www.pngpix.com/wp-content/uploads/2016/03/Cosmea-Flower-PNG-Image.png");
        mImageUrls.add("http://www.pngpix.com/wp-content/uploads/2016/03/Cosmea-Flower-PNG-Image.png");

        mNames.add("http://1.bp.blogspot.com/-9ap0ucaAb7o/Tk3qYjXokmI/AAAAAAAAF6Q/oC8iBvRSM8I/s1600/Yellow-Narcissus-Flower.JPG");
        mImageUrls.add("http://1.bp.blogspot.com/-9ap0ucaAb7o/Tk3qYjXokmI/AAAAAAAAF6Q/oC8iBvRSM8I/s1600/Yellow-Narcissus-Flower.JPG");

        initRecyclerView();
    }
    
    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: init recydleview. ");

        RecyclerView recyclerView = findViewById(R.id.recyclerv_view);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(mNames, mImageUrls, this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
    }
}
