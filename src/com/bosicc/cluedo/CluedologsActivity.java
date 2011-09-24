package com.bosicc.cluedo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class CluedologsActivity extends Activity {
  
	private Button mBtnNew;
	private Button mBtnAbout;
	private Button mBtnDonate;
	  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mBtnNew = (Button) findViewById(R.id.btnNew);
        mBtnAbout = (Button) findViewById(R.id.btnAbout);
        mBtnDonate = (Button) findViewById(R.id.btnDonate);
        mBtnNew.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(CluedologsActivity.this, CluedoLogs.class));
			}
		});
        
        mBtnAbout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(CluedologsActivity.this, Table.class));
			}
		});
        
        mBtnDonate.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			startActivity(new Intent(CluedologsActivity.this, CluedoLogs.class));
		}
	});
    }
}