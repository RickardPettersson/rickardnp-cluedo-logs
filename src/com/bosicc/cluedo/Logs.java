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
import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bosicc.cluedo.GamePOJO.ShowModeType;


/**
 * A list view example where the data comes from a custom ListAdapter
 */
public class Logs extends ListActivity {
	
	//private static String TAG = "Logs";

	private LinearLayout mHeaderBox;
	private Button mBtnXodit;
	private Button mBtnPodtverdil;
	private TextView mTitle;
	private TextView mSlyx;
	
	private ListView mList;
	private BaseAdapter mAdapter;
    private CluedoApp cApp;
    private GamePOJO game;
    private Utils utils;
    private boolean isFinished = false;
    private ArrayList<PlayerPOJO> mCurentDialogList;
    private int nc = 100;
    
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
		mBtnXodit = (Button) findViewById(R.id.btnXodit);
		mBtnPodtverdil = (Button) findViewById(R.id.btnPodtverdil);
		mTitle = (TextView) findViewById(R.id.txtTitle);
		mSlyx = (TextView) findViewById(R.id.txtSlyx);
		mHeaderBox = (LinearLayout) findViewById(R.id.LLheader);
		
		cApp = (CluedoApp) getApplication();
		game = cApp.getGame();
		utils = new Utils(this,game);

		
		mCurentDialogList = new ArrayList<PlayerPOJO>();
		
		mBtnXodit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mViewMode == ShowModeType.ALL){
					if ((utils.getAllList().size() !=0) && (utils.getAllList().get(0).getPlayerPodtverdil() == -1)){
		            	new AlertDialog.Builder(Logs.this)
		            		.setIcon(R.drawable.btn_info)
		            		.setTitle(R.string.logs_alert_title)
		                	.setMessage(R.string.logs_txt2)
		                	.setPositiveButton(R.string.alert_dialog_ok, null)
		                	.show();
	            	}else{
	            		 showDialog(DIALOG_XODIT);
	            	}
				}
            	 
				
			}
		});
		
		mBtnPodtverdil.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mViewMode == ShowModeType.ALL){
					if (utils.getAllList().size() == 0){
						new AlertDialog.Builder(Logs.this)
	            		.setIcon(R.drawable.btn_info)
	            		.setTitle(R.string.logs_alert_title)
	                	.setMessage(R.string.logs_txt3)
	                	.setPositiveButton(R.string.alert_dialog_ok, null)
	                	.show();
					}else{
						if ((utils.getAllList().get(0).getSlyxPerson() == -1) ||
							(utils.getAllList().get(0).getSlyxPlace() == -1) ||
							(utils.getAllList().get(0).getSlyxWeapon() == -1)){
					            	new AlertDialog.Builder(Logs.this)
					            		.setIcon(R.drawable.btn_info)
					            		.setTitle(R.string.logs_alert_title)
					                	.setMessage(R.string.logs_txt4)
					                	.setPositiveButton(R.string.alert_dialog_ok, null)
					                	.show();
			            	}else{
			            		 showDialog(DIALOG_PODTVERDIL);
			            	}
					}
				}
			}
		});
		
		// Set up our adapter
		mAdapter = new MyListAdapter(this);
		mList.setAdapter(mAdapter);
	}
	
	@Override
	protected void onResume() {
		setSlyxText();
		super.onResume();		
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DIALOG_XODIT:
        	mCurentDialogList = utils.getSortXodilList();
            return new AlertDialog.Builder(Logs.this)
            .setTitle(R.string.logs_btnxodit)
            .setItems(utils.getString(mCurentDialogList), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	
                	int num = mCurentDialogList.get(which).getNumber();
                	
            		PMovePOJO item = new PMovePOJO(num);
                	utils.getAllList().add(0,item);
                	mSlyx.setText("");
                	mTitle.setText(mCurentDialogList.get(which).getLabel());
                	mAdapter.notifyDataSetChanged();
                	mCurentDialogList.removeAll(mCurentDialogList);
                	removeDialog(DIALOG_XODIT);

                }
            })
            .create();
        case DIALOG_PODTVERDIL:
        	int xodit = utils.getAllList().get(0).getPlayerXodit();
        	mCurentDialogList = utils.getPodtverdilList(xodit);
        	
            return new AlertDialog.Builder(Logs.this)
            .setTitle(R.string.logs_btnpodtverdil)
            .setItems(utils.getString(mCurentDialogList), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	if (which==0){
                		which = 100;
                	}else{
                		which = mCurentDialogList.get(which).getNumber();
                	}
                	utils.getAllList().get(0).setPlayerPodtverdil(which);
                	mAdapter.notifyDataSetChanged();
                	mCurentDialogList.removeAll(mCurentDialogList);
                	removeDialog(DIALOG_PODTVERDIL);
                }
            })
            .create();
        case DIALOG_PEOPLE:
            return new AlertDialog.Builder(Logs.this)
            .setTitle(R.string.title_people)
            .setItems(game.mPeople, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	utils.getAllList().get(0).setSlyxPerson(which);
                	mAdapter.notifyDataSetChanged();
                }
            })
            .create();
        case DIALOG_PLACE:
            return new AlertDialog.Builder(Logs.this)
            .setTitle(R.string.title_place)
            .setItems(game.mPlace, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	utils.getAllList().get(0).setSlyxPlace(which);
                	mAdapter.notifyDataSetChanged();
                }
            })
            .create();
        case DIALOG_WEAPON:
            return new AlertDialog.Builder(Logs.this)
            .setTitle(R.string.title_weapon)
            .setItems(game.mWeapon, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	utils.getAllList().get(0).setSlyxWeapon(which);
                	mAdapter.notifyDataSetChanged();
                }
            })
            .create();
        case DIALOG_SORT_BY_XODIL:
        	mCurentDialogList = utils.getSortXodilList();
            return new AlertDialog.Builder(Logs.this)
            .setTitle(R.string.logs_alert_title_sort_xodil)
            .setItems(utils.getString(mCurentDialogList), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
            		mViewMode = ShowModeType.XODIT;
            		mPerson = mCurentDialogList.get(which).getNumber();;
                	mAdapter.notifyDataSetChanged();
                	mCurentDialogList.removeAll(mCurentDialogList);
                	removeDialog(DIALOG_SORT_BY_XODIL);
                }
            })
            .create();
        case DIALOG_SORT_BY_PODTVERDIL:
        	mCurentDialogList = utils.getSortPodtverdilList();
            return new AlertDialog.Builder(Logs.this)
            .setTitle(R.string.logs_alert_title_sort_podtverdil)
            .setItems(utils.getString(mCurentDialogList), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
            		mViewMode = ShowModeType.PODTVERDIL;
            		mPerson = mCurentDialogList.get(which).getNumber();;
                	mAdapter.notifyDataSetChanged();
                	mCurentDialogList.removeAll(mCurentDialogList);
                	removeDialog(DIALOG_SORT_BY_PODTVERDIL);
                }
            })
            .create();
        }
        return null;
    }
	
