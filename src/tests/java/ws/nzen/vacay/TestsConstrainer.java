/**
 * 
 */
package tests.java.ws.nzen.vacay;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import main.java.ws.nzen.vacay.Constrainer;
import main.java.ws.nzen.vacay.model.Requestant;

/**
 * @author nzen
 *
 */
public class TestsConstrainer
{
	private Constrainer fixesCalendar;
	private List<Requestant> everyone = new ArrayList<>();
	private Map<LocalDate, List<Requestant>> requests = new TreeMap<>();
	private int activeSpaces = 1;
	private int year = 2015;
	private int levelOne = 0;
	private int levelTwo = 1;
	private int levelThree = 2;
	private LocalDate jan1 = LocalDate.of( year, 1, 1 );
	private LocalDate jan2 = LocalDate.of( year, 1, 2 );
	private LocalDate jan3 = LocalDate.of( year, 1, 3 );

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception
	{
		fixesCalendar = new Constrainer( activeSpaces, everyone, year );
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception
	{
		everyone.clear();
		requests.clear();
	}

	/**
	 * Test method for {@link main.java.ws.nzen.vacay.Constrainer#addRequest(java.time.LocalDate, main.java.ws.nzen.vacay.model.Requestant, int, java.util.Map)}.
	 */
	@Test
	public void testFirstAddRequest()
	{
		Requestant alpha = new Requestant( "alpha", levelOne );
		alpha.addDay( levelOne, jan1 );
		everyone.add( alpha );
		requests.put( jan1, new ArrayList<Requestant>() );
		List<Requestant> prob = fixesCalendar.addRequest(
				jan1, alpha, levelOne, requests );
		assertTrue( "first insert must return null", prob == null );
		assertTrue( "first insert with no conflict must activate person",
				alpha.hasActiveDesire() );
		List<Requestant> whoOnJan1 = requests.get( jan1 );
		assertEquals( "first person should be alpha",
				whoOnJan1.get( 0 ).getName(), alpha.getName() );
	}

	/**
	 * Test method for {@link main.java.ws.nzen.vacay.Constrainer#addRequest(java.time.LocalDate, main.java.ws.nzen.vacay.model.Requestant, int, java.util.Map)}.
	 */
	@Test
	public void testOpenSpotAddRequest()
	{
		testFirstAddRequest();
		Requestant alpha = everyone.get( levelOne );
		int alphaDesireLevel = alpha.getActiveLevel();
		Requestant beta = new Requestant( "beta", levelTwo );
		everyone.add( beta );
		beta.addDay( levelTwo, jan2 );
		List<Requestant> prob = fixesCalendar
				.addRequest( jan2, beta, levelTwo, requests );
		assertTrue( "first insert to day must return null", prob == null );
		assertTrue( "first insert with no conflict must activate person",
				beta.hasActiveDesire() );
		assertEquals( "insert with no conflict must activate person",
				alpha.getActiveLevel(), alphaDesireLevel );
		List<Requestant> whoOnJan2 = requests.get( jan2 );
		assertEquals( "first person should be beta",
				whoOnJan2.get( 0 ).getName(), beta.getName() );
	}

	/**
	 * Test method for {@link main.java.ws.nzen.vacay.Constrainer#addRequest(java.time.LocalDate, main.java.ws.nzen.vacay.model.Requestant, int, java.util.Map)}.
	 */
	@Test
	public void testInactiveStaysInactiveAddRequest()
	{
		testFirstAddRequest();
		Requestant beta = new Requestant( "beta", 0 );
		beta.addDay( levelOne, jan1 );
		List<Requestant> prob = fixesCalendar
				.addRequest( jan1, beta, levelTwo, requests );
		assertFalse( "beta should not be active", beta.hasActiveDesire() );
	}

	/**
	 * Test method for {@link main.java.ws.nzen.vacay.Constrainer#addRequest(java.time.LocalDate, main.java.ws.nzen.vacay.model.Requestant, int, java.util.Map)}.
	 */
	@Test
	public void testActivePushedDownAddRequest()
	{
		testOpenSpotAddRequest();
		Requestant alpha = everyone.get( levelOne );
		Requestant beta = everyone.get( levelTwo );
		alpha.addDay( levelOne, jan2 );
		List<Requestant> prob = fixesCalendar.addRequest(
				jan2, alpha, levelOne, requests );
		assertTrue( "prob should have beta in it",
				prob != null && prob.size() > 0 );
		assertFalse( "beta should not be active", beta.hasActiveDesire() );
		assertEquals( "first on day should be alpha",
				requests.get( jan2 ).get( levelOne ).getName(),
				alpha.getName() );
		assertEquals( "third on day should be beta",
				requests.get( jan2 )
				.get( levelTwo ).getName(),
				beta.getName() );
		// NOTE alpha vacated spot 1. I may want to 'delete' the vacated spot rather than end with a sparse list
	}

	/*
	 * Test method for {@link main.java.ws.nzen.vacay.Constrainer#addRequest(java.time.LocalDate, main.java.ws.nzen.vacay.model.Requestant, int, java.util.Map)}.
	 *
	@Test
	public void testAddRequest()
	{
		fail( "Not yet implemented" );
	}*/

	/*
	 * Test method for {@link main.java.ws.nzen.vacay.Constrainer#removeRequest(java.time.LocalDate, main.java.ws.nzen.vacay.model.Requestant, int, java.util.Map)}.
	 /
	@Test
	public void testRemoveRequest()
	{
		fail( "Not yet implemented" );
	}*/

}





























