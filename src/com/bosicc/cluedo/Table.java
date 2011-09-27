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
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.bosicc.cluedo.PlayerPOJO.CardType;


/**
 * A list view example where the data comes from a custom ListAdapter
 */
public class Table extends ListActivity {
	
	private static String TAG = "Table";

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

    	public int pos;
    	
    	public View header;
        public TextView Text;
        public TextView headerText;
        public View divider;

        public ImageButton btn1;
        public ImageButton btn2;
        public ImageButton btn3;
        public ImageButton btn4;
        public ImageButton btn5;
        public ImageButton btn6;
        
//          public ImageView btn1;
//	      public ImageView btn2;
//	      public ImageView btn3;
//	      public ImageView btn4;
//	      public ImageView btn5;
//	      public ImageView btn6;
       
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
		public void notifyDataSetChanged() {
			super.notifyDataSetChanged();
			
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
        	
        	ListItemCache cache = new ListItemCache();
            if (view == null) {
            	view = (View) LayoutInflater.from(mContext).inflate(
                        R.layout.table_row, parent, false);
            	
            	cache.header = view.findViewById(R.id.header);
                cache.Text = (TextView)view.findViewById(R.id.txtLeft);
                cache.headerText = (TextView)view.findViewById(R.id.header_text);
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
            
            Log.i("Table","pos"+position);
            cache.btn1.setOnClickListener(new OnItemClickListener(position)); 
            cache.btn2.setOnClickListener(new OnItemClickListener(position)); 
            cache.btn3.setOnClickListener(new OnItemClickListener(position)); 
            cache.btn4.setOnClickListener(new OnItemClickListener(position)); 
            cache.btn5.setOnClickListener(new OnItemClickListener(position));
            cache.btn6.setOnClickListener(new OnItemClickListener(position));
            
            cache.header.setVisibility(View.GONE);
            
            if (position == 0){
            	cache.headerText.setText(R.string.title_people);
            	cache.header.setVisibility(View.VISIBLE);
            }
            
            if (position == 6){
            	cache.headerText.setText(R.string.title_place);
            	cache.header.setVisibility(View.VISIBLE);
            }
        
            if (position == 15){
            	cache.headerText.setText(R.string.title_weapon);
            	cache.header.setVisibility(View.VISIBLE);
            }
            
            cache.Text.setText(mCards[position]);

            cache.btn1.setImageResource(getResourceByType(game.getCardsData()[position][0]));
            cache.btn2.setImageResource(getResourceByType(game.getCardsData()[position][1]));
            cache.btn3.setImageResource(getResourceByType(game.getCardsData()[position][2]));
            cache.btn4.setImageResource(getResourceByType(game.getCardsData()[position][3]));
            cache.btn5.setImageResource(getResourceByType(game.getCardsData()[position][4]));
            cache.btn6.setImageResource(getResourceByType(game.getCardsData()[position][5]));

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
        
        private class OnItemClickListener implements OnClickListener{           
            private int mPosition;
            OnItemClickListener(int position){
                    mPosition = position;
            }
            @Override
            public void onClick(View view) {
                       
                    int num = 0;
                    switch (view.getId()){
	                    case R.id.btn1:
	                    	num = 0;
	                    	break;
	                    case R.id.btn2:
	                    	num = 1;
		                    break;
	                    case R.id.btn3:
	                    	num = 2;
		                    break;
	                    case R.id.btn4:
	                    	num = 3;
		                    break;
	                    case R.id.btn5:
	                    	num = 4;
		                    break;
	                    case R.id.btn6:
	                    	num = 5;
		                    break;
                    }
                    Log.i(TAG, "onItemClick at pos=" + mPosition + "num=" + num);  
                    game.setCardsData(mPosition,num,CardType.YES);
                    view.setBackgroundColor(R.color.bgPeoplelistSelect);
                    notifyDataSetChanged();
            }               
        }



    }
}