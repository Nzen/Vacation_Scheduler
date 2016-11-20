/**
 * 
 */
package ws.nzen.vacay;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import ws.nzen.vacay.Desires.RequestViability;

/** @author nzen */
public class CalendarByMonth
{

	static final String cl = "cbm.";
	@FXML
	private ComboBox<String> cbNavYear = new ComboBox<>();
	@FXML
	private ComboBox<String> cbNavMonth = new ComboBox<>();
	@FXML
	private Button btNavEarlier = new Button();
	@FXML
	private Button btNavLater = new Button();
	@FXML
	private Button btDayM0 = new Button();
	@FXML
	private Button btDayT0 = new Button();
	@FXML
	private Button btDayW0 = new Button();
	@FXML
	private Button btDayR0 = new Button();
	@FXML
	private Button btDayF0 = new Button();
	@FXML
	private Label lDayS0 = new Label();
	@FXML
	private Label lDayU0 = new Label();
	@FXML
	private Button btDayM1 = new Button();
	@FXML
	private Button btDayT1 = new Button();
	@FXML
	private Button btDayW1 = new Button();
	@FXML
	private Button btDayR1 = new Button();
	@FXML
	private Button btDayF1 = new Button();
	@FXML
	private Label lDayS1 = new Label();
	@FXML
	private Label lDayU1 = new Label();
	@FXML
	private Button btDayM2 = new Button();
	@FXML
	private Button btDayT2 = new Button();
	@FXML
	private Button btDayW2 = new Button();
	@FXML
	private Button btDayR2 = new Button();
	@FXML
	private Button btDayF2 = new Button();
	@FXML
	private Label lDayS2 = new Label();
	@FXML
	private Label lDayU2 = new Label();
	@FXML
	private Button btDayM3 = new Button();
	@FXML
	private Button btDayT3 = new Button();
	@FXML
	private Button btDayW3 = new Button();
	@FXML
	private Button btDayR3 = new Button();
	@FXML
	private Button btDayF3 = new Button();
	@FXML
	private Label lDayS3 = new Label();
	@FXML
	private Label lDayU3 = new Label();
	@FXML
	private Button btDayM4 = new Button();
	@FXML
	private Button btDayT4 = new Button();
	@FXML
	private Button btDayW4 = new Button();
	@FXML
	private Button btDayR4 = new Button();
	@FXML
	private Button btDayF4 = new Button();
	@FXML
	private Label lDayS4 = new Label();
	@FXML
	private Label lDayU4 = new Label();
	@FXML
	private Button btDayM5 = new Button();
	@FXML
	private Button btDayT5 = new Button();
	@FXML
	private TextField tfName = new TextField();
	@FXML
	private TextField tfSeniority = new TextField();
	@FXML
	private TextField tfDesire = new TextField();
	@FXML
	private RadioButton rbDoesAdd = new RadioButton();
	@FXML
	private RadioButton rbDoesDelete = new RadioButton();
/*
	@FXML
	private Button btSave = new Button();
	@FXML
	private Button btRestore = new Button();
	@FXML
	private Button btExport = new Button();
*/
	private List<Labeled> controlsThatShowDay;
	private YearMonth currMonth;
	private int year = 2016;
	private Desires vacationPreferences = new Desires();
	// so it doesn't die without receiving; I'd prefer to send it via constructor, but FxmlLoader

	public CalendarByMonth()
	{
		controlsThatShowDay = new ArrayList<>( 37 );
	}

