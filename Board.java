/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hawaiianhanafuda;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Screen;

/**
 *
 * @author GreyNeko
 */
public class Board extends GameLogic{
    private int IMAGEWIDTH = 64;
    private int IMAGEHEIGHT = 104;
    String fieldCard = "";
    String handCard = "";
    String deckCard = "";
    String currentfieldCard = "";
    String currentHandCard = "";
    String currentDeckCard = "";
    int fieldEventCounter;
    int HandEventCounter;
    int deckEventCounter;
    ImageView fieldIV01;
    ImageView playerHand;
    ImageView newCard;
    DropShadow ds;
    Group btnGroup;
    Button finBtn;
    Button passBtn;
    Button hikiBtn;
    GridPane pane;
    
    public Board(GridPane root){
        pane = root;
    }
    
    public String getFieldCard(){
        return currentfieldCard;
    }
    
    public String getHandCard(){
        return currentHandCard;
    }
    
    public void BoardDisplay(){
        //Displays Player's two hand ***currently on top of the board***
        for(int i=0; i<playerTwoHand.size();i++){
            if(currentPlayer.equals("Player One")){
                pane.add(new ImageView(new Image(getClass().getResourceAsStream("images/1301.jpg"))),i+1, 2);
            }
            else{
                playerHand = new ImageView(new Image(getClass().getResourceAsStream("images/"+ playerTwoHand.get(i).substring(0,4) + ".png")));
                playerHand.setId(playerTwoHand.get(i));
                if(showNewCard==false){newPlayerMatchMaker(playerHand);}                
                pane.add(playerHand,i+1, 2);
            }
        }
        
        //Image display of the fieldCards into two rows 
        for (int i=0;i<fieldCards.size();i++){
            if (i<=(fieldCards.size()/2-1)){ // think of something to do with equation similar is found on L91
                fieldIV01 = new ImageView(new Image(getClass().getResourceAsStream("images/"+fieldCards.get(i).substring(0,4) + ".png")));
                fieldIV01.setId(fieldCards.get(i));//regex .replaceAll(".png", "")
                newfieldMatchMaker(fieldIV01);
                pane.add(fieldIV01, (i+2) , 3);
            }
            else{
                fieldIV01 = new ImageView(new Image(getClass().getResourceAsStream("images/"+fieldCards.get(i).substring(0,4) + ".png")));
                fieldIV01.setId(fieldCards.get(i));
                newfieldMatchMaker(fieldIV01);
                pane.add(fieldIV01, (i-(fieldCards.size()/2-1)+1), 4);                
            }
        }
        
        //Displays Player's one hand ***Currently displaying on the bottom of the board***
        for(int i=0; i<playerOneHand.size();i++){            
            if(currentPlayer.equals("Player Two")){
                pane.add(new ImageView(new Image(getClass().getResourceAsStream("images/1301.jpg"))),i+1, 5);
            }
            else{
                
                playerHand = new ImageView(new Image(getClass().getResourceAsStream("images/"+playerOneHand.get(i).substring(0,4) + ".png")));
                playerHand.setId(playerOneHand.get(i));
                if (showNewCard==false){newPlayerMatchMaker(playerHand);}                
                pane.add(playerHand,i+1, 5);
            }
        }
        
        //Image display of the DeckCards (show/noShow)
        if(showNewCard==true){
            newCard = new ImageView(new Image(getClass().getResourceAsStream("images/"+ deckCards.get(0).substring(0,4)+".png")));
//            newCard.setId(deckCards.get(0));
//            deckMatchMaker(newCard);
            ds = new DropShadow();
            ds.setOffsetX(6.0);
            ds.setOffsetY(6.0);
            ds.setColor(Color.BLACK);
            newCard.setEffect(ds);
            currentDeckCard = deckCards.get(0);
            pane.add(newCard, 7, 3, 1, 2);
        }
        else if (showNewCard==false){
            pane.add(new ImageView(new Image(getClass().getResourceAsStream("images/1301.jpg"))), 7,3,1,2);
        }
        
        //Mini menu HBox that offers Reset and quit options
        HBox menuHbox = new HBox();
        
        Label resetLbl = new Label("Reset");
        resetLbl.setId("txtBox-Text");
        resetLbl.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                pane.getChildren().clear();
                Board okay = new Board(pane);
                okay.makeDeck();
                okay.CardDealer();
                okay.BoardDisplay();
            }
        });
        
        Label quitLbl = new Label("Quit");
        quitLbl.setId("txtBox-Text");
        quitLbl.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                System.exit(0);
            }
        });
        
        menuHbox.getStyleClass().add("txtBox");
        menuHbox.setId("txtBox-custom");
        menuHbox.getChildren().add(resetLbl);
        menuHbox.getChildren().add(quitLbl);
        
        pane.add(menuHbox, 0, 0, 2, 1);
        
        HBox txtHBox = new HBox();       
        
        //Text display - Displays the turn of events and notifies the player of particular events. 
        Label lineOne = new Label(currentPlayer+" \tPlayer One: " + PlayerOnePoints + " \tPlayer Two: " + PlayerTwoPoints);
        lineOne.setId("txtBox-Text");
        txtHBox.getChildren().add(lineOne);
        txtHBox.getStyleClass().add("txtBox");
        txtHBox.setId("txtBox-custom");
        pane.add(txtHBox, 3, 0, 6, 1);
        
        //The button grouping -- Finished Button, Pass Button, Hiki Button
        btnGroup = new Group();
        
        //Finished Button - This would finalize the player's choice         
        finBtn = new Button("Next");
        finBtn.setLayoutX(18-finBtn.getLayoutBounds().getMinX());
        btnGroup.getChildren().add(finBtn);
        
        finBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                if(deckCards.size()>=0){
                    if(currentPlayer.equals("Player One")&&showNewCard==false){
                        System.out.println(currentPlayer+""+showNewCard);
                        fieldMatchMaker(playerOneHand, currentfieldCard, currentHandCard);
                        
                    }
                    else if(currentPlayer.equals("Player One")&&showNewCard==true){
                        System.out.println(currentPlayer+""+showNewCard);
                        deckPairing(playerOneHand, currentfieldCard, currentDeckCard);
                    }
                    else if(currentPlayer.equals("Player Two")&&showNewCard==false){
                        System.out.println(currentPlayer+""+showNewCard);
                        fieldMatchMaker(playerTwoHand, currentfieldCard, currentHandCard);
                    }
                    else if(currentPlayer.equals("Player Two")&&showNewCard==true){
                        System.out.println(currentPlayer+""+showNewCard);
                        deckPairing(playerTwoHand, currentfieldCard, currentDeckCard);
                    }
                    if(showErrBox == true){
                        messageBox();
                        showErrBox = false;
                    }
                    clearBoard();
                    showEnd();
                }
            }
        });
        
        //Pass Button - If a match isn't produced during a turn, then this would finalize the player's choice.
        passBtn = new Button("Pass");
        passBtn.setLayoutY(30-passBtn.getLayoutBounds().getMinY());
        passBtn.setLayoutX(18-passBtn.getLayoutBounds().getMinX());
        btnGroup.getChildren().add(passBtn);
        passBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                passingLane(currentHandCard, currentDeckCard);
                if(showErrBox == true){
                        messageBox();
                        showErrBox = false;
                }
                clearBoard();
                showEnd();
            }
        });
        
        pane.setAlignment(Pos.TOP_CENTER);
        pane.add(btnGroup, 0, 4);
    }
    
    public void newfieldMatchMaker(ImageView fieldCardViewer){
        ds = new DropShadow();
        ds.setOffsetX(6.0);
        ds.setOffsetY(6.0);
        ds.setColor(Color.BLACK);

        fieldCardViewer.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                fieldCard = fieldCardViewer.getId();
                ++fieldEventCounter; //since that this is an either or choice, a boolean could be used instead
                if(fieldCardViewer.getId() == fieldCard && fieldEventCounter%2==1){
                    fieldCardViewer.setEffect(ds);
                    fieldCardViewer.setTranslateX(-5);
                    fieldCardViewer.setTranslateY(-5);
                    currentfieldCard = fieldCard;
                }
            }
        });

        fieldCardViewer.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                fieldCard = fieldCardViewer.getId();
                fieldCardViewer.setEffect(ds);
                fieldCardViewer.setTranslateX(-5);
                fieldCardViewer.setTranslateY(-5);
            }
        });

        fieldCardViewer.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                fieldCard = fieldCardViewer.getId();
                

                if(currentfieldCard != fieldCard || fieldEventCounter%2==0){
                    fieldCardViewer.setEffect(null);
                    fieldCardViewer.setTranslateX(0);
                    fieldCardViewer.setTranslateY(0);
                }
            }
        });
        
    }
    public void newPlayerMatchMaker(ImageView playerCardToggle){
        ds = new DropShadow();
        ds.setOffsetX(6.0);
        ds.setOffsetY(6.0);
        ds.setColor(Color.BLACK);

        playerCardToggle.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                handCard = playerCardToggle.getId();
                ++HandEventCounter; //since that this is an either or choice, a boolean could be used instead
                if(playerCardToggle.getId() == handCard && HandEventCounter%2==1){
                    playerCardToggle.setEffect(ds);
                    playerCardToggle.setTranslateX(-5);
                    playerCardToggle.setTranslateY(-5);
                    currentHandCard = handCard;
                }
            }
        });
        playerCardToggle.setOnMouseEntered(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                handCard = playerCardToggle.getId();
                playerCardToggle.setEffect(ds);
                playerCardToggle.setTranslateX(-5);
                playerCardToggle.setTranslateY(-5);
            }
        });

        playerCardToggle.setOnMouseExited(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                handCard = playerCardToggle.getId();
                if(currentHandCard != handCard || HandEventCounter%2==0){
                    playerCardToggle.setEffect(null);
                    playerCardToggle.setTranslateX(0);
                    playerCardToggle.setTranslateY(0);
                }
            }
        });
    }
    
    public void clearBoard(){
        pane.getChildren().clear();
        currentfieldCard = "";
        currentHandCard = "";
        currentDeckCard = "";
        fieldEventCounter = 0;
        HandEventCounter = 0;
        deckEventCounter = 0;
        BoardDisplay();
    }
    public Popup messageBox(){
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setAutoHide(true);
        popup.setHideOnEscape(true);
        Label label = new Label(messageShowInfo);
        label.getStyleClass().add("messageBox");
        popup.getContent().add(label);
//        popup.show(btnGroup, MouseInfo.getPointerInfo().getLocation().getX()-25, MouseInfo.getPointerInfo().getLocation().getY()-75);
        popup.show(btnGroup, screenBounds.getWidth()/2-75, screenBounds.getHeight()/2);
        
        return popup;        
    }
    public void showEnd(){
        if(showEndScreen.equals(true)){
        MenuScreen tehEnd = new MenuScreen(pane, true, PlayerOnePoints, PlayerTwoPoints);        
        tehEnd.menuSpace();
        }        
    }
}
