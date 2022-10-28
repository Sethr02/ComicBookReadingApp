package com.example.comicreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comicreader.Adapters.MyViewPagerAdapter;
import com.example.comicreader.Common.Common;
import com.example.comicreader.Interface.PageImageCallback;
import com.example.comicreader.Model.Chapter;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.github.chrisbanes.photoview.OnViewTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wajahatkarim3.easyflipviewpager.BookFlipPageTransformer;

import java.util.Objects;

public class ComicActivity extends AppCompatActivity implements PageImageCallback {

    Toolbar toolbar;
    ViewPager viewPager;
    FloatingActionButton nextBtn, previousBtn;
    SeekBar seekBar;
    Dialog bottomDialog;
    DatabaseReference currentPage, completed;
    Integer CurrentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comic);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        initViews();
        fetchLinks(Common.chapterSelected);

        viewPager.setCurrentItem(Common.chapterSelected.CurrentPage);

        currentPage = FirebaseDatabase.getInstance().getReference("Comic").child(String.valueOf(Common.comicIndex)).child("Chapters").child(String.valueOf(Common.chapterIndex)).child("CurrentPage");
        completed = FirebaseDatabase.getInstance().getReference("Comic").child(String.valueOf(Common.comicIndex)).child("Chapters").child(String.valueOf(Common.chapterIndex)).child("Completed");

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(ComicActivity.this, (position + 1) + "/" + Common.chapterSelected.Links.size(), Toast.LENGTH_SHORT).show();
                CurrentPage = position;
                if ((position + 1) == Common.chapterSelected.Links.size()) {
                    completed.setValue(true).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(ComicActivity.this, "Chapter Complete", Toast.LENGTH_SHORT).show();
                        }
                    });
                    Toast.makeText(ComicActivity.this, (position + 1) + "/" + Common.chapterSelected.Links.size(), Toast.LENGTH_SHORT).show();
                    Common.chapterIndex++;
                    fetchLinks(Common.chapterList.get(Common.chapterIndex));
                }

                currentPage.setValue(position).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ComicActivity.this, "Success", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void showTopDialog() {
        Dialog topDialog = new Dialog(this, R.style.TopDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.custom_toolbar, null);
        topDialog.setContentView(contentView);

        toolbar = contentView.findViewById(R.id.comicToolbar);

        toolbar.setNavigationIcon(R.drawable.ic_back);

        toolbar.setSubtitle(Common.formatString(Common.chapterSelected.Name));
        toolbar.setTitle(Common.formatString(Common.comicSelected.Name));

        toolbar.setNavigationOnClickListener(view -> finish());

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - dp2px(this, 16f);
        params.bottomMargin = dp2px(this, 8f);
        contentView.setLayoutParams(params);
        topDialog.setOnCancelListener(dialogInterface -> {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            bottomDialog.hide();
        });
        topDialog.getWindow().setGravity(Gravity.TOP);
        topDialog.getWindow().setWindowAnimations(R.style.TopDialog_Animation);
        topDialog.show();
    }

    private void showBottomDialog() {
        bottomDialog = new Dialog(this, R.style.BottomDialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.controls_dialog, null);
        bottomDialog.setContentView(contentView);

        nextBtn = contentView.findViewById(R.id.nextBtn);
        previousBtn = contentView.findViewById(R.id.previousBtn);
        seekBar = contentView.findViewById(R.id.seekBar);
        seekBar.setMax(Common.chapterSelected.Links.size());
        seekBar.setProgress(viewPager.getCurrentItem());

        listenersSetup();

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
        params.width = getResources().getDisplayMetrics().widthPixels - dp2px(this, 16f);
        params.bottomMargin = dp2px(this, 8f);
        contentView.setLayoutParams(params);
        //bottomDialog.setCancelable(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    private void listenersSetup() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                viewPager.setCurrentItem(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        previousBtn.setOnClickListener(view -> {
            if (Common.chapterIndex == 0)
                Toast.makeText(ComicActivity.this, "You are Reading the First Chapter!", Toast.LENGTH_SHORT).show();
            else {
                Common.chapterIndex--;
                fetchLinks(Common.chapterList.get(Common.chapterIndex));
            }
        });

        nextBtn.setOnClickListener(view -> {
            if (Common.chapterIndex == Common.chapterList.size() - 1)
                Toast.makeText(ComicActivity.this, "You are Reading the Last Chapter!", Toast.LENGTH_SHORT).show();
            else {
                Common.chapterIndex++;
                fetchLinks(Common.chapterList.get(Common.chapterIndex));
            }
        });
    }

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal,
                context.getResources().getDisplayMetrics());
    }

    private void fetchLinks(Chapter chapter) {
        if (chapter.Links != null) {
            if (chapter.Links.size() > 0) {
                MyViewPagerAdapter adapter = new MyViewPagerAdapter(getBaseContext(), chapter.Links);
                adapter.setPageImageCallback(this);
                viewPager.setAdapter(adapter);
            } else {
                Toast.makeText(this, "No Image...", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Please wait...", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews() {
        Objects.requireNonNull(getSupportActionBar()).hide();
        viewPager = findViewById(R.id.viewPager);
    }

    @Override
    public void onClick() {
        showBottomDialog();
        //showTopDialog();
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}