	/** it keeps clearing my sinnning list of buttons */
	private void prepControlList()
	{
		if ( controlsThatShowDay.isEmpty() )
		{
			controlsThatShowDay.add( btNavEarlier ); // ignored, convenience for math
			controlsThatShowDay.add( btDayM0 );
			controlsThatShowDay.add( btDayT0 );
			controlsThatShowDay.add( btDayW0 );
			controlsThatShowDay.add( btDayR0 );
			controlsThatShowDay.add( btDayF0 );
			controlsThatShowDay.add( lDayS0 );
			controlsThatShowDay.add( lDayU0 );
			controlsThatShowDay.add( btDayM1 );
			controlsThatShowDay.add( btDayT1 );
			controlsThatShowDay.add( btDayW1 );
			controlsThatShowDay.add( btDayR1 );
			controlsThatShowDay.add( btDayF1 );
			controlsThatShowDay.add( lDayS1 );
			controlsThatShowDay.add( lDayU1 );
			controlsThatShowDay.add( btDayM2 );
			controlsThatShowDay.add( btDayT2 );
			controlsThatShowDay.add( btDayW2 );
			controlsThatShowDay.add( btDayR2 );
			controlsThatShowDay.add( btDayF2 );
			controlsThatShowDay.add( lDayS2 );
			controlsThatShowDay.add( lDayU2 );
			controlsThatShowDay.add( btDayM3 );
			controlsThatShowDay.add( btDayT3 );
			controlsThatShowDay.add( btDayW3 );
			controlsThatShowDay.add( btDayR3 );
			controlsThatShowDay.add( btDayF3 );
			controlsThatShowDay.add( lDayS3 );
			controlsThatShowDay.add( lDayU3 );
			controlsThatShowDay.add( btDayM4 );
			controlsThatShowDay.add( btDayT4 );
			controlsThatShowDay.add( btDayW4 );
			controlsThatShowDay.add( btDayR4 );
			controlsThatShowDay.add( btDayF4 );
			controlsThatShowDay.add( lDayS4 );
			controlsThatShowDay.add( lDayU4 );
			controlsThatShowDay.add( btDayM5 );
			controlsThatShowDay.add( btDayT5 );
		}
	}

	@FXML
	/** IMPROVE eventually reset all the text */
	public void choseYear( ActionEvent whatHappened )
	{
		String selection = cbNavYear.getValue();
		if ( selection != null && ! selection.equals( "Year" ) )
		{
			year = Integer.parseInt( selection );
		}
		clearTheVisibleCalendar();
	}

	private void clearTheVisibleCalendar()
	{
		int skipZeroIsInvalidDay = 1;
		for ( int ind = skipZeroIsInvalidDay; ind < controlsThatShowDay
				.size(); ind++ )
		{
			controlsThatShowDay.get( ind ).setText( ".." );
		}
	}

	@FXML
	public void choseMonth( ActionEvent whatHappened )
	{
		String selection = cbNavMonth.getValue();
		if ( selection != null && ! selection.equals( "Month" ) )
		{
			currMonth = YearMonth.of( year,
					Month.valueOf( selection.toUpperCase() ) );
			refreshMainCalendar();
		}
	}

	private void refreshMainCalendar()
	{
		prepControlList();
		List<String> newLabels = vacationPreferences.getLabelsFor( currMonth );
		int ind = 1; // skip 0
		for ( String text : newLabels )
		{
			controlsThatShowDay.get( ind ).setText( text );
			ind++;
		}
	}

	@FXML
	/** show and work with the previous month ; wraps rather than increments year */
	public void pressedNavEarlier( ActionEvent whatHappened )
	{
		int earlier = 1;
		pressedNavDirection( earlier, whatHappened );
	}

	@FXML
	/** show and work with the next month ; wraps rather than increments year */
	public void pressedNavLater( ActionEvent whatHappened )
	{
		int earlier = - 1;
		pressedNavDirection( earlier, whatHappened );
	}

	/** change nav to the month that is current selection minus magnitude */
	private void pressedNavDirection( int magnitude, ActionEvent whatHappened )
	{
		String selected = cbNavMonth.getValue();
		ObservableList<String> monthList = cbNavMonth.getItems();
		if ( selected == null || selected.equals( "Month" ) )
		{
			// nothing selected, I guess I could say earlier than this yearmonth
			return;
		}
		else
		{
			cbNavMonth.setValue(
					Month.valueOf( selected.toUpperCase() ).minus( magnitude )
							.getDisplayName( TextStyle.FULL,
									Locale.getDefault() ) );
		}
		choseMonth( whatHappened );
	}

