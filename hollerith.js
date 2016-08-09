
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

/*
	test the model by adding and removing people
	decide how constrainer will pull data from the model
	
*/

var termin = function() {};
termin.log = function( message )
{
	try
	{ console.log( message ); }
	catch ( exception )
	{ return; } // IE reputedly has no console.
}

// --- data model

// REM acts as a constructor ; commas rather than semicolons
/** a list of days; callee handles preference level */
function Choice()
{
	this.days = [];
};
Choice.prototype.addDate = function( anotherDay )
{
	var ind = this.days.indexOf( anotherDay );
	if ( ind < 0 )
	{
		this.days.push( anotherDay );
	}
};
Choice.prototype.removeDate = function( badDay )
{
	var ind = this.days.indexOf( anotherDay );
	if ( ind >= 0 )
	{
		this.days.splice( ind, 1 );
		// REM remove that element, reindex array
	}
};
Choice.prototype.getDays = function()
{
	return this.days;
};
Choice.prototype.numOfDays = function()
{
	return this.days.length;
};
Choice.prototype.asString = function()
{
	var desc = this.level.toString();
	for ( var aDay in this.days )
	{
		desc += ", "+ aDay.toString();
	}
	return desc;
};

/** an ordered list of Choices; callee handles seniority */
function Person( theName )
{
	this.nameId = theName,
	this.preferences = [];
};
Person.prototype.addToChoice = function( aDay, level )
{
	if ( level > this.preferences.length
		|| this.preferences[ level ] == undefined )
	{
		this.preferences[ level ] = new Choice();
	}
	this.preferences[ level ].addDate( aDay );
};
Person.prototype.removeFromChoice = function( aDay, level )
{
	if ( this.preferences[ level ] != undefined )
		this.preferences[ level ].removeDate( aDay );
};
/** value or undefined; null means you're past the limit */
Person.prototype.getChoice = function( level )
{
	if ( level > this.preferences.length )
		return null;
	else
		return this.preferences[ level ];		
};
/** the highest value choice, not how many are allocated */
Person.prototype.numOfChoices = function()
{
	return this.preferences.length;
}
Person.prototype.asString = function()
{
	var pDesc = this.nameId;
	for ( var desire in this.preferences )
	{
		pDesc += "\n"+ desire.asString();
	}
	return desc;
};

/** holds all the people entered currently */
function VsModel()
{
	this.people = [];
};
/** add choice, conflict pushes original to the bottom */
VsModel.prototype.addChoiceToPerson = function( nameEntered, seniority, priority, day )
{
	if ( seniority > this.people.length
		|| this.people[ seniority ] == undefined )
	{
		this.people[ seniority ] = new Person( nameEntered );
	}
	else if ( this.people[ seniority ].nameId != nameEntered )
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
		this.people[ seniority ] = new Person( nameEntered );
	}
	this.people[ seniority ].addToChoice( day, priority );
};
/** remove a choice from a person */
VsModel.prototype.removeChoiceFromPerson = function( nameEntered, seniority, priority, day )
{
	if ( seniority > this.people.length
		|| this.people[ seniority ] == undefined )
	{
		this.people[ seniority ] = new Person( nameEntered );
	}
	else if ( this.people[ seniority ].nameId != nameEntered )
	{
		var indOfPerson = this.people.indexOf( nameEntered );
		if ( indOfPerson < 0 )
		{
			this.people.push(new Person( nameEntered ));
		}
		else
		{
			this.people[ indOfPerson ].removeFromChoice( day, priority );
		}
	}
	else
	{
			this.people[ indOfPerson ].removeFromChoice( day, priority );
	}
};
/** print everything within the VsModel */
VsModel.prototype.asString = function()
{
	var theEverything = "";
	for ( var ind = 0; ind < this.people.length; ind++ )
	{
		theEverything += ind.toString() +" "+ this.people[ ind ].asString(); 
	}
	return theEverything;
};
	// IMPROVE other getters for constrainer, or will it access them directly?

// --- dom interface

var peoples = new VsModel();

function addChoice()
{
	termin.log( "add choice" );
	peoples.addChoiceToPerson(
			document.getElementById("name").value,
			document.getElementById("senority").value,
			document.getElementById("preference").value,
			document.getElementById("day").value );
	document.getElementById("entryOut").value = peoples.asString();
}
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

function removeChoice()
{
	termin.log( "remove choice" );
	peoples.removeChoiceFromPerson(
			document.getElementById("name").value,
			document.getElementById("senority").value,
			document.getElementById("preference").value,
			document.getElementById("day").value );
	document.getElementById("entryOut").value = peoples.asString();
}
















