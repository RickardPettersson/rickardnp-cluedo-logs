/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.bosicc.cluedo.activity;

//Need the following import to get access to the app resources, since this
//class is in a sub-package.
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bosicc.cluedo.CluedoApp;
import com.bosicc.cluedo.R;
import com.bosicc.cluedo.pojo.GamePOJO;
import com.bosicc.cluedo.pojo.GamePOJO.ShowModeType;
import com.bosicc.cluedo.pojo.PlayerPOJO;
import com.bosicc.cluedo.utils.CConstants;
import com.bosicc.cluedo.utils.Utils;


/**
 * A list view example where the data comes from a custom ListAdapter
 */
public class LogsTextActivity extends ListActivity {
	
	//private static String TAG = "LogsText";

	private LinearLayout mHeaderBox;
	private TextView mTitle;
	private TextView mSlyx;
	
	private ListView mList;
	private MyLogsTextListAdapter mAdapter;
    private CluedoApp cApp;
    //private GamePOJO game;
    private GamePOJO gameLocal;
    private Utils utils;
    private ArrayList<PlayerPOJO> mCurentDialogList;
    
    private LogsTextDataChangeReceiver mLogsTextDataChangeReceiver = null;
    
    private ShowModeType mViewMode = ShowModeType.ALL;
    private int mPerson = 0;
    
    private static final int DIALOG_XODIT = 1;
    private static final int DIALOG_PODTVERDIL = 2;
    private static final int DIALOG_PEOPLE = 3;
    private static final int DIALOG_PLACE = 4;
    private static final int DIALOG_WEAPON = 5;
    private static final int DIALOG_SORT_BY_XODIL = 6;
    private static final int DIALOG_SORT_BY_PODTVERDIL = 7;
    
	//======================================================
	// Menu items ID
	//======================================================
	private static final int MENU_ITEM_SORT_BY_XODIL		=11;		
	private static final int MENU_ITEM_SORT_ALL				=12;
	private static final int MENU_ITEM_SORT_BY_PODTVERDIL	=13;
	
	private static final int group2Id = 0;  

