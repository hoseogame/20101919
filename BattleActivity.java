package com.example.jin.loadgame;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.Set;

public class BattleActivity extends Activity implements View.OnClickListener{

    private ImageView               mMyChar, mMyWarp , mEnemy ,mEnemyWarp ,  mMySheild, mEnemySheild;
    private AnimationDrawable       mMyAnChar, mMyAnWarp , mEnemyAnWarp,mAnEnemy , mMyAnSheild , mEnemyAnSheild;

    private long               mTime = System.currentTimeMillis();
    private long               mTimeTemp = 0 , mTimecurr = 0;
    private ImageButton         mRunButton;
    private ImageButton         attbutton, defbutton, coubutton, chabutton ;

    private ProgressBar         myhppro, enemyhppro, attpro, defpro, coupro;

    private TranslateAnimation  AttTA, CouTA;
    private AlphaAnimation      AlphaAn;

    private int                 mMyinthppro = 70, mEnemyinthppro = 50;
    private int                 intattpro = 30, intdefpro = 20, intcoupro = 40;
    private int                 mCount = 0, mEnemyCount = 0;
    private int[]               mEnemyFSM = {4,4,4,4,1,2,3,1,1,1};

    private boolean             mButton = false;

    //private BluetoothAdapter        mBtAdapter;
    //private CountThread             mCountThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battle);

        TranslateAnimation tempAn = new TranslateAnimation(0,0,0,100);
        tempAn.setRepeatMode(Animation.REVERSE);
        tempAn.setDuration(800);
        tempAn.setRepeatCount(1);

        AttTA = new TranslateAnimation(0,100,0,0);
        AttTA.setRepeatMode(Animation.REVERSE);
        AttTA.setDuration(800);
        AttTA.setRepeatCount(1);

        CouTA = new TranslateAnimation(0,-100,0,0);
        CouTA.setRepeatMode(Animation.REVERSE);
        CouTA.setDuration(800);
        CouTA.setRepeatCount(1);

        AlphaAn = new AlphaAnimation(1.0f, 0.5f);
        AlphaAn.setDuration(200);
        AlphaAn.setRepeatCount(3);

        mMySheild = (ImageView)findViewById(R.id.mysheild);
        mMySheild.setVisibility(View.GONE);

        mEnemySheild = (ImageView)findViewById(R.id.enemysheild);
        mEnemySheild.setVisibility(View.GONE);

        attbutton = (ImageButton)findViewById(R.id.attbutton);
        attbutton.setOnClickListener(this);

        defbutton = (ImageButton)findViewById(R.id.defbutton);
        defbutton.setOnClickListener(this);

        coubutton = (ImageButton)findViewById(R.id.coubutton);
        coubutton.setOnClickListener(this);

        chabutton = (ImageButton)findViewById(R.id.chabutton);
        chabutton.setOnClickListener(this);

        attpro = (ProgressBar)findViewById(R.id.attpro);
        attpro.setProgress(intattpro);

        defpro = (ProgressBar)findViewById(R.id.defpro);
        defpro.setProgress(intdefpro);

        coupro = (ProgressBar)findViewById(R.id.coupro);
        coupro.setProgress(intcoupro);

        myhppro = (ProgressBar)findViewById(R.id.myhppro);
        myhppro.setProgress(mMyinthppro);

        enemyhppro = (ProgressBar)findViewById(R.id.enemyhppro);
        enemyhppro.setProgress(mEnemyinthppro);

        mMyChar = (ImageView)findViewById(R.id.battle_mych);
        mMyAnChar = (AnimationDrawable) mMyChar.getBackground();

        mEnemy = (ImageView)findViewById(R.id.enemy);
        mAnEnemy = (AnimationDrawable) mEnemy.getBackground();

        mMyAnSheild = (AnimationDrawable) mMySheild.getBackground();
        mEnemyAnSheild = (AnimationDrawable) mEnemySheild.getBackground();

        //mMyChar.startAnimation(tempAn);

        mMyAnChar.start();
        mAnEnemy.start();

        mMyWarp = (ImageView)findViewById(R.id.battle_mywarp);
        mMyAnWarp = (AnimationDrawable) mMyWarp.getBackground();

        mEnemyWarp = (ImageView)findViewById(R.id.battle_enemywarp);
        mEnemyAnWarp = (AnimationDrawable) mEnemyWarp.getBackground();

        mRunButton = (ImageButton)findViewById(R.id.run_button);
        mRunButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.run_button:
                Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.attbutton:
                mEnemySheild.setVisibility(View.GONE);
                mMySheild.setVisibility(View.GONE);
                mEnemyAnSheild.stop();
                mMyAnSheild.stop();
                mEnemyAnWarp.stop();
                mMyAnWarp.stop();
                mCount = 1;
                intattpro -= 10;
                if(intattpro < 0){
                    intattpro = 0;
                    mCount = 5;
                }