// TODO: think about http://stackoverflow.com/questions/4811688/how-to-set-contents-of-setsinglechoiceitems-in-onpreparedialog
//	@Override
//	protected void onPrepareDialog(int id, Dialog dialog) {
//		super.onPrepareDialog(id, dialog);
//		
//		 switch (id) {
//	        case DIALOG_XODIT:
//	        	mCurentDialogList = utils.getSortXodilList();
//	        	AlertDialog alertDialog = (AlertDialog) dialog;
//			    ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.select_dialog_singlechoice, 
//			    		android.R.id.text1, utils.getString(mCurentDialogList));
//			    alertDialog.getListView().setAdapter(adapter);
//	        	
//	        	return;
//	        case DIALOG_PODTVERDIL:
//	        	int xodit = utils.getAllList().get(0).getPlayerXodit();
//	        	mCurentDialogList = utils.getPodtverdilList(xodit);
//	            return;
//	        case DIALOG_SORT_BY_XODIL:
//	        	mCurentDialogList = utils.getSortXodilList();
//	            return;
//	        case DIALOG_SORT_BY_PODTVERDIL:
//	        	mCurentDialogList = utils.getSortPodtverdilList();
//	            return;
//	        }
//	}
	
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

    	public LinearLayout LLmain;
        public TextView TextXodil;
        public TextView TextPodtverdil;
        public View divider;

        public Button btn1;
        public Button btn2;
        public Button btn3;

    }
    
	private class MyListAdapter extends BaseAdapter {

		private Context mContext;
		 
        public MyListAdapter(Context context) {
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
                        R.layout.logs_row, parent, false);

            	cache.LLmain = (LinearLayout) view.findViewById(R.id.LLmainrow);
                cache.TextXodil = (TextView)view.findViewById(R.id.txtLeft);
                cache.TextPodtverdil = (TextView)view.findViewById(R.id.txtRight);
                cache.btn1 = (Button)view.findViewById(R.id.btn1);
                cache.btn2 = (Button)view.findViewById(R.id.btn2);
                cache.btn3 = (Button)view.findViewById(R.id.btn3);
                
                view.setTag(cache);

            } else {
            	cache = (ListItemCache) view.getTag();
            }
            
            cache.btn1.setOnClickListener(new OnItemClickListener(position)); 
            cache.btn2.setOnClickListener(new OnItemClickListener(position)); 
            cache.btn3.setOnClickListener(new OnItemClickListener(position)); 

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					ViewGroup.LayoutParams.FILL_PARENT, 36);
			if (mViewMode == ShowModeType.ALL){
				if (position == 0){
		            lp = new LinearLayout.LayoutParams(
							ViewGroup.LayoutParams.FILL_PARENT, 56);
				}
			}
			cache.LLmain.setLayoutParams(lp);
            
            cache.TextXodil.setText(" ");
            int num = utils.getCurentList(mViewMode, mPerson).get(position).getPlayerXodit();
            cache.TextXodil.setBackgroundResource(utils.getColorForPlayer(num));
            num = utils.getCurentList(mViewMode, mPerson).get(position).getPlayerPodtverdil();
           	cache.TextPodtverdil.setBackgroundResource(utils.getColorForPlayer(num));
           
            
            int [] slux = utils.getCurentList(mViewMode, mPerson).get(position).getSlyx();
            //Log.i(TAG,"slyx:"+slux[0]+slux[1]+slux[2]);
