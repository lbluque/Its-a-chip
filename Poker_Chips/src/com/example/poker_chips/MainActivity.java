package com.example.poker_chips;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class MainActivity extends ActionBarActivity {

	Context context = this;

	//declare button and spinners
	Button start;
	Spinner num_players;
	
	//sets up the layout
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    	addItemsOnSpinner();
    }
    
    //adds the values to the spinner. Just 2-8
    public void addItemsOnSpinner(){
    	num_players = (Spinner) findViewById(R.id.num_players);
    	List<String> list= new ArrayList<String>();
    	list.add("2");
    	list.add("3");
    	list.add("4");
    	list.add("5");
    	list.add("6");
    	list.add("7");
    	list.add("8");
    	ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
    		android.R.layout.simple_spinner_item, list);
    	dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	num_players.setAdapter(dataAdapter);
    }
    
 
    //when start button gets pressed run this function to set up next activity
    public void StartGame(View view){
    	//creates new intent for the next activity. This is used to start a new screen
    	Intent intent = new Intent(this, NewGameActivity.class);
        //startActivity(intent);
    	//loads in the spinner
		Spinner mySpinner=(Spinner) findViewById(R.id.num_players);
		//reads in the value of the spinner
		String text = mySpinner.getSelectedItem().toString();
		//stores the value of the spinner for use in the next activity
		intent.putExtra("Title", text);
		//launches the new activity that was created on the first line
		startActivity(intent);
//		
    }

    /** Called when the user clicks the Help button to set up the help screen */
    public void Help(View view) {
    	//creates new intent for the next activity. This is used to start a new screen
    	Intent intent = new Intent(this, HelpActivity.class);
    	//launches the new activity that was created on the first line
    	startActivity(intent);
    }                  
    
  //Remove loaded graphics from memory when calling the destructor
    @Override
    protected void onDestroy() {
        super.onDestroy();

        unbindDrawables(findViewById(R.id.background));
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