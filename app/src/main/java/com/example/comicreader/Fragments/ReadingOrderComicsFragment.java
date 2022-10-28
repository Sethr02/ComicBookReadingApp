package com.example.comicreader.Fragments;

import static com.example.comicreader.Common.Common.readingOrderSelected;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comicreader.Adapters.MyComicAdapter;
import com.example.comicreader.Common.Common;
import com.example.comicreader.R;

public class ReadingOrderComicsFragment extends Fragment {

    RecyclerView readingOrderComicsRv;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ReadingOrderComicsFragment() { }

    public static ReadingOrderComicsFragment newInstance(String param1, String param2) {
        ReadingOrderComicsFragment fragment = new ReadingOrderComicsFragment();
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
        Common.comicList = readingOrderSelected.Comic;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_reading_order_comics, container, false);

        readingOrderComicsRv = view.findViewById(R.id.readingOrderComicsRv);
        MyComicAdapter myComicAdapter = new MyComicAdapter(getContext(), readingOrderSelected.Comic);
        readingOrderComicsRv.setHasFixedSize(true);
        readingOrderComicsRv.setVisibility(View.VISIBLE);
        readingOrderComicsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        readingOrderComicsRv.setAdapter(myComicAdapter);

        return view;
    }
}