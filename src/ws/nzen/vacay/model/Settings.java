/**
 * 
 */
package ws.nzen.vacay.model;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nzen
 *
 */
public class Settings
{
	private int peopleOnSameDay = 1;
	private boolean remindSaveOnClose = false;
	private ArrayList<LocalDate> holidates = new ArrayList<>( 6 );
	String dateSymbols = "yy/MM/dd";
	SimpleDateFormat acceptedDateStyle = new SimpleDateFormat( dateSymbols );
	// IMPROVE a set eventually
	private String typicalFilename = "settings.v_s";
	private final String peopleKey = "people per day";
	private final String remindKey = "remind to save on close";
	private final String holidaysKey = "holidays";

	public boolean adoptVals()
	{
		return adoptValsFrom( typicalFilename );
	}

	public boolean adoptValsFrom( String filename )
	{
		final boolean worked = true;
		try
		{
			return adoptValsFrom( Paths.get( filename ) );
		}
		catch ( InvalidPathException ipe )
		{
			final String here = getClass().getName() +".avf-s ";
			System.err.println( here +"couldn't make path of "
					+ filename + ipe );
		}
		return ! worked;
	}

	public boolean adoptValsFrom( Path relPath )
	{
		final String here = getClass().getName() +".avf-p ";
		final boolean worked = true;
		if ( relPath == null )
			return ! worked;
		try {
			if ( Files.exists(relPath) )
			{
				int ind = 0;
				for ( String configLine : Files.readAllLines( relPath ) )
				{
					if ( configLine != null )
					{
						applyLineOfConfigFile( configLine, ind );
					}
					ind++;
				}
				return worked;
			}
		}
		catch ( IOException ioe )
		{
			System.err.println( here +" had some I/O problem."
					+ " there's like five options\n "+ ioe );
		}
		catch ( SecurityException se )
		{
			System.err.println( here +" had some access problem "+ se );
		}
		return ! worked;
	}

	// IMPROVE do something nicer later
	/** assumes name = value */
	private void applyLineOfConfigFile( String line, int lineNum )
	{
		final String here = getClass().getName() +".alocf ";
		if ( line.isEmpty() || line.startsWith( " #" ) )
			return; // NOTE ignore
		int indOfEqualSign = line.indexOf( '=' );
		if ( indOfEqualSign <= 0 || indOfEqualSign == line.length() -1 )
		{
			System.out.println( here + lineNum+" |"+ line
					+"| is an invalid config" );
			return;
		}
		String key = line.substring( 0, indOfEqualSign ).trim();
		String value = line.substring( indOfEqualSign +1 ).trim();
		if ( key.equals( peopleKey ) )
		{
			setPeopleOnSameDay( value );
		}
		else if ( key.equals( holidaysKey ) )
		{
			addHolidate( value );
		}
		else if ( key.equals( remindKey ) )
		{
			setRemindSaveOnClose( value );
		}
		else
		{
			System.out.println( here + lineNum +" "
					+ key +" is an invalid config key" );
		}
	}

	private void setPeopleOnSameDay( String value )
	{
		try
		{
			int valAsInt = Integer.parseInt( value );
			if ( valAsInt < 1 )
			{
				peopleOnSameDay = 1;
			}
			else
			{
				peopleOnSameDay = valAsInt;
			}
		}
		catch ( NumberFormatException nfe )
		{
			final String here = getClass().getName() +".sposd-s ";
			System.err.println( here + value
					+" bad number format "+ nfe );
		}
	}

	/** assumes I'm getting csv dates, can get multiple times */
	private void addHolidate( String value )
	{
		/*
		functional way to count terms
		int terms = (int)value.chars().filter(
				charIntVal -> charIntVal == ',' ).count();
		 */
		String[] dates;
		if ( value.contains( "," ) )
		{
			dates = value.split( ",\\s*" );
			if ( dates.length < 1 )
				return;
		}
		else
		{
			dates = new String[]{ value };
		}
		for ( String hopefullyDate : dates )
		{
			try
			{
				LocalDate dayParsed = acceptedDateStyle
						.parse( hopefullyDate ).toInstant()
						.atZone( ZoneId.systemDefault() )
						.toLocalDate();
				holidates.add( dayParsed );
			}
			catch ( ParseException pe )
			{
				final String here = getClass().getName() +".ah-s ";
				System.err.println( here + hopefullyDate
						+" bad date format "+ pe );
			}
		}
	}

	private void setRemindSaveOnClose( String value )
	{
		value = value.toLowerCase();
		if ( value.equals( "yes" ) )
		{
			remindSaveOnClose = true;
		}
		else if ( value.equals( "no" ) )
		{
			remindSaveOnClose = false;
		}
	}

	// yngni
	public void addHolidate( LocalDate when )
	{
		if ( ! (when == null || holidates.contains( when )) )
		{
			holidates.add( when );
		}
	}

	public boolean isHoliday( LocalDate when )
	{
		return holidates.contains( when );
	}

	public int getPeopleOnSameDay()
	{
		return peopleOnSameDay;
	}

	public void setPeopleOnSameDay( int peopleOnSameDay )
	{
		this.peopleOnSameDay = peopleOnSameDay;
	}

	public boolean isRemindSaveOnClose()
	{
		return remindSaveOnClose;
	}

	public void setRemindSaveOnClose( boolean remindSaveOnClose )
	{
		this.remindSaveOnClose = remindSaveOnClose;
	}

}





















