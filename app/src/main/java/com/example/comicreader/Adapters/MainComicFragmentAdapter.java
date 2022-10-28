package com.example.comicreader.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.comicreader.Fragments.CharactersFragment;
import com.example.comicreader.Fragments.ComicFragment;
import com.example.comicreader.Fragments.FavouriteFragment;
import com.example.comicreader.Fragments.ReadingOrderComicsFragment;
import com.example.comicreader.Fragments.ReadingOrderFragment;

public class MainComicFragmentAdapter extends FragmentStateAdapter {
    public MainComicFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new ReadingOrderComicsFragment();
        } else {
            return new CharactersFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
