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
		// prep the model
		CalendarByMonth view = new CalendarByMonth();
		view.start(primaryStage);
	}

	private boolean wroteFile()
	{
		final boolean worked = true;
		String shoveInWhatever = "Yay, wrote to a file at "
				+ System.currentTimeMillis() +"\r\n";
		try (BufferedWriter writer = Files.newBufferedWriter(
				new File( "worked.txt" ).toPath(),
				Charset.defaultCharset())) {
		    writer.write(shoveInWhatever, 0, shoveInWhatever.length());
		    return worked;
		} catch (IOException x) {
		    System.err.format("IOException: %s%n", x);
		    return ! worked;
		}
	}
}




















