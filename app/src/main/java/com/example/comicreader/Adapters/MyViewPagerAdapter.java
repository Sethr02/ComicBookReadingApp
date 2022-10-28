package com.example.comicreader.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;

import com.example.comicreader.Interface.PageImageCallback;
import com.example.comicreader.R;
import com.example.comicreader.databinding.ActivityMainBinding;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

public class MyViewPagerAdapter extends PagerAdapter{
    Context context;
    List<String> imageLinks;
    LayoutInflater inflater;
    PageImageCallback pageImageCallback;

    public void setPageImageCallback(PageImageCallback pageImageCallback) {
        this.pageImageCallback = pageImageCallback;
    }

    public MyViewPagerAdapter(Context context, List<String> imageLinks) {
        this.context = context;
        this.imageLinks = imageLinks;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageLinks.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }



    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View image_layout = inflater.inflate(R.layout.view_pager_item, container, false);

        PhotoView page_image = image_layout.findViewById(R.id.page_image);

        //Picasso.get().load(imageLinks.get(position)).into(page_image);

        Picasso.get().load(imageLinks.get(position)).networkPolicy(NetworkPolicy.OFFLINE).into(page_image, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Picasso.get().load(imageLinks.get(position)).into(page_image);
            }
        });

        container.addView(page_image);

        page_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pageImageCallback.onClick();
                Toast.makeText(context, (position + 1) + "/" + imageLinks.size(), Toast.LENGTH_SHORT).show();
            }
        });

        return image_layout;
    }
}
