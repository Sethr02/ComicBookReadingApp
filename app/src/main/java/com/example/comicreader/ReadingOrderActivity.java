package com.example.comicreader;

import static com.example.comicreader.Common.Common.comicSelected;
import static com.example.comicreader.Common.Common.readingOrderSelected;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.comicreader.Adapters.ChaptersFragmentAdapter;
import com.example.comicreader.Adapters.MainComicFragmentAdapter;
import com.example.comicreader.Common.Common;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

public class ReadingOrderActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MainComicFragmentAdapter mainComicFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_order);
        initViews();
        setupTabs();

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

    }

    private void initViews() {
        tabLayout = findViewById(R.id.characterTabLayout);
        toolbar = findViewById(R.id.toolbar);
    }

    private void setupTabs() {
        tabLayout.getTabAt(0).setText(new StringBuilder("Comics (")
                .append(readingOrderSelected.Comic.size())
                .append(")"));
        if (readingOrderSelected.Characters.size() <= 1)
            tabLayout.getTabAt(1).setText("Characters");
        else
            tabLayout.getTabAt(1).setText(new StringBuilder("Characters (")
                    .append(readingOrderSelected.Characters.size())
                    .append(")"));
        viewPager2 = findViewById(R.id.viewPager2);
        mainComicFragmentAdapter = new MainComicFragmentAdapter(this);
        viewPager2.setAdapter(mainComicFragmentAdapter);
    }
}