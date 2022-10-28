package com.example.comicreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.example.comicreader.Adapters.MainComicFragmentAdapter;
import com.google.android.material.tabs.TabLayout;

public class OldMainActivity extends AppCompatActivity {

    Toolbar toolbar;
    TabLayout tabLayout;
    ViewPager2 viewPager2;
    MainComicFragmentAdapter mainComicFragmentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_old);
        setupTabs();
        toolbar = findViewById(R.id.toolbar);

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

    private void setupTabs() {
        tabLayout = findViewById(R.id.tabLayout);

        viewPager2 = findViewById(R.id.viewPager2);
        mainComicFragmentAdapter = new MainComicFragmentAdapter(this);
        viewPager2.setAdapter(mainComicFragmentAdapter);
    }
}