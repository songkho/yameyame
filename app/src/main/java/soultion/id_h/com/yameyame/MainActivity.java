package soultion.id_h.com.yameyame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
//import android.support.annotation.RequiresApi;
//import android.support.v7.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements RewardedVideoAdListener, View.OnClickListener {

    private InterstitialAd mInterstitialAd;  //전면광고용
    private AdView mAdView;  // 하단 관고용

    RewardedVideoAd mRewardedVideoAd;

    LinearLayout sharelay, shareb, btn4;
    ImageView btn1;
    TextView sharetv, shareE, tv7;
    Vibrator vib;
    Button btn2 , btn3 , btn7  ,  btn5 ;


    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    //
    @Override
    public void onBackPressed() {

//----------------------------------------------------------------------
        long tempTime = System.currentTimeMillis();
        long intervalTime = tempTime - backPressedTime;

        if (0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime) {
            super.onBackPressed();
            vib.cancel();

        } else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(), "'뒤로'버튼을한번더누르시면종료됩니다.\n     Click the'Back' button again to finish.", Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //////////////---------네트워크 연결 ----------/////////////////
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm.getActiveNetworkInfo() != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            switch (activeNetwork.getType()) {
                case ConnectivityManager.TYPE_WIMAX:
                    //Log.d("Test","Wibro 네트워크연결");

                    break;
                case ConnectivityManager.TYPE_WIFI:
                    //Log.d("Test","WiFi 네트워크연결");

                    break;
                case ConnectivityManager.TYPE_MOBILE:
                    //Log.d("Test","3G 네트워크연결");

                    break;
            }
        } else {
            Log.d("Test", "네트워크연결 안됨");
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("인터넷 연결 에러(Data connection request)");
            alertDialog.setMessage("인터넷 연결을 다시 확인해주세요! \n연결 후 다시 만나요 :) \n\n" +
                    "Data connection request");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    android.os.Process.killProcess(android.os.Process.myPid());
                }
            });
            alertDialog.show();
        }


        //////////////---------네트워크 연결 ----------/////////////////


        btn7 = (Button) findViewById(R.id.button7);
        tv7 = (TextView) findViewById(R.id.textView7);


        //   ------------------   /////////////// -- 실제 전면 시작 ---/////////////////////////////////////////////---//

        MobileAds.initialize(this, "ca-app-pub-2821664824480291~3107429569");

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-2821664824480291/1776197566");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("asd", "The interstitial wasn't loaded yet.");
                }
            }
        });

        //   ------------------   /////////////// -- 실제 전면 끝 ---/////////////////////////////////////////////---//



        ////////////////////////////////////리워드 시작/////////////////////////////////////////////////////


        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener((RewardedVideoAdListener) this);
        mRewardedVideoAd.loadAd("ca-app-pub-2821664824480291/1878587871", new AdRequest.Builder().build());


        ////////////////////////////////////리워드 끝/////////////////////////////////////////////////////


        ////하단광고

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        AdView adView = new AdView(this);
        adView.setAdSize(AdSize.SMART_BANNER);

        ////하단 광고 끝



        btn7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                btn7.setEnabled(false);
                if (mRewardedVideoAd.isLoaded()) {
                    mRewardedVideoAd.show();
                }

            }
        });


        ImageView img3 = (ImageView) findViewById(R.id.imageView5);
        sharelay = (LinearLayout) findViewById(R.id.sharelay);
        sharelay.setOnClickListener(this);
        shareb = (LinearLayout) findViewById(R.id.shareb);
        shareb.setOnClickListener(this);
        shareE = (TextView) findViewById(R.id.shareE);


        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/jua.ttf");
        sharetv = (TextView) findViewById(R.id.sharetv);
        sharetv.setTypeface(typeFace);
        shareE.setTypeface(typeFace);



        //AdView mAdView = (AdView) findViewById(R.id.ads);
        //this.setAdsContainer(R.id.ads);
        // AdRequest adRequest = new AdRequest.Builder().build();
        //mAdView.loadAd(adRequest);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        btn1 = (ImageView) findViewById(R.id.button_1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
//        btn4 = (LinearLayout)findViewById(R.id.libtn5);
        btn5 = (Button)findViewById(R.id.goodbtn);


        vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);



        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.vibrate(500);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long[] pattern = {0, 500, 0, 500};
                vib.vibrate(pattern, 0);
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vib.cancel();

            }
        });


        btn5.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, goodApp.class);
                startActivity(intent);

                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                } else {
                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                }

            }
        });



    }


    @Override
    public void onClick(View v) {


        switch (v.getId()) {


            case R.id.shareb:

                Intent msg = new Intent(Intent.ACTION_SEND);
                msg.addCategory(Intent.CATEGORY_DEFAULT);
                msg.putExtra(Intent.EXTRA_SUBJECT, "진동무한 https://play.google.com/store/apps/details?id=soultion.id_h.com.yameyame&hl=ko");
                msg.putExtra(Intent.EXTRA_TEXT, "");
                msg.putExtra(Intent.EXTRA_TITLE, "제목");
                msg.setType("text/plain");
                startActivity(Intent.createChooser(msg, "공유"));
                break;
        }
    }


    @Override
    public void onRewardedVideoAdLoaded() {

        tv7.append("\n처음 사용자는 사용 TIP 버튼을 눌러 주세요. \n");

        btn7.setEnabled(true);


    }

    @Override
    public void onRewardedVideoAdOpened() {
//        tv7.append("An ad has open.\n");


    }

    @Override
    public void onRewardedVideoStarted() {
//        tv7.append("An ad has start.\n");


    }

    @Override
    public void onRewardedVideoAdClosed() {
//        tv7.append("An ad has close.\n");

        mRewardedVideoAd.loadAd("ca-app-pub-2821664824480291/1878587871", new AdRequest.Builder().build());


    }

    @Override
    public void onRewarded(RewardItem rewardItem) {
        tv7.append("\n사용법.\n");
        tv7.append(String.format(Locale.getDefault(),
                "무한진동 버튼시 무한진동!\n사랑합니다!♥ 뀨♥\n", rewardItem.getAmount(), rewardItem.getType()));


    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        tv7.append("An ad has ideoAdLeftApplication.\n");


    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int i) {
        tv7.append("사용 TIP을 눌러주세요.\n");


    }

    @Override
    public void onRewardedVideoCompleted() {
//        tv7.append("시청을 완료 하셨습니다.\n");


    }
}



