/**
 * 
 */
package main.java.ws.nzen.vacay;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import main.java.ws.nzen.vacay.model.Requestant;

/**
 * @author nzen
 *
 */
public class Constrainer
{
	private int activeArea = 1;
	private int year = 2016;
	List<Requestant> people;

	public Constrainer( int spacesActive, List<Requestant> peopleSaved,
			int year )
	{
		if ( spacesActive > 0 )
		{
			activeArea = spacesActive;
		}
		people = peopleSaved;
		this.year = year;
	}

	/** returns conflicts or totally inactive people ; consider returning requestants */
	public List<Requestant> addRequest( LocalDate when, Requestant who, int desire,
			Map<LocalDate, List< Requestant >> scheduledRequests )
	{
		List< Requestant > peopleOfDay = scheduledRequests.get( when );
		peopleOfDay = ensureListForDayIsReady( when, peopleOfDay, scheduledRequests );
		if ( who.hasActiveDesire() )
		{
			if ( desire == who.getActiveLevel() )
			{
				if ( activeSpotOpen( peopleOfDay ) )
				{
					peopleOfDay.set( indexOfOpenActiveSpot( peopleOfDay ), who );
					return null;
				}
				else
				{
					peopleOfDay.add( who );
					return constrainFromScratch( scheduledRequests );
				}
			}
			else if ( desire > who.getActiveLevel() )
			{
				// other is already active, just mark it
				peopleOfDay.add( who );
				return null;
			}
			else
			{
				/*
				check whether this activates the previous level,
				ex, a puts desire 2 then desire 1 ; 2 was active, does 1 work ?
				is it cheaper to test more ?
				*/
				peopleOfDay.add( who );
				return constrainFromScratch( scheduledRequests );
			}
		}
		else
		{
			if ( activeSpotOpen( peopleOfDay ) )
			{
				who.setActiveLevel( desire );
				peopleOfDay.set( indexOfOpenActiveSpot( peopleOfDay ), who );
				return null;
			}
			else
			{
				peopleOfDay.add( who );
				return null;
			}
		}
	}

	private List<Requestant> ensureListForDayIsReady( LocalDate when, List< Requestant > peopleOfDay,
			Map<LocalDate, List< Requestant >> scheduledRequests )
	{
		if ( peopleOfDay == null )
		{
			peopleOfDay = new ArrayList<>();
			for ( int ind = 0; ind < activeArea; ind++ )
			{
				peopleOfDay.add( null );
			}
			scheduledRequests.put( when, peopleOfDay );
		}
		else if ( peopleOfDay.size() < activeArea )
		{
			for ( int ind = 0; ind < activeArea; ind++ )
			{
				peopleOfDay.add( null );
			}
		}
		return peopleOfDay;
	}

	private boolean activeSpotOpen( List< Requestant > peopleOnDay )
	{
		for ( int ind = 0; ind < activeArea; ind++ )
		{
			if ( peopleOnDay.get( ind ) == null )
			{
				return true;
			}
		}
		return false;
	}

	public List<Requestant> removeRequest( LocalDate when, Requestant who, int desire,
			Map<LocalDate, List< Requestant >> scheduledRequests )
	{
		List< Requestant > peopleOnDay = scheduledRequests.get( when );
		if ( peopleOnDay == null )
		{
			return null;
		}
		peopleOnDay.remove( who );
		return constrainFromScratch( scheduledRequests );
	}

	public List<Requestant> prepYearForDisplay(
			Map<LocalDate, List< Requestant >> scheduledRequests )
	{
		return constrainFromScratch( scheduledRequests );
	}

