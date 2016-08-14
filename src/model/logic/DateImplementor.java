package model.logic;

import java.sql.Date;

import model.entities.Copy;
import model.entities.PresenceEnum;

/**
 * @author Artem Pryzhkov
 *Class contains methods that provides operations with date
 */
public class DateImplementor {
	public static long MILLISECONDS_IN_MONTH=2628000000l;

	/**
	 * Method initialized values of the taking date and bring back date in the class Copy
	 * in case giving copy in arms
	 * @param copy - object of the class in which will be init values
	 * @return Copy object after initializing
	 */
	public static Copy takeBookPerMonth(Copy copy){
		copy.setDateTaking(currentDate());
		copy.setDateBringBack(new Date(new java.util.Date().getTime()+MILLISECONDS_IN_MONTH));
		return copy;
	}
	
	/**
	 * Method initialized values of the taking date and bring back date in the class Copy 
	 * in case giving copy in hall
	 * @param copy - object of the class in which will be init values
	 * @return Copy object after initializing
	 */
	public static Copy takeBookInHall(Copy copy){
		copy.setDateTaking(currentDate());
		copy.setDateBringBack(currentDate());
		return copy;
	}
	
	/**
	 * Method set current date to the field "dateFactBringBack" of the input object
	 * @param copy - object of the class in which will be init values
	 * @return Copy object after initializing
	 */
	public static Copy bookBringBack(Copy copy){
		copy.setDateFactBringBack(currentDate());
		return copy;
	}
	
	/**
	 * @return current date
	 */
	public static Date currentDate(){
		return new Date(new java.util.Date().getTime());
	}
}
