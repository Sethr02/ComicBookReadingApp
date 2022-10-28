package com.example.comicreader.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.comicreader.Fragments.AboutFragment;
import com.example.comicreader.Fragments.ChaptersFragment;
import com.example.comicreader.Fragments.ComicFragment;
import com.example.comicreader.Fragments.DownloadsFragment;

public class ChaptersFragmentAdapter extends FragmentStateAdapter {
    public ChaptersFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new ChaptersFragment();
            case 2:
                return new DownloadsFragment();
            default:
                return new AboutFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
