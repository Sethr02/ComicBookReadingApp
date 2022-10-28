package com.example.comicreader.Model;

import java.util.List;

public class Comic{
    public String Name, Image, Description, Category;
    public List<Chapter> Chapters;
    private boolean isShrink = true;

    public Comic() {
    }

    public boolean isShrink() {
        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }
}
