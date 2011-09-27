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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bosicc.cluedo.PlayerPOJO.CardType;


/**
 * A list view example where the data comes from a custom ListAdapter
 */
public class Logs extends ListActivity {
	
	private static String TAG = "Logs";

	private Button mBtnXodit;
	private Button mBtnPodtverdil;
	
	private ListView mList;
	private BaseAdapter mAdapter;
    private CluedoApp cApp;
    private GamePOJO game;
    
    private static final int DIALOG_XODIT = 1;
    private static final int DIALOG_PODTVERDIL = 2;
    private static final int DIALOG_PEOPLE = 3;
    private static final int DIALOG_PLACE = 4;
    private static final int DIALOG_WEAPON = 5;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.logs);
		mList = (ListView) findViewById(android.R.id.list);
		mBtnXodit = (Button) findViewById(R.id.btnXodit);
		mBtnPodtverdil = (Button) findViewById(R.id.btnPodtverdil);
		
		cApp = (CluedoApp) getApplication();
		game = cApp.getGame();
		
		mBtnXodit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if ((game.mLogsList.size() !=0) && (game.mLogsList.get(0).getPlayerPodtverdil() == 7)){
	            	new AlertDialog.Builder(Logs.this)
	            		.setIcon(R.drawable.btn_info)
	            		.setTitle(R.string.logs_alert_title)
	                	.setMessage(R.string.logs_txt2)
	                	.show();
            	}else{
            		 showDialog(DIALOG_XODIT);
            	}
            	 
				
			}
		});
		
		mBtnPodtverdil.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (game.mLogsList.size() == 0){
					new AlertDialog.Builder(Logs.this)
            		.setIcon(R.drawable.btn_info)
            		.setTitle(R.string.logs_alert_title)
                	.setMessage(R.string.logs_txt3)
                	.setPositiveButton(R.string.alert_dialog_ok, null)
                	.show();
				}else{
					if ((game.mLogsList.get(0).getSlyxPerson() == 7) &&
							(game.mLogsList.get(0).getSlyxPlace() == 7) &&
							(game.mLogsList.get(0).getSlyxWeapon() == 7)){
				            	new AlertDialog.Builder(Logs.this)
				            		.setIcon(R.drawable.btn_info)
				            		.setTitle(R.string.logs_alert_title)
				                	.setMessage(R.string.logs_txt4)
				                	.show();
		            	}else{
		            		 showDialog(DIALOG_PODTVERDIL);
		            	}
				}
			}
		});
		
		// Set up our adapter
		mAdapter = new MyListAdapter(this);
		mList.setAdapter(mAdapter);
		
		

	    }
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DIALOG_XODIT:
            return new AlertDialog.Builder(Logs.this)
            .setTitle(R.string.logs_btnxodit)
            .setItems(R.array.people, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

            		PMovePOJO item = new PMovePOJO(which);
                	game.mLogsList.add(0,item);
                	mAdapter.notifyDataSetChanged();

                }
            })
            .create();
        case DIALOG_PODTVERDIL:
            return new AlertDialog.Builder(Logs.this)
            .setTitle(R.string.logs_btnpodtverdil)
            .setItems(R.array.people, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	game.mLogsList.get(0).setPlayerPodtverdil(which);
                	mAdapter.notifyDataSetChanged();
                }
            })
            .create();
        case DIALOG_PEOPLE:
            return new AlertDialog.Builder(Logs.this)
            .setTitle(R.string.title_people)
            .setItems(R.array.people, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	game.mLogsList.get(0).setSlyxPerson(which);
                	mAdapter.notifyDataSetChanged();
                }
            })
            .create();
        case DIALOG_PLACE:
            return new AlertDialog.Builder(Logs.this)
            .setTitle(R.string.title_place)
            .setItems(R.array.place, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	game.mLogsList.get(0).setSlyxPlace(which);
                	mAdapter.notifyDataSetChanged();
                }
            })
            .create();
        case DIALOG_WEAPON:
            return new AlertDialog.Builder(Logs.this)
            .setTitle(R.string.title_weapon)
            .setItems(R.array.weapon, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                	game.mLogsList.get(0).setSlyxWeapon(which);
                	mAdapter.notifyDataSetChanged();
                }
            })
            .create();
        }
        return null;
    }
	
	/**
     * Item view cache holder.
     */
    private static final class ListItemCache {

        public TextView TextXodil;
        public TextView TextPodtverdil;
        public View divider;

        public ImageButton btn1;
        public ImageButton btn2;
        public ImageButton btn3;

    }
	
	private class MyListAdapter extends BaseAdapter {

		private Context mContext;
		 
        public MyListAdapter(Context context) {
        	this.mContext = context;
        }

        public int getCount() {
            return game.mLogsList.size();
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

                cache.TextXodil = (TextView)view.findViewById(R.id.txtLeft);
                cache.TextPodtverdil = (TextView)view.findViewById(R.id.txtRight);
                cache.btn1 = (ImageButton)view.findViewById(R.id.btn1);
                cache.btn2 = (ImageButton)view.findViewById(R.id.btn2);
                cache.btn3 = (ImageButton)view.findViewById(R.id.btn3);
                
                view.setTag(cache);

            } else {
            	cache = (ListItemCache) view.getTag();
            }
            
            cache.btn1.setOnClickListener(new OnItemClickListener(position)); 
            cache.btn2.setOnClickListener(new OnItemClickListener(position)); 
            cache.btn3.setOnClickListener(new OnItemClickListener(position)); 

            
            
            //String text = "" + (position+1);
            cache.TextXodil.setText(" ");
            int num = game.mLogsList.get(position).getPlayerXodit();
            cache.TextXodil.setBackgroundResource(getColorForPlayer(num));
            num = game.mLogsList.get(position).getPlayerPodtverdil();
           	cache.TextPodtverdil.setBackgroundResource(getColorForPlayer(num));
           
            
            int [] slux = game.mLogsList.get(position).getSlyx();
            cache.btn1.setImageResource(getIconForPlayer(slux[0]));
            cache.btn2.setImageResource(getIconForPlayer(slux[1]));
            cache.btn3.setImageResource(getIconForPlayer(slux[2]));

            return view;
        }
        
        
        private int getResourceByType(CardType type){
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
        
        private int getColorForPlayer(int pleyernum){
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
        
        private int getIconForPlayer(int pleyernum){
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
}