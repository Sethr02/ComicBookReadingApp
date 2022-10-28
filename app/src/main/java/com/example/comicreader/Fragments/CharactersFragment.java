package com.example.comicreader.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

import com.example.comicreader.Adapters.MyCharacterAdapter;
import com.example.comicreader.Common.Common;
import com.example.comicreader.R;

public class CharactersFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public CharactersFragment() { }

    public static CharactersFragment newInstance(String param1, String param2) {
        CharactersFragment fragment = new CharactersFragment();
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
        Common.characterList = Common.readingOrderSelected.Characters;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_characters, container, false);

        RecyclerView characterRv = view.findViewById(R.id.characterRv);
        ViewFlipper viewFlipper = view.findViewById(R.id.viewFlipper);
        MyCharacterAdapter myCharacterAdapter = new MyCharacterAdapter(getContext(), Common.characterList);
        characterRv.setHasFixedSize(true);
        characterRv.setVisibility(View.VISIBLE);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        characterRv.setLayoutManager(layoutManager);
        characterRv.setAdapter(myCharacterAdapter);

        if (Common.characterList.size() <= 1) {
            viewFlipper.showNext();
        } else {
            characterRv.setAdapter(myCharacterAdapter);
        }

        return view;
    }
}