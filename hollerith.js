
/*
Nicholas Prado
MIT license
Handles scheduling employees with heirarchal seniority and several preferences.
*/

/*
	<td id="d28">...</td>
	Name <input type="text" id="name">
	<select id="senority">
	Choice <select id="preference">
	month (01-12) <input type="text" id="month" maxlength="2" size="5" disabled />
	day <input type="text" id="day" maxlength="2" size="5" />
	<input type="button" value="Add" onclick="addChoice()" /> |
	 <input type="button" value="Remove" onclick="removeChoice()" />
	<textarea id="entryOut" rows="4" cols="50" disabled></textarea>
*/

var termin = function() {};
termin.log = function( message )
{ try
	{ console.log( message ); }
catch ( exception )
	{ return; } // IE reputedly has no console.
}

// --- data model

// REM acts as a constructor
/** a list of days with a preference level */
function Choice()
{
	this.level = -1;
	this.days = [];
	addDate: function( anotherDay )
	{
		var ind = this.days.indexOf( anotherDay );
		if ( ind < 0 )
		{
			this.days.push( anotherDay );
		}
	};
	removeDate: function( badDay )
	{
		var ind = this.days.indexOf( anotherDay );
		if ( ind >= 0 )
		{
			this.days.splice( ind, 1 );
			// REM remove that element, reindex array
		}
	}
	asString: function()
	{
		var desc = this.level.toString();
		for ( var aDay in this.days )
		{
			desc += aDay.toString();
		}
		return desc
	}
}

// --- dom interface

var people = [];

function addChoice()
{
	// NEXT make this use an array of choices, as a test
	var currName = document.getElementById("name").value;
	if ( currName.length < 1 )
	{
		termin.log( "blank name" );
		return;
	}
	else if (people.indexOf( currName ) >= 0)
	{
		termin.log( "already saved" );
		return;
	}
	else
	{
		people.push( currName );
		document.getElementById("entryOut").value += currName + "\n";
		termin.log( "added name" );
	}
}

function removeChoice()
{
	termin.log( "remove choice" );
}
















