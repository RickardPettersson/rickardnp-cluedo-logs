package com.bosicc.cluedo.utils;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.bosicc.cluedo.R;
import com.bosicc.cluedo.pojo.GamePOJO;
import com.bosicc.cluedo.pojo.GamePOJO.CardType;
import com.bosicc.cluedo.pojo.GamePOJO.ShowModeType;
import com.bosicc.cluedo.pojo.PMovePOJO;
import com.bosicc.cluedo.pojo.PlayerPOJO;

public class Utils {
	
	//private static String TAG = "Utils";
	
	private Context ctx;
    private GamePOJO game;
	
	public Utils(Context context, GamePOJO game){
		ctx = context;
		this.game = game;
	}
	
	public enum GemeType {
		RuRaskroiSekret,	//		<item>Cluedo: Раскрой все секреты</item>
		RuRoadGame,			//		<item>Cluedo: Дорожная игра</item>
		DiscoverTheSecrets,	//      <item>Clue: Discover the Secrets</item>
		Entdecken,			//		<item>Cluedo: Entdecken sie die Geheimnisse</item>
		AfslorHemmelighederne,//	<item>Cluedo: Afslør hemmelighederne</item>
		OdkryjNT,			//      <item>Cluedo: Odkryj najciemniejsze tajemnice</item>
		SecretinParis,		//        <item>Clue: Secrets in Paris</item>
		The24,				//        <item>Clue: The 24 Edition</item>
		TheOffice,			//        <item>Clue: The Office</item>
		EnglishOriginal,	//        <item>Clue: English original</item>
        EnglishRussianVer,	//        <item>Clue: English original (Русская версия)</item>
		LimitedGift,		//        <item>Clue: Limited Gift (50th Anniversary) Edition</item> <!-- lim_gif -->
		Simpsons,			//        <item>Clue: The Simpsons</item>
		Simpsons3D,			//        <item>Clue: The Simpsons 3rd edition</item>
		HarryPotrer,		//        <item>Clue: The Harry Potter</item>
		Scooby,				//        <item>Clue: The Scooby Doo</item>
		LeedsCentenary,		//        <item>Clue: Leeds Centenary</item>
		SuperSleuth,		//        <item>Cluedo: Super Sleuth</item> <!-- Place: = lim_gif = 50th -->
		MasterDetective,	//        <item>Clue: Master Detective</item>
		OriginalVCR,		//        <item>Clue: Original VCR Game</item>
		MurderinDisguise,	//        <item>Clue II: Murder in Disguise</item>
		Passport2Murder,	//        <item>Cluedo: Passport to Murder</item>
		DD,					//        <item>Clue: Dungeons and Dragons Edition</item>
		Seinfeld,			//        <item>Clue: The Seinfeld Edition</item>						
		MysteryAtSea,		//        <item>Clue: Mystery at Sea</item>		
		SuperChalange,		//        <item>Cluedo: Super Challenge</item>		
		SFX;				//		  <item>Clue: SFX/FX</item>

		public static GemeType findByOrdinal(int ordinal) {
			for (GemeType item : values()) {
				if (item.ordinal() == ordinal) {
					return item;
				}
			}
			return RuRaskroiSekret;
		}	
		
	}
		
