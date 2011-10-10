package com.bosicc.cluedo;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.sql.ConnectionEventListener;

import com.bosicc.cluedo.PlayerPOJO.CardType;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Credentials;
import android.provider.Contacts.People;
import android.util.Log;


/**
 * Android application's main class
 * 
 * @author bosicc
 * 
 */
public class CluedoApp extends Application {
	
    @Override
	public void onLowMemory() {
		//Log.i(TAG, "onLowMemory()");
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		//Log.i(TAG, "onTerminate()");
		super.onTerminate();
	}

	//private static final String TAG = "[bosicc]CluedoApp";

	private static ContentResolver resolver;

    private boolean isScreenActive = true;

	private GamePOJO game;
	
	private GameSave utils;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		//Log.i(TAG,"onCreate()");
		resolver = getContentResolver();
		
		game = new GamePOJO();
		//Load default cards
		Resources r = getResources();
		game.mPeople = r.getStringArray(R.array.people_ru);
		game.mPlace = r.getStringArray(R.array.place_ru);
		game.mWeapon = r.getStringArray(R.array.weapon_ru);
		
		utils = new GameSave(getBaseContext());
		
		int ver = -1;
	    try {
	        ver = getPackageManager().getPackageInfo(getApplicationInfo().packageName, 0).versionCode;
	    } catch (NameNotFoundException e) {
	    }
		
		GamePOJO mLoadGame = utils.Load();
		
		if (mLoadGame != null){
			if (ver == mLoadGame.getVersion()){
				game = mLoadGame;
			}
		}
		//Set current version
		game.setVersion(ver);

	}
	
	public GamePOJO getGame(){
		return this.game;
	}
	
	public GameSave getUtils(){
		return this.utils;
	}
	
	public int getResourceByType(CardType type){
    	int res = R.drawable.btn_none;
    	switch (type){
    		case DEFAULT:
    			res = R.drawable.btn_none;
    			break;
    		case NO:
    			res = R.drawable.btn_no;
    			break;

    		case YES:
    			res = R.drawable.btn_yes;
    			break;

    		case QUESTION:
    			res = R.drawable.btn_help;
    			break;
    	}
    	return res;
    }
    
	public int getColorForPlayer(int pleyernum){
    	int res = R.color.bgMain;
    	switch (pleyernum){
    		case 0:
    			res = R.color.bgPeople1;
    			break;
    		case 1:
    			res = R.color.bgPeople2;
    			break;
    		case 2:
    			res = R.color.bgPeople3;
    			break;
    		case 3:
    			res = R.color.bgPeople4;
    			break;
    		case 4:
    			res = R.color.bgPeople5;
    			break;
    		case 5:
    			res = R.color.bgPeople6;
    			break;
    		case 6:
    			res = R.color.bgTransperent;
    			break;
    		case 7:
    			res = R.color.bgMain;
    			break;
    	}
    	return res;
    }
    
	public int getIconForPlayer(int pleyernum){
    	int res = R.drawable.btn_none;
    	switch (pleyernum){
    		case 0:
    			res = R.drawable.p1_icon;
    			break;
    		case 1:
    			res = R.drawable.p2_icon;
    			break;
    		case 2:
    			res = R.drawable.p3_icon;
    			break;
    		case 3:
    			res = R.drawable.p4_icon;
    			break;
    		case 4:
    			res = R.drawable.p5_icon;
    			break;
    		case 5:
    			res = R.drawable.p6_icon;
    			break;
    	}
    	return res;
    }
    
	public int getIconForPlace(int placenum){
    	int res = R.drawable.btn_none;
    	switch (placenum){
    		case 0:
    			res = R.drawable.pl1_icon;
    			break;
    		case 1:
    			res = R.drawable.pl2_icon;
    			break;
    		case 2:
    			res = R.drawable.pl3_icon;
    			break;
    		case 3:
    			res = R.drawable.pl4_icon;
    			break;
    		case 4:
    			res = R.drawable.pl5_icon;
    			break;
    		case 5:
    			res = R.drawable.pl6_icon;
    			break;
    		case 6:
    			res = R.drawable.pl7_icon;
    			break;
    		case 7:
    			res = R.drawable.pl8_icon;
    			break;
    		case 8:
    			res = R.drawable.pl9_icon;
    			break;
    	}
    	return res;
    }
    
    public int getIconForWeapon(int weaponnum){
    	int res = R.drawable.btn_none;
    	switch (weaponnum){
    		case 0:
    			res = R.drawable.w1_icon;
    			break;
    		case 1:
    			res = R.drawable.w2_icon;
    			break;
    		case 2:
    			res = R.drawable.w3_icon;
    			break;
    		case 3:
    			res = R.drawable.w4_icon;
    			break;
    		case 4:
    			res = R.drawable.w5_icon;
    			break;
    		case 5:
    			res = R.drawable.w6_icon;
    			break;
    		case 6:
    			res = R.drawable.w7_icon;
    			break;
    		case 7:
    			res = R.drawable.w8_icon;
    			break;
    		case 8:
    			res = R.drawable.w9_icon;
    			break;
    	}
    	return res;
    }

}
