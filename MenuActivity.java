package com.example.jin.loadgame;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Region;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;

import android.os.Handler;
import android.os.Message;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MenuActivity extends Activity implements View.OnClickListener {

    private ImageView mMyChar;
    private AnimationDrawable mMyAnChar;
    private BluetoothAdapter mBtAdapter;
    private CountThread mCountThread;
    private int mCount;

    private Button mAttribute_button, mRight_view_ok_button;
    private ImageButton mXButton, mStart_button, mRight_view_back_button, mRight_view_next_button;
    private Button mBookButton;
    private LinearLayout mRight_view;

    private TextView mLocation_text;

    private long mTime = System.currentTimeMillis();
    private long mTimeTemp = 0, mTimecurr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mCount = 0;

        mMyChar = (ImageView) findViewById(R.id.mychar);
        mMyAnChar = (AnimationDrawable) mMyChar.getBackground();

        mMyAnChar.start();

        mAttribute_button = (Button) findViewById(R.id.Attribute_view_button);
        mAttribute_button.setOnClickListener(this);

        mXButton = (ImageButton) findViewById(R.id.x_button);
        mXButton.setOnClickListener(this);

        mBookButton = (Button) findViewById(R.id.bookbutton);
        mBookButton.setOnClickListener(this);

        mStart_button = (ImageButton) findViewById(R.id.start_button);
        mStart_button.setOnClickListener(this);

        mRight_view_ok_button = (Button) findViewById(R.id.right_view_ok_button);
        mRight_view_ok_button.setOnClickListener(this);

        mRight_view_back_button = (ImageButton) findViewById(R.id.right_view_back_button);
        mRight_view_back_button.setOnClickListener(this);

        mRight_view_next_button = (ImageButton) findViewById(R.id.right_view_next_button);
        mRight_view_next_button.setOnClickListener(this);

        mRight_view = (LinearLayout) findViewById(R.id.right_view);
        mRight_view.setVisibility(View.GONE);

        mLocation_text = (TextView) findViewById(R.id.location_text);
        mLocation_text.setText(" ");

       // if( ((PublicActivity) this.getApplication()).startthread == true) {
            mCountThread = new CountThread();
            mCountThread.start();
           // ((PublicActivity) this.getApplication()).startthread = false;

            mBtAdapter = BluetoothAdapter.getDefaultAdapter();
            Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
            this.registerReceiver(mReceiver, filter);

            filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
            this.registerReceiver(mReceiver, filter);
      //  }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bookbutton:
                if(mCount == 0) {

                    Intent intent = new Intent(getApplicationContext(), BookActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.Attribute_view_button:
                mCount = 1;
                mRight_view.setVisibility(View.VISIBLE);
                break;
            case R.id.x_button:
                onBackPressed();
                break;
            case R.id.right_view_ok_button:
                mCount = 0;
                mRight_view.setVisibility(View.GONE);
                break;
            case R.id.right_view_back_button:
                if (mCount == 1) {
                    mRight_view.setBackgroundResource(R.drawable.attribute_2);
                    mCount = 2;
                } else if (mCount == 2) {
                    mRight_view.setBackgroundResource(R.drawable.attribute);
                    mCount = 1;
                }
                break;
            case R.id.right_view_next_button:
                if (mCount == 1) {
                    mRight_view.setBackgroundResource(R.drawable.attribute_2);
                    mCount = 2;
                } else if (mCount == 2) {
                    mRight_view.setBackgroundResource(R.drawable.attribute);
                    mCount = 1;
                }
                break;
            case R.id.start_button:
                //mCountThread.stopThread();
                ((PublicActivity) this.getApplication()).SetStateLayout(1);
                Intent intent = new Intent(getApplicationContext(), LoadActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
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

            while (isPlay) {

                if (mBtAdapter != null) {
                    if (mBtAdapter.isDiscovering()) {
                        mBtAdapter.cancelDiscovery();
                    }
                    mBtAdapter.startDiscovery();
                    UpDataBeacon();
                }
                try {Thread.sleep(800);}
                catch (InterruptedException e) {e.printStackTrace();}
            }
        }
    }

    private void UpDataBeacon() {

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);
    }

    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //mCountThread.stopThread();
            String action = intent.getAction();

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {

                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                short rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {

                    SetUpData(device.getName(), device.getAddress(), rssi);
                    /// rssi 비콘의 세기 -99 ~ -1 까지
                    //mNewDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress() + " Rssi" + rssi);
                    //getAddress() : 아이디 값
                    //getName() : 이름
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setProgressBarIndeterminateVisibility(false);
            }
        }
    };

    @Override
    public void onBackPressed() {

        if (mCount == 0) {

            finish();
        }
    }

    private void SetUpData(String name, String ID, short Location) {

        if (name.equals("KIBEACON")) {

            if(((PublicActivity)getApplication()).GetStateLayout() == 0) {
                mLocation_text.setText("내거임");
            }
            else if (((PublicActivity)getApplication()).GetStateLayout() == 1 ) {
                ((PublicActivity) this.getApplication()).SetStateLayout(2);
                LoadActivity loadActivity = (LoadActivity) ((PublicActivity)this.getApplication()).activity;
                Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
                startActivity(intent);
                loadActivity.finish();
            }
            else {
            }
        }
        if (name.equals("RECO")) {

            if(((PublicActivity)getApplication()).GetStateLayout() == 0) {
                mLocation_text.setText("내거임");
            }
            else if (((PublicActivity)getApplication()).GetStateLayout() == 1 ) {
                ((PublicActivity) this.getApplication()).SetStateLayout(2);
                LoadActivity loadActivity = (LoadActivity) ((PublicActivity)this.getApplication()).activity;
                Intent intent = new Intent(getApplicationContext(), BattleActivity.class);
                startActivity(intent);
                loadActivity.finish();
            }
            else {

            }
        }
    }
}
