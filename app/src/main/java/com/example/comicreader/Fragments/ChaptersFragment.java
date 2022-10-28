package com.example.comicreader.Fragments;

import static com.example.comicreader.Common.Common.comicSelected;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.comicreader.Adapters.MyChapterAdapter;
import com.example.comicreader.Common.Common;
import com.example.comicreader.Model.Comic;
import com.example.comicreader.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChaptersFragment extends Fragment {

    RecyclerView chapterRv;
    LinearLayoutManager layoutManager;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ChaptersFragment() { }

    public static ChaptersFragment newInstance(String param1, String param2) {
        ChaptersFragment fragment = new ChaptersFragment();
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
        Common.chapterList = comicSelected.Chapters;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chapters, container, false);

        chapterRv = view.findViewById(R.id.chaptersRv);
        MyChapterAdapter myChapterAdapter = new MyChapterAdapter(getContext(), Common.chapterList);
        chapterRv.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        chapterRv.setLayoutManager(layoutManager);
        chapterRv.addItemDecoration(new DividerItemDecoration(getContext(), layoutManager.getOrientation()));
        chapterRv.setAdapter(myChapterAdapter);

        return view;
    }
}