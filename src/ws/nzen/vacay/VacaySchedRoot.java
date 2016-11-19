package ws.nzen.vacay;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class VacaySchedRoot extends Application
{

	public static void main( String[] args )
	{
		launch( args );
	}

	private Desires enteredInformation;

	@Override
	public void start( Stage primaryStage ) throws IOException
	{
		// TODO prep the model
		FXMLLoader linker = new FXMLLoader( getClass()
				.getResource( "CalendarByMonthInput.fxml" ) );
		Scene theWindow = new Scene( linker.load() );
		primaryStage.setTitle( "Vacation Scheduler" );
		primaryStage.setScene( theWindow );
		restoreEnteredDesires();
		dynamicPrepOf( (CalendarByMonth) linker.getController() );
		primaryStage.show();
	}

	// IMPROVE
	private void restoreEnteredDesires()
	{
		enteredInformation = new Desires();
	}

	private void dynamicPrepOf( CalendarByMonth calControl )
	{
		calControl.receiveModel( enteredInformation );
		// calControl.setInitialDayLabels();
	}

}