	/**
	 * Fill new game data in Game class from resourses
	 * @param GameNum
	 */
	public void UpdateGameDataList(int GameNum){
		
    	Resources r = ctx.getResources();
		int [] colorlist = r.getIntArray(R.array.colors_default);
		GemeType type = GemeType.findByOrdinal(GameNum); // In list begin with 0
		//Log.i(TAG,"GameNum="+GameNum+" Type=" + type);
		switch(type){
		case RuRaskroiSekret: // Russian version
			game.mPeople = r.getStringArray(R.array.people_ru);
			game.mPlace = r.getStringArray(R.array.place_ru);
			game.mWeapon = r.getStringArray(R.array.weapon_ru);
			break;
		case RuRoadGame: // Russian version road game
			game.mPeople = r.getStringArray(R.array.people_ru);
			game.mPlace = r.getStringArray(R.array.place_ru_road);
			game.mWeapon = r.getStringArray(R.array.weapon_ru_road);
			break;
		case DiscoverTheSecrets:// Clue: Discover the Secrets
			game.mPeople = r.getStringArray(R.array.people_discover);
			game.mPlace = r.getStringArray(R.array.place_discover);
			game.mWeapon = r.getStringArray(R.array.weapon_discover);
			break;	
		case Entdecken: // Germany
			game.mPeople = r.getStringArray(R.array.people_Entdecken);
			game.mPlace = r.getStringArray(R.array.place_Entdecken);
			game.mWeapon = r.getStringArray(R.array.weapon_Entdecken);
			break;
		case AfslorHemmelighederne: // Danish
			game.mPeople = r.getStringArray(R.array.people_AfslorHemmelighederne);
			game.mPlace = r.getStringArray(R.array.place_AfslorHemmelighederne);
			game.mWeapon = r.getStringArray(R.array.weapon_AfslorHemmelighederne);
			break;	
		case OdkryjNT:// Polish
			game.mPeople = r.getStringArray(R.array.people_OdkryjNT);
			game.mPlace = r.getStringArray(R.array.place_OdkryjNT);
			game.mWeapon = r.getStringArray(R.array.weapon_OdkryjNT);
			break;	
		case SecretinParis:// Cluedo in Paris
			game.mPeople = r.getStringArray(R.array.people_paris);
			game.mPlace = r.getStringArray(R.array.place_paris);
			game.mWeapon = r.getStringArray(R.array.weapon_paris);
			break;
		case The24:// Clue the 24
			game.mPeople = r.getStringArray(R.array.people_24);
			game.mPlace = r.getStringArray(R.array.place_24);
			game.mWeapon = r.getStringArray(R.array.weapon_24);
			break;
		case TheOffice:// Clue the office
			game.mPeople = r.getStringArray(R.array.people_office);
			game.mPlace = r.getStringArray(R.array.place_office);
			game.mWeapon = r.getStringArray(R.array.weapon_office);
			break;
		case EnglishOriginal:// Clue English (original)
			game.mPeople = r.getStringArray(R.array.people_original);
			game.mPlace = r.getStringArray(R.array.place_original);
			game.mWeapon = r.getStringArray(R.array.weapon_original);
			break;
        case EnglishRussianVer:// Clue English Russian (russian version)
			game.mPeople = r.getStringArray(R.array.people_original_rus);
			game.mPlace = r.getStringArray(R.array.place_original_rus);
			game.mWeapon = r.getStringArray(R.array.weapon_original_rus);
			break;
		case LimitedGift:// Clue Limited edition
			game.mPeople = r.getStringArray(R.array.people_lim_gif);
			game.mPlace = r.getStringArray(R.array.place_lim_gif);
			game.mWeapon = r.getStringArray(R.array.weapon_lim_gif);
		case Simpsons:// Clue the Simpsons
			game.mPeople = r.getStringArray(R.array.people_simpsons);
			game.mPlace = r.getStringArray(R.array.place_simpsons);
			game.mWeapon = r.getStringArray(R.array.weapon_simpsons);
			break;
		case Simpsons3D:// Clue the Simpsons 3rd edition
			game.mPeople = r.getStringArray(R.array.people_simpsons_3rd);
			game.mPlace = r.getStringArray(R.array.place_simpsons_3rd);
			game.mWeapon = r.getStringArray(R.array.weapon_simpsons_3rd);
			break;
		case HarryPotrer:// Clue the HarryPoter
			game.mPeople = r.getStringArray(R.array.people_potter);
			game.mPlace = r.getStringArray(R.array.place_potter);
			game.mWeapon = r.getStringArray(R.array.weapon_potter);
			break;
		case Scooby:// Clue the Scooby Doo
			game.mPeople = r.getStringArray(R.array.people_scooby);
			game.mPlace = r.getStringArray(R.array.place_scooby);
			game.mWeapon = r.getStringArray(R.array.weapon_scooby);
			break;
		case LeedsCentenary:// Clue Leeds Centenary
			game.mPeople = r.getStringArray(R.array.people_leeds);
			game.mPlace = r.getStringArray(R.array.place_leeds);
			game.mWeapon = r.getStringArray(R.array.weapon_leeds);
			break;
		case SuperSleuth:// Cluedo Super Sleuth
			game.mPeople = r.getStringArray(R.array.people_lim_gif);// the same as lim_gif
			game.mPlace = r.getStringArray(R.array.place_lim_gif); 	// the same as lim_gif
			game.mWeapon = r.getStringArray(R.array.weapon_leeds); 	// the same as leeds
			break;
		case MasterDetective:// Clue Master Detective
			game.mPeople = r.getStringArray(R.array.people_master);
			game.mPlace = r.getStringArray(R.array.place_master);
			game.mWeapon = r.getStringArray(R.array.weapon_master);
			colorlist = r.getIntArray(R.array.colors_master);
			break;	
		case OriginalVCR:// Clue VCR 
			game.mPeople = r.getStringArray(R.array.people_orig_vcr_murder);
			game.mPlace = r.getStringArray(R.array.place_orig_vcr);
			game.mWeapon = r.getStringArray(R.array.weapon_orig_vcr_murder);
			colorlist = r.getIntArray(R.array.colors_cvr);
			break;	
		case MurderinDisguise:// Clue Murder in Disguise
			game.mPeople = r.getStringArray(R.array.people_orig_vcr_murder);
			game.mPlace = r.getStringArray(R.array.place_murder);
			game.mWeapon = r.getStringArray(R.array.weapon_orig_vcr_murder);
			colorlist = r.getIntArray(R.array.colors_cvr);
			break;	
		case Passport2Murder:// Cluedo Passport to Murder
			game.mPeople = r.getStringArray(R.array.people_passport);
			game.mPlace = r.getStringArray(R.array.place_passport);
			game.mWeapon = r.getStringArray(R.array.weapon_passport);
			colorlist = r.getIntArray(R.array.colors_pasport);
			break;	
		case DD:// Clue: Dungeons and Dragons Edition
			game.mPeople = r.getStringArray(R.array.people_dd);
			game.mPlace = r.getStringArray(R.array.place_dd);
			game.mWeapon = r.getStringArray(R.array.weapon_dd);
			break;	
		case Seinfeld:// Clue: The Seinfeld Edition
			game.mPeople = r.getStringArray(R.array.people_seinfeld);
			game.mPlace = r.getStringArray(R.array.place_seinfeld);
			game.mWeapon = r.getStringArray(R.array.weapon_seinfeld);
			break;	
		case MysteryAtSea:// Clue: Mystery at Sea
			game.mPeople = r.getStringArray(R.array.people_sea_card);
			game.mPlace = r.getStringArray(R.array.place_sea_card);
			game.mWeapon = r.getStringArray(R.array.weapon_sea_card);
			colorlist = r.getIntArray(R.array.colors_sea_card);
			break;	
		case SuperChalange:// Cluedo: Super Challenge
			game.mPeople = r.getStringArray(R.array.people_challenge);
			game.mPlace = r.getStringArray(R.array.place_challenge);
			game.mWeapon = r.getStringArray(R.array.weapon_challenge);
			colorlist = r.getIntArray(R.array.colors_pasport); // same colors
			break;
		case SFX:// Cluedo: SFX/FX
			game.mPeople = r.getStringArray(R.array.people_fx);
			game.mPlace = r.getStringArray(R.array.place_fx);
			game.mWeapon = r.getStringArray(R.array.weapon_fx);
			colorlist = r.getIntArray(R.array.colors_fx); 
			break;
		}
				
		//Number of players in game
		game.playernum = game.mPeople.length;
		
		//Calculate number of cards in game 
		game.cardnum = game.mPeople.length + game.mPlace.length + game.mWeapon.length;
		
		//Create new Array for cards
		game.mCards = new CardType[game.cardnum][game.playernum]; 
		
		//Fill with default data 
		for (int i=0;i<game.playernum;i++){
			for (int j=0; j<game.cardnum; j++){
				game.mCards[j][i] = CardType.DEFAULT;
			}
		}

		//Reset Players list 
		game.mPlayers.removeAll(game.mPlayers);
		for (int i=0;i<game.mPeople.length;i++){
			PlayerPOJO item = new PlayerPOJO();
			item.setCardName(game.mPeople[i]);
			item.setLabel("",game.mPeople[i]);
			item.setNumber(i);
			item.setColor(colorlist[i]);
			item.inGame(true);
			game.mPlayers.add(item);
		}
    }
	

