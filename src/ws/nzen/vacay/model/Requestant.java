/**
 * 
 */
package ws.nzen.vacay.model;

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
	private ArrayList< HashSet<LocalDate> > requestedDays;

	public Requestant( String appelation )
	{
		name = appelation;
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

	public Iterator<HashSet<LocalDate>> getDaysOf()
	{
		return requestedDays.iterator();
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

}




















