package com.example.comicreader;

import static com.example.comicreader.Common.Common.comicSelected;
import static com.google.android.material.tabs.TabLayout.INDICATOR_ANIMATION_MODE_ELASTIC;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.comicreader.Adapters.ChaptersFragmentAdapter;
import com.example.comicreader.Common.Common;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class ChaptersActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPager2;
    ChaptersFragmentAdapter chaptersFragmentAdapter;
    TextView comicTitle;
    ImageView comicImage;
    AppCompatButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapters);
        setupTabs();
        initViews();

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tabLayout.getTabAt(position).select();
            }
        });

        comicTitle.setText(Common.comicSelected.Name);

        Picasso.get().load(comicSelected.Image).fit().centerCrop().into(comicImage);

        backBtn.setOnClickListener(view -> finish());
    }

    private void initViews() {
        comicTitle = findViewById(R.id.comicTitle);
        comicImage = findViewById(R.id.comicImage);
        backBtn = findViewById(R.id.backBtn);
    }

    private void setupTabs() {
        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.getTabAt(1).setText(new StringBuilder("Issues (")
                .append(comicSelected.Chapters.size())
                .append(")"));
        viewPager2 = findViewById(R.id.viewPager2);
        chaptersFragmentAdapter = new ChaptersFragmentAdapter(this);
        viewPager2.setAdapter(chaptersFragmentAdapter);
    }
}