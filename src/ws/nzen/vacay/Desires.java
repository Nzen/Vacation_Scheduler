/**
 * 
 */
package ws.nzen.vacay;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** @author nzen */
public class Desires
{ // VacationCalendar?

	static final String cl = "d.";
	private int labelsShown = 37;

	private Map<LocalDate, String> dayToPeople = new HashMap<>();

	public List<String> getLabelsFor( YearMonth aMonth )
	{
		List<String> theDisplayValsOfMonth = new ArrayList<>( labelsShown );
		theDisplayValsOfMonth = applyTheDateLabels( theDisplayValsOfMonth,
				aMonth );
		/* get the day labels
		 * paste in the people who won */
		return theDisplayValsOfMonth;
	}

	private List<String> applyTheDateLabels( List<String> receivesDays,
			YearMonth currMonth )
	{
		String here = cl + "atdl ";
		String nonDayLabel = "..";
		LocalDate firstDayOfMonth = currMonth.atDay( 1 );
		int indexOfFirstDay = firstDayOfMonth.get( ChronoField.DAY_OF_WEEK )
				- 1;
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
				String labelText = Integer.toString( dayToShow );
				String allocators = desireFor( currDay );

				if ( ! allocators.isEmpty() )
					labelText += " " + allocators;
				receivesDays.add( labelText );
				dayToShow++;
			}
		}
		return receivesDays;
	}

	private String desireFor( LocalDate when )
	{
		String savedPeople = dayToPeople.get( when );
		if ( savedPeople == null )
			return "";
		else
			return savedPeople;
	}

	/* people
	 * their desires
	 * view model ? or is that calculated on the fly ? */

	@Deprecated
	/** likely will supplant,evolve from this */
	public void addPersonName( LocalDate when, String name )
	{
		if ( when == null || name == null || name.isEmpty() )
			return;
		else if ( dayToPeople.containsKey( when ) )
		{
			String aggregatedName = dayToPeople.get( when ) + ", " + name;
			dayToPeople.put( when, aggregatedName );
		}
		else
			dayToPeople.put( when, name );
	}

}
