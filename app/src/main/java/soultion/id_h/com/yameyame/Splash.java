package soultion.id_h.com.yameyame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created by SONG on 2017-02-27.
 */

public class Splash extends Activity {
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);

        try{
            Thread.sleep(1900); // 800 , 1900
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }



}
