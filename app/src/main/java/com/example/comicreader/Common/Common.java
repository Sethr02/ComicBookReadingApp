package com.example.comicreader.Common;

import com.example.comicreader.Model.Chapter;
import com.example.comicreader.Model.Character;
import com.example.comicreader.Model.Comic;
import com.example.comicreader.Model.ReadingOrder;

import java.util.ArrayList;
import java.util.List;

public class Common {
    public static List<Comic> comicList = new ArrayList<>();
    public static Comic comicSelected;
    public static List<Chapter> chapterList;
    public static Chapter chapterSelected;
    public static int chapterIndex = -1;
    public static int comicIndex = -1;
    public static List<ReadingOrder> readingOrderList;
    public static List<Character> characterList;
    public static ReadingOrder readingOrderSelected;
    public static int readingOrderIndex = -1;

    public static String formatString(String name) {
        StringBuilder finalResult = new StringBuilder(name.length() > 15?name.substring(0, 15) + "...":name);
        return finalResult.toString();
    }
}
