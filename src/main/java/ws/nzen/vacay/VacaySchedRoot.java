package main.java.ws.nzen.vacay;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.java.ws.nzen.vacay.model.Settings;

public class VacaySchedRoot extends Application
{

	public static void main( String[] args )
	{
		launch( args );
	}

	private Desires enteredInformation;
	private Settings userConfig;

	@Override
	public void start( Stage primaryStage ) throws IOException
	{
		// TODO prep the model
		FXMLLoader linker = new FXMLLoader( getClass()
				.getResource( "CalendarByMonthInput.fxml" ) );
		Scene theWindow = new Scene( linker.load() );
		primaryStage.setTitle( "Vacation Scheduler" );
		primaryStage.setScene( theWindow );
		probeForSettings();
		restoreEnteredDesires();
		dynamicPrepOf( (CalendarByMonth) linker.getController() );
		primaryStage.show();
	}

	private void probeForSettings()
	{
		userConfig = new Settings();
		userConfig.adoptVals();
	}

	private void restoreEnteredDesires()
	{
		enteredInformation = new Desires();
		enteredInformation.restorePersisted();
		enteredInformation.receiveSettings( userConfig );
	}

	private void dynamicPrepOf( CalendarByMonth calControl )
	{
		calControl.receiveModel( enteredInformation,
				userConfig );
	}

}





















