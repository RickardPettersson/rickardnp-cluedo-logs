package com.bosicc.cluedo;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;




public class PlayersName extends Dialog implements OnClickListener{
	private ImageButton okButton;

	private GamePOJO game;
	private Utils utils;
	private Context context;
    private ListView mList;
    private ListAdapter mAdapter;
    
	public PlayersName(Context context, GamePOJO game) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.playernames);
		okButton = (ImageButton) findViewById(R.id.OkButton);
		okButton.setOnClickListener(this);

		this.game = game;
		this.context = context;
		utils = new Utils(context, game);
		
		mList = (ListView) findViewById(R.id.listView);
		mAdapter = new MyNameAdapter();
        mList.setAdapter(mAdapter);
	}
	
	/**
     * Item view cache holder.
     */
    private static final class ListItemCache {
    	
        public TextView text;
        public EditText editText;
        public CheckBox check;

    }
	
	public class MyNameAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return game.mPlayers.size();
		}

		@Override
		public Object getItem(int position) {
			return game.mPlayers.get(position);
		}
		
		@Override
		public long getItemId(int position) {
			return game.mPlayers.get(position).getNumber();
		}

		@Override
		public View getView(int position, View view, ViewGroup parent) {
			ListItemCache cache = new ListItemCache();
            if (view == null) {
            	view = (View) LayoutInflater.from(context).inflate(
                        R.layout.playername_row, parent, false);
            	
                cache.text = (TextView)view.findViewById(R.id.text);
                cache.editText = (EditText)view.findViewById(R.id.editText);
                cache.check = (CheckBox)view.findViewById(R.id.checkBox);
                
                view.setTag(cache);

            } else {
            	cache = (ListItemCache) view.getTag();
            }
            
                       
            //Set player color
            cache.text.setBackgroundColor(game.mPlayers.get(position).getColor());
            //Edit text field
            String name = game.mPlayers.get(position).getName();
            if (name.equals("")){
            	cache.editText.setText("");
            	cache.editText.setHint(game.mPlayers.get(position).getCardName());
            }else{
            	cache.editText.setText(name);
            }
          //we need to update adapter once we finish with editing
            
//            cache.editText.setOnFocusChangeListener(new OnFocusChangeListener() {
//                            public void onFocusChange(View v, boolean hasFocus) {
//                                if (!hasFocus){
//                                    final int position = v.getId();
//                                    final EditText Caption = (EditText) v;
//                                    //myItems.get(position).caption = Caption.getText().toString();
//            
//                                }
//                            }
//                        });

            
            // Check Box
            cache.check.setEnabled(!game.isCreated);
            cache.check.setChecked(game.mPlayers.get(position).inGame());

            return view;
		}
		
	}

	@Override
	public void onClick(View v) {
		if (v == okButton){
			dismiss();
			
		}
	}

}
