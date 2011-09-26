package com.bosicc.cluedo;

public class GamePOJO {
	
	private int NumberOfPlayers;
	private int YourPlayer;
	private boolean isCreated = false;
	private PlayerPOJO[] Players;

	public GamePOJO(){
		YourPlayer = 1; // default
		NumberOfPlayers = 3; // default
		
		Players = new PlayerPOJO[6];
		
		for (int i=0;i<6;i++){
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
	
}
