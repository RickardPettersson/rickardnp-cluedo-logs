package com.bosicc.cluedo;

public class PlayerPOJO {
	
	public enum CardType {
		DEFAULT(0), NO(1), YES(2), QUESTION(3);

		private final int id;
		
		CardType(int id) {
			this.id = id;
		}

		public int getValue() {
			return id;
		}

		public static CardType findByOrdinal(int ordinal) {
			for (CardType item : values()) {
				if (item.ordinal() == ordinal) {
					return item;
				}
			}
			return DEFAULT;
		}
	}
	
	private CardType[] people = new CardType[6];// {false,false,false,false,false,false};
	private CardType[] place  = new CardType[9];// {false,false,false,false,false,false,false,false,false};
	private CardType[] weapon = new CardType[9];// {false,false,false,false,false,false,false,false,false};;
	
	public PlayerPOJO(){
		
	}
	
	// ===== Kards relatet to People ---------
	public void setPeople(int people_num, CardType type){
		this.people[people_num] = type;
	}
	
	public CardType getPeople(int people_num){
		return this.people[people_num];
	}
	
	public CardType[] getPeople(){
		return this.people;
	}
	
	// ===== Kards relatet to Place ---------
	public void setPlace(int place_num, CardType type){
		this.people[place_num] = type;
	}
	
	public CardType getPlace(int place_num){
		return this.people[place_num];
	}
	
	public CardType[] getPlace(){
		return this.place;
	}
	
	// ===== Kards relatet to Weapon ---------
	public void setWeapon(int weapon_num, CardType type){
		this.weapon[weapon_num] = type;
	}
	
	public CardType getWeapon(int weapon_num){
		return this.weapon[weapon_num];
	}
	
	public CardType[] getWeapon(){
		return this.weapon;
	}
	
	public void reset(){
		
	}
}
