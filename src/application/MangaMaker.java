package application;
	
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;


public class MangaMaker extends Application {
	private static ResourceBundle rb;
	private static Locale locale = Locale.getDefault();
	@Override
	public void start(Stage stage) {
		Parent root = null;
    	try {
    		
    		 FXMLLoader loader = new FXMLLoader(getClass().getResource("MyMangaMaker.fxml"));
    		 root = loader.load();
    		 
    		  Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
    		  
    		  Scene scene = new Scene(root, 1000,1000);

    	        //set Stage boundaries to visible bounds of the main screen
    	        stage.setX(primaryScreenBounds.getMinX());
    	        stage.setY(primaryScreenBounds.getMinY());
    	        stage.setWidth(primaryScreenBounds.getWidth());
    	        stage.setHeight(primaryScreenBounds.getHeight());
    		 
    		 
    		 
    		 MangaMakerController control = loader.getController();
     	    control.setScene(scene);
     	    
     	      stage.setTitle(rb.getString("key.MMaker"));
     	        
     	       
     	       stage.setScene(scene); 
     	       
     	       
     	       
     	       stage.show();
     	  

    		 
    	} catch(Exception ex) {
    		System.out.println(ex);
    		Alert alert = new Alert(Alert.AlertType.ERROR);
        	alert.setTitle("Hiba");
        	alert.setHeaderText(null);
        	alert.setContentText(ex.toString());
        	System.exit(1);

    	}
    	
  
		
	}
	
	public static void main(String[] args) {
		rb = ResourceBundle.getBundle("application/MessageBundle", locale);
		launch(args);
	}
}
