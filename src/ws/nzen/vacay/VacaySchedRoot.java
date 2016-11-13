package ws.nzen.vacay;
	
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class VacaySchedRoot extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws IOException {
		// TODO prep the model
		FXMLLoader linker = new FXMLLoader( getClass()
				.getResource( "CalendarByMonthInput.fxml" ) );
		Scene theWindow = new Scene( linker.load() );
		primaryStage.setTitle( "Vacation Scheduler" );
		primaryStage.setScene(theWindow);
		dynamicPrepOf( (CalendarByMonth)linker.getController() );
		primaryStage.show();
	}

	private void dynamicPrepOf( CalendarByMonth calControl )
	{
		// calControl.setInitialDayLabels();
	}

}




















