package com.bosicc.cluedo;

import java.util.ArrayList;
import java.util.List;

import android.content.pm.ResolveInfo;

import com.bosicc.cluedo.PlayerPOJO.CardType;

public class GamePOJO {
	
	private int NumberOfPlayers;
	private int YourPlayer;
	private boolean isCreated = false;
	private PlayerPOJO[] Players;
	private CardType[][] mCards; 
	
	public ArrayList<PMovePOJO> mLogsList;

	public GamePOJO(){
		YourPlayer = 1; // default
		NumberOfPlayers = 3; // default
		
		Players = new PlayerPOJO[6];
		// all raws for list
		mCards  = new CardType[24][6]; 
		
		for (int i=0;i<6;i++){
			for (int j=0; j<24; j++){
				mCards[j][i] = CardType.DEFAULT;
			}
			Players[i] = new PlayerPOJO();
		}
		
		mLogsList = new ArrayList<PMovePOJO>();
	}
	
	public void setNumberOfPlayers(int num){
		this.NumberOfPlayers = num;
	}
	
	public int getNumberOfPlayers(){
		return NumberOfPlayers;
	}
	
	public void setYourPlayer(int num){
		this.YourPlayer = num;
	}
	
	public int getYourPlayer(){
		return YourPlayer;
	}
	
	public void fillCardsData(){
		setCreatedGame(true);
	}
	
	public CardType[][] getCardsData(){
		return mCards;
	}
	
	public void setCardsData(int pos, int num, CardType type){
		mCards[pos][num] = type;
	}
	
	public void setRowNoData(int pos){
		for (int i=0;i<6;i++){
    		setCardsData(pos,i,CardType.NO);
    	}
	}
	
	public void setTypeinRowNoData(int pos, int num, CardType type){
		setRowNoData(pos);
		setCardsData(pos, num, type);
	}
	
	public boolean isStarted(){
		return isCreated;
	}
	
	public void setCreatedGame(boolean is){
		isCreated = is;
	}
	
	public void setPlayerNoColumn(int player){
		for (int i=0; i<24; i++){
			mCards[i][player] = CardType.NO;
		}
		
	}
	
	public void reset(){
		// Clear logs items
		mLogsList.clear();
		// Clear tabele
		for (int j=0; j<6; j++){
			for (int i=0; i<24; i++){
				mCards[i][j] = CardType.DEFAULT;
			}
			// TODO: May be remove not used Class?
			Players[j].reset();
		}
		
		// Set Flag that game not started
		this.setCreatedGame(false);
		
	}
	
}
