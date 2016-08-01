/*
 * Main
 */
package hawaiianhanafuda;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author GreyNeko
 */
public class HawaiianHanafuda extends Application{
    @Override
    public void start(Stage primaryStage) {        
        GridPane root = new GridPane();
        root.setHgap(10);
        root.setVgap(10);
        root.setPadding(new Insets(0,10,0,10));
        
        Scene scene = new Scene(root, 800, 600); 
        scene.getStylesheets().add(HawaiianHanafuda.class.getResource("css/layoutStyles.css").toExternalForm());
        System.out.println(HawaiianHanafuda.class.getResource("css/layoutStyles.css").toExternalForm());
        
        
        MenuScreen menuScreen = new MenuScreen(root, false, 0, 0);
        menuScreen.menuSpace();
            
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(screenBounds.getWidth()/2-400);
        primaryStage.setY(screenBounds.getHeight()/2-300);
        primaryStage.setTitle("Hawaiian Hanafuda");
        root.getStyleClass().add("pane");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