//            cache.btn1.setImageResource(cApp.getIconForPlayer(slux[0]));
//            cache.btn2.setImageResource(cApp.getIconForPlace(slux[1]));
//            cache.btn3.setImageResource(cApp.getIconForWeapon(slux[2]));
			// Layout parameters for the ExpandableListView
            
            if (position == 0){
            	cache.btn1.setBackgroundResource(android.R.drawable.btn_default);
            	cache.btn2.setBackgroundResource(android.R.drawable.btn_default);
            	cache.btn3.setBackgroundResource(android.R.drawable.btn_default);
			}else{
            	cache.btn1.setBackgroundResource(R.drawable.bgempty);
            	cache.btn2.setBackgroundResource(R.drawable.bgempty);
            	cache.btn3.setBackgroundResource(R.drawable.bgempty);
			}

            if (slux[0] != -1){
            	cache.btn1.setText(game.mPeople[slux[0]]);
            }else{
            	cache.btn1.setText("[---]");
            }
            if (slux[1] != -1){
            	cache.btn2.setText(game.mPlace[slux[1]]);	
            }else{
            	cache.btn2.setText("[---]");
            }
            if (slux[2] != -1){
            	cache.btn3.setText(game.mWeapon[slux[2]]);
            }else{
            	cache.btn3.setText("[---]");
            }

            return view;
        }
        
        
        @Override
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
			setSlyxText();
		}


        
        private class OnItemClickListener implements OnClickListener{           
            private int mPosition;
            OnItemClickListener(int position){
                    mPosition = position;
            }
            @Override
            public void onClick(View view) {
            	// Perform Clicks only for current row
            	if (mPosition != 0){
            		return;
            	}
            		
                switch (view.getId()){
                    case R.id.btn1:
                    	showDialog(DIALOG_PEOPLE);
                    	break;
                    case R.id.btn2:
                    	showDialog(DIALOG_PLACE);
	                    break;
                    case R.id.btn3:
                    	showDialog(DIALOG_WEAPON);
	                    break;
                }
            }               
        }

    }
	
	private void setSlyxText() {
		if (mViewMode == ShowModeType.ALL) {
			String text1 = "[???]";
			String text2 = "[???]";
			String text3 = "[???]";
			mHeaderBox.setVisibility(View.VISIBLE);

			if (utils.getAllList().size() != 0) {
				int[] slux = utils.getAllList().get(0).getSlyx();
				if (utils.getAllList().get(0).getPlayerPodtverdil() == -1) {
					// Log.i(TAG,"Slyxi=" + slux[0] +" " + slux[1] + slux[2]);
					if (slux[0] != -1) {
						text1 = game.mPeople[slux[0]];
					}
					if (slux[1] != -1) {
						text2 = game.mPlace[slux[1]];
					}
					if (slux[2] != -1) {
						text3 = game.mWeapon[slux[2]];
					}
					mSlyx.setText(text1 + " + " + text2 + " + " + text3);
				} else {
					mSlyx.setText(R.string.logs_txt4);
					mTitle.setText("");
				}
			}
		} else {
			mHeaderBox.setVisibility(View.GONE);
			String text = "";
			if (mViewMode == ShowModeType.XODIT) {
				text = this.getText(R.string.logs_toast_xoda)
						+ game.mPeople[mPerson];
			} else {
				text = this.getText(R.string.logs_toast_podtverdil)
						+ game.mPeople[mPerson];
			}
			mSlyx.setText(text);
		}

	}
}