package com.example.comicreader.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuHost;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.comicreader.Adapters.MyComicAdapter;
import com.example.comicreader.Common.Common;
import com.example.comicreader.Interface.IComicLoadDone;
import com.example.comicreader.Model.Comic;
import com.example.comicreader.R;
import com.example.comicreader.Service.PicassoImageLoadingService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import ss.com.bannerslider.Slider;

public class ComicFragment extends Fragment implements IComicLoadDone {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    Toolbar toolbar;
    MyComicAdapter myComicAdapter;

    DatabaseReference comic;

    IComicLoadDone comicListener;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ComicFragment() { }

    public static ComicFragment newInstance(String param1, String param2) {
        ComicFragment fragment = new ComicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comic, container, false);

        toolbar = view.findViewById(R.id.toolbar);

        recyclerView = view.findViewById(R.id.comicRv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Slider.init(new PicassoImageLoadingService());
        swipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);
        swipeRefreshLayout.setColorSchemeResources(com.google.android.material.R.color.design_default_color_primary,
                com.google.android.material.R.color.design_default_color_primary_dark);

        comicListener = this;

        comic = FirebaseDatabase.getInstance().getReference("Comic");
        comic.keepSynced(true);

        swipeRefreshLayout.setOnRefreshListener(this::loadComic);

        swipeRefreshLayout.post(this::loadComic);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MenuHost menuHost = requireActivity();

        menuHost.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menuInflater.inflate(R.menu.toolbar_menu, menu);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.sort_action) {
                    Toast.makeText(getContext(), "x", Toast.LENGTH_SHORT).show();
                    Common.comicList.sort(new Comparator<Comic>() {
                        @Override
                        public int compare(Comic comic, Comic t1) {
                            return comic.Name.compareToIgnoreCase(t1.Name);
                        }
                    });
                }
                return true;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void loadComic() {
        comic.addListenerForSingleValueEvent(new ValueEventListener() {
            final List<Comic> comic_load = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    Comic comic = ds.getValue(Comic.class);
                    comic_load.add(comic);
                }
                comicListener.OnComicLoadDoneListener(comic_load);
                recyclerView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void OnComicLoadDoneListener(List<Comic> comicList) {
        Common.comicList = comicList;

        /*comicList.sort(new Comparator<Comic>() {
            @Override
            public int compare(Comic comic, Comic t1) {
                return comic.Name.compareToIgnoreCase(t1.Name);
            }
        });*/

        myComicAdapter = new MyComicAdapter(getActivity().getBaseContext(), comicList);
        recyclerView.setAdapter(myComicAdapter);

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}