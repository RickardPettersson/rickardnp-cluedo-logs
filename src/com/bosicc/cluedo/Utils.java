package com.bosicc.cluedo;

import java.util.ArrayList;

import com.bosicc.cluedo.GamePOJO.CardType;
import com.bosicc.cluedo.GamePOJO.ShowModeType;

import android.content.Context;
import android.content.res.Resources;

public class Utils {
	
	private Context ctx;
    private GamePOJO game;
	
	public Utils(Context context, GamePOJO game){
		ctx = context;
		this.game = game;
	}
	
	/**
	 * Fill new game data in Game class from resourses
	 * @param GameNum
	 */
	public void UpdateGameDataList(int GameNum){
    	Resources r = ctx.getResources();
		
		switch(GameNum){
		case 0: // Russian version
			game.mPeople = r.getStringArray(R.array.people_ru);
			game.mPlace = r.getStringArray(R.array.place_ru);
			game.mWeapon = r.getStringArray(R.array.weapon_ru);
			break;
		case 1:// Cluedo in Paris
			game.mPeople = r.getStringArray(R.array.people_ru);
			game.mPlace = r.getStringArray(R.array.place_ru);
			game.mWeapon = r.getStringArray(R.array.weapon_ru);
			break;
		case 2:// Clue the 24
			game.mPeople = r.getStringArray(R.array.people_24);
			game.mPlace = r.getStringArray(R.array.place_24);
			game.mWeapon = r.getStringArray(R.array.weapon_24);
			break;
		case 3:// Clue the office
			game.mPeople = r.getStringArray(R.array.people_office);
			game.mPlace = r.getStringArray(R.array.place_office);
			game.mWeapon = r.getStringArray(R.array.weapon_office);
			break;
		case 4:// Clue English (original)
			game.mPeople = r.getStringArray(R.array.people_original);
			game.mPlace = r.getStringArray(R.array.place_original);
			game.mWeapon = r.getStringArray(R.array.weapon_original);
			break;
		case 5:// Clue Limited edition
			game.mPeople = r.getStringArray(R.array.people_lim_gif);
			game.mPlace = r.getStringArray(R.array.place_lim_gif);
			game.mWeapon = r.getStringArray(R.array.weapon_lim_gif);
		case 6:// Clue the Simpsons
			game.mPeople = r.getStringArray(R.array.people_simpsons);
			game.mPlace = r.getStringArray(R.array.place_simpsons);
			game.mWeapon = r.getStringArray(R.array.weapon_simpsons);
			break;
		case 7:// Clue the Simpsons 3rd edition
			game.mPeople = r.getStringArray(R.array.people_simpsons_3rd);
			game.mPlace = r.getStringArray(R.array.place_simpsons_3rd);
			game.mWeapon = r.getStringArray(R.array.weapon_simpsons_3rd);
			break;
		case 8:// Clue the HarryPoter
			game.mPeople = r.getStringArray(R.array.people_potter);
			game.mPlace = r.getStringArray(R.array.place_potter);
			game.mWeapon = r.getStringArray(R.array.weapon_potter);
			break;
			
		}
		
		//Number of players in game
		game.playernum = game.mPeople.length;
		
		//Calculate number of cards in game 
		game.cardnum = game.mPeople.length + game.mPlace.length + game.mWeapon.length;
		
		//Reset Players list 
		game.mPlayers.removeAll(game.mPlayers);
		for (int i=0;i<game.mPeople.length;i++){
			PlayerPOJO item = new PlayerPOJO();
			item.setCardName(game.mPeople[i]);
			item.setLabel("",game.mPeople[i]);
			item.setNumber(i);
			item.setColor(getColorForPlayer(i));
			item.inGame(true);
			game.mPlayers.add(item);
		}
    }
	
	/**
	 * Update Players labels
	 * @param GameNum
	 */
	public void UpdatePlayerLabels(){

		for (PlayerPOJO item:game.mPlayers){
			item.setLabel(item.getName(),item.getCardName());
		}
    }
	
	
	/**
	 * Get String array from ArrayList<PlayerPOJO> list from Label value
	 * @return
	 */
	public String[] getString(ArrayList<PlayerPOJO> list){
		String[] StringList = new String[list.size()];
		int j=0;
		for (PlayerPOJO item : list){
			StringList[j] = item.getLabel();
			j++;
		}

    	return StringList;
	}
	
	/**
	 * Get ArrayList<PlayerPOJO> of podtverdil people (for Sort)
	 * @return
	 */
	public ArrayList<PlayerPOJO> getSortPodtverdilList(){
		ArrayList<PlayerPOJO> list = new ArrayList<PlayerPOJO>();
		list.add(new PlayerPOJO(ctx.getText(R.string.logs_notconfirm).toString(),R.color.bgBlack));
		for (PlayerPOJO item : game.mPlayers){
			if (item.inGame()){
				list.add(item);
			}
		}
    	return list;
	}
	
	/**
	 * Get ArrayList<PlayerPOJO> of podtverdil people 
	 * @return
	 */
	public ArrayList<PlayerPOJO> getPodtverdilList(int xodit){
		ArrayList<PlayerPOJO> list = new ArrayList<PlayerPOJO>();
		list.add(new PlayerPOJO(ctx.getText(R.string.logs_notconfirm).toString(),R.color.bgBlack));
		for (PlayerPOJO item : game.mPlayers){
			if (item.inGame() && (item.getNumber() != xodit)){
				list.add(item);
			}
		}
    	return list;
	}
	
