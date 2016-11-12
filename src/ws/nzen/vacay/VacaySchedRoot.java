package ws.nzen.vacay;
	
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;


public class VacaySchedRoot extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			Label saysStatus = new Label( "not sure yet" );
			Button pushListener = new Button();
			pushListener.setText( "Tests filewriting" );
			pushListener.setOnAction( new EventHandler<ActionEvent>()
			{
				@Override
				public void handle( ActionEvent whatHappened )
				{
					String newStatus;
					if ( wroteFile() )
						newStatus = "Worked yay";
					else
						newStatus = "I'll need to use a webpage instead";
					saysStatus.setText( newStatus );
				}
			});
			VBox root = new VBox();
			root.getChildren().add(saysStatus);
			root.getChildren().add(pushListener);
			Scene scene = new Scene(root,400,400);
			scene.getStylesheets().add(getClass()
					.getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
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
	
	public static void main(String[] args) {
		launch(args);
	}
}




















