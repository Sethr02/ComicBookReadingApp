package com.example.comicreader.Fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baoyachi.stepview.HorizontalStepView;
import com.baoyachi.stepview.VerticalStepView;
import com.baoyachi.stepview.bean.StepBean;
import com.example.comicreader.Adapters.MyReadingOrderAdapter;
import com.example.comicreader.Common.Common;
import com.example.comicreader.Interface.IComicLoadDone;
import com.example.comicreader.Interface.IReadingOrderDone;
import com.example.comicreader.Model.Comic;
import com.example.comicreader.Model.ReadingOrder;
import com.example.comicreader.R;
import com.example.comicreader.Service.PicassoImageLoadingService;
import com.github.vipulasri.timelineview.TimelineView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import ss.com.bannerslider.Slider;

public class ReadingOrderFragment extends Fragment implements IReadingOrderDone {

    RecyclerView readingOrderRv;
    SwipeRefreshLayout swipeRefreshLayout;

    DatabaseReference readingOrder;

    IReadingOrderDone readingOrderListener;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public ReadingOrderFragment() { }

    public static ReadingOrderFragment newInstance(String param1, String param2) {
        ReadingOrderFragment fragment = new ReadingOrderFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view  = inflater.inflate(R.layout.fragment_timeline, container, false);

        readingOrderRv = view.findViewById(R.id.readingOrderRv);
        readingOrderRv.setHasFixedSize(true);
        readingOrderRv.setLayoutManager(new LinearLayoutManager(getContext()));

        Slider.init(new PicassoImageLoadingService());
        swipeRefreshLayout = view.findViewById(R.id.swipeToRefresh);
        swipeRefreshLayout.setColorSchemeResources(com.google.android.material.R.color.design_default_color_primary,
                com.google.android.material.R.color.design_default_color_primary_dark);

        readingOrderListener = this;

        readingOrder = FirebaseDatabase.getInstance().getReference("Reading Orders");
        readingOrder.keepSynced(true);

        swipeRefreshLayout.setOnRefreshListener(this::loadReadingOrders);

        swipeRefreshLayout.post(this::loadReadingOrders);

        return view;
    }

    private void loadReadingOrders() {
        readingOrder.addListenerForSingleValueEvent(new ValueEventListener() {
            final List<ReadingOrder> readingOrder_load = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    ReadingOrder readingOrder = ds.getValue(ReadingOrder.class);
                    readingOrder_load.add(readingOrder);
                }
                readingOrderListener.OnReadingOrderLoadDoneListener(readingOrder_load);
                readingOrderRv.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void OnReadingOrderLoadDoneListener(List<ReadingOrder> readingOrderList) {
        Common.readingOrderList = readingOrderList;

        readingOrderRv.setAdapter(new MyReadingOrderAdapter(getActivity().getBaseContext(), readingOrderList));

        if (swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }
}