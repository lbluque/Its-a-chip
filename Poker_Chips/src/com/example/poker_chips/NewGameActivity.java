package com.example.poker_chips;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import java.lang.*;
import java.util.ArrayList;
import java.util.List;

public class NewGameActivity extends ActionBarActivity {
	
	
	//initializes variables to be used throughout activity
	Player[] game_players = null;
	int Pot = 0;
	//loads number of players in the game
	int num_players = 0;
	int current_bet = 0;
	Spinner spin1;
	SeekBar s1;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//reads in the number of players that was selected from last activity
		String title= getIntent().getExtras().getString("Title");
		final int num_players = Integer.parseInt(title);
		
		//loads number of players into the num_players variable
		set_players(num_players);

		//creates an array of game players
		final Player[] game_players = new Player[num_players];
		//loads the array of game players
		for(int i = 0; i<num_players;i++){
			game_players[i] = new Player(1000,i+1);
		}
		
		//loads into game_players array
		set_game_players(game_players);
		
		//initialize player 1 to start
		game_players[0].is_turn = true;

		//switch statement for different views. Each number of players has a different
		//xml file
		switch(num_players){
			case 2: 
				setContentView(R.layout.activity_new_game);
				break;
			case 3:
				setContentView(R.layout.activity_new_game3);
				break;
			case 4:
				setContentView(R.layout.activity_new_game4);
				break;
			case 5:
				setContentView(R.layout.activity_new_game5);
				break;
			case 6:
				setContentView(R.layout.activity_new_game6);
				break;
			case 7:
				setContentView(R.layout.activity_new_game7);
				break;
			case 8:
				setContentView(R.layout.activity_new_game8);
				break;
		}
		
