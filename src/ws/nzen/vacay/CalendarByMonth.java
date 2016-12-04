/**
 * 
 */
package ws.nzen.vacay;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import ws.nzen.vacay.Desires.RequestViability;
import ws.nzen.vacay.model.CalendarUi;
import ws.nzen.vacay.model.Settings;

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
	@FXML
	private Button btbtSave = new Button();
	@FXML
	private Button btRestore = new Button();
	@FXML
	private Button btExport = new Button();
	private List<Labeled> controlsThatShowDay;
	private YearMonth currMonth;
	private int year = 2016;
	private Desires vacationPreferences = new Desires();
	private Settings userConfig = new Settings();
	// so it doesn't die without receiving; I'd prefer to send it via constructor, but FxmlLoader
	private String persistedFilename = "ui.ser";

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
		if ( ! presentTimeNotSelected( selection, true ) ) // IMPROVE define ptnsYear once
		{
			year = Integer.parseInt( selection );
			cbNavMonth.setValue( "Month" );
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
		if ( ! presentTimeNotSelected( selection, false ) )
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
		if ( presentTimeNotSelected( selected, false ) )
		{
			// ASK nothing selected, I guess I could say earlier than this yearmonth
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
		boolean emptyInput = false;
		String maybeName = tfName.getText();
		
		if ( maybeName == null || maybeName.isEmpty() )
		{
			System.out.println( here + "name empty" );
			tfName.setText( "!needed!" );
			// IMPROVE make the background red, but then de-red it if it passes
			emptyInput = true;
		}
		String maybeSenior = tfSeniority.getText();
		if ( maybeSenior == null || maybeSenior.isEmpty() )
		{
			System.out.println( here + "seniority empty" );
			tfSeniority.setText( "!needed!" );
			emptyInput = true;
		}
		String maybeDesire = tfDesire.getText();
		if ( maybeDesire == null || maybeDesire.isEmpty() )
		{
			System.out.println( here + "desire empty" );
			tfDesire.setText( "!needed!" );
			emptyInput = true;
		}
		if ( emptyInput )
			return; // NOTE suppressed to here so I highlight several problems
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
			removeRequest( dateOfComponent, maybeName, seniority, desirability );
		}
		refreshMainCalendar();
	}

	public boolean someReasonToIgnoreDayPress()
	{
		final boolean problem = true;
		String here = cl + "srtidp ";
		final boolean year = true;
		String selection = cbNavYear.getValue();
		if ( presentTimeNotSelected( selection, year ) )
		{
			System.out.println( here + "hasn't chosen year" );
			return problem;
		}
		selection = cbNavMonth.getValue();
		if ( presentTimeNotSelected( selection, ! year ) )
		{
			System.out.println( here + "hasn't chosen month" );
			return problem;
		}
		// IMPROVE more validation
		return ! problem;
	}

	/** is the requested combobox in the default, unselected state ? */
	private boolean presentTimeNotSelected( String selection, boolean iMeanTheYear )
	{
		String defaultText = ( iMeanTheYear ) ? "Year": "Month";
		return selection == null || selection.equals( defaultText );
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
				whatToAsk = "Another person exists at "
						+ adjustedSeniority( seniority ) +"\nOverwrite or Swap?";
				Alert dialogRequest = new Alert(
						Alert.AlertType.CONFIRMATION,
						whatToAsk,
						ButtonType.APPLY,
						new ButtonType( "Swap",
								ButtonData.NEXT_FORWARD ),
						ButtonType.CANCEL );
				dialogRequest.showAndWait().ifPresent( 
					response ->
					{
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
				dialogRequest.showAndWait().ifPresent(
					response ->
					{
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

	private void removeRequest( LocalDate when, String name,
			int seniority, int desire )
	{
		RequestViability removability = vacationPreferences
				.requestChangesSeniority( name, seniority );
		if ( removability == RequestViability.inoffensive )
		{
			vacationPreferences.removeRequest( name, seniority, desire, when );
			return;
		}
		String whatToAsk = "";
		if ( removability == RequestViability.displacesAnother )
		{
			boolean overwrite = true;
			String personAt = vacationPreferences.personOfSeniority( seniority );
				whatToAsk = personAt +" exists at "+ adjustedSeniority( seniority )
						+"\nDelete "+ name +"'s request anyway ?";
				Alert dialogRequest = new Alert(
						Alert.AlertType.CONFIRMATION,
						whatToAsk,
						ButtonType.APPLY,
						new ButtonType( "Cut "+ personAt,
								ButtonData.NEXT_FORWARD ),
						ButtonType.CANCEL );
				dialogRequest.showAndWait().ifPresent( 
					response ->
					{
						if ( response == ButtonType.APPLY )
						{
							int realSeniority = vacationPreferences
									.personSeniority( name );
							vacationPreferences.removeRequest(
									name, realSeniority, desire, when );
						}
						else if ( response == ButtonType.CANCEL )
						{
							return;
						}
						else // it's swap
						{
							vacationPreferences.removeRequest(
									personAt, seniority, desire, when );
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
			dialogRequest.showAndWait().ifPresent(
				response ->
				{
					if ( response == ButtonType.YES )
					{
						vacationPreferences.removeRequest(
								name, seniority, desire, when );
					}
					else
					{
						return;
					}
				}
			);
		}
	}

	private int adjustedSeniority( int rawSeniority )
	{
		return rawSeniority +1;
	}

	/**  */
	public void pressedSave( ActionEvent whatHappened )
	{
		String selectedYear = cbNavYear.getValue();
		String selectedMonth = cbNavMonth.getValue();
		if ( presentTimeNotSelected( selectedYear, true )
			|| presentTimeNotSelected( selectedMonth, false ) )
		{
			Alert reportNoRestoration = new Alert( AlertType.ERROR );
			reportNoRestoration.setContentText(
					"Nothing to save yet, dude" );
		}
		else
		{
			vacationPreferences.persist();
		}
		persistUi();
	}

	/** save current values */
	private void persistUi()
	{
		final String here = cl + "pu ";
		String name = beBlankNotNull( tfName.getText() );
		String senior = beBlankNotNull( tfSeniority.getText() );
		String desire = beBlankNotNull( tfDesire.getText() );
		Boolean amAdding = rbDoesAdd.isSelected();
		CalendarUi ui = new CalendarUi( currMonth, name, senior, desire, amAdding );
		try ( FileOutputStream fos = new FileOutputStream( persistedFilename );
				ObjectOutputStream oos = new ObjectOutputStream(fos); )
		{
			oos.writeObject( ui );
		}
		catch ( IOException ioe )
		{
			System.out.println( here +"couldn't persist ui because "
					+ ioe );
		}
	}

	private String beBlankNotNull( String whatever )
	{
		return ( whatever == null ) ? "" : whatever;
	}

	/**  */
	public void pressedRestore( ActionEvent whatHappened )
	{
		boolean worked = vacationPreferences.restorePersisted();
		if ( ! worked )
		{
			Alert reportNoRestoration = new Alert( AlertType.ERROR );
			reportNoRestoration.setContentText(
					"Unable to restore previous session" );
		}
		restoreUi();
		choseMonth( whatHappened );
	}

	/**  */
	private boolean restoreUi()
	{
		final String here = cl + "rp ";
		final boolean worked = true;
		try ( FileInputStream fis = new FileInputStream( persistedFilename );
				ObjectInputStream ois = new ObjectInputStream(fis); )
		{
			CalendarUi savedInput = (CalendarUi) ois.readObject();
			tfName.setText( savedInput.getPersonName() );
			tfSeniority.setText( savedInput.getSeniorityVal() );
			tfDesire.setText( savedInput.getDesireVal() );
			rbDoesAdd.setSelected( savedInput.isRadioAdd() );
			rbDoesDelete.setSelected( ! savedInput.isRadioAdd() );
			YearMonth maybeDateRange = savedInput.getCurrentMonth();
			if ( maybeDateRange != null )
			{
				currMonth = maybeDateRange;
				restoreYearMonthSelections();
			}
		}
		catch ( IOException ie )
		{
			System.err.println( here +"inaccessible persisted ui file"
					+ " because "+ ie );
		}
		catch ( ClassNotFoundException cne )
		{
			System.err.println( here +"restored from a non-ui package?"
					+ "\n fail because "+ cne );
		}
		return false;
	}

	/**  */
	private void restoreYearMonthSelections()
	{
		// assert currMonth != null
		year = currMonth.get( ChronoField.YEAR );
		cbNavYear.setValue( Integer.toString( year ) );
		cbNavMonth.setValue( Month.of( currMonth.getMonthValue() )
				.getDisplayName( TextStyle.FULL, Locale.getDefault() ) );
	}

	/**  */
	public void pressedExport( ActionEvent whatHappened )
	{
		/*
		IMPROVE ask what type and where ; show a different thing instead ? 
		*/
		// vacationPreferences.testExport( year );
		vacationPreferences.exportYear( year );
	}

	/**  */
	public void receiveModel( Desires peopleAndWhatTheyWant,
			Settings settingsGathered )
	{
		if ( peopleAndWhatTheyWant != null )
			vacationPreferences = peopleAndWhatTheyWant;
		if ( settingsGathered != null )
			userConfig = settingsGathered;
	}

}

































