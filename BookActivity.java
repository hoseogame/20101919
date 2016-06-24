package com.example.jin.loadgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class BookActivity extends Activity implements View.OnClickListener {

    private ImageButton         pbook_f, pbook_w, pbook_t, pbook_d, pbook_p;
    private ImageButton         n1, n2, n3, n4;
    private int                 mCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        pbook_f = (ImageButton)findViewById(R.id.pbook_f);
        pbook_f.setOnClickListener(this);

        pbook_w = (ImageButton)findViewById(R.id.pbook_w);
        pbook_w.setOnClickListener(this);

        pbook_t = (ImageButton)findViewById(R.id.pbook_t);
        pbook_t.setOnClickListener(this);
        pbook_t.setBackgroundResource(R.drawable.pbook_t2);

        pbook_d = (ImageButton)findViewById(R.id.pbook_d);
        pbook_d.setOnClickListener(this);

        pbook_p = (ImageButton)findViewById(R.id.pbook_p);
        pbook_p.setOnClickListener(this);

        n1 = (ImageButton)findViewById(R.id.n1);
        n1.setOnClickListener(this);

        n2 = (ImageButton)findViewById(R.id.n2);
        n2.setOnClickListener(this);

        n3 = (ImageButton)findViewById(R.id.n3);
        n3.setOnClickListener(this);

        n4 = (ImageButton)findViewById(R.id.n4);
        n4.setOnClickListener(this);
        boook_n_Init();
        pbook_w.setBackgroundResource(R.drawable.pbook_w2);
    }

    void Init() {
        pbook_p.setBackgroundResource(R.drawable.pbook_p);
        pbook_d.setBackgroundResource(R.drawable.pbook_d);
        pbook_t.setBackgroundResource(R.drawable.pbook_t);
        pbook_w.setBackgroundResource(R.drawable.pbook_w);
        pbook_f.setBackgroundResource(R.drawable.pbook_f);
    }

    void boook_n_Init()
    {
        if(mCount == 1) {
            n1.setBackgroundResource(R.drawable.n2);
            n2.setBackgroundResource(R.drawable.n3);
            n3.setBackgroundResource(R.drawable.n4);
            n4.setBackgroundResource(R.drawable.n11);
        }
        else if(mCount == 2){
            n1.setBackgroundResource(R.drawable.n7);
            n2.setBackgroundResource(R.drawable.n8);
            n3.setBackgroundResource(R.drawable.n12);
            n4.setBackgroundResource(R.drawable.n13);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.n1:
                if(mCount == 1) {
                    boook_n_Init();
                    n1.setBackgroundResource(R.drawable.ns2);
                }
                else if(mCount == 2) {
                    boook_n_Init();
                    n1.setBackgroundResource(R.drawable.ns7);
                }
                break;
            case R.id.n2:
                if(mCount == 1) {
                    boook_n_Init();
                    n2.setBackgroundResource(R.drawable.ns3);
                }
                else if(mCount == 2) {
                    boook_n_Init();
                    n2.setBackgroundResource(R.drawable.ns8);
                }
                break;
            case R.id.pbook_f:
                mCount = 0;
                Init();
                pbook_f.setBackgroundResource(R.drawable.pbook_f2);
                boook_n_Init();
                break;
            case R.id.pbook_w:
                mCount = 1;
                Init();
                pbook_w.setBackgroundResource(R.drawable.pbook_w2);
                boook_n_Init();
                break;
            case R.id.pbook_t:
                mCount = 2;
                Init();
                pbook_t.setBackgroundResource(R.drawable.pbook_t2);
                boook_n_Init();
                break;
            case R.id.pbook_d:
                mCount = 3;
                Init();
                pbook_d.setBackgroundResource(R.drawable.pbook_d2);
                boook_n_Init();
                break;
            case R.id.pbook_p:
                mCount = 4;
                Init();
                pbook_p.setBackgroundResource(R.drawable.pbook_p2);
                boook_n_Init();
                break;
        }
    }

    @Override
    public void onBackPressed(){
        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