	/** save or remove info from input panel */
	public void pressedDay( ActionEvent whatHappened )
	{
		String here = cl + "pd ";
		if ( someReasonToIgnoreDayPress() )
			return;
		String maybeName = tfName.getText();
		if ( maybeName == null || maybeName.isEmpty() )
		{
			System.out.println( here + "name empty" );
			return;
		}
		String maybeSenior = tfSeniority.getText();
		if ( maybeSenior == null || maybeSenior.isEmpty() )
		{
			System.out.println( here + "seniority empty" );
			return;
		}
		String maybeDesire = tfDesire.getText();
		if ( maybeDesire == null || maybeDesire.isEmpty() )
		{
			System.out.println( here + "desire empty" );
			return;
		}
		int seniority, desirability;
		boolean failedForSenior = true;
		try
		{
			seniority = Integer.parseUnsignedInt( maybeSenior ) -1;
			failedForSenior = false;
			desirability = Integer.parseUnsignedInt( maybeDesire ) -1;
		}
		catch ( NumberFormatException nfe )
		{
			String whyFail;
			if ( failedForSenior )
			{
				whyFail = maybeSenior +" isnt a valid number for seniority";
			}
			else
			{
				whyFail = maybeDesire +" isnt a valid number for desirability";
			}
			new Alert( Alert.AlertType.WARNING, whyFail ).show();
			return;
		}
		Button pressee = (Button) whatHappened.getSource();
		LocalDate dateOfComponent = dateFromComponent( pressee );
		if ( dateOfComponent == null )
		{
			return;
		}
		if ( rbDoesAdd.isSelected() )
		{
			addRequest( dateOfComponent, maybeName, seniority, desirability );
		}
		else
		{
			// remove()
			return;
		}
		refreshMainCalendar();
	}

	public boolean someReasonToIgnoreDayPress()
	{
		final boolean problem = true;
		String here = cl + "srtidp ";
		String selection = cbNavYear.getValue();
		if ( selection == null || selection.equals( "Year" ) )
		{
			System.out.println( here + "hasn't chosen year" );
			return problem;
		}
		selection = cbNavMonth.getValue();
		if ( selection == null || selection.equals( "Month" ) )
		{
			System.out.println( here + "hasn't chosen month" );
			return problem;
		}
		// IMPROVE more validation
		return ! problem;
	}

	private LocalDate dateFromComponent( Button whichBtn )
	{
		String here = cl + "dfc ";
		String nonDayLabel = "..";
		String btnLabel = whichBtn.getText();
		if ( btnLabel.equals( nonDayLabel ) )
		{
			return null; // likely better to disable them
		}
		String dayNumber;
		if ( btnLabel.contains( " " ) )
		{
			int indOfNonNumber = btnLabel.indexOf( ' ' );
			dayNumber = btnLabel.substring( 0, indOfNonNumber );
		}
		else
		{
			dayNumber = btnLabel;
		}
		int dayOfMonth = Integer.parseInt( dayNumber );
		// assuming nothing went wrong with the day labels
		return currMonth.atDay( dayOfMonth );
	}

	private void addRequest( LocalDate when, String name,
			int seniority, int desirability )
	{
		RequestViability addability = vacationPreferences
				.requestChangesSeniority( name, seniority );
		if ( addability == RequestViability.inoffensive )
		{
			vacationPreferences.addRequest( name, seniority, desirability, when );
		}
		else
		{
			String whatToAsk = "";
			if ( addability == RequestViability.displacesAnother )
			{
				boolean overwrite = true;
				whatToAsk = "Person exists at "+ (seniority +1)
						+"\nOverwrite or Swap?";
				Alert dialogRequest = new Alert(
						Alert.AlertType.CONFIRMATION,
						whatToAsk,
						ButtonType.APPLY,
						new ButtonType( "Swap",
								ButtonData.NEXT_FORWARD ),
						ButtonType.CANCEL );
				dialogRequest.showAndWait()
					.ifPresent( 
						response -> {
							if ( response == ButtonType.APPLY )
							{
								vacationPreferences.addRequest(
										name, seniority, desirability, when,
										overwrite );
							}
							else if ( response == ButtonType.CANCEL )
							{
								return;
							}
							else // it's swap
							{
								vacationPreferences.addRequest(
										name, seniority, desirability, when,
										! overwrite );
							}
						}
					);
			}
			else
			{
				// must be Rv.movesPerson
				whatToAsk = "Move "+ name +" to new seniority ?";
				Alert dialogRequest = new Alert(
						AlertType.CONFIRMATION, whatToAsk,
						ButtonType.YES, ButtonType.NO );
				dialogRequest.showAndWait()
					.ifPresent( 
						response -> {
							if ( response == ButtonType.YES )
							{
								vacationPreferences.addRequest(
										name, seniority, desirability, when );
							}
							else
							{
								return;
							}
						}
					);
			}
			
		}
		
	}

	public void receiveModel( Desires peopleAndWhatTheyWant )
	{
		vacationPreferences = peopleAndWhatTheyWant;
	}

}





















