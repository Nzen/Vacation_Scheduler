
// Nicholas Prado
// Let's schedule people

/*
store all the preferences
when button pushed, sort preferences

	<"button" value="Add" onclick="stor()">
	<"button" value="Synergize" onclick="sortPrefs()">
	<input type="text" id="name">
	<select id="senor">
	;
	<select id="weekL1">
	<select id="dayL1">
	<select id="weekL2">
	<select id="dayL2">
	<textarea id="out"
*/


var termin = function() {};
termin.log = function( message )
{ try
	{ console.log( message ); }
catch ( exception )
	{ return; } // IE reputedly has no console.
}

var everything = [ 0,0,0,0 ];

function sortPrefs()
{	// sorts the preferences, updates page
	var which = firstPass( everything );
	secondPass( everything, which );
}

function asStr( desire, weight )
{
	var neme = 0; var da1 = 1; var da2 = 2;
	return desire[neme] +" "+ (weight+1) +" "+ parseDay(desire[da1]) +" ; "+ parseDay(desire[da2]) +"\n";
}

function parseDay( compressed )
{
	var wk = 0; var da = 1;
	var both = uncompress( compressed );
	return "" + both[ wk ] + "-" + both[ da ];
}

function uncompress( compressed )
{
	var wk_da = [ 0,0 ];
	wk_da[ 0 ] = Math.floor(compressed);
	wk_da[ 1 ] = Math.floor( ((compressed - wk_da[0]) * 10) );
	return wk_da;
}

function stor()
{	// sorts the preferences, updates page
	var ind = document.getElementById("senor").selectedIndex;
	var nam = document.getElementById("name").value;
	var offset = 10.0;
	var date1 = document.getElementById("weekL1").selectedIndex + ( document.getElementById("dayL1").selectedIndex / offset ) + 1.1;
	var date2 = document.getElementById("weekL2").selectedIndex + ( document.getElementById("dayL2").selectedIndex / offset ) + 1.1;
	everything[ind] = [ nam, date1, date2 ];
	document.getElementById("out").value = nam +" "+ (ind+1) +" "+ parseDay(date1) +" ; "+ parseDay(date2) +"\n";
}

function updateGrid( wholePref, prefD )
{
	var name = 0;
	var wk_da = uncompress( wholePref[ prefD ] );
	termin.log( wholePref[name] + " " + cellName(wk_da) );
	document.getElementById( cellName(wk_da) ).innerHTML = wholePref[ name ];
}

function cellName( both )
{ // wk3_d5
	var week = 0; var day = 1;
	return "wk" + both[week] + "_d" + both[day];
}

function firstPass( allPrefs )
{
	var which = new Array();
	var pref1 = 1;
	for ( var ind = 0; ind < allPrefs.length; ind++ )
	{
		if ( allPrefs[ind] === 0 )
			continue; // skip empty spot
		else if ( blocked(ind, which) )
			continue; // resolve via second pass
		for ( var sen = ind+1; sen < allPrefs.length; sen++ )
		{
			if ( allPrefs[ind][pref1] === allPrefs[sen][pref1] )
			which.push( sen );
		}
		updateGrid( allPrefs[ind], pref1 );
	}
	return which;
}

function blocked( needle, pile )
{
	if ( pile.length <= 0 )
		return false;
	else
	{	for ( var ind = 0; ind < pile.length; ind++ )
		{
			if ( pile[ind] == needle )
				return true;
	}	}
	return false;
}

function secondPass( allPrefs, which )
{	// which is a list of ints-seniority unresolved
	var da2 = 2;
	var failed = new Array();
	for ( var ind = 0; ind < which.length; ind++ )
	{
		if ( cellUsed( allPrefs[ which[ind] ][da2] ) )
			failed.push( which[ind] );
		else
			updateGrid( allPrefs[ which[ind] ], da2 );
	}
	if ( failed.length > 0 )
		warnUser( allPrefs, failed );
}

function cellUsed( compressed )
{
	var wk_da = uncompress( compressed );
	termin.log( cellName(wk_da) ); // cut when done
	return document.getElementById( cellName(wk_da) ).innerHTML != "...";
}

function warnUser( all, failed )
{
	var name = 0;
	var scream = "Couldn't accomodate:\n";
	for ( var ind = 0; ind < failed.length; ind++ )
	{
		scream += all[failed[ ind ]][name] + "\n";
	}
	document.getElementById("out").value = scream;
}















