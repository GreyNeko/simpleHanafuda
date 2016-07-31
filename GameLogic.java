/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hawaiianhanafuda;

import java.util.ArrayList;

/**
 *
 * @author GreyNeko
 */
public class GameLogic extends HandCards{
    public String currentPlayer = "Player One";
    public static String textDisplay = "";
    public static String playerTextDisplay = "";
    public Boolean showNewCard = false;
    public int PlayerOnePoints = 0;
    public int PlayerTwoPoints = 0;
    public Boolean showErrBox = false;
    public Boolean showEndScreen = false;
    public String messageShowInfo;
    
    
    public void fieldMatchMaker(ArrayList<String> PlayersHand, String currentFieldCard, String currentHandCard){
        
        
        if (currentFieldCard.isEmpty() || currentHandCard.isEmpty()){
            messageShowInfo = "Please choose a card from \neither the field or your hand. \nTry Again";
            showErrBox = true;
        }
        else if(currentFieldCard.substring(0,2).equals(currentHandCard.substring(0,2))){            
            PlayersHand.remove(currentHandCard);
            fieldCards.remove(currentFieldCard);
            
            pointSystem(currentPlayer, currentFieldCard, currentHandCard);
            EmptierChecker(currentPlayer, playerOneHand, playerTwoHand, fieldCards, deckCards);
            pairPointVarifi(currentPlayer, playerOneHand, playerTwoHand, deckCards); //need to implement the paired matched cards arrayLists
            showNewCard = true;            
        }
        else if(!currentFieldCard.substring(0,2).equals(currentHandCard.substring(0,2))){
            messageShowInfo = "These cards do not match. \nTry again.";
            showErrBox = true;
            
        }
        else{
            System.out.println(currentPlayer + " fieldMatchMaker :" + currentFieldCard+" "+currentHandCard + " --Failure");
        }
    }
    
    public void deckPairing(ArrayList<String> PlayersHand, String currentFieldCard, String currentDeckCard){
        if(currentFieldCard.isEmpty()){
            messageShowInfo = "Please choose a card from the field. \nTry Again.";
            showErrBox = true;
        }
        else if(currentFieldCard.substring(0,2).equals(currentDeckCard.substring(0,2))){            
            deckCards.remove(currentDeckCard);
            fieldCards.remove(currentFieldCard);
            
            pointSystem(currentPlayer, currentFieldCard, currentDeckCard);
            EmptierChecker(currentPlayer, playerOneHand, playerTwoHand, fieldCards, deckCards);
            pairPointVarifi(currentPlayer, playerOneHand, playerTwoHand, deckCards);
            
            showNewCard = false;
            if(currentPlayer.equals("Player One")){
                currentPlayer = "Player Two";
            }
            else{currentPlayer = "Player One";}
        }
        else if(!currentFieldCard.substring(0,2).equals(currentDeckCard.substring(0,2))){
            messageShowInfo = "These cards do not match. \nTry again.";
            showErrBox = true;
        }
        else{
            System.out.println(currentPlayer + " deckPairing :" + currentFieldCard+" "+currentDeckCard + " --Failure");
        }
    }
    public void passingLane(String currentHandCard,String currentDeckCard){
        
        if(showNewCard==true){
            fieldCards.add(fieldCards.size(), currentDeckCard);
            deckCards.remove(currentDeckCard);

            if(currentPlayer.equals("Player One")){
                currentPlayer = "Player Two";
            }
            else{currentPlayer = "Player One";}
            showNewCard = false;
        }
        else if(currentPlayer.equals("Player One") && !currentHandCard.isEmpty()){
            fieldCards.add(fieldCards.size(), currentHandCard);
            playerOneHand.remove(currentHandCard);
            showNewCard = true;
        }
        else if (currentPlayer.equals("Player Two") && !currentHandCard.isEmpty()){
            fieldCards.add(fieldCards.size(), currentHandCard);
            playerTwoHand.remove(currentHandCard);
            showNewCard = true;
        }
        else if(currentHandCard.isEmpty()){
            messageShowInfo = "Please choose a card to pass.";
            showErrBox = true;
        }
        
        EmptierChecker(currentPlayer, playerOneHand, playerTwoHand, fieldCards, deckCards);
        pairPointVarifi(currentPlayer, playerOneHand, playerTwoHand, deckCards);
    }
    
    public void pointSystem(String playerTurn, String currentFieldCard, String currentMatchingCard){
            int matchingCard = Integer.parseInt(currentMatchingCard.substring(4, currentMatchingCard.length()));
            int playerFieldPoint = Integer.parseInt(currentFieldCard.substring(4,currentFieldCard.length()));
            System.out.println(matchingCard);
            if(playerTurn.equals("Player One")){//&&showNewCard==false
                PlayerOnePoints = PlayerOnePoints + matchingCard + playerFieldPoint;
//                System.out.println(playerTurn +" hand phase: "+ currentFieldCard + " + " + currentMatchingCard + " = "+ PlayerOnePoints);
            }
            else if(playerTurn.equals("Player Two")){//&&showNewCard==false
                PlayerTwoPoints = PlayerTwoPoints + matchingCard + playerFieldPoint;
//                System.out.println(playerTurn +"hand phase: "+ currentFieldCard + " + " + currentMatchingCard + " = "+ PlayerTwoPoints);
            }
    }
    
    public static void EmptierChecker(String playerTurn, ArrayList<String> playerOneHand, ArrayList<String> playerTwoHand, ArrayList<String> fieldCards, ArrayList<String> sortedDeck){
        if (playerOneHand.isEmpty()||playerTwoHand.isEmpty()){
            System.out.println(playerTurn + " has used all cards - game has ended.");
        }
        else if(fieldCards.isEmpty()){
            System.out.println("There is no more cards left in the field.");
        }
        else if(sortedDeck.isEmpty()){
            System.out.println("There is no more cards left in the deck - game has ended.");
        }
    }
    
    public void pairPointVarifi(String playerTurn, ArrayList<String> playerOneHand, ArrayList<String> playerTwoHand, ArrayList<String> sortedDeck){
        if((playerOneHand.isEmpty())||(playerTwoHand.isEmpty())||(sortedDeck.isEmpty())){
            if(PlayerOnePoints > PlayerTwoPoints){
                showEndScreen = true;
                System.out.println("Player one has the most pairs - Player One is the Winner.");
            }
            else {
                showEndScreen = true;
                System.out.println("Player Two has the most pairs - Player Two is the Winner.");
            }
        }
        else {
            System.out.println("******************************************"+"\nPlayer One: "+PlayerOnePoints+" pairs *** Player Two: "+PlayerTwoPoints+" pairs\n******************************************");
        }
        
    }
    
        
}
