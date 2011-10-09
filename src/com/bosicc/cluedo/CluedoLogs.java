package com.bosicc.cluedo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TabActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;


public class CluedoLogs extends TabActivity {
	
	private static String TAG = "CluedoLogs";
   
	private TabHost tabHost;
    private CluedoApp cApp;
    private GamePOJO game;

	public static final String TAB_TABLE = "Table";
	public static final String TAB_LOGS = "Logs";
	public static final String TAB_LOGSText = "LogsText";
	
	//======================================================
	// Dialog items ID
	//======================================================
	private static final int DIALOG_NEWGAME = 1;
	

	//======================================================
	// Menu items ID
	//======================================================
	private static final int MENU_ITEM_PEOPLE			 	=1;			
	private static final int MENU_ITEM_NEW					=2;
	private static final int MENU_ITEM_HELP					=3;
	
	private static final int group1Id = 1;  

	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintab);
        
		cApp = (CluedoApp) getApplication();
		game = cApp.getGame();
    	
		tabHost = getTabHost();
		//tabHost.setOnTabChangedListener(this);
        
		setTabs(0);
       
    }

    @Override
	protected void onRestart() {
		super.onRestart();
		//Log.i(TAG, "onRestart()");
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		//Log.i(TAG, "onStart()");
		
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		//Log.i(TAG, "onPause()");
	}

	@Override
	protected void onResume() {
		super.onResume();		
		
		//Log.i(TAG, "onResume()");
	}

	@Override
	public void onStop() {
		super.onStop();

		//Log.i(TAG, "onStop()");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (game.isStarted()){
			cApp.getUtils().Save(game);
		}
		//Log.i(TAG, "onDestroy()");
	}
	
	private void setTabs(int activeTab){
		setupTableTab(); 		// tab_1
		setupLogsTab();			// tab_2
		setupLogsTextTab();			// tab_2
		
		tabHost.setCurrentTab(activeTab);
	}
	
	private void setupTableTab() {
		tabHost.addTab(tabHost.newTabSpec(TAB_TABLE).setIndicator(
				"Table",
				getResources().getDrawable(R.drawable.tab_table_icon))
				.setContent(new Intent(this, Table.class)));
	}
	
	private void setupLogsTab() {
		tabHost.addTab(tabHost.newTabSpec(TAB_LOGS).setIndicator(
				"Logs",
				getResources().getDrawable(R.drawable.tab_log2_icon))
				.setContent(new Intent(this, Logs.class)));
	}
	
	private void setupLogsTextTab() {
		tabHost.addTab(tabHost.newTabSpec(TAB_LOGS).setIndicator(
				"Text Logs",
				getResources().getDrawable(R.drawable.tab_log_icon))
				.setContent(new Intent(this, LogsText.class)));
	}
	
	@Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DIALOG_NEWGAME:
            return new AlertDialog.Builder(CluedoLogs.this)
            .setTitle(R.string.tab_dialog_newgame_titile)
            .setIcon(R.drawable.btn_info)
            .setMessage(R.string.tab_dialog_newgame_msg)
            .setPositiveButton(R.string.tab_dialog_yes, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					
					// Reset all states in game
					game.reset();
					// Delete current game from disk;
					cApp.getUtils().DeleteCurentGame();
					startActivity(new Intent(CluedoLogs.this, CluedologsActivity.class));
					finish();
					
				}
			}).setNegativeButton(R.string.tab_dialog_no, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {

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
//    	// ===
//		MenuItem item_new_contact = menu.add(Menu.NONE, MENU_ITEM_PEOPLE, Menu.NONE, R.string.mainmenu_new);
//		item_new_contact.setIcon(android.R.drawable.ic_menu_preferences);
		
		// ===
		MenuItem item_2 = menu.add(group1Id  , MENU_ITEM_NEW, Menu.FIRST, R.string.mainmenu_new);
		item_2.setIcon(android.R.drawable.ic_menu_agenda);

		// ===
     	MenuItem item_3 = menu.add(group1Id, MENU_ITEM_HELP, Menu.FIRST+1, R.string.mainmenu_about);
     	item_3.setIcon(android.R.drawable.ic_menu_info_details);
     
     	
     	return super.onCreateOptionsMenu(menu);
    }
    
    /**
     * On options menu item selection.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
          
	        case MENU_ITEM_PEOPLE:{
	        	//startActivity(new Intent(this, .class));
	        	return true;
	        }
	        
	        case MENU_ITEM_NEW:{
	        	showDialog(DIALOG_NEWGAME);
	        	return true;
	        }
	        
	        case MENU_ITEM_HELP:{
	        	//startActivity(new Intent(this, .class));
//	        	cApp.getUtils().Save(game);
//	        	GamePOJO loadGame = cApp.getUtils().Load();
//	        	if (loadGame != null)
//	        		Log.i(TAG,"game=" +loadGame.getYourPlayer());
	        	startActivity(new Intent(CluedoLogs.this, About.class));
	        	return true;
	        }
        }
        

		return super.onOptionsItemSelected(item);
   }
    
}