		//sets player 1 to have the active chip
		ImageView v1 = (ImageView) findViewById(R.id.image1);
		v1.setImageDrawable(getResources().getDrawable(R.drawable.turn));

		
		//allows for betting by creating a seekbar
		//value should go from what has been bet to whatever the current players pot is
		SeekBar s1 = (SeekBar)findViewById(R.id.seekbar); 
		//textview to display proper amount to be bet
	    final TextView seekBarValue = (TextView)findViewById(R.id.bet_amount); 
	    
	    
	    //listener for any changes in the seekbar
	    s1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){
	    	@Override 
	    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
	    		//loads in whos turn it currently is
	    		Player current_player = whos_turn();
	    		//stores the progress of the seekbar as a double
	    	    double val = progress;
	    	    
	    	    //sets the appropriate value to the seekbar textview by calculating what the 
	    	    //current player has bet, and what has to be bet.
	    	    //the value goes from the minimum they have to bet to their max chip value
	    	    if(current_bet<current_player.get_chips()){
	    	    	seekBarValue.setText(String.valueOf(Math.round(val/100 * (current_player.get_chips()-(current_bet - current_player.been_bet))+current_bet-current_player.been_bet)));
	    	    }
	    	    else{
	    	    	seekBarValue.setText(String.valueOf(Math.round(current_player.get_chips())));
	    	    }
	    	    
	    	    
	    	} 
	    	
	    	//did not implement these aspects
	    	@Override 
    	   public void onStartTrackingTouch(SeekBar seekBar) { 
    	    // TODO Auto-generated method stub 
    	   } 

    	   @Override 
    	   public void onStopTrackingTouch(SeekBar seekBar) { 
    	    // TODO Auto-generated method stub 
    	   } 
	    });
	    
	    
	    
	    
	    //loads in the betting button
	    Button b1 = (Button)findViewById(R.id.bet_button);
	    
	    //sets up a click listener for the betting button
	    b1.setOnClickListener(new View.OnClickListener() {

	        @Override
	        public void onClick(View v) {
	        	
	        	//load in the total in the middle pot
	    		TextView t1 = (TextView)findViewById(R.id.pot_amount);
	    		//load in what this player has bet so far (starts at 0)
	    		TextView sval1 = (TextView)findViewById(R.id.bet_amount); 
	    		//loads in the amount from the seekbar textview as bet_amount
	    		int bet_amount =(int)Float.parseFloat(sval1.getText().toString());
	    		//gets who bet this turn
	    		Player current_player = whos_turn();
	    		//make that player bet the selected amount
	    		current_player.bet(bet_amount);
	    		
	    		//determines which chip pile to update by getting current players number
	    		int play_num = current_player.player_number;
	    		ImageView v1 = null;
	    		switch(play_num){
	    			case 1:{
	    				TextView temp = (TextView)findViewById(R.id.chipsP1);
	    				temp.setText(String.valueOf(Math.round(current_player.get_chips()))); 
    					v1 = (ImageView) findViewById(R.id.image1_below);
	    				break;
	    			}
	    			case 2:{
	    				TextView temp = (TextView)findViewById(R.id.chipsP2);
	    				temp.setText(String.valueOf(Math.round(current_player.get_chips()))); 
	    				v1 = (ImageView) findViewById(R.id.image2_below);
	    				break;
	    			}
	    			case 3:{
	    				TextView temp = (TextView)findViewById(R.id.chipsP3);
	    				temp.setText(String.valueOf(Math.round(current_player.get_chips()))); 
	    				v1 = (ImageView) findViewById(R.id.image3_below);
	    				break;
	    			}
	    			case 4:{
	    				TextView temp = (TextView)findViewById(R.id.chipsP4);
	    				temp.setText(String.valueOf(Math.round(current_player.get_chips())));
	    				v1 = (ImageView) findViewById(R.id.image4_below);
	    				break;
	    			}
	    			case 5:{
	    				TextView temp = (TextView)findViewById(R.id.chipsP5);
	    				temp.setText(String.valueOf(Math.round(current_player.get_chips()))); 
	    				v1 = (ImageView) findViewById(R.id.image5_below);
	    				break;
	    			}
	    			case 6:{
	    				TextView temp = (TextView)findViewById(R.id.chipsP6);
	    				temp.setText(String.valueOf(Math.round(current_player.get_chips())));
	    				v1 = (ImageView) findViewById(R.id.image6_below);
	    				break;
	    			}
	    			case 7:{
	    				TextView temp = (TextView)findViewById(R.id.chipsP7);
	    				temp.setText(String.valueOf(Math.round(current_player.get_chips()))); 
	    				v1 = (ImageView) findViewById(R.id.image7_below);
	    				break;
	    			}
	    			case 8:{
	    				TextView temp = (TextView)findViewById(R.id.chipsP8);
	    				temp.setText(String.valueOf(Math.round(current_player.get_chips())));
	    				v1 = (ImageView) findViewById(R.id.image8_below);
	    				break;
	    			}
	    			
	    		}
	    		
	    		//if the betting players chips have dropped below a given threshold
	    		//adjust the back pile of chips
	    		//the front pile is the big chip used as the indicator of whos turn it is
	    		//the front pile goes away in a seperate function when they player is out of the game
	    		Resources res = getResources();
				if(current_player.get_chips()>750){
					v1.setImageDrawable(res.getDrawable(R.drawable.dchip_quadruple));
				}
				else if(current_player.get_chips()>500){
					v1.setImageDrawable(res.getDrawable(R.drawable.dchip_triple));
				}
				else if(current_player.get_chips()>250){
					v1.setImageDrawable(res.getDrawable(R.drawable.dchip_double));
				}
				else{
					v1.setVisibility(View.INVISIBLE);
				}
	    		
				//adds to what the current player has bet
	    		current_player.been_bet += bet_amount;

	    		//checks to see if a player has raised and adjust if put is good
	    		//if a player raises every other player gets there is_good variable 
	    		//set to false because they need to call the max bet value
	    		if(bet_amount>current_bet){
	    			current_bet = bet_amount;
		    	    for(int i = 0; i<num_players; i++){
		    	    	 game_players[i].is_good = false;
		    	    }
		    	    current_player.is_good = true;
	    		}
	    		//check to see if player has called
	    		//if player calls, they are good for this current pot
	    		else if(bet_amount==current_bet){
	    			current_player.is_good = true;
	    		}
	    		
	    		//updates the pot
	    		int new_pot = update_pot(bet_amount);
	    		t1.setText("$" + Float.toString(new_pot));
	    		


	    		//update who's turn it is
	    		update_turn();
	    		//gets whos turn it is after the update
	    		Player new_player = whos_turn();
	    		//reset seekbar
				SeekBar s1 = (SeekBar)findViewById(R.id.seekbar); 
	    		s1.setProgress(0);
	    		
	    		//set the appropriate text value
	    		//seekBarValue.setText(String.valueOf(Math.round(current_bet-new_player.been_bet))); 
	    		
	    		//TextView seekBarValue = (TextView)findViewById(R.id.bet_amount);
	    		
	    		
	    		}
	        
	    });
	    
	    
	    //loads in win_button
	    Button win_button = (Button)findViewById(R.id.Win);
	    //click listener for win_button
	    win_button.setOnClickListener(new View.OnClickListener() {

	        @Override
	        public void onClick(View v) {
	        	Spinner Winner=(Spinner) findViewById(R.id.Winner);
	    		//reads in the value of the spinner
	    		int winning_id =Integer.parseInt(Winner.getSelectedItem().toString());
	    		//loads in the winning player class
	    		Player winning_player = game_players[winning_id-1];
	    		//adds to winning player pot
	    		winning_player.win(Pot);
	    		//updates players chip amount
	    		ImageView v1 = null;
	    		//switch statement to determine whos pot to update and chip pictures to update
	    		switch(winning_id){
	    			case 1:{
	    				TextView t = (TextView)findViewById(R.id.chipsP1); 
	    				t.setText(String.valueOf(winning_player.get_chips()));
	    				v1 = (ImageView) findViewById(R.id.image1_below);
	    				break;
	    			}
	    			case 2:{
	    				TextView t = (TextView)findViewById(R.id.chipsP2); 
	    				t.setText(String.valueOf(winning_player.get_chips()));
	    				v1 = (ImageView) findViewById(R.id.image2_below);
	    				break;
	    			}
	    			case 3:{
	    				TextView t = (TextView)findViewById(R.id.chipsP3); 
	    				t.setText(String.valueOf(winning_player.get_chips()));
	    				v1 = (ImageView) findViewById(R.id.image3_below);
	    				break;
	    			}
	    			case 4:{
	    				TextView t = (TextView)findViewById(R.id.chipsP4); 
	    				t.setText(String.valueOf(winning_player.get_chips()));
	    				v1 = (ImageView) findViewById(R.id.image4_below);
	    				break;
	    			}
	    			case 5:{
	    				TextView t = (TextView)findViewById(R.id.chipsP5); 
	    				t.setText(String.valueOf(winning_player.get_chips()));
	    				v1 = (ImageView) findViewById(R.id.image5_below);
	    				break;
	    			}
	    			case 6:{
	    				TextView t = (TextView)findViewById(R.id.chipsP6); 
	    				t.setText(String.valueOf(winning_player.get_chips()));
	    				v1 = (ImageView) findViewById(R.id.image6_below);
	    				break;
	    			}
	    			case 7:{
	    				TextView t = (TextView)findViewById(R.id.chipsP7); 
	    				t.setText(String.valueOf(winning_player.get_chips()));
	    				v1 = (ImageView) findViewById(R.id.image7_below);
	    				break;
	    			}
	    			case 8:{
	    				TextView t = (TextView)findViewById(R.id.chipsP8); 
	    				t.setText(String.valueOf(winning_player.get_chips()));
	    				v1 = (ImageView) findViewById(R.id.image8_below);
	    				break;
	    			}
	    		}
	    		//updates players background chip picture.
	    		//sets it to visible because it could be invisible before hand
	    		Resources res = getResources();
				if(winning_player.get_chips()>750){
					v1.setVisibility(View.VISIBLE);
					v1.setImageDrawable(res.getDrawable(R.drawable.dchip_quadruple));
				}
				else if(winning_player.get_chips()>500){
					v1.setVisibility(View.VISIBLE);
					v1.setImageDrawable(res.getDrawable(R.drawable.dchip_triple));
				}
				else if(winning_player.get_chips()>250){
					v1.setVisibility(View.VISIBLE);
					v1.setImageDrawable(res.getDrawable(R.drawable.dchip_double));
				}
				else{
					v1.setVisibility(View.INVISIBLE);
				}
				//calls end round function to set up the next round!
	    		end_round();
	    		
	    		
	        }
	        });
	    

	    Button b2 = (Button)findViewById(R.id.fold_button);
	    //fold function
	    b2.setOnClickListener(new View.OnClickListener() {
	    	//sets player to be out of this round of betting 
	        @Override
	        public void onClick(View v) {
	        	Player current_player = whos_turn();
	        	current_player.in_turn = false;
	        	update_turn();
	        }
	    });
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public int update_pot(int amount){
		//checks if tried to bet a negative amount
		if(amount<0)
		{
			amount = 0;
		}
//		
		Player current = whos_turn();
		//determines if the player tried to bet more than they should
		if(amount>current.been_bet){
			amount = current.get_chips();
		}
		Pot = Pot+amount;
		return Pot;
	}
	
	//sets in_players to be the game_players array variable used throughout
	public void set_game_players(Player[] in_players){
		game_players = in_players;
	}
	
	//sets the number of players to num_players to be used throughout
	public void set_players(int in_players){
		num_players = in_players;
	}
	
	//loops over all players and determines whos turn it is
	//returns the player whos turn it is
	public Player whos_turn(){
		Player current_player = null;
	    for(int i = 0; i<num_players; i++){
	    	if(game_players[i].is_turn){
	    		current_player = game_players[i];
	    		
	    	}
	    }
	    return current_player;
	}
	
	//resets variables after a round to start a new round of betting after a player has won
	void end_round(){
		//clear central pot
		Pot = 0;
		//clear bet tracker 
		current_bet = 0;
		
		//sets the textview to show $0 
		TextView t1 = (TextView)findViewById(R.id.pot_amount);
		t1.setText("$0");
		
		
		ImageView v2 = null;
		TextView v3 = null;
		//loops over every player
		for(int i = 0; i<num_players; i++){
	
			//determines if they have no chips left
			//if they do remove them from the game and set their images and 
			//textview's to INVISIBLE
			if(game_players[i].get_chips()<=0){
				//sets in_game to false to permanently remove the player from the game
				game_players[i].in_game = false;
				//determines which images to make invisible
				switch (i+1){
					case 1:{
						v2 = (ImageView) findViewById(R.id.image1);
						v3 = (TextView) findViewById(R.id.chipsP1);
						break;
					}
					case 2:{
						v2 = (ImageView) findViewById(R.id.image2);
						v3 = (TextView) findViewById(R.id.chipsP2);
						break;
					}
					case 3:{
						v2 = (ImageView) findViewById(R.id.image3);
						v3 = (TextView) findViewById(R.id.chipsP3);
						break;
					}
					case 4:{
						v2 = (ImageView) findViewById(R.id.image4);
						v3 = (TextView) findViewById(R.id.chipsP4);
						break;
					}
					case 5:{
						v2 = (ImageView) findViewById(R.id.image5);
						v3 = (TextView) findViewById(R.id.chipsP5);
						break;
					}
					case 6:{
						v2 = (ImageView) findViewById(R.id.image6);
						v3 = (TextView) findViewById(R.id.chipsP6);
						break;
					}
					case 7:{
						v2 = (ImageView) findViewById(R.id.image7);
						v3 = (TextView) findViewById(R.id.chipsP7);
						break;
					}
					case 8:{
						v2 = (ImageView) findViewById(R.id.image8);
						v3 = (TextView) findViewById(R.id.chipsP8);
						break;
					}
				
				}
				//if a player runs out of chips. Set it to be invisible
				v2.setVisibility(View.INVISIBLE);
				v3.setVisibility(View.INVISIBLE);
				
		
			}
			//if the player still has chips make sure they are in the round
			else{
			game_players[i].in_turn = true;
			}
			//set is_good function to false
			//needs to be this way to ensure proper betting
			game_players[i].is_good = false;
			//resets each players bet tracker since this round is over
			game_players[i].been_bet = 0;
		}
		
		//whos turn did we end on?
		Player ending_player = whos_turn();
		//no longer their turn
		ending_player.is_turn = false;
		
		
		
		
		//if player 1 isn't in the game. Go to the next player
		if(!game_players[0].in_game){
			int next = 0;
			boolean found_player = false;
			while(!found_player){
				next+=1;
				if(game_players[next].in_game){
					game_players[next].is_turn = true;
					found_player = true;
				}
				
			}
			//clear board
			update_board();
			
			switch(next+1){
			case 1:{
				ImageView v = (ImageView) findViewById(R.id.image1);
				Resources res = getResources();
				v.setImageDrawable(res.getDrawable(R.drawable.turn));
				break;
			}
			case 2:{
				ImageView v = (ImageView) findViewById(R.id.image2);
				Resources res = getResources();
				v.setImageDrawable(res.getDrawable(R.drawable.turn));
				break;
			}
			case 3:{
				ImageView v = (ImageView) findViewById(R.id.image3);
				Resources res = getResources();
				v.setImageDrawable(res.getDrawable(R.drawable.turn));
				break;
			}
			case 4:{
				ImageView v = (ImageView) findViewById(R.id.image4);
				Resources res = getResources();
				v.setImageDrawable(res.getDrawable(R.drawable.turn));
				break;
			}
			case 5:{
				ImageView v = (ImageView) findViewById(R.id.image5);
				Resources res = getResources();
				v.setImageDrawable(res.getDrawable(R.drawable.turn));
				break;
			}
			case 6:{
				ImageView v = (ImageView) findViewById(R.id.image6);
				Resources res = getResources();
				v.setImageDrawable(res.getDrawable(R.drawable.turn));
				break;
			}
			case 7:{
				ImageView v = (ImageView) findViewById(R.id.image7);
				Resources res = getResources();
				v.setImageDrawable(res.getDrawable(R.drawable.turn));
				break;
			}
			case 8:{
				ImageView v = (ImageView) findViewById(R.id.image8);
				Resources res = getResources();
				v.setImageDrawable(res.getDrawable(R.drawable.turn));
				break;
			}
			
				
		}
		
		
		}
		else{
			game_players[0].is_turn = true;
			//update board which clears all chips to be green
			update_board();
			
			//sets correct starting player with indicator chip
			ImageView v1 = (ImageView) findViewById(R.id.image1);
			Resources res = getResources();
			v1.setImageDrawable(res.getDrawable(R.drawable.turn));
		}

		SeekBar s1 = (SeekBar)findViewById(R.id.seekbar); 
		s1.setProgress(0);
		
		TextView seekBarValue = (TextView)findViewById(R.id.bet_amount); 
		seekBarValue.setText("0");
		
		
		
		

		
		
	}
	
	//sets every players chip color to green
	//this function is super long but is needed to ensure there aren't
	//two players with orange chips on the board
	public void update_board(){
		switch(num_players){
			case 2:{
				ImageView v1 = (ImageView) findViewById(R.id.image1);
				Resources res = getResources();
				v1.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v2 = (ImageView) findViewById(R.id.image2);
				//Resources res = getResources();
				v2.setImageDrawable(res.getDrawable(R.drawable.dchip));
				break;
			}
			
			case 3:{
				ImageView v1 = (ImageView) findViewById(R.id.image1);
				Resources res = getResources();
				v1.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v2 = (ImageView) findViewById(R.id.image2);
				//Resources res = getResources();
				v2.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v3 = (ImageView) findViewById(R.id.image3);
				//Resources res = getResources();
				v3.setImageDrawable(res.getDrawable(R.drawable.dchip));	
				break;
			}
			case 4:{
				ImageView v1 = (ImageView) findViewById(R.id.image1);
				Resources res = getResources();
				v1.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v2 = (ImageView) findViewById(R.id.image2);
				//Resources res = getResources();
				v2.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v3 = (ImageView) findViewById(R.id.image3);
				//Resources res = getResources();
				v3.setImageDrawable(res.getDrawable(R.drawable.dchip));	
				
				ImageView v4 = (ImageView) findViewById(R.id.image4);
				//Resources res = getResources();
				v4.setImageDrawable(res.getDrawable(R.drawable.dchip));
				break;
			}
			case 5:{
				
				ImageView v1 = (ImageView) findViewById(R.id.image1);
				Resources res = getResources();
				v1.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v2 = (ImageView) findViewById(R.id.image2);
				//Resources res = getResources();
				v2.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v3 = (ImageView) findViewById(R.id.image3);
				//Resources res = getResources();
				v3.setImageDrawable(res.getDrawable(R.drawable.dchip));	
				
				ImageView v4 = (ImageView) findViewById(R.id.image4);
				//Resources res = getResources();
				v4.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v5 = (ImageView) findViewById(R.id.image5);
				//Resources res = getResources();
				v5.setImageDrawable(res.getDrawable(R.drawable.dchip));
				break;
				
			}
			
			case 6:{
				ImageView v1 = (ImageView) findViewById(R.id.image1);
				Resources res = getResources();
				v1.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v2 = (ImageView) findViewById(R.id.image2);
				//Resources res = getResources();
				v2.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v3 = (ImageView) findViewById(R.id.image3);
				//Resources res = getResources();
				v3.setImageDrawable(res.getDrawable(R.drawable.dchip));	
				
				ImageView v4 = (ImageView) findViewById(R.id.image4);
				//Resources res = getResources();
				v4.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v5 = (ImageView) findViewById(R.id.image5);
				//Resources res = getResources();
				v5.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v6 = (ImageView) findViewById(R.id.image6);
				//Resources res = getResources();
				v6.setImageDrawable(res.getDrawable(R.drawable.dchip));
				break;
			}
			
			case 7:{
				ImageView v1 = (ImageView) findViewById(R.id.image1);
				Resources res = getResources();
				v1.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v2 = (ImageView) findViewById(R.id.image2);
				//Resources res = getResources();
				v2.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v3 = (ImageView) findViewById(R.id.image3);
				//Resources res = getResources();
				v3.setImageDrawable(res.getDrawable(R.drawable.dchip));	
				
				ImageView v4 = (ImageView) findViewById(R.id.image4);
				//Resources res = getResources();
				v4.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v5 = (ImageView) findViewById(R.id.image5);
				//Resources res = getResources();
				v5.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v6 = (ImageView) findViewById(R.id.image6);
				//Resources res = getResources();
				v6.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v7 = (ImageView) findViewById(R.id.image7);
				//Resources res = getResources();
				v7.setImageDrawable(res.getDrawable(R.drawable.dchip));
				break;
			}
			case 8:{
				ImageView v1 = (ImageView) findViewById(R.id.image1);
				Resources res = getResources();
				v1.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v2 = (ImageView) findViewById(R.id.image2);
				//Resources res = getResources();
				v2.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v3 = (ImageView) findViewById(R.id.image3);
				//Resources res = getResources();
				v3.setImageDrawable(res.getDrawable(R.drawable.dchip));	
				
				ImageView v4 = (ImageView) findViewById(R.id.image4);
				//Resources res = getResources();
				v4.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v5 = (ImageView) findViewById(R.id.image5);
				//Resources res = getResources();
				v5.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v6 = (ImageView) findViewById(R.id.image6);
				//Resources res = getResources();
				v6.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v7 = (ImageView) findViewById(R.id.image7);
				//Resources res = getResources();
				v7.setImageDrawable(res.getDrawable(R.drawable.dchip));
				
				ImageView v8 = (ImageView) findViewById(R.id.image8);
				//Resources res = getResources();
				v8.setImageDrawable(res.getDrawable(R.drawable.dchip));
				break;
			}
		}
	}
	
	
	
	//returns a player with a given id
	public Player find_player(int id){
		Player return_player = null;
		switch(id){
			case 1:{
				return_player = game_players[0];
			}
			case 2:{
				return_player = game_players[1];
			}
			case 3:{
				return_player = game_players[2];
			}
			case 4:{
				return_player = game_players[3];
			}
			case 5:{
				return_player = game_players[4];
			}
			case 6:{
				return_player = game_players[5];
			}
			case 7:{
				return_player = game_players[6];
			}
			case 8:{
				return_player = game_players[7];
			}
		}
		return return_player;
	}
	
	//checks if pot is good
	//if all players have bet the required amount 
	//it returns true
	public boolean pot_good(){
		boolean return_value =true;
	    for(int i = 0; i<num_players; i++){
	    	 if(game_players[i].is_good==false){
	    		 return_value = false;
	    	 }
	    }
	    return return_value;
	}

	public void update_turn(){
		//sets current players turn to be false
		Player current = whos_turn();
		int next_num = current.player_number;
		current.is_turn = false;
		//checks if pot is good
		//if pot is good it resets to first player
		if(pot_good()){
			
			int current_player = current.player_number;
			//reset current bet 
			current_bet = 0;
			//reset to start new betting round
			//set players been bet values to 0
			//just re-initializing each player for the next round
		    for(int i = 0; i<num_players; i++){
		    	game_players[i].is_good=false;
		    	game_players[i].been_bet = 0;
		    }
		    //reset seekbar
			SeekBar s1 = (SeekBar)findViewById(R.id.seekbar); 
    		s1.setProgress(0);
		
    		//clears the board to set all players to have green chips
    		update_board();

			//restart at player 1
			ImageView v1 = (ImageView) findViewById(R.id.image1);
			Resources res = getResources();
			v1.setImageDrawable(res.getDrawable(R.drawable.turn));
			game_players[0].is_turn = true;
			
			TextView seekBarValue = (TextView)findViewById(R.id.bet_amount); 
			seekBarValue.setText("0");
			return;
		}

		//if you haven't reached the end of a cycle
		if(next_num<(num_players)){

			//checks if player is not in turn or game
	    		if((!game_players[next_num].in_turn) || (!game_players[next_num].in_game)){
	    			game_players[next_num].is_turn = false;
					int next = next_num+1;
					boolean found_player = false;
					//searches for next available player
					while(!found_player){
						if(game_players[next].in_turn){
							game_players[next].is_turn = true;
							found_player = true;
						}
						next+=1;
						if(next>=num_players){
							next = 1;
						}
					}
					//update board to make all chips green
					update_board();
					
					//set view chip (orange chip) to be whos turn it is
					switch(next){
						case 1:{
							ImageView v1 = (ImageView) findViewById(R.id.image1);
							Resources res = getResources();
							v1.setImageDrawable(res.getDrawable(R.drawable.turn));
							break;
						}
						case 2:{
							ImageView v2 = (ImageView) findViewById(R.id.image2);
							Resources res = getResources();
							v2.setImageDrawable(res.getDrawable(R.drawable.turn));
							break;
						}
						case 3:{
							ImageView v3 = (ImageView) findViewById(R.id.image3);
							Resources res = getResources();
							v3.setImageDrawable(res.getDrawable(R.drawable.turn));
							break;
						}
						case 4:{
							ImageView v4 = (ImageView) findViewById(R.id.image4);
							Resources res = getResources();
							v4.setImageDrawable(res.getDrawable(R.drawable.turn));
							break;
						}
						case 5:{
							ImageView v5 = (ImageView) findViewById(R.id.image5);
							Resources res = getResources();
							v5.setImageDrawable(res.getDrawable(R.drawable.turn));
							break;
						}
						case 6:{
							ImageView v6 = (ImageView) findViewById(R.id.image6);
							Resources res = getResources();
							v6.setImageDrawable(res.getDrawable(R.drawable.turn));
							break;
						}
						case 7:{
							ImageView v7 = (ImageView) findViewById(R.id.image7);
							Resources res = getResources();
							v7.setImageDrawable(res.getDrawable(R.drawable.turn));
							break;
						}
						case 8:{
							ImageView v8 = (ImageView) findViewById(R.id.image8);
							Resources res = getResources();
							v8.setImageDrawable(res.getDrawable(R.drawable.turn));
							break;
						}
						
							
					}
					return;
	    		}
	    		
	    		else{
					game_players[next_num].is_turn = true;
					//switch statement moves indicator(orange) chip forward
					//and resets previous players chip to be green
					switch(next_num){
						case 1:{
							ImageView v1 = (ImageView) findViewById(R.id.image2);
							Resources res = getResources();
							v1.setImageDrawable(res.getDrawable(R.drawable.turn));
							//set old chip back
							ImageView v2 = (ImageView) findViewById(R.id.image1);
							Resources res2 = getResources();
							v2.setImageDrawable(res2.getDrawable(R.drawable.dchip));
							break;
						}
						
						case 2:{

							ImageView v1 = (ImageView) findViewById(R.id.image3);
							Resources res = getResources();
							v1.setImageDrawable(res.getDrawable(R.drawable.turn));
							//set old chip back
							ImageView v2 = (ImageView) findViewById(R.id.image2);
							Resources res2 = getResources();
							v2.setImageDrawable(res2.getDrawable(R.drawable.dchip));
							break;
						}
						case 3:{
							ImageView v1 = (ImageView) findViewById(R.id.image4);
							Resources res = getResources();
							v1.setImageDrawable(res.getDrawable(R.drawable.turn));
							//set old chip back
							ImageView v2 = (ImageView) findViewById(R.id.image3);
							Resources res2 = getResources();
							v2.setImageDrawable(res2.getDrawable(R.drawable.dchip));
							break;
						}
						case 4:{
							ImageView v1 = (ImageView) findViewById(R.id.image5);
							Resources res = getResources();
							v1.setImageDrawable(res.getDrawable(R.drawable.turn));
							//set old chip back
							ImageView v2 = (ImageView) findViewById(R.id.image4);
							Resources res2 = getResources();
							v2.setImageDrawable(res2.getDrawable(R.drawable.dchip));
							break;
						}
						case 5:{
							ImageView v1 = (ImageView) findViewById(R.id.image6);
							Resources res = getResources();
							v1.setImageDrawable(res.getDrawable(R.drawable.turn));
							//set old chip back
							ImageView v2 = (ImageView) findViewById(R.id.image5);
							Resources res2 = getResources();
							v2.setImageDrawable(res2.getDrawable(R.drawable.dchip));
							break;
						}
						case 6:{
							ImageView v1 = (ImageView) findViewById(R.id.image7);
							Resources res = getResources();
							v1.setImageDrawable(res.getDrawable(R.drawable.turn));
							//set old chip back
							ImageView v2 = (ImageView) findViewById(R.id.image6);
							Resources res2 = getResources();
							v2.setImageDrawable(res2.getDrawable(R.drawable.dchip));
							break;
						}
						case 7:{
							ImageView v1 = (ImageView) findViewById(R.id.image8);
							Resources res = getResources();
							v1.setImageDrawable(res.getDrawable(R.drawable.turn));
							//set old chip back
							ImageView v2 = (ImageView) findViewById(R.id.image7);
							Resources res2 = getResources();
							v2.setImageDrawable(res2.getDrawable(R.drawable.dchip));
							break;
						}
					}
	    		}
			}
		//if we have looped around every player reset to start of player array
			else{
				//if player 1 isn't in turn. Go to the next player
				if(!game_players[0].in_turn){
					int next = 1;
					boolean found_player = false;
					while(!found_player){
						if(game_players[next].in_turn){
							game_players[next].is_turn = true;
							found_player = true;
						}
						next+=1;
					}
					update_board();
					update_turn();
				}
				else{
					//if player 1 is in the game set it to be there
					game_players[0].is_turn = true;
					//clears
					update_board();
					ImageView v1 = (ImageView) findViewById(R.id.image1);
					Resources res = getResources();
					v1.setImageDrawable(res.getDrawable(R.drawable.turn));
				}
			
		}
	}
	
	
	@Override
    //Override the default destructor to free loaded bitmaps from memory
    //This will prevent using up too much memory
    protected void onDestroy() {
        super.onDestroy();
        switch (num_players)
        {
        case 2:
        	unbindDrawables(findViewById(R.id.newGame));
        	break;
        case 3:
        	unbindDrawables(findViewById(R.id.newGame3));
        	break;
        case 4:
        	unbindDrawables(findViewById(R.id.newGame4));
        	break;
        case 5:
        	unbindDrawables(findViewById(R.id.newGame5));
        	break;
        case 6:
        	unbindDrawables(findViewById(R.id.newGame6));
        	break;
        case 7:
        	unbindDrawables(findViewById(R.id.newGame7));
        	break;
        case 8:
        	unbindDrawables(findViewById(R.id.newGame8));
        	break;
     
        }
        System.gc();
        }

        private void unbindDrawables(View view) {
            if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
            }
            if (view instanceof ViewGroup) {
                for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
                }
            }
        }

}
