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

import java.util.zip.Inflater;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class Setup extends ExpandableListActivity {

    private CluedoApp cApp;
    private GamePOJO game;
	private ExpandableListAdapter mAdapter;
	private ExpandableListView mList;
	private Spinner mPleyers;
	private Spinner mPerson;
	private Button mStart;
	
	private String[] groups = new String[3];
	private String[][] children = new String[3][];

	// All check box state
	private Card[] items = new Card[24];
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.setup);
		mList = (ExpandableListView) findViewById(android.R.id.list);
		mPleyers = (Spinner) findViewById(R.id.spinnerNumberOfPlayers);
		mPerson = (Spinner) findViewById(R.id.spinnerYourPerson);
		mStart = (Button) findViewById(R.id.btnStart);
		
		cApp = (CluedoApp) getApplication();
		game = cApp.getGame();
		
		// Set number of Players
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.NumberOfPlayers,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mPleyers.setAdapter(adapter);

		// Set list of persons
		adapter = ArrayAdapter.createFromResource(this, R.array.people,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mPerson.setAdapter(adapter);

		// Set up our adapter
		mAdapter = new CardsExpandableListAdapter();
		mList.setAdapter(mAdapter);
		
		mStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				game.setNumberOfPlayers(mPleyers.getSelectedItemPosition()+2);
				game.setYourPlayer(mPerson.getSelectedItemPosition());
				
				startActivity(new Intent(Setup.this, Table.class));
			}
		});

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
    		children[0] = r.getStringArray(R.array.people);
    		children[1] = r.getStringArray(R.array.place);
    		children[2] = r.getStringArray(R.array.weapon);
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
            
			View v = null;
			if (convertView != null)
				v = convertView;
			else
				v = inflater.inflate(R.layout.setup_child_row, parent, false);
			
			final Object c = (Object) getChild(groupPosition, childPosition);
			TextView item = (TextView) v.findViewById(R.id.childname);
			if (item != null)
				item.setText(getChild(groupPosition, childPosition).toString());
			
			final CheckBox cb = (CheckBox) v.findViewById(R.id.check1);
			//cb.setChecked(c.getState());

			cb.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					//c.setState(cb.isChecked());
				}
			});

            return v;
            
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

    }
    
    public class Card{
    	
    	private String text;
    	private boolean state;
    	
    	public void setState(boolean select){
    		this.state = select;
    	}
    	
    	public boolean getState(){
    		return this.state;
    	}
    	
    	public void setText(String text){
    		this.text = text;
    	}
    	
    	public String getText(){
    		return this.text;
    	}
    }

}
