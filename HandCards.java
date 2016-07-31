/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hawaiianhanafuda;

import java.util.ArrayList;

/**
 *
 * @author HentaiNeko
 */
public class HandCards extends Deck {
    ArrayList<String> playerOneHand = new ArrayList<>();
    ArrayList<String> playerTwoHand = new ArrayList<>();
    ArrayList<String> playerOneMatches = new ArrayList<>();
    ArrayList<String> playerTwoMatches = new ArrayList<>();
    ArrayList<String> fieldCards = new ArrayList<>();
    
    public ArrayList<String> getPlayerOneHand(){
        return playerOneHand;
    }
    
    public ArrayList<String> getplayerTwoHand(){
        return playerTwoHand;
    }
    
    public ArrayList<String> getplayerOneMatches(){
        return playerOneMatches;
    }
    
    public ArrayList<String> getplayerTwoMatches(){
        return playerTwoMatches;
    }
    
    public ArrayList<String> getfieldCards(){
        return fieldCards;
    }
    
    public void CardDealer(){
        for (int i=0; i<24; i++){
            if ((i%3)==0){
                playerOneHand.add(deckCards.remove(0));
            }
            else if ((i%3)==1){
                playerTwoHand.add(deckCards.remove(0));
            }
            else{
                fieldCards.add(deckCards.remove(0));
            }
        }
    }
}
