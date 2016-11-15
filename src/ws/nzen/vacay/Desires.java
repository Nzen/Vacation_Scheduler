/**
 * 
 */
package ws.nzen.vacay;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Labeled;

/**
 * @author nzen
 *
 */
public class Desires { // VacationCalendar?

	int labelsShown = 37;

	public List<String> getLabelsFor( YearMonth aMonth )
	{
		List<String> theDisplayValsOfMonth = new ArrayList<>( labelsShown );
		theDisplayValsOfMonth = applyTheDateLabels( theDisplayValsOfMonth, aMonth );
		/*
		get the day labels
		paste in the people who won
		*/
		return theDisplayValsOfMonth;
	}

	private List<String> applyTheDateLabels( List<String> receivesDays, YearMonth currMonth )
	{
		String nonDayLabel = "..";
		LocalDate firstDayOfMonth = currMonth.atDay( 1 );
		int indexOfFirstDay = firstDayOfMonth.get( ChronoField.DAY_OF_WEEK ) -1;
		int daysOfMonth = currMonth.lengthOfMonth();
		int dayToShow = 1;
		for ( int ind = 0; ind < labelsShown; ind++ )
		{
			if ( ind < indexOfFirstDay || dayToShow > daysOfMonth )
			{
				receivesDays.add( nonDayLabel );
			}
			else
			{
				LocalDate currDay = currMonth.atDay( dayToShow );
				receivesDays.add(Integer.toString( dayToShow )
						+" "+ desireFor( currDay ) );
				dayToShow++;
			}
		}
		return receivesDays;
	}

	private String desireFor( LocalDate when )
	{
		int dayOfWeek = when.get(ChronoField.DAY_OF_WEEK); 
		if ( dayOfWeek % 3 == 0 )
			return "BE,NN"; // FIX search a map for this
		else if ( dayOfWeek % 5 == 0 )
			return "BE";
		else
			return "";
	}

	/*
	people
		their desires
	view model ? or is that calculated on the fly ?
	*/

}




























