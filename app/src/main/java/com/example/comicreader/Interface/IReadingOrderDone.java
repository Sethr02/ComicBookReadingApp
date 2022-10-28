package com.example.comicreader.Interface;

import com.example.comicreader.Model.ReadingOrder;

import java.util.List;

public interface IReadingOrderDone {
    void OnReadingOrderLoadDoneListener(List<ReadingOrder> readingOrderList);
}
