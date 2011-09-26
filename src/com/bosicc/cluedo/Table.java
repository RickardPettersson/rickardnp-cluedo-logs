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
import android.app.ListActivity;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;


/**
 * A list view example where the data comes from a custom ListAdapter
 */
public class Table extends ListActivity {
	
	private ListView mList;
	private ListAdapter mAdapter;
	private String[] mCards = new String[24];
    private CluedoApp cApp;
    private GamePOJO game;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.table);
		mList = (ListView) findViewById(android.R.id.list);
		
		cApp = (CluedoApp) getApplication();
		game = cApp.getGame();
		
	    // Example how to read String array from Resources
		int i=0;
	    Resources r = getResources();
	    for (int j=0;j<6;j++){
	    	mCards[i] = r.getStringArray(R.array.people)[j];
	    	i++;
	    }
	    for (int j=0;j<9;j++){
	    	mCards[i] = r.getStringArray(R.array.place)[j];
	    	i++;
	    }
	    for (int j=0;j<9;j++){
	    	mCards[i] = r.getStringArray(R.array.weapon)[j];
	    	i++;
	    }
	       
		// Set up our adapter
		mAdapter = new MyListAdapter(this);
		mList.setAdapter(mAdapter);
		

	    }
	
	/**
     * Item view cache holder.
     */
    private static final class ListItemCache {

        
        public TextView Text;
        public View divider;

        public ImageButton btn1;
        public ImageButton btn2;
        public ImageButton btn3;
        public ImageButton btn4;
        public ImageButton btn5;
        public ImageButton btn6;
       
    }
	
	private class MyListAdapter extends BaseAdapter {
		
		private Context mContext;
		 
        public MyListAdapter(Context context) {
        	this.mContext = context;
        }

        public int getCount() {
            return mCards.length;
        }

        @Override
        public boolean areAllItemsEnabled() {
            return false;
        }

        @Override
        public boolean isEnabled(int position) {
            return !mCards[position].startsWith("-");
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
            
        	
            //cache.divider = view.findViewById(R.id.list_divider);
        	ListItemCache cache = new ListItemCache();
        	
            if (view == null) {
            	view = (View) LayoutInflater.from(mContext).inflate(
                        R.layout.table_row, parent, false);
            	
                cache.Text = (TextView)view.findViewById(R.id.txtLeft);
                cache.btn1 = (ImageButton)view.findViewById(R.id.btn1);
                cache.btn2 = (ImageButton)view.findViewById(R.id.btn2);
                cache.btn3 = (ImageButton)view.findViewById(R.id.btn3);
                cache.btn4 = (ImageButton)view.findViewById(R.id.btn4);
                cache.btn5 = (ImageButton)view.findViewById(R.id.btn5);
                cache.btn6 = (ImageButton)view.findViewById(R.id.btn6);
                view.setTag(cache);
            } else {
            	cache = (ListItemCache) view.getTag();
            }
            
            cache.Text.setText(mCards[position]);
            return view;
        }



    }
    
  
}