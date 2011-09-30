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

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
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
		Log.i(TAG, "onLowMemory()");
		super.onLowMemory();
	}

	@Override
	public void onTerminate() {
		Log.i(TAG, "onTerminate()");
		super.onTerminate();
	}

	private static final String TAG = "[bosicc]CluedoApp";

	private static ContentResolver resolver;

    private boolean isScreenActive = true;

	private GamePOJO game;
	
	private GameSave utils;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Log.i(TAG,"onCreate()");
		resolver = getContentResolver();
		
		game = new GamePOJO();
		utils = new GameSave(getBaseContext());
		
		GamePOJO mLoadGame = utils.Load();
		
		if (mLoadGame != null){
			game = mLoadGame;
		}

	}
	
	public GamePOJO getGame(){
		return this.game;
	}
	
	public GameSave getUtils(){
		return this.utils;
	}

}
