/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hawaiianhanafuda;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author HentaiNeko
 */
public class Deck {
    final String[] cardSuits = {"01","02","03","04","05","06","07","08","09","10","11","12"};
    final String[] cardRanks = {"01","02","03","04"};
    final String[] cardPoints = {"0","0","10","20","0","0","5","10","0","0","10","20","0","0","5","10","0","0","5","10","0","0","5","10","0","0","5","10","0","0","5","20","0","0","5","10","0","0","5","10","0","5","5","10","0","0","10","20"};
    ArrayList<String> deckCards = new ArrayList<>();
    int k = 0;
    
    public String[] getCardSuits(){
        return cardSuits;
    }
    public String[] getCardRank(){
        return cardRanks;
    }
    public ArrayList<String> getCardList(){
        return deckCards;
    }
    public void makeDeck(){
        for (int i=0;i<cardSuits.length;i++){
            for (int j=0;j<cardRanks.length; j++){
                    deckCards.add(cardSuits[i] + cardRanks[j]+cardPoints[k]);
                    k++;
            }
        }
        Collections.shuffle(deckCards);
    }
}
