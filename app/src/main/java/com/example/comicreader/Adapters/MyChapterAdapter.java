package com.example.comicreader.Adapters;

import static android.content.Context.DOWNLOAD_SERVICE;
import static android.os.Environment.DIRECTORY_DOWNLOADS;
import static android.os.Environment.getDownloadCacheDirectory;

import static com.example.comicreader.App.CHANNEL_1_ID;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comicreader.ComicActivity;
import com.example.comicreader.Common.Common;
import com.example.comicreader.Interface.IRecyclerItemClickListener;
import com.example.comicreader.Model.Chapter;
import com.example.comicreader.R;

import java.io.File;
import java.util.List;

public class MyChapterAdapter extends RecyclerView.Adapter<MyChapterAdapter.MyViewHolder> {
    Context context;
    List<Chapter> chapterList;
    LayoutInflater inflater;
    Integer totalFileSize;

    File dir;

    public MyChapterAdapter(Context context, List<Chapter> chapterList) {
        this.context = context;
        this.chapterList = chapterList;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public MyChapterAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.chapter_item, parent, false);
        return new MyChapterAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyChapterAdapter.MyViewHolder holder, int position) {
        holder.chapterNumber.setText(chapterList.get(position).Name);

        holder.setRecyclerItemClickListener((view, position1) -> {
            Common.chapterSelected = chapterList.get(position1);
            Common.chapterIndex = position1;
            context.startActivity(new Intent(context, ComicActivity.class));
        });

        holder.downloadChapter.setOnClickListener(view -> {
            List<String> url = chapterList.get(position).Links;
            for (int i = 0; i < url.size(); i++) {
                downloadFile(context, URLUtil.guessFileName(url.get(i), null, null), chapterList.get(position).Name, url.get(i));
            }
        });

        if (chapterList.get(position).Completed) {
            holder.downloadChapter.setBackgroundResource(R.drawable.check_circle);
        } else {
            holder.downloadChapter.setBackgroundResource(R.drawable.check_circle_outline);
        }
    }

    public void askForPermissions(String name) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            if (!Environment.isExternalStorageManager()) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                context.startActivity(intent);
                return;
            }
            createDir(name);
        }
    }

    public void createDir(String name) {
        dir = new File(new File(Environment.getExternalStorageDirectory(), "Comic Reader"), name);
        if (!dir.exists()) {
            Toast.makeText(context.getApplicationContext(), "Directory Created Successfully!", Toast.LENGTH_SHORT).show();
            dir.mkdirs();
        } else {
            Toast.makeText(context.getApplicationContext(), "Directory Already Exists!", Toast.LENGTH_SHORT).show();
        }
    }

    public void downloadFile(Context context, String fileName, String destinationDirectory, String url) {
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context, String.valueOf(getDownloadCacheDirectory()), "/Comic Reader/" + destinationDirectory + "/" + fileName);

        downloadManager.enqueue(request);
    }

    @Override
    public int getItemCount() {
        return chapterList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView chapterNumber;
        IRecyclerItemClickListener recyclerItemClickListener;
        ImageButton downloadChapter;

        public void setRecyclerItemClickListener(IRecyclerItemClickListener recyclerItemClickListener) {
            this.recyclerItemClickListener = recyclerItemClickListener;
        }

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            chapterNumber = itemView.findViewById(R.id.chapterNumber);
            downloadChapter = itemView.findViewById(R.id.downloadChapter);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            recyclerItemClickListener.onClick(view, getAdapterPosition());
        }
    }
}
