/**
 * 
 */
package ws.nzen.vacay;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import ws.nzen.vacay.model.Requestant;

/**
 * @author nzen
 *
 */
public class Constrainer
{
	private int activeArea = 1;
	List<Requestant> people;

	public Constrainer( int spacesActive, List<Requestant> peopleSaved )
	{
		if ( spacesActive > 0 )
		{
			activeArea = spacesActive;
		}
		people = peopleSaved;
	}

	/** returns conflicts or totally inactive people ; consider returning requestants */
	public String addRequest( LocalDate when, Requestant who, int desire,
			Map<LocalDate, List< Requestant >> scheduledRequests )
	{
		List< Requestant > peopleOnDay = scheduledRequests.get( when );
		if ( peopleOnDay == null )
		{
			peopleOnDay = new ArrayList<>();
			peopleOnDay.add( who );
			return null;
		}
		else if ( who.getActiveLevel() != desire )
		{
			peopleOnDay.add( who );
			return null;
		}
		else if ( activeSpotOpen( peopleOnDay ) )
		{
			ListIterator<Requestant> forLoop = peopleOnDay.listIterator();
			Requestant currPerson;
			int ind = 0;
			while ( ind < activeArea && forLoop.hasNext() )
			{
				currPerson = forLoop.next();
				if ( currPerson == null )
				{
					forLoop.set( who );
					break;
				}
				ind++;
			}
			currPerson = peopleOnDay.get( ind );
			if ( currPerson != who )
			{
				peopleOnDay.set( ind, who ); // ASK check if this adds it twice
			}
			return null;
		}
		else
		{
			return constrainFromScratch( scheduledRequests );
		}
	}

	private boolean activeSpotOpen( List< Requestant > peopleOnDay )
	{
		for ( int ind = 0; ind < activeArea; ind++ )
		{
			if ( peopleOnDay.get( ind ) != null )
			{
				return false;
			}
		}
		return true;
	}

	public String removeRequest( LocalDate when, Requestant who, int desire,
			Map<LocalDate, List< Requestant >> scheduledRequests )
	{
		List< Requestant > peopleOnDay = scheduledRequests.get( when );
		// if ( peopleOnDay == null ) return null;
		if ( activeSpotOpen( peopleOnDay ) )
		{
			peopleOnDay.remove( who );
			return null;
		}
		else
		{
			return constrainFromScratch( scheduledRequests );
		}
	}

	private String constrainFromScratch(
			Map<LocalDate, List< Requestant >> scheduledRequests )
	{
		String problems = "";
		inactivateEveryone( scheduledRequests );
		boolean[] activePeople = activePeopleFactory();
		// I'll be resetting this to 0 for most times ; potentially nLogn
		for ( int activeInd = 0; activeInd < activePeople.length; activeInd++ )
		{
			if ( activePeople[ activeInd ] )
			{
				continue; // spot satisfied
			}
			Requestant currPerson = people.get( activeInd );
			// ...
		}
		/*
		inactivateEveryone
		for people in active []
			for desire levels until one satisfied
				if active spot empty
					add day
					ind = 0
				else
					if desire greater OR (desire equal & senior greater)
						replace
						add other to inactive
						remove others days of that level
						ind = 0
					else
						mark as inactive
						move days of this level down
						add remaining days of level
						continue outer loop , ind = 0
		*/
		return null; // FIX
		// if problems.isempty return ""; else return problems;
	}

	private void inactivateEveryone(
			Map<LocalDate, List< Requestant >> scheduledRequests )
	{
		for ( Requestant currPerson : people )
		{
			currPerson.setActiveLevel( -1 );
		}
		for ( List<Requestant> currDayPeople : scheduledRequests.values() )
		{
			currDayPeople.clear();
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

	private boolean[] activePeopleFactory()
	{
		boolean[] activePeople = new boolean[ maxSeniority() +1 ];
		int lim = Math.min( activePeople.length, people.size() );
		for ( int ind = 0; ind < lim; ind++ )
		{
			activePeople[ ind ] = people.get( ind ) == null;
			// ie we won't check gaps, but mark real entries inactive
		}
		return activePeople;
	}

	public void setActiveArea( int activeArea )
	{
		this.activeArea = activeArea;
	}

	public void setPeople( List<Requestant> people )
	{
		this.people = people;
	}

}



























