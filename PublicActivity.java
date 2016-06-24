package com.example.jin.loadgame;

import android.app.Activity;
import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.Set;

public class PublicActivity extends Application {

    private static int statelayout = 0;
    public static boolean startthread = true;
    public static Activity activity = null;

    public void SetStateLayout(int statelayout){this.statelayout = statelayout;}
    public int GetStateLayout(){return statelayout;}

}