                attpro.setProgress(intattpro);
                BattleFSM();
                break;
            case R.id.defbutton:
                mEnemySheild.setVisibility(View.GONE);
                mMySheild.setVisibility(View.GONE);
                mEnemyAnSheild.stop();
                mMyAnSheild.stop();
                mEnemyAnWarp.stop();
                mMyAnWarp.stop();
                mCount = 2;
                intdefpro -= 10;
                if(intdefpro <0) {
                    intdefpro = 0;
                    mCount = 5;
                }
                defpro.setProgress(intdefpro);
                BattleFSM();
                break;
            case R.id.coubutton:
                mEnemySheild.setVisibility(View.GONE);
                mMySheild.setVisibility(View.GONE);
                mEnemyAnSheild.stop();
                mMyAnSheild.stop();
                mEnemyAnWarp.stop();
                mMyAnWarp.stop();
                mCount = 3;
                intcoupro -= 10;
                if(intcoupro <0) {
                    intcoupro = 0;
                    mCount = 5;
                }
                coupro.setProgress(intcoupro);
                BattleFSM();
                break;
            case R.id.chabutton:
                mEnemySheild.setVisibility(View.GONE);
                mMySheild.setVisibility(View.GONE);
                mEnemyAnSheild.stop();
                mMyAnSheild.stop();
                mEnemyAnWarp.stop();
                mMyAnWarp.stop();
                mCount = 4;
                AddChaButton();
                BattleFSM();
                break;
        }
    }
    void BattleFSM(){
        if(mEnemyFSM[mEnemyCount] == 1){
            mEnemy.startAnimation(AttTA);
        }
        else if(mEnemyFSM[mEnemyCount] == 2){
            mEnemySheild.setVisibility(View.VISIBLE);
            mEnemyAnSheild.start();
            mEnemyAnSheild.setOneShot(true);
        }
        else if(mEnemyFSM[mEnemyCount] == 3){
            mEnemy.startAnimation(CouTA);
        }
        else {
            mEnemyAnWarp.start();
            mEnemyAnWarp.setOneShot(true);
        }

        if( mCount == 1){
            mMyChar.startAnimation(AttTA);
            if(mEnemyFSM[mEnemyCount] == 1){
                mMyinthppro -= 10;
                mEnemyinthppro -= 10;
                mMyChar.startAnimation(AlphaAn);
                mEnemy.startAnimation(AlphaAn);
            }
            else if(mEnemyFSM[mEnemyCount] == 2){
                mEnemyinthppro -= 5;
                mEnemy.startAnimation(AlphaAn);
            }
            else if(mEnemyFSM[mEnemyCount] == 3){
                mMyinthppro -= 15;
                mMyChar.startAnimation(AlphaAn);
            }
            else {
                mEnemyinthppro -= 15;
                mEnemy.startAnimation(AlphaAn);
            }
        }
        else if( mCount == 2){
            mMySheild.setVisibility(View.VISIBLE);
            mMyAnSheild.start();
            mMyAnSheild.setOneShot(true);
            if(mEnemyFSM[mEnemyCount] == 1){
                mMyinthppro -= 5;
                mMyChar.startAnimation(AlphaAn);

            }
            else if(mEnemyFSM[mEnemyCount] == 3){
                mEnemyinthppro -= 15;
                mEnemy.startAnimation(AlphaAn);
            }
        }
        else if( mCount == 3){
            mMyChar.startAnimation(CouTA);
            if(mEnemyFSM[mEnemyCount] == 1){
                mEnemyinthppro -= 15;
                mEnemy.startAnimation(AlphaAn);
            }
            else if(mEnemyFSM[mEnemyCount] == 2){
                mMyinthppro -= 15;
                mMyChar.startAnimation(AlphaAn);
            }
            else if(mEnemyFSM[mEnemyCount] == 4){
                mMyinthppro -= 15;
                mMyChar.startAnimation(AlphaAn);
            }
        }
        else if(mCount == 4) {
            mMyAnWarp.start();
            mMyAnWarp.setOneShot(true);
        }
        else {
            mMyinthppro -= 15;
            mMyChar.startAnimation(AlphaAn);
        }

        myhppro.setProgress(mMyinthppro);
        enemyhppro.setProgress(mEnemyinthppro);

        mEnemyCount++;
        if(mEnemyCount <= 10) mEnemyCount = 0;

        if(mMyinthppro <= 0 || mEnemyinthppro <= 0){
            Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
            finish();
        }
    }
    void AddChaButton() {
        intattpro += 10;
        if(intattpro >=100) intattpro = 100;
        attpro.setProgress(intattpro);
        intdefpro += 10;
        if(intdefpro >=100) intdefpro = 100;
        defpro.setProgress(intdefpro);
        intcoupro += 10;
        if(intcoupro >=100) intcoupro = 100;
        coupro.setProgress(intcoupro);

    }

    @Override
    public void onBackPressed(){

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