	/**
	 * Get ArrayList<PlayerPOJO> of xodil people (for Sort)
	 * @return
	 */
	public ArrayList<PlayerPOJO> getSortXodilList(){
		ArrayList<PlayerPOJO> list = new ArrayList<PlayerPOJO>();
		for (PlayerPOJO item : game.mPlayers){
			if (item.inGame()){
				list.add(item);
			}
		}
    	return list;
	}
	
	
//	/**
//	 * Get string array of played persons (all person in game) + not confirm at 0 position
//	 * @return
//	 */
//	public String[] getSortPodtverdilList(){
//		String[] list = new String[game.mPlayers.size()+1];
//   	list[0] = ctx.getText(R.string.logs_notconfirm).toString();
//   	for (int i=1;i<game.mPlayers.size()+1;i++){
//   		if (game.mPlayers.get(i-1).getName().equals("")){
//   			list[i] =game.mPlayers.get(i-1).getName();
//			}else{
//				list[i] =game.mPlayers.get(i-1).getName()+ " (" + game.mPlayers.get(i-1).getCardName() + " )";
//			}
//   	}
//   	return list;
//	}
	
	
//		game.mPlayersList = new String[playerNum];
//		int j=0;
//		for (PlayerPOJO item : game.mPlayers){
//			if (!item.getName().equals("")){
//				game.mPlayersList[j] =item.getName()+ " (" + item.getCardName() + " )";
//			}else{
//				game.mPlayersList[j] = item.getCardName();
//			}
//			j++;
//		}
//		return ;
//	}
	
//	public String[] getPodtverdilList(){
//		game.mPlayersList = new String[playerNum];
//		int j=0;
//		for (PlayerPOJO item : game.mPlayers){
//			if (!item.getName().equals("")){
//				game.mPlayersList[j] =item.getName()+ " (" + item.getCardName() + " )";
//			}else{
//				game.mPlayersList[j] = item.getCardName();
//			}
//			j++;
//		}
//		return ;
//	}
	
	
	
	public ArrayList<PMovePOJO> getAllList(){
		return game.mLogsList;
	}
	
	public ArrayList<PMovePOJO> getCurentList(ShowModeType mode, int person){
		ArrayList<PMovePOJO> mNewList = new ArrayList<PMovePOJO>();
		switch(mode){
			case ALL:
				return game.mLogsList;
			case XODIT:{
				for (PMovePOJO item: game.mLogsList) {
					if (item.getPlayerXodit() == person) {
						mNewList.add(item);
					}
				}
			}
				break;
			case PODTVERDIL:{
				for (PMovePOJO item: game.mLogsList) {
					if (item.getPlayerPodtverdil() == person) {
						mNewList.add(item);
					}
				}
			}
				break;
		}
		
		return mNewList;
	}
	

	public int getResourceByType(CardType type){
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
    
	public int getColorForPlayer(int pleyernum){
    	int res = R.color.bgMain;
    	switch (pleyernum){
    		case 0:
    			res = R.color.bgPeople1;
    			break;
    		case 1:
    			res = R.color.bgPeople2;
    			break;
    		case 2:
    			res = R.color.bgPeople3;
    			break;
    		case 3:
    			res = R.color.bgPeople4;
    			break;
    		case 4:
    			res = R.color.bgPeople5;
    			break;
    		case 5:
    			res = R.color.bgPeople6;
    			break;
    		case 6:
    			res = R.color.bgTransperent;
    			break;
    		case 100: //NotConfirm
    			res = R.color.bgBlack;
    			break;
    		case -1:
    			res = R.color.bgMain;
    			break;
    	}
    	return res;
    }
    
	public int getIconForPlayer(int pleyernum){
    	int res = R.drawable.btn_none;
    	switch (pleyernum){
    		case 0:
    			res = R.drawable.p1_icon;
    			break;
    		case 1:
    			res = R.drawable.p2_icon;
    			break;
    		case 2:
    			res = R.drawable.p3_icon;
    			break;
    		case 3:
    			res = R.drawable.p4_icon;
    			break;
    		case 4:
    			res = R.drawable.p5_icon;
    			break;
    		case 5:
    			res = R.drawable.p6_icon;
    			break;
    	}
    	return res;
    }
    
	public int getIconForPlace(int placenum){
    	int res = R.drawable.btn_none;
    	switch (placenum){
    		case 0:
    			res = R.drawable.pl1_icon;
    			break;
    		case 1:
    			res = R.drawable.pl2_icon;
    			break;
    		case 2:
    			res = R.drawable.pl3_icon;
    			break;
    		case 3:
    			res = R.drawable.pl4_icon;
    			break;
    		case 4:
    			res = R.drawable.pl5_icon;
    			break;
    		case 5:
    			res = R.drawable.pl6_icon;
    			break;
    		case 6:
    			res = R.drawable.pl7_icon;
    			break;
    		case 7:
    			res = R.drawable.pl8_icon;
    			break;
    		case 8:
    			res = R.drawable.pl9_icon;
    			break;
    	}
    	return res;
    }
    
    public int getIconForWeapon(int weaponnum){
    	int res = R.drawable.btn_none;
    	switch (weaponnum){
    		case 0:
    			res = R.drawable.w1_icon;
    			break;
    		case 1:
    			res = R.drawable.w2_icon;
    			break;
    		case 2:
    			res = R.drawable.w3_icon;
    			break;
    		case 3:
    			res = R.drawable.w4_icon;
    			break;
    		case 4:
    			res = R.drawable.w5_icon;
    			break;
    		case 5:
    			res = R.drawable.w6_icon;
    			break;
    		case 6:
    			res = R.drawable.w7_icon;
    			break;
    		case 7:
    			res = R.drawable.w8_icon;
    			break;
    		case 8:
    			res = R.drawable.w9_icon;
    			break;
    	}
    	return res;
    }


}
