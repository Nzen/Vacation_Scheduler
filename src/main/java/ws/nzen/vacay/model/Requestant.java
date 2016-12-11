/**
 * 
 */
package main.java.ws.nzen.vacay.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author nzen
 *
 */
public class Requestant implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String name;
	private int seniority = -1;
	private ArrayList< HashSet<LocalDate> > requestedDays;
	private int activeLevel = -1;

	public Requestant( String appelation, int personLevel )
	{
		name = appelation;
		seniority = personLevel;
		requestedDays = new ArrayList< HashSet<LocalDate> >();
	}

	// IMPROVE return boolean : whether add did anything 
	public void addDay( int care, LocalDate when )
	{
		ensureSpaceFor( care );
		HashSet<LocalDate> previousRequests = requestedDays.get( care );
		if ( previousRequests == null )
		{
			HashSet<LocalDate> requestsForCare = new HashSet<>();
			requestsForCare.add( when );
			requestedDays.set( care, requestsForCare );
		}
		else
		{
			previousRequests.add( when );
		}
	}

	private void ensureSpaceFor( int desire )
	{
		if ( desire >= requestedDays.size() )
		{
			// make spots for spaces in between
			for ( int ind = requestedDays.size(); ind <= desire; ind++ )
			{
				requestedDays.add( null );
			}
		}
	}

	public void removeDay( int care, LocalDate when )
	{
		if ( care > requestedDays.size() )
		{
			return;
		}
		HashSet<LocalDate> previousRequests = requestedDays.get( care );
		if ( previousRequests == null )
		{
			return;
		}
		else
		{
			previousRequests.remove( when );
		}
	}

	@Deprecated
	public Iterator<HashSet<LocalDate>> getDaysOf()
	{
		return requestedDays.iterator();
	}

	/** set of days of that level or null */
	public Set<LocalDate> getDaysOfLevel( int desirability )
	{
		// desirability -= 1; // for zero index -- this is a bad idea if I don't transform human to machine
		if ( desirability > requestedDays.size() )
			return null;
		else
			return requestedDays.get( desirability );
	}

	public int getLevelsOfDesire()
	{
		return requestedDays.size();
	}

	public boolean hasAnyDays()
	{
		final boolean hasAtLeastOne = true;
		for ( HashSet<LocalDate> requests : requestedDays )
		{
			if ( requests.size() > 0 )
			{
				return hasAtLeastOne;
			}
		}
		return ! hasAtLeastOne;
	}

	public boolean hasActiveDesire()
	{
		return activeLevel >= 0;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public int getSeniority()
	{
		return seniority;
	}

	public void setSeniority( int seniority )
	{
		this.seniority = seniority;
	}

	public int getActiveLevel()
	{
		return activeLevel;
	}

	public void setActiveLevel( int activeLevel )
	{
		this.activeLevel = activeLevel;
	}

}




















