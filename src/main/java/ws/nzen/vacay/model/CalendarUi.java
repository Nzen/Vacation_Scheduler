
package main.java.ws.nzen.vacay.model;

import java.io.Serializable;
import java.time.YearMonth;

/** struct for saving and restoring ui entries */
public class CalendarUi implements Serializable
{

	private static final long serialVersionUID = 1L;
	private YearMonth currentMonth;
	private String personName;
	private String seniorityVal;
	private String desireVal;
	private boolean radioAdd;

	public CalendarUi( YearMonth shownYearMonth, String name,
			String seniorness, String desire, boolean isAdd )
	{
		currentMonth = shownYearMonth;
		personName = name;
		seniorityVal = seniorness;
		desireVal = desire;
		radioAdd = isAdd;
	}

	public YearMonth getCurrentMonth()
	{
		return currentMonth;
	}
	public void setCurrentMonth( YearMonth currentMonth )
	{
		this.currentMonth = currentMonth;
	}
	public String getPersonName()
	{
		return personName;
	}
	public void setPersonName( String personName )
	{
		this.personName = personName;
	}
	public String getSeniorityVal()
	{
		return seniorityVal;
	}
	public void setSeniorityVal( String seniorityVal )
	{
		this.seniorityVal = seniorityVal;
	}
	public String getDesireVal()
	{
		return desireVal;
	}
	public void setDesireVal( String desireVal )
	{
		this.desireVal = desireVal;
	}
	public boolean isRadioAdd()
	{
		return radioAdd;
	}
	public void setRadioAdd( boolean radioAdd )
	{
		this.radioAdd = radioAdd;
	}
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

}
