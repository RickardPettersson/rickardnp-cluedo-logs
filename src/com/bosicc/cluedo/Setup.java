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

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bosicc.cluedo.PlayerPOJO.CardType;

public class Setup extends ExpandableListActivity {
	
	private static String TAG = "Setup";
	//private static final int DIALOG_INFO = 1;
	private static final int DIALOG_CARDSNOTSELECTED = 2;

    private CluedoApp cApp;
    private GamePOJO game;
	private ExpandableListAdapter mAdapter;
	
	private ExpandableListView mList;
	private Spinner mGameType;
	private Spinner mPerson;
	private ArrayAdapter mPersonAdapter;
	private Button mBack;
	private Button mNext;
	private ScrollView mScroll;
	private LinearLayout mLL1;
	private LinearLayout mLL2;
	private LinearLayout mLL3;
	private LinearLayout mLL4;
	private LinearLayout mLL5;
	private LinearLayout mLL6;
	private EditText mEdit1;
	private EditText mEdit2;
	private EditText mEdit3;
	private EditText mEdit4;
	private EditText mEdit5;
	private EditText mEdit6;
	private CheckBox mCheckBox1;
	private CheckBox mCheckBox2;
	private CheckBox mCheckBox3;
	private CheckBox mCheckBox4;
	private CheckBox mCheckBox5;
	private CheckBox mCheckBox6;
	private TextView mText3;
	
	private String[] groups = new String[3];
	private String[][] children = new String[3][];

	// All check box state
	private boolean[][] items = new boolean[3][9];
	
	private int stage = 0;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		cApp = (CluedoApp) getApplication();
		game = cApp.getGame();

		setContentView(R.layout.setup);
		mNext = (Button) findViewById(R.id.btnNext);
		mBack = (Button) findViewById(R.id.btnBack);
		mGameType = (Spinner) findViewById(R.id.spinnerGameType);
		mPerson = (Spinner) findViewById(R.id.spinnerYourPerson);
		mList = (ExpandableListView) findViewById(android.R.id.list);
		mScroll = (ScrollView) findViewById(R.id.scrollView);
		
		mLL1 = (LinearLayout) findViewById(R.id.LL1);
		mLL2 = (LinearLayout) findViewById(R.id.LL2);
		mLL3 = (LinearLayout) findViewById(R.id.LL3);
		mLL4 = (LinearLayout) findViewById(R.id.LL4);
		mLL5 = (LinearLayout) findViewById(R.id.LL5);
		mLL6 = (LinearLayout) findViewById(R.id.LL6);
		
		mEdit1 = (EditText) findViewById(R.id.editText1);
		mEdit2 = (EditText) findViewById(R.id.editText2);
		mEdit3= (EditText) findViewById(R.id.editText3);
		mEdit4 = (EditText) findViewById(R.id.editText4);
		mEdit5 = (EditText) findViewById(R.id.editText5);
		mEdit6 = (EditText) findViewById(R.id.editText6);
		
		mCheckBox1 = (CheckBox) findViewById(R.id.checkBox1);
		mCheckBox2 = (CheckBox) findViewById(R.id.checkBox2);
		mCheckBox3 = (CheckBox) findViewById(R.id.checkBox3);
		mCheckBox4 = (CheckBox) findViewById(R.id.checkBox4);
		mCheckBox5 = (CheckBox) findViewById(R.id.checkBox5);
		mCheckBox6 = (CheckBox) findViewById(R.id.checkBox6);
		
		mEdit1.setHint(getText(R.string.setup_edittext_hint) + game.mPeople[0]);
		mEdit2.setHint(getText(R.string.setup_edittext_hint) + game.mPeople[1]);
		mEdit3.setHint(getText(R.string.setup_edittext_hint) + game.mPeople[2]);
		mEdit4.setHint(getText(R.string.setup_edittext_hint) + game.mPeople[3]);
		mEdit5.setHint(getText(R.string.setup_edittext_hint) + game.mPeople[4]);
		mEdit6.setHint(getText(R.string.setup_edittext_hint) + game.mPeople[5]);
		
		mText3 = (TextView) findViewById(R.id.text3);
		
		mLL1.setVisibility(View.GONE);
		
