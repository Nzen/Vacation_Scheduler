/**
 * 
 */
package ws.nzen.vacay.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

/**
 * @author nzen
 *
 */
public class Requestant implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String name;
	private int seniority;
	private ArrayList< HashSet<LocalDate> > requestedDays;

	public Requestant( String appelation, int priority )
	{
		name = appelation;
		seniority = priority;
		requestedDays = new ArrayList< HashSet<LocalDate> >();
	}

	public void addDay( int care, LocalDate when )
	{
		if ( care > requestedDays.size() )
		{
			for ( int ind = requestedDays.size(); ind < care; ind++ )
			{
				requestedDays.add( null );
			}
		}
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

}




















