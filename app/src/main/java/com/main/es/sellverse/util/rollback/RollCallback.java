package com.main.es.sellverse.util.rollback;

import com.main.es.sellverse.model.Auction;

import java.util.List;

public interface RollCallback {
    void callBack(final List<Auction> list);
}
