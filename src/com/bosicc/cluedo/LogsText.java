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

package com.bosicc.cluedo;

//Need the following import to get access to the app resources, since this
//class is in a sub-package.
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bosicc.cluedo.GamePOJO.ShowModeType;


/**
 * A list view example where the data comes from a custom ListAdapter
 */
public class LogsText extends ListActivity {
	
	//private static String TAG = "LogsText";

	private LinearLayout mHeaderBox;
	private TextView mTitle;
	private TextView mSlyx;
	
	private ListView mList;
	private BaseAdapter mAdapter;
    private CluedoApp cApp;
    private GamePOJO game;
    
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
		game = cApp.getGame();
	
		mHeaderBox.setVisibility(View.GONE);
		mSlyx.setVisibility(View.GONE);
		
		// Set up our adapter
		mAdapter = new MyListAdapter(this);
		mList.setAdapter(mAdapter);
	}
	
	@Override
	protected void onResume() {
		mAdapter.notifyDataSetChanged();
		super.onResume();		
	}
	
	
	
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DIALOG_PEOPLE:
            return new AlertDialog.Builder(LogsText.this)
            .setTitle(R.string.title_people)
            .setItems(game.mPeople, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	game.getAllList().get(0).setSlyxPerson(which);
                	mAdapter.notifyDataSetChanged();
                }
            })
            .create();
        case DIALOG_PLACE:
            return new AlertDialog.Builder(LogsText.this)
            .setTitle(R.string.title_place)
            .setItems(game.mPlace, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	game.getAllList().get(0).setSlyxPlace(which);
                	mAdapter.notifyDataSetChanged();
                }
            })
            .create();
        case DIALOG_WEAPON:
            return new AlertDialog.Builder(LogsText.this)
            .setTitle(R.string.title_weapon)
            .setItems(game.mWeapon, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	game.getAllList().get(0).setSlyxWeapon(which);
                	mAdapter.notifyDataSetChanged();
                }
            })
            .create();
        case DIALOG_SORT_BY_XODIL:
            return new AlertDialog.Builder(LogsText.this)
            .setTitle(R.string.logs_alert_title_sort_xodil)
            .setItems(game.mPeople, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
            		mViewMode = ShowModeType.XODIT;
            		mPerson = which;
                	mAdapter.notifyDataSetChanged();
                }
            })
            .create();
        case DIALOG_SORT_BY_PODTVERDIL:
            return new AlertDialog.Builder(LogsText.this)
            .setTitle(R.string.logs_alert_title_sort_podtverdil)
            .setItems(game.mPeople, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
            		mViewMode = ShowModeType.PODTVERDIL;
            		mPerson = which;
                	mAdapter.notifyDataSetChanged();
                }
            })
            .create();
        }
        return null;
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
        public View divider;

        public TextView txt1;
        public TextView txt2;
        public TextView txt3;

    }
    
	private class MyListAdapter extends BaseAdapter {

		private Context mContext;
		 
        public MyListAdapter(Context context) {
        	this.mContext = context;
        }

        public int getCount() {
            return game.getCurentList(mViewMode, mPerson).size();
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
           
            
            String text = "" + (position+1);
            cache.TextXodil.setText(" ");
            int num = game.getCurentList(mViewMode, mPerson).get(position).getPlayerXodit();
            cache.TextXodil.setBackgroundResource(cApp.getColorForPlayer(num));
            num = game.getCurentList(mViewMode, mPerson).get(position).getPlayerPodtverdil();
           	cache.TextPodtverdil.setBackgroundResource(cApp.getColorForPlayer(num));
           
            
            int [] slux = game.getCurentList(mViewMode, mPerson).get(position).getSlyx();
            //Log.i(TAG,"slyx:"+slux[0]+slux[1]+slux[2]);

			if (slux[0] != 7){
				cache.txt1.setText(game.mPeople[slux[0]]);
				//cache.txt1.setTextColor(cApp.getColorForPlayer(num));
			}else{
				cache.txt1.setText("");
			}
			
			if (slux[1] != 10){
				cache.txt2.setText(game.mPlace[slux[1]]);
			}else{
				cache.txt2.setText("");
			}
			
			if (slux[2] != 10){
				 cache.txt3.setText(game.mWeapon[slux[2]]);
			}else{
				cache.txt3.setText("");
			}

            return view;
        }
        
        
        @Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
			if (mViewMode == ShowModeType.ALL){
				
			}else{
				String text = "";
				if (mViewMode == ShowModeType.XODIT){
					text = mContext.getText(R.string.logs_toast_xoda)+ game.mPeople[mPerson];
				}else{
					text = mContext.getText(R.string.logs_toast_podtverdil) + game.mPeople[mPerson];
				}
				Toast.makeText(mContext, text, Toast.LENGTH_LONG).show();
			}
			
		}

		

    }
}