	private List<Requestant> constrainFromScratch(
			Map<LocalDate, List< Requestant >> scheduledRequests )
	{
		/*
		for desire levels of the most desirous person
			for people without an active level
				for day in this person's range at this level
					if active spot empty
						move to active
					else if desire equal and seniority greater
						move other to inactive
						move to active
						move other's days to inactive for that level
						mark other as inactive
					else
						inactivate preceeding active days of this level
						break
				mark this level as active
		*/
		inactivateEveryone( scheduledRequests );
		int levels = weakestDesireOfEveryone();
		for ( int desire = 0; desire < levels; desire++ )
		{
			for ( Requestant currPerson : people )
			{
				if ( currPerson == null
					|| currPerson.getActiveLevel() >= 0 )
				{
					continue;
				}
				Set<LocalDate> daysOfLevel = currPerson.getDaysOfLevel( desire );
				if ( daysOfLevel == null
					|| daysOfLevel.size() < 1 )
				{
					// don't have any of this level
					continue;
				}
				LocalDate dayItWentBad = null;
				for ( LocalDate currDay : daysOfLevel )
				{
					if ( currDay.getYear() != year )
					{
						continue;
					}
					List<Requestant> peopleOfDay = scheduledRequests
							.get( currDay );
					if ( activeSpotOpen( peopleOfDay ) )
					{
						int inactiveInd = peopleOfDay.indexOf( currPerson );
						int indOfSpotToFill = indexOfOpenActiveSpot( peopleOfDay );
						if ( inactiveInd < 0 ) // um why you wrong? you no get added?
							System.out.println("4TESTS");//throw implementation exception, activeSpotOpen lied
						peopleOfDay.set( inactiveInd, null );
						peopleOfDay.set( indOfSpotToFill, currPerson );
					}
					else if ( desireEqualButSeniorityLesser( desire,
							currPerson.getSeniority(), peopleOfDay ) )
					{
						int indOfDisplacee = indexDesireEqualButLessSenior( desire, 
								currPerson.getSeniority(), peopleOfDay );
						inactivatePersonForDesireLevel( peopleOfDay.get( indOfDisplacee ),
								desire, scheduledRequests );
						int inactiveInd = peopleOfDay.indexOf( currPerson );
						peopleOfDay.set( inactiveInd, null );
						int indOfSpotToFill = indexOfOpenActiveSpot( peopleOfDay );
						peopleOfDay.set( indOfSpotToFill, currPerson );
					}
					else
					{
						dayItWentBad = currDay;
						break;
					}
				}
				if ( dayItWentBad == null )
				{
					currPerson.setActiveLevel( desire );
				}
				else
				{
					for ( LocalDate currDay : daysOfLevel )
					{
						if ( currDay == dayItWentBad )
						{
							break; // inactivated all of this level
						}
						else
						{
							List<Requestant> peopleOnThen = scheduledRequests
									.get( currDay );
							int indOfActivePerson = peopleOnThen.indexOf( currPerson );
							peopleOnThen.set( indOfActivePerson, null );
							peopleOnThen.add( currPerson );
						}
					}
				}
			}
		}
		// IMPROVE defragment null spots after activeArea
		List<Requestant> problems = new ArrayList<>();
		// check for inactive people
		for ( Requestant currPerson : people )
		{
			if ( currPerson.getActiveLevel() < 0 )
			{
				problems.add( currPerson );
			}
		}
		return problems;
	}

	private void inactivateEveryone(
			Map<LocalDate, List< Requestant >> scheduledRequests )
	{
		for ( Requestant currPerson : people )
		{
			currPerson.setActiveLevel( -1 ); // IMPROVE handle per year?
		}
		for ( LocalDate currDay : scheduledRequests.keySet() )
		{
			if ( currDay.getYear() == year )
			{
				List<Requestant> peopleOfDay = ensureListForDayIsReady( 
						currDay, scheduledRequests.get( currDay ), scheduledRequests );
				emptyActiveSpots( peopleOfDay );
			}
		}
	}

	private void inactivatePersonForDesireLevel( Requestant bumped, int formerlyActiveLevel,
			Map<LocalDate, List< Requestant >> scheduledRequests )
	{
		Set<LocalDate> daysForLevel = bumped.getDaysOfLevel( formerlyActiveLevel );
		for ( LocalDate currDay : daysForLevel )
		{
			if ( currDay.getYear() == year )
			{
				List<Requestant> peopleOnDay = scheduledRequests.get( currDay );
				int firstOccurrance = peopleOnDay.indexOf( bumped );
				if ( firstOccurrance < activeArea )
				{
					peopleOnDay.add( bumped );
					peopleOnDay.set( firstOccurrance, null );
				}
			}
		}
		bumped.setActiveLevel( -1 );
	}