		mCheckBox1.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mEdit1.setEnabled(isChecked);
				mEdit1.setFocusable(isChecked);
				mEdit1.setFocusableInTouchMode(isChecked);
			}
		});
		
		mCheckBox2.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mEdit2.setEnabled(isChecked);
				mEdit2.setFocusable(isChecked);
				mEdit3.setFocusableInTouchMode(isChecked);
			}
		});
		
		mCheckBox3.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mEdit3.setEnabled(isChecked);
				mEdit3.setFocusable(isChecked);
				mEdit3.setFocusableInTouchMode(isChecked);
			}
		});
		
		mCheckBox4.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mEdit4.setEnabled(isChecked);
				mEdit4.setFocusable(isChecked);
				mEdit4.setFocusableInTouchMode(isChecked);
			}
		});
		
		mCheckBox5.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mEdit5.setEnabled(isChecked);
				mEdit5.setFocusable(isChecked);
				mEdit5.setFocusableInTouchMode(isChecked);
			}
		});
		
		mCheckBox6.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mEdit6.setEnabled(isChecked);
				mEdit6.setFocusable(isChecked);
				mEdit6.setFocusableInTouchMode(isChecked);
			}
		});
		
		// Set list of game types
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.gameTypes,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mGameType.setAdapter(adapter);
		mGameType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Resources r = getResources();
				
				switch(arg2){
				case 0: // Russian version
					game.mPeople = r.getStringArray(R.array.people_ru);
					game.mPlace = r.getStringArray(R.array.place_ru);
					game.mWeapon = r.getStringArray(R.array.weapon_ru);
					break;
				case 1:// Cluedo in Paris
					game.mPeople = r.getStringArray(R.array.people_ru);
					game.mPlace = r.getStringArray(R.array.place_ru);
					game.mWeapon = r.getStringArray(R.array.weapon_ru);
					break;
				case 2:// Clue the 24
					game.mPeople = r.getStringArray(R.array.people_24);
					game.mPlace = r.getStringArray(R.array.place_24);
					game.mWeapon = r.getStringArray(R.array.weapon_24);
					break;
				case 3:// Clue the office
					game.mPeople = r.getStringArray(R.array.people_office);
					game.mPlace = r.getStringArray(R.array.place_office);
					game.mWeapon = r.getStringArray(R.array.weapon_office);
					break;
				case 4:// Clue English (original)
					game.mPeople = r.getStringArray(R.array.people_original);
					game.mPlace = r.getStringArray(R.array.place_original);
					game.mWeapon = r.getStringArray(R.array.weapon_original);
					break;
				case 5:// Clue Limited edition
					game.mPeople = r.getStringArray(R.array.people_lim_gif);
					game.mPlace = r.getStringArray(R.array.place_lim_gif);
					game.mWeapon = r.getStringArray(R.array.weapon_lim_gif);
				case 6:// Clue the Simpsons
					game.mPeople = r.getStringArray(R.array.people_simpsons);
					game.mPlace = r.getStringArray(R.array.place_simpsons);
					game.mWeapon = r.getStringArray(R.array.weapon_simpsons);
					break;
				case 7:// Clue the Simpsons 3rd edition
					game.mPeople = r.getStringArray(R.array.people_simpsons_3rd);
					game.mPlace = r.getStringArray(R.array.place_simpsons_3rd);
					game.mWeapon = r.getStringArray(R.array.weapon_simpsons_3rd);
					break;
				case 8:// Clue the HarryPoter
					game.mPeople = r.getStringArray(R.array.people_potter);
					game.mPlace = r.getStringArray(R.array.place_potter);
					game.mWeapon = r.getStringArray(R.array.weapon_potter);
					break;
					
				}
				game.playernum = game.mPeople.length;
				game.cardnum = game.mPeople.length + game.mPlace.length + game.mWeapon.length;
				
				mPersonAdapter = new ArrayAdapter<String>(Setup.this, android.R.layout.simple_spinner_item, game.mPeople);
				mPersonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				mPerson.setAdapter(mPersonAdapter);
				mPersonAdapter.notifyDataSetChanged();
				
				mEdit1.setHint(getText(R.string.setup_edittext_hint) + game.mPeople[0]);
				mEdit2.setHint(getText(R.string.setup_edittext_hint) + game.mPeople[1]);
				mEdit3.setHint(getText(R.string.setup_edittext_hint) + game.mPeople[2]);
				mEdit4.setHint(getText(R.string.setup_edittext_hint) + game.mPeople[3]);
				mEdit5.setHint(getText(R.string.setup_edittext_hint) + game.mPeople[4]);
				mEdit6.setHint(getText(R.string.setup_edittext_hint) + game.mPeople[5]);
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		// Set list of persons
		mPersonAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, game.mPeople);
		mPersonAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mPerson.setAdapter(mPersonAdapter);
		mPerson.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				
				switch(arg2){
				case 0:
					mLL1.setVisibility(View.GONE);
					mLL2.setVisibility(View.VISIBLE);
					mLL3.setVisibility(View.VISIBLE); 
					mLL4.setVisibility(View.VISIBLE); 
					mLL5.setVisibility(View.VISIBLE); 
					mLL6.setVisibility(View.VISIBLE); 
					break;
				case 1:
					mLL1.setVisibility(View.VISIBLE);
					mLL2.setVisibility(View.GONE);
					mLL3.setVisibility(View.VISIBLE); 
					mLL4.setVisibility(View.VISIBLE); 
					mLL5.setVisibility(View.VISIBLE); 
					mLL6.setVisibility(View.VISIBLE); 
					break;
				case 2:
					mLL1.setVisibility(View.VISIBLE);
					mLL2.setVisibility(View.VISIBLE);
					mLL3.setVisibility(View.GONE); 
					mLL4.setVisibility(View.VISIBLE); 
					mLL5.setVisibility(View.VISIBLE); 
					mLL6.setVisibility(View.VISIBLE); 
					break;
				case 3:
					mLL1.setVisibility(View.VISIBLE);
					mLL2.setVisibility(View.VISIBLE);
					mLL3.setVisibility(View.VISIBLE); 
					mLL4.setVisibility(View.GONE); 
					mLL5.setVisibility(View.VISIBLE); 
					mLL6.setVisibility(View.VISIBLE); 
					break;
				case 4:
					mLL1.setVisibility(View.VISIBLE);
					mLL2.setVisibility(View.VISIBLE);
					mLL3.setVisibility(View.VISIBLE); 
					mLL4.setVisibility(View.VISIBLE); 
					mLL5.setVisibility(View.GONE); 
					mLL6.setVisibility(View.VISIBLE); 
					break;
				case 5:
					mLL1.setVisibility(View.VISIBLE);
					mLL2.setVisibility(View.VISIBLE);
					mLL3.setVisibility(View.VISIBLE); 
					mLL4.setVisibility(View.VISIBLE); 
					mLL5.setVisibility(View.VISIBLE); 
					mLL6.setVisibility(View.GONE); 
					break;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});


		mBack.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				DrawStage0();
				stage--;
				
			}
		});
		
		mNext.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						
						if (stage == 0){
							mScroll.setVisibility(View.GONE);
							mList.setVisibility(View.VISIBLE);
							mText3.setVisibility(View.VISIBLE);
							mBack.setVisibility(View.VISIBLE);
							// Set up our adapter
							mAdapter = new CardsExpandableListAdapter();
							mList.setAdapter(mAdapter);
							
							mNext.setText(R.string.setup_txtbtnStart);
							stage++;
						}else{
							if (stage == 1){
							
								game.setYourPlayer(mPerson.getSelectedItemPosition());
								
								game.setPlayerNoColumn(game.getYourPlayer());
								int TotalCards = 0;
								for (int i=0; i<game.mPeople.length; i++){
									if (items[0][i]){
										game.setTypeinRowNoData(i, game.getYourPlayer(), CardType.YES);
										TotalCards++;
									}
								}
								for (int i=0; i<game.mPlace.length; i++){
									if (items[1][i]){
										game.setTypeinRowNoData(6+i, game.getYourPlayer(), CardType.YES);
										TotalCards++;
									}
								}
								for (int i=0; i<game.mWeapon.length; i++){
									if (items[2][i]){
										game.setTypeinRowNoData(15+i, game.getYourPlayer(), CardType.YES);
										TotalCards++;
									}
								}
								if (TotalCards < 3){
									showDialog(DIALOG_CARDSNOTSELECTED);
								}else{
									game.setCreatedGame(true);
									startActivity(new Intent(Setup.this, CluedoLogs.class));
									finish();
								}
								
								int playerNum = 0; // you are the first one
								
								// Save names
								if (mCheckBox1.isChecked()){
									playerNum++;
									game.setPlayerName(0, mEdit1.getText().toString());
								}else{
									game.setColumnNoData(0);
								}
								if (mCheckBox2.isChecked()){
									playerNum++;
									game.setPlayerName(1, mEdit2.getText().toString());
								}else{
									game.setColumnNoData(1);
								}
								if (mCheckBox3.isChecked()){
									playerNum++;
									game.setPlayerName(2, mEdit3.getText().toString());
								}else{
									game.setColumnNoData(2);
								}
								if (mCheckBox4.isChecked()){
									playerNum++;
									game.setPlayerName(3, mEdit4.getText().toString());
								}else{
									game.setColumnNoData(3);
								}
								if (mCheckBox5.isChecked()){
									playerNum++;
									game.setPlayerName(4, mEdit5.getText().toString());
								}else{
									game.setColumnNoData(4);
								}
								if (mCheckBox6.isChecked()){
									playerNum++;
									game.setPlayerName(5, mEdit6.getText().toString());
								}else{
									game.setColumnNoData(5);
								}
								
								//Save number of Players
								game.setNumberOfPlayers(playerNum);
								
								String[] tmp = new String[playerNum];
								int j=0;
								for (int i=0; i<game.mPeople.length; i++){
									if (!game.mPeopleName[i].equals("")){
										tmp[j] = game.mPeopleName[i] + " (" + game.mPeople[i] + " )";
									}else{
										tmp[j] = game.mPeople[i];
									}
									j++;
								}
								
								//Save players list
								game.mPlayersList = tmp;
							}
						}
					}
				});

		//showDialog(DIALOG_INFO);
	}
	
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if ((keyCode == KeyEvent.KEYCODE_BACK)&&(stage == 1)){
			stage--;
			DrawStage0();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}



	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		
		items[groupPosition][childPosition] = !items[groupPosition][childPosition];
		ListItemCache cache = (ListItemCache) v.getTag();
		
		if (items[groupPosition][childPosition]){
			cache.check.setVisibility(View.VISIBLE);
		}else{
			cache.check.setVisibility(View.INVISIBLE);
		}
		return super.onChildClick(parent, v, groupPosition, childPosition, id);
	}

	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DIALOG_CARDSNOTSELECTED:
            return new AlertDialog.Builder(Setup.this)
            		.setTitle(R.string.logs_alert_title)
            		.setIcon(R.drawable.btn_info)
            		.setMessage(R.string.setup_dialog_notenoughtpcards_msg)
            		.setPositiveButton(R.string.alert_dialog_ok, null)
            .create();
