package com.bosicc.cluedo;

import android.app.Dialog;
import android.app.TabActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TabHost;


public class CluedoLogs extends TabActivity {
   
	private TabHost tabHost;

	public static final String TAB_MAP = "Map";
	public static final String TAB_ACCOUNT = "Account";
	public static final String TAB_SETTINGS = "Settings";
	

	//======================================================
	// Menu items ID
	//======================================================
	/**
     * Mene settings activity 
     */
	private static final int MENU_ITEM_PEOPLE			 	=1;			
	private static final int MENU_ITEM_NEW					=2;
	private static final int MENU_ITEM_HELP					=3;

	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.maintab);
        
    	
		tabHost = getTabHost();
		//tabHost.setOnTabChangedListener(this);
        
		setTabs(0);
       
    }

	/* (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	@Override
	protected void onResume() {
		super.onResume();
		
	}
	
	private void setTabs(int activeTab){
		setupTableTab(); 		// tab_1
		setupLogsTab();			// tab_2
		
		tabHost.setCurrentTab(activeTab);
	}
	
	private void setupTableTab() {
//		tabHost.addTab(tabHost.newTabSpec(TAB_MAP).setIndicator(
//				"Menu",
//				getResources().getDrawable(android.R.drawable.ic_dialog_map))
//				.setContent(new Intent(this, .class)));
	}
	private void setupLogsTab() {
//		tabHost.addTab(tabHost.newTabSpec(TAB_MAP).setIndicator(
//				"Menu",
//				getResources().getDrawable(android.R.drawable.ic_dialog_map))
//				.setContent(new Intent(this, .class)));
	}
	
//	private void setupNewsTab() {
//		tabHost.addTab(tabHost.newTabSpec(TAB_MAP).setIndicator(
//				"News",
//				getResources().getDrawable(android.R.drawable.ic_dialog_info))
//				.setContent(new Intent(this, MessageList.class)));
//	}
	
//	private void setupKpiTab() {
////		tabHost.addTab(tabHost.newTabSpec(TAB_MAP).setIndicator(
////				"Account",
////				getResources().getDrawable(R.drawable.tab_users))
////				.setContent(new Intent(this, News.class)));
//		tabHost.addTab(tabHost.newTabSpec(TAB_MAP).setIndicator(
//		"Account",
//		getResources().getDrawable(android.R.drawable.ic_menu_preferences))
//		.setContent(new Intent(this, Settings.class)));
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
		MenuItem item_new_contact = menu.add(Menu.NONE, MENU_ITEM_PEOPLE, Menu.NONE, R.string.mainmenu_new);
		item_new_contact.setIcon(android.R.drawable.ic_menu_preferences);
		
		// ===
		MenuItem item_2 = menu.add(Menu.NONE, MENU_ITEM_NEW, Menu.NONE, R.string.mainmenu_new);
		item_2.setIcon(android.R.drawable.ic_menu_preferences);

		// ===
     	MenuItem item_3 = menu.add(Menu.NONE, MENU_ITEM_HELP, Menu.NONE, R.string.mainmenu_about);
     	item_3.setIcon(android.R.drawable.ic_menu_mapmode);
     	
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
	        	//startActivity(new Intent(this, .class));
	        	return true;
	        }
	        
	        case MENU_ITEM_HELP:{
	        	//startActivity(new Intent(this, .class));
	        	return true;
	        }
        }
        

		return super.onOptionsItemSelected(item);
   }
    
}