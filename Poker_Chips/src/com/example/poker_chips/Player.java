package com.example.poker_chips;

public class Player {
	
	//public member variables
	public boolean is_turn;
	public int player_number;
	//used to track if player has chips and is in game
	public boolean in_game;
	//used to track if they have bet the proper amount
	public boolean is_good;
	//used to track if they have folded
	public boolean in_turn;
	//tracks what the player has bet
	public int been_bet;
	
	private int num_chips;
	
	//player constructor sets up all variables
	public Player(int load_chips, int load_number){
		num_chips = load_chips;
		player_number = load_number;
		is_turn = false;
		in_game = true;
		is_good = false;
		in_turn = true;
		been_bet = 0;
	}
	
	//betting function checks if asked to bet more than their chips, or less than 0
	public int bet(int chips){
		if(chips>=num_chips){
			num_chips = 0;
			return 0;
		}
		else if(chips<0){
		return num_chips;
		}
		else{
			num_chips = num_chips - chips;
			return num_chips;
		}
	}
	
	//called when player wins the pot
	public int win(int chips){
		num_chips = num_chips + chips;
		return num_chips;
	}
	
	//getter to access chip count
	public int get_chips(){
		return num_chips;
	}
}