//        case DIALOG_INFO:
//	        return new AlertDialog.Builder(Setup.this)
//	        		.setTitle(R.string.logs_alert_title)
//	        		.setIcon(R.drawable.btn_info)
//	        		.setMessage(R.string.setup_dialog_info_msg)
//	        		.setPositiveButton(R.string.alert_dialog_ok, null)
//	        .create();
        }
	    
        return null;
    }

    private static final class ListItemCache {
    	
    	public TextView Text;
        public ImageView check;
       
    }
    
	/**
     * A simple adapter which maintains an ArrayList of photo resource Ids. 
     * Each photo is displayed as an image. This adapter supports clearing the
     * list of photos and adding a new photo.
     *
     */
    public class CardsExpandableListAdapter extends BaseExpandableListAdapter {
        

    	private LayoutInflater inflater;
    	
    	public CardsExpandableListAdapter(){
    		// Example how to read String array from Resources
    		Resources r = getResources();
    		children[0] = game.mPeople;
    		children[1] = game.mPlace;
    		children[2] = game.mWeapon;
    		groups = r.getStringArray(R.array.category);
    		
    		inflater = (LayoutInflater)getBaseContext().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
    	}
        
        public Object getChild(int groupPosition, int childPosition) {
            return children[groupPosition][childPosition];
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return children[groupPosition].length;
        }

        public TextView getGenericView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.FILL_PARENT, 64);

            TextView textView = new TextView(Setup.this);
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            textView.setPadding(36, 0, 0, 0);
            return textView;
        }
        
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
        	
            TextView textView = getGenericView();
            textView.setText(getChild(groupPosition, childPosition).toString());
            
            ListItemCache cache = new ListItemCache();
			View view = null;
			if (convertView != null){
				view = convertView;
				cache = (ListItemCache) view.getTag();
			}
			else{
				view = inflater.inflate(R.layout.setup_child_row, parent, false);
				
                cache.Text = (TextView)view.findViewById(R.id.childname);
                cache.check = (ImageView)view.findViewById(R.id.check);
                
                view.setTag(cache);
			}
			
			final Object c = (Object) getChild(groupPosition, childPosition);

			cache.Text.setText(getChild(groupPosition, childPosition).toString());
			if (items[groupPosition][childPosition]){
				cache.check.setVisibility(View.VISIBLE);
			}else{
				cache.check.setVisibility(View.INVISIBLE);
			}

            return view;
            
        }

        public Object getGroup(int groupPosition) {
            return groups[groupPosition];
        }

        public int getGroupCount() {
            return groups.length;
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
        	View v = null;
			if (convertView != null)
				v = convertView;
			else
				v = inflater.inflate(R.layout.setup_group_row, parent, false);
			
			TextView item = (TextView) v.findViewById(R.id.groupname);
			item.setText(getGroup(groupPosition).toString());
            
            return v;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }
        
        public void updateData(){        	
        	children[0] = game.mPeople;
    		children[1] = game.mPlace;
    		children[2] = game.mWeapon;
        }

    }
    
 
    private void DrawStage0(){
		mList.setVisibility(View.GONE);
		mScroll.setVisibility(View.VISIBLE);
		mBack.setVisibility(View.INVISIBLE);
		mNext.setText(R.string.setup_txtbtnNext);
		mText3.setVisibility(View.GONE);
    }
}
