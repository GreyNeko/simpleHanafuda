/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hawaiianhanafuda;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author GreyNeko
 */
public class MenuScreen {
    GridPane pane;
    Boolean showEnd;
    int PlayerOnePoints;
    int PlayerTwoPoints;
    
    public MenuScreen(GridPane root, Boolean isEnd, int plOnePt, int plTwoPt){
        pane = root;
        showEnd = isEnd;
        PlayerOnePoints = plOnePt;
        PlayerTwoPoints = plTwoPt;
    }
    public void menuSpace(){
        if(showEnd.equals(false)){
            pane.getStyleClass().add("MSPane");
        }
        else if(showEnd.equals(true)){
            Effect frostEffect = new BoxBlur(10,10,3);
            ImageView backgroundImage = new ImageView();
            pane.setEffect(frostEffect);
            backgroundImage.setImage(pane.getScene().snapshot(null));
            backgroundImage.setTranslateX(-10);
            pane.setEffect(null);
            pane.getChildren().clear();
            pane.add(backgroundImage, 0, 0,2,2);
            
            VBox endVBox = new VBox();            
            Label endLbl = new Label("Thank You for Playing \n\n\tScore Results: ");            
            Label playerLbl = new Label("Player One: "+ PlayerOnePoints +" \nPlayer Two: " + PlayerTwoPoints);
            Label winNotif = new Label();
            if (PlayerOnePoints>PlayerTwoPoints){
                winNotif.setText("\n******************************************"+
                        "\n******** Player One is the Winner ********" + 
                        "\n******************************************");
            }
            else if(PlayerOnePoints<PlayerTwoPoints){
                winNotif.setText("\n******************************************"+
                        "\n******** Player One is the Winner ********" + 
                        "\n******************************************");
            }
            else{
                winNotif.setText("\n******************************************"+
                        "\n********** Neither Player - Draw *********" + 
                        "\n******************************************");
            } 
            
            endVBox.getStyleClass().add("messageBox");
            endVBox.getChildren().add(endLbl);
            endVBox.getChildren().add(playerLbl);
            endVBox.getChildren().add(winNotif);
            endVBox.setMaxSize(350, 225);
            endVBox.setTranslateX(150);
            pane.add(endVBox, 1, 1);
        }
        Text initText = new Text("Hanafuda Hawaiian Style");
        initText.setId("initTxt");
        initText.setTranslateX(-50);
        initText.setTranslateY(50);
        pane.add(initText, 1, 0);
        
        VBox vertMenuBtn = new VBox();
        
        Button newBtn = new Button("New Game");
        vertMenuBtn.getChildren().add(newBtn);
        
        newBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                pane.getChildren().clear();
                pane.getStyleClass().remove("MSPane");
                Board okay = new Board(pane);
                okay.makeDeck();
                okay.CardDealer();
                okay.BoardDisplay();
                
            }
        });
        
        Button quitBtn = new Button("Quit");
        quitBtn.setTranslateX(17);
        vertMenuBtn.getChildren().add(quitBtn);
        
        quitBtn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        
        vertMenuBtn.getStyleClass().add("menuBtnBox");
        vertMenuBtn.setTranslateX(75);
        vertMenuBtn.setTranslateY(250);
        pane.add(vertMenuBtn, 0, 0);
    }
}
