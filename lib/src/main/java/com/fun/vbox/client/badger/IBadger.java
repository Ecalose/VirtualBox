package com.fun.vbox.client.badger;

import android.content.Intent;

import com.fun.vbox.remote.BadgerInfo;

/**
 * @author Lody
 */
public interface IBadger {

    String getAction();

    BadgerInfo handleBadger(Intent intent);

}