	public CardType[][] getCardsData(){
		return game.mCards;
	}
	
	public void setCardsData(int pos, int num, CardType type){
		game.mCards[pos][num] = type;
	}
	
	public void setRowNoData(int pos){
		for (int i=0;i<game.playernum;i++){
    		setCardsData(pos,i,CardType.NO);
    	}
	}
	
	public void setTypeinRowNoData(int pos, int num, CardType type){
		setRowNoData(pos);
		setCardsData(pos, num, type);
	}
	
	public void setColumnNoData(int raw){
		for (int i=0;i<game.cardnum;i++){
    		setCardsData(i,raw,CardType.NO);
    	}
	}
	
//	public boolean isStarted(){
//		return game.isCreated;
//	}
	
	public void setCreatedGame(boolean is){
		game.isCreated = is;
	}
	
	public void setPlayerNoColumn(int player){
		for (int i=0; i<game.cardnum; i++){
			game.mCards[i][player] = CardType.NO;
		}
		
	}
	
	public void setNumberOfPlayers(int num){
		game.NumberOfPlayers = num;
	}
	
	public int getNumberOfPlayers(){
		return game.NumberOfPlayers;
	}
	
	public void setYourPlayer(int num){
		game.YourPlayer = num;
	}
	
	public int getYourPlayer(){
		return game.YourPlayer;
	}

	public void reset(){
		// Clear logs items
		game.mLogsList.clear();
		// Clear tabele
		for (int j=0; j<game.playernum; j++){
			for (int i=0; i<game.cardnum; i++){
				game.mCards[i][j] = CardType.DEFAULT;
			}
			game.mPlayers.clear();
		}
		
		// Set Flag that game not started
		setCreatedGame(false);
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
				
			case PEOPLE:{
				for (PMovePOJO item: game.mLogsList) {
					if (item.getSlyx()[0]  == person) {
						mNewList.add(item);
					}
				}
			}
				break;
				
			case PLACE:{
				for (PMovePOJO item: game.mLogsList) {
					if (item.getSlyx()[1]  == person) {
						mNewList.add(item);
					}
				}
			}
				break;
				
			case WEAPON:{
				for (PMovePOJO item: game.mLogsList) {
					if (item.getSlyx()[2]  == person) {
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
            case ASK:
    			res = R.drawable.btn_ask;
    			break;
    	}
    	return res;
    }

}
