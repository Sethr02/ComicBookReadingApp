package com.example.comicreader.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicreader.ChaptersActivity;
import com.example.comicreader.Common.Common;
import com.example.comicreader.Interface.IRecyclerItemClickListener;
import com.example.comicreader.Model.Comic;
import com.example.comicreader.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import ru.embersoft.expandabletextview.ExpandableTextView;


public class MyComicAdapter extends RecyclerView.Adapter<MyComicAdapter.MyViewHolder> implements Filterable {

    Context context;
    List<Comic> comicList;
    List<Comic> comicListFull;
    LayoutInflater inflater;

    DatabaseReference favouriteRef;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    public MyComicAdapter(Context context, List<Comic> comicList) {
        this.context = context;
        this.comicListFull = comicList;
        this.comicList = new ArrayList<>(comicListFull);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.comic_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if (comicList.get(position).Image.equals("")) {
            Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/comic-book-app-12d12.appspot.com/o/marvel.jpg?alt=media&token=800c62cd-5d82-40d5-b421-4f526ac6ec96").into(holder.comicImage);
        } else {
            Picasso.get().load(comicList.get(position).Image).into(holder.comicImage);
        }

        holder.comicName.setText(comicList.get(position).Name);
        holder.descTv.setText(comicList.get(position).Description);

        if (holder.getAdapterPosition() > -1) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in);
            holder.itemView.startAnimation(animation);
        }

        holder.descTv.setOnStateChangeListener(isShrink -> {
            Comic contentItem = comicList.get(position);
            contentItem.setShrink(isShrink);
            comicList.set(position, contentItem);
        });
        holder.descTv.setText(comicList.get(position).Description);
        holder.descTv.resetState(comicList.get(position).isShrink());

        holder.setRecyclerItemClickListener((view, position1) -> {
            Common.comicSelected = comicList.get(position1);
            Common.comicIndex = position1;
            Intent intent = new Intent(context.getApplicationContext(), ChaptersActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });

        holder.favBtn.setOnClickListener(view -> holder.favBtn.setBackgroundResource(R.drawable.ic_favorite));
    }

    @Override
    public int getItemCount() {
        return comicList.size();
    }

    @Override
    public Filter getFilter() {
        return comicFilter;
    }

    private final Filter comicFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Comic> filteredComicList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0) {
                filteredComicList.addAll(comicListFull);
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Comic comic : comicListFull) {
                    if (comic.Name.toLowerCase().contains(filterPattern))
                        comicListFull.add(comic);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredComicList;
            results.count = filteredComicList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            comicList.clear();
            comicList.addAll((List) filterResults.values);
            notifyDataSetChanged();
        }
    };

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView comicName;
        ExpandableTextView descTv;
        ImageView comicImage;
        CardView cardView;
        Button favBtn;

        IRecyclerItemClickListener recyclerItemClickListener;

        public void setRecyclerItemClickListener(IRecyclerItemClickListener recyclerItemClickListener) {
            this.recyclerItemClickListener = recyclerItemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            comicName = itemView.findViewById(R.id.comicName);
            descTv = itemView.findViewById(R.id.descTv);
            comicImage = itemView.findViewById(R.id.comicImage);
            cardView = itemView.findViewById(R.id.cardView);
            favBtn = itemView.findViewById(R.id.favBtn);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recyclerItemClickListener.onClick(view, getAdapterPosition());
        }
    }
}