	private void emptyActiveSpots( List<Requestant> peopleOnDay )
	{
		if ( peopleOnDay.size() < activeArea )
		{
			for ( int ind = peopleOnDay.size(); ind < activeArea; ind++ )
			{
				peopleOnDay.add( null );
			}
		}
		for ( int ind = 0; ind < activeArea; ind++ )
		{
			Requestant currPerson = peopleOnDay.get( ind );
			if ( currPerson != null )
			{
				peopleOnDay.add( currPerson );
				peopleOnDay.set( ind, null );
			}
		}
	}

	private int maxSeniority()
	{
		int maxSeniority = 0;
		for ( Requestant currPerson : people )
		{
			if ( maxSeniority < currPerson.getSeniority() )
			{
				maxSeniority = currPerson.getSeniority();
			}
		}
		return maxSeniority;
	}

	/** lowest level of currently declared desire */
	private int weakestDesireOfEveryone()
	{
		int maxdesire = 0;/*people.stream().max( 
			( Requestant prev, Requestant curr ) ->
			{
				if ( prev.getLevelsOfDesire() > curr.getLevelsOfDesire() )
					return -1;
				else if ( prev.getLevelsOfDesire() == curr.getLevelsOfDesire() )
					return 0;
				else
					return 1;
			} // could not convert from Optional to int :\
		);*/
		for ( Requestant currPerson : people )
		{
			if ( maxdesire < currPerson.getLevelsOfDesire() )
			{
				maxdesire = currPerson.getLevelsOfDesire();
			}
		}
		return maxdesire;
	}

	private int lowestSeniorityFor( List<Requestant> peopleOnDay )
	{
		int lowest = -1;
		for ( int ind = 0; ind < activeArea; ind++ )
		{
			Requestant currPerson = peopleOnDay.get( ind );
			if ( ! (currPerson == null
				|| currPerson.getSeniority() <= lowest) )
			{
				lowest = currPerson.getSeniority();
			}
		}
		if ( lowest < 0 )
		{
			lowest = peopleOnDay.size();
		}
		return lowest;
	}

	private boolean desireEqualButSeniorityLesser( int currentDesire,
			int currSeniority, List<Requestant> peopleOfDay )
	{
		return indexDesireEqualButLessSenior( currentDesire,
				currSeniority, peopleOfDay ) < activeArea;
	}

	private int indexDesireEqualButLessSenior( int otherDesire,
			int otherSeniority, List<Requestant> peopleOfDay )
	{
		int index = peopleOfDay.size();
		for ( int ind = 0; ind < activeArea; ind++ )
		{
			Requestant currPerson = peopleOfDay.get( ind );
			if ( currPerson == null )
			{
				continue;
			}
			else if ( currPerson.getActiveLevel() == otherDesire
				&& currPerson.getSeniority() > otherSeniority )
			{
				index = ind;
				break;
			}
		}
		return index;
	}

	/** index or -1 */
	private int indexOfOpenActiveSpot( List<Requestant> peopleOfDay )
	{
		int indOfSpot = -1;
		for ( int ind = 0; ind < activeArea; ind++ )
		{
			Requestant currPerson = peopleOfDay.get( ind );
			if ( currPerson == null )
			{
				indOfSpot = ind;
				break;
			}
		}
		return indOfSpot;
	}

	public void setActiveArea( int activeArea )
	{
		this.activeArea = activeArea;
	}

	public void setPeople( List<Requestant> people )
	{
		this.people = people;
	}

	public int getYear()
	{
		return year;
	}

	public void setYear( int year )
	{
		this.year = year;
	}

}
/*
	attempts at constraining algorithm

for people in active [] // impossible is 'active'
	for desire levels until one satisfied or all exhausted
		for days in desire level
			if active spot empty
				add day
			else
				if desire greater OR (desire equal & senior greater)
					replace other person's day
					mark other inactive
					remove other's days of that level
					// I see a state machine implicit in here { shunting, probing }
				else
					mark as inactive
					move previously added days of this level to inactive
					add remaining days of level as inactive
					continue outer loop
	people ind = 0

	----

	for person in people
		if active
			continue
		
*/



























