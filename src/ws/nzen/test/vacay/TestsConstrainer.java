package ws.nzen.test.vacay;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import ws.nzen.vacay.Constrainer;
import ws.nzen.vacay.model.Requestant;

public class TestsConstrainer
{
	private Constrainer fixesCalendar;
	int activeSpaces = 1;
	int year = 2015;

	public TestsConstrainer()
	{
		fixesCalendar = new Constrainer( activeSpaces, new ArrayList<Requestant>(), year );
	}

	public void addSimple()
	{
		List<Requestant> everyone = new ArrayList<>();
		Map<LocalDate, List<Requestant>> scheduledRequests = new TreeMap<>();
		Requestant alpha = new Requestant( "1", 0 );
		everyone.add( alpha );
		LocalDate jan1 = LocalDate.of( year, 1, 1 );
		int levelOne = 0;
		alpha.addDay( levelOne, jan1 );
		fixesCalendar.setPeople( everyone );
		scheduledRequests.put( jan1, new ArrayList<Requestant>() );
		List<Requestant> prob = fixesCalendar.addRequest(
				jan1, alpha, levelOne, scheduledRequests );
		if ( prob != null )
		{
			System.out.println( "first insert must return null" );
		}
		if ( alpha.getActiveLevel() < 0 )
		{
			System.out.println( "first insert with no conflict must activate person" );
		}
		System.out.println( "sr\t"+ scheduledRequests );
		LocalDate jan2 = LocalDate.of( year, 1, 2 );
		Requestant beta = new Requestant( "2", 0 );
		int levelTwo = 1;
		beta.addDay( levelTwo, jan2 );
		prob = fixesCalendar.addRequest( jan2, beta, levelTwo, scheduledRequests );
		if ( prob != null )
		{
			System.out.println( "unconflicting insert must return null" );
		}
		//Requestant gamma = new Requestant( "3", 0 );
	}

	private int firstInactiveSpace()
	{
		return activeSpaces;
	}

}




























