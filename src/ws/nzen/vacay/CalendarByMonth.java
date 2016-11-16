/**
 * 
 */
package ws.nzen.vacay;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

/**
 * @author nzen
 *
 */
public class CalendarByMonth {

	static final String cl = "cbm.";
	@FXML private ComboBox<String> cbNavYear = new ComboBox<>();
	@FXML private ComboBox<String> cbNavMonth = new ComboBox<>();
	@FXML private Button btNavEarlier = new Button();
	@FXML private Button btNavLater = new Button();
	@FXML private Button btDayM0 = new Button();
	@FXML private Button btDayT0 = new Button();
	@FXML private Button btDayW0 = new Button();
	@FXML private Button btDayR0 = new Button();
	@FXML private Button btDayF0 = new Button();
	@FXML private Label lDayS0 = new Label();
	@FXML private Label lDayU0 = new Label();
	@FXML private Button btDayM1 = new Button();
	@FXML private Button btDayT1 = new Button();
	@FXML private Button btDayW1 = new Button();
	@FXML private Button btDayR1 = new Button();
	@FXML private Button btDayF1 = new Button();
	@FXML private Label lDayS1 = new Label();
	@FXML private Label lDayU1 = new Label();
	@FXML private Button btDayM2 = new Button();
	@FXML private Button btDayT2 = new Button();
	@FXML private Button btDayW2 = new Button();
	@FXML private Button btDayR2 = new Button();
	@FXML private Button btDayF2 = new Button();
	@FXML private Label lDayS2 = new Label();
	@FXML private Label lDayU2 = new Label();
	@FXML private Button btDayM3 = new Button();
	@FXML private Button btDayT3 = new Button();
	@FXML private Button btDayW3 = new Button();
	@FXML private Button btDayR3 = new Button();
	@FXML private Button btDayF3 = new Button();
	@FXML private Label lDayS3 = new Label();
	@FXML private Label lDayU3 = new Label();
	@FXML private Button btDayM4 = new Button();
	@FXML private Button btDayT4 = new Button();
	@FXML private Button btDayW4 = new Button();
	@FXML private Button btDayR4 = new Button();
	@FXML private Button btDayF4 = new Button();
	@FXML private Label lDayS4 = new Label();
	@FXML private Label lDayU4 = new Label();
	@FXML private Button btDayM5 = new Button();
	@FXML private Button btDayT5 = new Button();
	@FXML private TextField tfName = new TextField();
	@FXML private RadioButton rbDoesAdd = new RadioButton();
	@FXML private RadioButton rbDoesDelete = new RadioButton();
	// @FXML private ToggleGroup clickConsequence = new ToggleGroup(); // do I need this?
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
			controlsThatShowDay.add(btNavEarlier); // ignorable convenience trades heap for math
			controlsThatShowDay.add(btDayM0);
			controlsThatShowDay.add(btDayT0);
			controlsThatShowDay.add(btDayW0);
			controlsThatShowDay.add(btDayR0);
			controlsThatShowDay.add(btDayF0);
			controlsThatShowDay.add(lDayS0);
			controlsThatShowDay.add(lDayU0);
			controlsThatShowDay.add(btDayM1);
			controlsThatShowDay.add(btDayT1);
			controlsThatShowDay.add(btDayW1);
			controlsThatShowDay.add(btDayR1);
			controlsThatShowDay.add(btDayF1);
			controlsThatShowDay.add(lDayS1);
			controlsThatShowDay.add(lDayU1);
			controlsThatShowDay.add(btDayM2);
			controlsThatShowDay.add(btDayT2);
			controlsThatShowDay.add(btDayW2);
			controlsThatShowDay.add(btDayR2);
			controlsThatShowDay.add(btDayF2);
			controlsThatShowDay.add(lDayS2);
			controlsThatShowDay.add(lDayU2);
			controlsThatShowDay.add(btDayM3);
			controlsThatShowDay.add(btDayT3);
			controlsThatShowDay.add(btDayW3);
			controlsThatShowDay.add(btDayR3);
			controlsThatShowDay.add(btDayF3);
			controlsThatShowDay.add(lDayS3);
			controlsThatShowDay.add(lDayU3);
			controlsThatShowDay.add(btDayM4);
			controlsThatShowDay.add(btDayT4);
			controlsThatShowDay.add(btDayW4);
			controlsThatShowDay.add(btDayR4);
			controlsThatShowDay.add(btDayF4);
			controlsThatShowDay.add(lDayS4);
			controlsThatShowDay.add(lDayU4);
			controlsThatShowDay.add(btDayM5);
			controlsThatShowDay.add(btDayT5);
		}
	}

	@FXML
	/** IMPROVE eventually reset all the text */
	public void choseYear( ActionEvent whatHappened )
	{
		String selection = cbNavYear.getValue();
		if ( selection != null && ! selection.equals("Year") )
		{
			year = Integer.parseInt(selection);
		}
		clearTheVisibleCalendar();
	}

	private void clearTheVisibleCalendar()
	{
		int skipZeroIsInvalidDay = 1;
		for ( int ind = skipZeroIsInvalidDay; ind < controlsThatShowDay.size(); ind++ )
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
			currMonth = YearMonth.of(
					year, monthIndexFromMonthName( selection ));
			refreshMainCalendar();
		}
	}

	private void refreshMainCalendar()
	{
		prepControlList();
		List<String> newLabels = vacationPreferences.getLabelsFor(currMonth);
		int ind = 1; // skip 0
		for ( String text : newLabels )
		{
			controlsThatShowDay.get( ind ).setText( text );
			ind++;
		}
	}

	/** if I made them all caps, I could use the enum Month.valueOf() */
	private int monthIndexFromMonthName( String monthName )
	{
		// IMPROVE getItems() and find the index +1
		switch ( monthName )
		{
		case "January" :
			return 1;
		case "February" :
			return 2;
		case "March" :
			return 3;
		case "April" :
			return 4;
		case "May" :
			return 5;
		case "June" :
			return 6;
		case "July" :
			return 7;
		case "August" :
			return 8;
		case "September" :
			return 9;
		case "October" :
			return 10;
		case "November" :
			return 11;
		case "December" :
			return 12;
		default :
			return 0;
		}
	}

	@FXML
	/** show and work with the previous month ; wraps rather than increments year */
	public void pressedNavEarlier( ActionEvent whatHappened )
	{
		String selected = cbNavMonth.getValue();
		ObservableList<String> monthList = cbNavMonth.getItems();
		if ( selected == null || selected.equals( "Month" ) )
		{
			// nothing selected, I guess I could say earlier than this yearmonth
			return;
		}
		else if ( selected.equals("January") )
		{
			cbNavMonth.setValue( monthList.get( monthList.size() -1 ) );
		}
		else
		{
			String previous = "";
			for ( String mon : monthList )
			{
				if ( selected.equals( previous ) )
				{
					break;
				}
				else
				{
					previous = mon;
				}
			}
			cbNavMonth.setValue( previous );
		}
		choseMonth( whatHappened );
	}

	@FXML
	/** show and work with the next month ; wraps rather than increments year */
	public void pressedNavLater( ActionEvent whatHappened )
	{
		String selected = cbNavMonth.getValue();
		ObservableList<String> monthList = cbNavMonth.getItems();
		if ( selected == null || selected.equals( "Month" ) )
		{
			// nothing selected, I guess I could say earlier than this yearmonth
			return;
		}
		else if ( selected.equals("December") )
		{
			cbNavMonth.setValue( monthList.get( 0 ));
		}
		else
		{
			String target = "";
			for ( String mon : monthList )
			{
				if ( selected.equals( mon ) )
				{
					target = mon;
				}
				else if ( ! target.isEmpty() )
				{
					target = mon;
					break;
				}
			}
			cbNavMonth.setValue( target );
		}
		choseMonth( whatHappened );
	}

	/** save or remove info from input panel */
	public void pressedDay( ActionEvent whatHappened )
	{
		String here = cl +"pd ";
		String maybeName = tfName.getText();
		if ( maybeName.isEmpty() )
		{
			System.out.println( here +"name empty" );
			return;
		}
		// IMPROVE more validation
		Button pressee = (Button)whatHappened.getSource();
		LocalDate dateOfComponent = dateFromComponent( pressee );
		if ( dateOfComponent == null )
		{
			return;
		}
		if ( rbDoesAdd.isSelected() )
		{
			addInfoToDay( dateOfComponent, maybeName );
		}
		refreshMainCalendar();
	}

	private LocalDate dateFromComponent( Button whichBtn )
	{
		String here = cl +"dfc ";
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
			dayNumber = btnLabel.substring(0, indOfNonNumber);
		}
		else
		{
			dayNumber = btnLabel;
		}
		int dayOfMonth = Integer.parseInt( dayNumber );
		// assuming nothing went wrong with the day labels
		return currMonth.atDay(dayOfMonth);
	}

	private void addInfoToDay( LocalDate dateOfComponent, String name )
	{
		vacationPreferences.addPersonName(dateOfComponent, name);
		
	}

	public void receiveModel( Desires peopleAndWhatTheyWant )
	{
		vacationPreferences = peopleAndWhatTheyWant;
	}

}



























