/**
 * 
 */
package ws.nzen.vacay;

/**
 * @author nzen
 *
 */
public class Constrainer
{

	/*
	add desire to calendar
	remove desire from calendar
	*/

	/*
	add desire to calendar
	
	if a desire above this level is satisfied
		if the active spots are full
			put in the back or whatever , punt on sorting probably
		else
			put blanks in the active spots and put in back
	elseif this is the active level, or first level
		if an active spot empty or intentionally blank
			put in spot
		else
			if mylev > present
				put in spot
				recur with displaced desire, turn off its activeness
			elsif mylev == present
				if mySen > present
					put in spot
				else
					put below
					deactivate level
					recheck levels
	
	*/

	/*
	remove desire from calendar
	
	
	*/

}



























