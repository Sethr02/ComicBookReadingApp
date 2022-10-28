package com.example.comicreader.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comicreader.DBHelper;
import com.example.comicreader.R;
import com.example.comicreader.ViewDataActivity;

public class DownloadsFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    DBHelper DB;

    EditText name, email, age;
    Button viewData, insertData;

    public DownloadsFragment() { }

    public static DownloadsFragment newInstance(String param1, String param2) {
        DownloadsFragment fragment = new DownloadsFragment();
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
        DB = new DBHelper(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_downloads, container, false);

        name = view.findViewById(R.id.name);
        email = view.findViewById(R.id.email);
        age = view.findViewById(R.id.age);
        viewData = view.findViewById(R.id.btnView);
        insertData = view.findViewById(R.id.btnInsert);

        viewData.setOnClickListener(view1 -> startActivity(new Intent(getContext(), ViewDataActivity.class)));

        insertData.setOnClickListener(view12 -> {
            String fName = name.getText().toString();
            String fEmail = email.getText().toString();
            String fAge = age.getText().toString();

            Boolean checkInsertData = DB.insertUserData(fName, fEmail, fAge);

            if (checkInsertData == true) {
                Toast.makeText(getContext(), "Success!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}