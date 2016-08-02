
/*
&copy; Nicholas Prado, released under Fair License (fairlicense.org) terms:

Usage of the works is permitted provided that this instrument is retained with the works, so that any entity that uses the works is notified of this instrument.

DISCLAIMER: THE WORKS ARE WITHOUT WARRANTY.

Vacation scheduler:
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

// REM acts as a constructor ; commas rather than semicolons
/** a list of days; callee handles preference level */
function Choice()
{
	this.days = [],
	addDate: function( anotherDay )
	{
		var ind = this.days.indexOf( anotherDay );
		if ( ind < 0 )
		{
			this.days.push( anotherDay );
		}
	},
	removeDate: function( badDay )
	{
		var ind = this.days.indexOf( anotherDay );
		if ( ind >= 0 )
		{
			this.days.splice( ind, 1 );
			// REM remove that element, reindex array
		}
	},
	getDays: function()
	{
		return this.days;
	},
	numOfDays: function()
	{
		return this.days.length;
	},
	asString: function()
	{
		var desc = this.level.toString();
		for ( var aDay in this.days )
		{
			desc += ", "+ aDay.toString();
		}
		return desc
	}
};

/** an ordered list of Choices; callee handles seniority */
function Person( theName )
{
	this.nameId = theName,
	this.preferences = [],
	addToChoice: function( aDay, level )
	{
		if ( level > this.preferences.length
			|| this.preferences[ level ] == undefined )
		{
			this.preferences[ level ] = new Choice();
		}
		this.preferences[ level ].addDate( aDay );
	},
	removeFromChoice( aDay, level )
	{
		if ( this.preferences[ level ] != undefined )
			this.preferences[ level ].removeDate( aDay );
	},
	/** value or undefined; null means you're past the limit */
	getChoice: function( level )
	{
		if ( level > this.preferences.length )
			return null;
		else
			return this.preferences[ level ];		
	},
	/** the highest value choice, not how many are allocated */
	numOfChoices: function()
	{
		return this.preferences.length;
	},
	asString()
	{
		var pDesc = this.nameId;
		for ( var desire in this.preferences )
		{
			pDesc += "\n"+ desire.asString();
		}
		return desc;
	}
};

function VsApp()
{
	this.people = [];
	/** add choice, conflict pushes original to the bottom */
	addChoiceToPerson: function( person, seniority, priority, day )
	{
		if ( seniority > this.people.length
			|| this.people[ seniority ] == undefined )
		{
			this.people[ seniority ] = new Person( person );
		}
		else if ( this.people[ seniority ].nameId != person )
		{
			// send it to the back
			var tempPerson = new Person( this.people[ seniority ].nameId );
			for ( var ind = 0; ind < this.people[ seniority ].numOfChoices();
				ind++ )
			{
				var prevChoiceOfLevel = this.people[ seniority ].getChoice( ind );
				for ( var aDay in prevChoiceOfLevel )
				{
					tempPerson.addToChoice( aDay, ind );
				}
			}
			this.people.push( tempPerson );
			// now add the new person in his'oer place
			this.people[ seniority ] = new Person( person );
		}
		this.people[ seniority ].addToChoice( day, priority );
	},
	removeChoiceFromPerson: function( person, seniority, priority, day )
	{
		
	}
};

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
















