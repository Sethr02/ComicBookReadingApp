package com.example.comicreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.comicreader.Adapters.MyComicAdapter;
import com.example.comicreader.Common.Common;
import com.example.comicreader.Fragments.ComicFragment;
import com.example.comicreader.Fragments.ReadingOrderFragment;
import com.example.comicreader.Model.Comic;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavBar;
    MyComicAdapter myComicAdapter;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceFragment(new ComicFragment());

        bottomNavBar = findViewById(R.id.bottomNavBar);

        bottomNavBar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.comic:
                    replaceFragment(new ComicFragment());
                    break;
                case R.id.readingOrder:
                    replaceFragment(new ReadingOrderFragment());
                    break;
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {

        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_action);
        MenuItem sortItem = menu.findItem(R.id.sort_action);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search Here!");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                myComicAdapter.getFilter().filter(s);
                return true;
            }
        });

        sortItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                Toast.makeText(MainActivity.this, "dddddddddddd", Toast.LENGTH_SHORT).show();
                Common.comicList.sort(new Comparator<Comic>() {
                    @Override
                    public int compare(Comic comic, Comic t1) {

                        return comic.Name.compareToIgnoreCase(t1.Name);
                    }
                });
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void replaceFragment (Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();
    }
}