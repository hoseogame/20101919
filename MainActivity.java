package com.example.jin.loadgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends Activity {

    private SendMassgeHandler   mMainHandler = null; // 핸들
    private CountThread         mCountThread = null; // 쓰레드
    private long               mTime = System.currentTimeMillis();
    private long               mTimeTemp = 0 , mTimecurr = 0;
    private int                mNext = 0;
    private Intent             intent;

    private ImageView           imageView1;
    private LinearLayout        imageView2, layout_p, layout_g;
    private Animation           anim;
    private AnimationDrawable   tempanim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ((PublicActivity) this.getApplication()).startthread = true;

        mMainHandler = new SendMassgeHandler();
        mCountThread = new CountThread();
        mCountThread.start();

        imageView1 = (ImageView)findViewById(R.id.imageView1);
        imageView1.setVisibility(View.VISIBLE);

        imageView2 = (LinearLayout)findViewById(R.id.mainView2);
        imageView2.setVisibility(View.GONE);

        layout_p = (LinearLayout)findViewById(R.id.layout_p);
        layout_g = (LinearLayout)findViewById(R.id.layout_g);

        anim = AnimationUtils.loadAnimation(this, R.xml.main);
    }

    class SendMassgeHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:

                    break;
                case 1:
                    imageView1.setVisibility(View.GONE);
                    imageView2.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    mCountThread.stopThread();
                    intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 3:
                    mCountThread.stopThread();
                    intent = new Intent(getApplicationContext(), LoadActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 4:
                    mCountThread.stopThread();
                    intent = new Intent(getApplicationContext(), BattleActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                default:
                    break;
            }
        }
    }

    class CountThread extends Thread implements Runnable {

        private boolean isPlay = false;

        public CountThread() {
            isPlay = true;
        }

        public void isThreadState(boolean isPlay) {
            this.isPlay = isPlay;
        }

        public void stopThread() {
            isPlay = !isPlay;
        }

        public void run() {

            super.run();

            while(isPlay){

                if(mTimecurr > 1000){
                    mTimecurr = 0;
                    TelephonyManager mt = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
                    if(mNext == 0) {
                        mNext = 1;
                        layout_p.startAnimation(anim);
                        layout_g.startAnimation(anim);
                    }else
                        mNext = 2;
                }
                else {
                    mTimeTemp = System.currentTimeMillis() - mTime;
                    mTime = System.currentTimeMillis();
                    mTimecurr += mTimeTemp;
                }
                Message msg = mMainHandler.obtainMessage();
                msg.what = mNext;

                mMainHandler.sendMessage(msg);

                try { Thread.sleep(1000); }
                catch (InterruptedException e) { e.printStackTrace(); }
            }
        }
    }
}
