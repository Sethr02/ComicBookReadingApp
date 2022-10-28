package com.example.comicreader.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicreader.ChaptersActivity;
import com.example.comicreader.Common.Common;
import com.example.comicreader.Fragments.ReadingOrderComicsFragment;
import com.example.comicreader.Interface.IRecyclerItemClickListener;
import com.example.comicreader.Model.Comic;
import com.example.comicreader.Model.ReadingOrder;
import com.example.comicreader.R;
import com.example.comicreader.ReadingOrderActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import ru.embersoft.expandabletextview.ExpandableTextView;

public class MyReadingOrderAdapter extends RecyclerView.Adapter<MyReadingOrderAdapter.MyViewHolder>{

    Context context;
    List<ReadingOrder> readingOrderList;
    LayoutInflater inflater;

    public MyReadingOrderAdapter(Context context, List<ReadingOrder> readingOrderList) {
        this.context = context;
        this.readingOrderList = readingOrderList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.reading_order_item, parent, false);
        return new MyReadingOrderAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.comicName.setText(readingOrderList.get(position).Name);
        holder.descTv.setText(readingOrderList.get(position).Description);

        holder.descTv.setOnStateChangeListener(isShrink -> {
            ReadingOrder contentItem = readingOrderList.get(position);
            contentItem.setShrink(isShrink);
            readingOrderList.set(position, contentItem);
        });

        holder.descTv.setText(readingOrderList.get(position).Description);
        holder.descTv.resetState(readingOrderList.get(position).isShrink());

        holder.setRecyclerItemClickListener((view, position1) -> {
            Common.readingOrderSelected = readingOrderList.get(position1);
            Common.readingOrderIndex = position1;
            Intent intent = new Intent(context.getApplicationContext(), ReadingOrderActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return readingOrderList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView comicName;
        ExpandableTextView descTv;
        CardView cardView;

        IRecyclerItemClickListener recyclerItemClickListener;

        public void setRecyclerItemClickListener(IRecyclerItemClickListener recyclerItemClickListener) {
            this.recyclerItemClickListener = recyclerItemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            comicName = itemView.findViewById(R.id.comicName);
            descTv = itemView.findViewById(R.id.descTv);
            cardView = itemView.findViewById(R.id.cardView);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recyclerItemClickListener.onClick(view, getAdapterPosition());
        }
    }
}
