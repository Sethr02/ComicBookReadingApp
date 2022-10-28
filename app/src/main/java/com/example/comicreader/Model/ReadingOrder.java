package com.example.comicreader.Model;

import java.util.List;

public class ReadingOrder {
    public String Name, Image, Description;
    public List<Comic> Comic;
    public List<Character> Characters;
    private boolean isShrink = true;

    public boolean isShrink() {
        return isShrink;
    }

    public void setShrink(boolean shrink) {
        isShrink = shrink;
    }
}
