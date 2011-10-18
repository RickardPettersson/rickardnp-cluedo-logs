package com.bosicc.cluedo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.bosicc.cluedo.pojo.GamePOJO;
import com.bosicc.cluedo.utils.Utils;

public class CluedologsActivity extends Activity {
  
	//private static String TAG = "CluedologsActivity";
	
    private CluedoApp cApp;
    private GamePOJO game;
    private Utils utils;
    
	private Button mBtnNew;
	private Button mBtnAbout;
	private Button mBtnDonate;
	  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        cApp = (CluedoApp) getApplication();
		game = cApp.getGame();
        
		//Log.i(TAG, "onCreate()");
        if (game.isCreated){
        	//Log.i(TAG, "Start Main. Game was saved()");
        	runMainThread();
        }else{
        	setContentView(R.layout.main);
            mBtnNew = (Button) findViewById(R.id.btnNew);
            mBtnAbout = (Button) findViewById(R.id.btnAbout);
            mBtnDonate = (Button) findViewById(R.id.btnDonate);
            mBtnNew.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				startActivity(new Intent(CluedologsActivity.this, Setup.class));
    				finish();
    			}
    		});
            
            mBtnAbout.setOnClickListener(new OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				startActivity(new Intent(CluedologsActivity.this, About.class));
    			}
    		});
            
            mBtnDonate.setOnClickListener(new OnClickListener() {
    		
	    		@Override
	    		public void onClick(View v) {
	    			startActivity(new Intent(CluedologsActivity.this, Logs.class));
	    		}
            });
        }
    }
    
    private void runMainThread() {
        new Thread() {

            public void run() {
                setPriority(Thread.MIN_PRIORITY);
        		startActivity(new Intent(CluedologsActivity.this, CluedoLogs.class));
				finish();
            }
        }.start();
    }
}