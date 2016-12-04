/**
 * 
 */
package ws.nzen.vacay;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import ws.nzen.vacay.model.Requestant;
import ws.nzen.vacay.model.Settings;

/** @author nzen */
public class Desires
{ // VacationCalendar?

	static final String cl = "d.";
	private int labelsShown = 37;
	private String persistedFilename = "people.ser";
	private Settings userConfig = new Settings();

	enum RequestViability
	{
		inoffensive, movesPerson, displacesAnother;
	}

	private Map<LocalDate, List< Requestant >> dayToPeople = new HashMap<>();
	private List<Requestant> people = new ArrayList<>();

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
				if ( userConfig.isHoliday( currDay ) )
				{
					labelText += " Holiday";
				}
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
		String label = "";
		List<Requestant> calledDibs = dayToPeople.get( when );
		if ( ! (calledDibs == null || calledDibs.isEmpty()) )
		{
			for ( Requestant currPerson : calledDibs )
			{
				// IMPROVE handle first differently
				label += ", "+ currPerson.getName();
			}
		}
		/*else
		{
			System.out.println( "no one for "+ when );
		}*/
		return label;
	}

	public boolean personPresent( String who )
	{
		boolean alreadySeen = true;
		for ( Requestant currPerson : people )
		{
			if ( currPerson == null )
			{
				continue;
			}
			else if ( currPerson.getName().equals( who ) )
			{
				return alreadySeen;
			}
		}
		return ! alreadySeen;
	}

	public int personSeniority( String who )
	{
		int ind = 0;
		for ( Requestant currPerson : people )
		{
			if ( currPerson != null
				&& currPerson.getName().equals( who ) )
			{
				return ind;
			}
			else
			{
				ind++;
			}
		}
		return -1;
	}

	public String personOfSeniority( int seniority )
	{
		if ( seniority >= people.size() )
		{
			return "";
		}
		Requestant currPerson = people.get( seniority );
		if ( currPerson == null )
		{
			return "";
		}
		else
		{
			return currPerson.getName();
		}
	}

	public RequestViability requestChangesSeniority( String who, int seniority )
	{
		if ( seniority >= people.size() )
		{
			return RequestViability.inoffensive;
		}
		Requestant currPerson = people.get( seniority );
		if ( currPerson == null )
		{
			if ( personPresent( who ) )
			{
				return RequestViability.movesPerson;
			}
			else
			{
				return RequestViability.inoffensive;
			}
		}
		else if ( currPerson.getName().equals( who ) )
		{
			return RequestViability.inoffensive;
		}
		else
		{
			return RequestViability.displacesAnother;
		}
	}

	/** add request without overwriting when seniority already taken by someone else */
	public void addRequest( String who, int seniority,
			int desirability, LocalDate when )
	{
		boolean overwriteOriginalIfSeniorityConflicts = false;
		addRequest( who, seniority, desirability, when,
				overwriteOriginalIfSeniorityConflicts );
	}

	public void addRequest( String who, int seniority,
			int desirability, LocalDate when, boolean overWritePerson )
	{
		Requestant charge;
		ensureSpaceFor( seniority );
		int seniorness = personSeniority( who );
		if ( seniorness < 0 )
		{
			// new person
			charge = new Requestant( who );
			people.set( seniority, charge );
		}
		else if ( seniority != seniorness )
		{
			// different val than before
			charge = move( who, seniority, seniorness, overWritePerson );
		}
		else
		{
			// we've seen you before
			charge = people.get( seniority );
		} 
		charge.addDay( desirability, when );
		// IMPROVE if ! added() skip below
		List<Requestant> currPeopleOnDay = dayToPeople.get( when );
		if ( currPeopleOnDay == null )
		{
			currPeopleOnDay = new LinkedList<>();
			currPeopleOnDay.add( charge );
			dayToPeople.put( when, currPeopleOnDay );
		}
		else
		{
			// FIX !! actually constrain instead
			if ( ! currPeopleOnDay.contains( charge ) )
			{
				currPeopleOnDay.add( charge );
			}
		}
	}

	private void ensureSpaceFor( int seniority )
	{
		if ( people.size() <= seniority )
		{
			for ( int ind = people.size(); ind <= seniority; ind++ )
			{
				people.add( null );
			}
		}
	}

	/** resolve request for who with different than previous seniority */
	private Requestant move( String who, int seniority,
			int seniorness, boolean overWritePerson )
	{
		Requestant charge = people.get( seniorness );
		if ( people.get( seniority ) == null )
		{
			people.set( seniority, charge );
			people.set( seniorness, null );
		}
		else if ( overWritePerson )
		{
			people.set( seniority, charge );
		}
		else
		{
			Requestant displacee = people.get( seniority );
			people.set( seniority, charge );
			people.set( seniorness, displacee );
			// ASK set to the back instead ?
		}
		// re-constrain affected choices
		return charge;
	}

	/**  */
	public void removeRequest( String who, int seniority,
			int desirability, LocalDate when )
	{
		if ( ! personPresent( who ) )
		{
			return;
		}
		Requestant person = people.get( seniority );
		person.removeDay( desirability, when );
		if ( ! person.hasAnyDays() )
		{
			people.remove( person );
		}
		List<Requestant> currPeopleOnDay = dayToPeople.get( when );
		currPeopleOnDay.remove( person );
	}

	public void persist()
	{
		final String here = cl + "p ";
		if ( people.size() < 1 )
		{
			System.out.println( here +"nothing to save yet" );
			return; // ASK alert? it seems heavy handed
		}
		Requestant[] flatPeople = people.toArray( new Requestant[ people.size() ] );
		try ( FileOutputStream fos = new FileOutputStream( persistedFilename );
				ObjectOutputStream oos = new ObjectOutputStream(fos); )
		{
			oos.writeObject( flatPeople );
		}
		catch ( IOException ioe )
		{
			System.out.println( here +"couldn't persist people because "
					+ ioe );
		}
	}

	public boolean restorePersisted()
	{
		final String here = cl + "rp ";
		final boolean worked = true;
		try ( FileInputStream fis = new FileInputStream( persistedFilename );
				ObjectInputStream ois = new ObjectInputStream(fis); )
		{
			Requestant[] flatPeople = (Requestant[])ois.readObject();
			if ( flatPeople == null )
				return ! worked;
			if ( people != null )
				people.clear();
			else
				people = new ArrayList<>( flatPeople.length );
			if ( dayToPeople != null )
				dayToPeople.clear();
			else
				dayToPeople = new HashMap<LocalDate, List<Requestant>>();
			for ( Requestant aPersonDesires : flatPeople )
			{
				people.add( (Requestant)aPersonDesires );
				Iterator<HashSet<LocalDate>> when = aPersonDesires.getDaysOf();
				while ( when.hasNext() )
				{
					Iterator<LocalDate> whenOfLevel = when.next().iterator();
					while ( whenOfLevel.hasNext() )
					{
						LocalDate exactlyWhen = whenOfLevel.next();
						// FIX I'm going to have to do this again, for real, when I have a constrainer
						List<Requestant> reservees = dayToPeople.get( whenOfLevel );
						if ( reservees == null )
						{
							reservees = new ArrayList<>();
						}
						reservees.add( aPersonDesires );
						dayToPeople.put( exactlyWhen, reservees );
					}
				}
			}
			return worked;
		}
		catch ( FileNotFoundException fnfe )
		{
			System.err.println( here +"no persisted people file"
					+ " because "+ fnfe );
		}
		catch ( IOException ie )
		{
			System.err.println( here +"inaccessible persisted people file"
					+ " because "+ ie );
		}
		catch ( ClassNotFoundException cne )
		{
			System.err.println( here +"restored from a non-people package?"
					+ "\n fail because "+ cne );
		}
		catch ( ClassCastException cce )
		{
			System.err.println( here +"restored from a non-people file"
					+ "\n fail because "+ cce );
		}
		return ! worked;
	}

	public void receiveSettings( Settings settingsGathered )
	{
		if ( settingsGathered != null )
			userConfig = settingsGathered;
	}

}





















