package com.example.comicreader.Model;

import java.util.List;

public class Character {
    public String CurrentAlias, Name, Image, FirstAppearance, Creators, Powers, Members, Description;
    private boolean expandable;

    public Character() {
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }
}