	private static final int sortXodilBtnId = Menu.FIRST+2;  
	private static final int sortAllBtnId = sortXodilBtnId + 1;  
	private static final int sortPodtverdilBtnId = sortAllBtnId + 1;  
	 

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logs);
		mList = (ListView) findViewById(android.R.id.list);
		mSlyx = (TextView) findViewById(R.id.txtSlyx);
		mHeaderBox = (LinearLayout) findViewById(R.id.LLheader);
		
		cApp = (CluedoApp) getApplication();
		gameLocal = cApp.getGame();
		utils = new Utils(this,gameLocal);

	
		mHeaderBox.setVisibility(View.GONE);
		mSlyx.setVisibility(View.GONE);
		
		mCurentDialogList = new ArrayList<PlayerPOJO>();
		
		// Register Data change receiver 
		mLogsTextDataChangeReceiver = new LogsTextDataChangeReceiver();
        IntentFilter intentFilter = new IntentFilter( CConstants.ACTION_UPDATE_DATA );
        registerReceiver(mLogsTextDataChangeReceiver, intentFilter);
        
		// Set up our adapter
		mAdapter = new MyLogsTextListAdapter(this);
		mList.setAdapter(mAdapter);
		
	}
	
	@Override
	protected void onResume() {
		//Log.i(TAG,"onResume");
		//gameLocal = game;
		//mAdapter.notifyDataSetChanged();
		//mList.setAdapter(mAdapter);
		super.onResume();		
	}
	
	@Override
	protected void onPause() {
		///Log.i(TAG,"onPause");
		//mList.setAdapter(null);
		super.onResume();		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if( mLogsTextDataChangeReceiver != null ){
        	unregisterReceiver(mLogsTextDataChangeReceiver);
        	mLogsTextDataChangeReceiver = null;
		}
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DIALOG_PEOPLE:
            return new AlertDialog.Builder(LogsTextActivity.this)
            .setTitle(R.string.title_people)
            .setItems(gameLocal.mPeople, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	utils.getAllList().get(0).setSlyxPerson(which);
                	mAdapter.notifyDataSetChanged();
                }
            })
            .create();
        case DIALOG_PLACE:
            return new AlertDialog.Builder(LogsTextActivity.this)
            .setTitle(R.string.title_place)
            .setItems(gameLocal.mPlace, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	utils.getAllList().get(0).setSlyxPlace(which);
                	mAdapter.notifyDataSetChanged();
                }
            })
            .create();
        case DIALOG_WEAPON:
            return new AlertDialog.Builder(LogsTextActivity.this)
            .setTitle(R.string.title_weapon)
            .setItems(gameLocal.mWeapon, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	utils.getAllList().get(0).setSlyxWeapon(which);
                	mAdapter.notifyDataSetChanged();
                }
            })
            .create();
        case DIALOG_SORT_BY_XODIL:
            return new AlertDialog.Builder(LogsTextActivity.this)
            .setTitle(R.string.logs_alert_title_sort_xodil)
            .setItems(gameLocal.mPeople, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
            		mViewMode = ShowModeType.XODIT;
            		mPerson = which;
                	mAdapter.notifyDataSetChanged();
                }
            })
            .create();
        case DIALOG_SORT_BY_PODTVERDIL:
        	mCurentDialogList = utils.getSortPodtverdilList();
            return new AlertDialog.Builder(LogsTextActivity.this)
            .setTitle(R.string.logs_alert_title_sort_podtverdil)
            .setItems(utils.getString(mCurentDialogList), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	if (which==0){
                		mPerson = 100;
                	}else{
                		mPerson = mCurentDialogList.get(which).getNumber();
                	}
            		mViewMode = ShowModeType.PODTVERDIL;
                	mAdapter.notifyDataSetChanged();
                	mCurentDialogList.removeAll(mCurentDialogList);
                }
            })
            .create();
        }
        return null;
    }
	
	
	@Override
	protected void onPrepareDialog(int id, Dialog dialog) {
		
		AlertDialog alertDialog = (AlertDialog) dialog;
		ArrayAdapter<CharSequence> adapter;
		 switch (id) {
		 
	        case DIALOG_XODIT:
	        	mCurentDialogList = utils.getSortXodilList();
			    adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.select_dialog_item, 
			    		android.R.id.text1, utils.getString(mCurentDialogList));
			    alertDialog.getListView().setAdapter(adapter);
			    break;
			    
	        case DIALOG_SORT_BY_PODTVERDIL:
	        	mCurentDialogList = utils.getSortPodtverdilList();
	        	adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.select_dialog_item, 
			    		android.R.id.text1, utils.getString(mCurentDialogList));
			    alertDialog.getListView().setAdapter(adapter);
	            break;
	        default:
	            	super.onPrepareDialog(id, dialog);
	        }
	}
	
	// ==============================================================================
    // Option Menu
    // ==============================================================================
	 /**
     * On options menu creation.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		
		// ===
		MenuItem item_1 = menu.add(group2Id, MENU_ITEM_SORT_BY_XODIL, sortXodilBtnId, R.string.logsmenu_sort_xodil);
		item_1.setIcon(android.R.drawable.ic_menu_sort_alphabetically);
		
		// ===
     	MenuItem item_2 = menu.add(group2Id, MENU_ITEM_SORT_ALL, sortAllBtnId, R.string.logsmenu_sort_all);
     	item_2.setIcon(android.R.drawable.ic_menu_edit);
     	
		// ===
     	MenuItem item_3 = menu.add(group2Id, MENU_ITEM_SORT_BY_PODTVERDIL, sortPodtverdilBtnId, R.string.logsmenu_sort_podtverdil);
     	item_3.setIcon(android.R.drawable.ic_menu_sort_by_size);
     	
     	return super.onCreateOptionsMenu(menu);
    }
	
    /**
     * On options menu item selection.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
          
	        case MENU_ITEM_SORT_BY_XODIL:{
	        	showDialog(DIALOG_SORT_BY_XODIL);   
	        	return true;
	        }
	        
	        case MENU_ITEM_SORT_ALL:{
            	mViewMode = ShowModeType.ALL;
            	mAdapter.notifyDataSetChanged();
	        	return true;
	        }
	        
	        case MENU_ITEM_SORT_BY_PODTVERDIL:{
	        	showDialog(DIALOG_SORT_BY_PODTVERDIL);   
	        	return true;
	        }
        }
        return super.onOptionsItemSelected(item);
    }
    
    
    
	/**
     * Item view cache holder.
     */
    private static final class ListItemCache {

        public TextView TextXodil;
        public TextView TextPodtverdil;

        public TextView txt1;
        public TextView txt2;
        public TextView txt3;

    }
    
	private class MyLogsTextListAdapter extends BaseAdapter {

		private Context mContext;
		 
        public MyLogsTextListAdapter(Context context) {
        	this.mContext = context;
        }

        public int getCount() {
            return utils.getCurentList(mViewMode, mPerson).size();
        }
        
        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }
        

        /**
         * On get view
         */
        @Override
        public View getView(int position, View view, ViewGroup parent) {
        	
        	ListItemCache cache = new ListItemCache();
            if (view == null) {
            	view = (View) LayoutInflater.from(mContext).inflate(
                        R.layout.logs_row_text, parent, false);

                cache.TextXodil = (TextView)view.findViewById(R.id.txtLeft);
                cache.TextPodtverdil = (TextView)view.findViewById(R.id.txtRight);
                cache.txt1 = (TextView)view.findViewById(R.id.txt1);
                cache.txt2 = (TextView)view.findViewById(R.id.txt2);
                cache.txt3 = (TextView)view.findViewById(R.id.txt3);
                
                view.setTag(cache);

            } else {
            	cache = (ListItemCache) view.getTag();
            }
           
            
            cache.TextXodil.setText(" ");
            int num = utils.getCurentList(mViewMode, mPerson).get(position).getPlayerXodit();
            if (num != -1){
            	cache.TextXodil.setBackgroundColor(gameLocal.mPlayers.get(num).getColor());
            }
            num = utils.getCurentList(mViewMode, mPerson).get(position).getPlayerPodtverdil();
            cache.TextPodtverdil.setBackgroundResource(R.color.bgMain);
            if (num != -1){
            	if (num == 100){
                	cache.TextPodtverdil.setBackgroundResource(R.color.bgBlack);
                }else{
                	cache.TextPodtverdil.setBackgroundColor(gameLocal.mPlayers.get(num).getColor());
                }
            }
           
            
            int [] slux = utils.getCurentList(mViewMode, mPerson).get(position).getSlyx();
            //Log.i(TAG,"slyx:"+slux[0]+slux[1]+slux[2]);

			if (slux[0] != -1){
				cache.txt1.setText(gameLocal.mPeople[slux[0]]);
				cache.txt1.setTextColor(gameLocal.mPlayers.get(slux[0]).getColor());
			}else{
				cache.txt1.setText("");
				cache.txt1.setTextColor(R.color.bgBlack);
			}
			
			if (slux[1] != -1){
				cache.txt2.setText(gameLocal.mPlace[slux[1]]);
			}else{
				cache.txt2.setText("");
			}
			
			if (slux[2] != -1){
				 cache.txt3.setText(gameLocal.mWeapon[slux[2]]);
			}else{
				cache.txt3.setText("");
			}

            return view;
        }
        
        
        @Override
		public void notifyDataSetChanged() {
        	//Log.i(TAG,"notifyDataSetChanged");
			super.notifyDataSetChanged();
			String text = "";
			if (mViewMode == ShowModeType.ALL){
				text = mContext.getText(R.string.logsmenu_sort_all).toString();
			}else{

				if (mViewMode == ShowModeType.XODIT){
					text = mContext.getText(R.string.logs_toast_xoda)+" "+ gameLocal.mPeople[mPerson];
				}else{
					if (mPerson == 100){
						text = mContext.getText(R.string.logs_toast_podtverdil) +" "+ mContext.getText(R.string.logs_notconfirm);
					}else{
						text = mContext.getText(R.string.logs_toast_podtverdil) +" "+ gameLocal.mPeople[mPerson];
					}
				}
				Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
			}
			
		}
    }
	
    public class LogsTextDataChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
        	mAdapter.notifyDataSetChanged();
        }
	}

}