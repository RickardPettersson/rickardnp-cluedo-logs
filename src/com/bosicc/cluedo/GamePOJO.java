package com.bosicc.cluedo;

import com.bosicc.cluedo.PlayerPOJO.CardType;

public class GamePOJO {
	
	private int NumberOfPlayers;
	private int YourPlayer;
	private boolean isCreated = false;
	private PlayerPOJO[] Players;
	private CardType[][] mCards; 

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
		isCreated = true;
	}
	
	public CardType[][] getCardsData(){
		return mCards;
	}
	
	public void setCardsData(int pos, int num, CardType type){
		mCards[pos][num] = type;
	}
	
//	public void setSelectItem(int pos, int num){
//		mCards[pos][num] = type;
//	}
	
}
