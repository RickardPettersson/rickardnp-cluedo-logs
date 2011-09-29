package com.bosicc.cluedo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
	
	@Override
	public void onCreate() {
		super.onCreate();
		
		Log.i(TAG,"onCreate()");
		
		game = new GamePOJO();

		resolver = getContentResolver();

	}
	
	public GamePOJO getGame(){
		return this.game;
	}

}
