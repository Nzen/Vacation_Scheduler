package main.java.ws.nzen.vacay;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;

public class Exporter
{
	static final String cl = "e.";
	private String[] yearAsOutput = new String[ 373 ]; // 12m * 31d 

	public Exporter()
	{
		
	}

	public void setDay( LocalDate when, String saysWhat )
	{
		int monthInd = when.getMonthValue() -1;
		int dayInd = when.getDayOfMonth() -1;
		int daysAllocatedPerMonth = 31;
		yearAsOutput[ monthInd * daysAllocatedPerMonth + dayInd ] = saysWhat;
	}

	public boolean asExcelCvs()
	{
		String defaultFile = "requests.csv";
		return asExcelCvs( defaultFile );
	}

	public boolean asExcelCvs( String filename )
	{
		final boolean didIt = true;
		if ( filename.isEmpty() )
		{
			asExcelCvs();
		}
		try
		{
			asExcelCvs( Paths.get( filename ) );
			return didIt;
		}
		catch ( InvalidPathException ipe )
		{
			String here = cl +"aec bad path name";
			return ! didIt;
		}
	}

	private boolean asExcelCvs( Path filepath )
	{
		/*
		j f m
		 , , ,	1
		f, ,g,	2
		f,m, ,	3rd day
		month of day wise
		*/
		StringBuilder yearAsBlock = new StringBuilder( 400 );
		int yearLength = 336;
		int monthOffset = 31;
		int monthlength = 31;
		int months = 11;
		for ( int daysOfMonth = 0; daysOfMonth < monthlength; daysOfMonth++ )
		{
			for ( int dayOfYear = 0; dayOfYear < yearLength;
					dayOfYear += monthOffset )
			{
				String says = yearAsOutput[ daysOfMonth + dayOfYear ];
				if ( says == null )
					says = "";
				yearAsBlock.append( says +"," );
			}
			yearAsBlock.append( "\r\n" );
		}
		return writeToFile( filepath, yearAsBlock.toString() );
	}

	private boolean writeToFile( Path where, String what )
	{
		final boolean didIt = true;
		try
		{
			if ( Files.notExists( where ) )
			{
				Files.createFile( where ); // IMPROVE give it permissable attributes
			}
			BufferedWriter paper = Files.newBufferedWriter( where,
					StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING );
			paper.append( what );
			paper.flush();
		}
		catch ( IOException ioe )
		{
			String here = cl + "wtf ";
			System.err.println( here + "had some I/O problem."
					+ " there's like five options\n " + ioe );
			return ! didIt;
		}
		return didIt;
	}

}


























