package model.logic;

import java.util.List;

import org.apache.log4j.Logger;

import model.dao.DaoFactory;
import model.entities.Copy;
import model.entities.PresenceEnum;
import model.entities.Reader;
import view.LoggerMessage;

/**
 * @author Artem Pryzhkov
 *	Class provides methods to work with book distributing and bring back
 */
public class OperationWithBooks {
	
	
//	/**
//	 * Method give available Book copy to the reader in arms
//	 * @param id - id of the Book object
//	 * @return Copy object that will be given to the Reader. If it is no available copy it will return null
//	 */
//	public static Copy takeAvailableCopyInArms(long id){
//		List<Copy> copies=DaoFactory.getFactory().createCopyDao().findCopyByBook(id);
//		for(Copy c:copies){
//			if(c.getPresence()==PresenceEnum.SHELF){
//				c=DateImplementor.takeBookPerMonth(c);
//				c.setPresence(PresenceEnum.ARMS);
//				c.setDateFactBringBack(null);
//				return c;
//			}
//		}
//		Logger.getLogger(OperationWithBooks.class.getName()).
//			info(LoggerMessage.RETURN_COPIES_NULL+OperationWithBooks.class.getName());
//		return null;
//	}
	
//	/**
//	 * Method give available Book copy to the reader in hall
//	 * @param id - id of the Book object
//	 * @return Copy object that will be given to the Reader. If it is no available copy it will return null
//	 */
//	public static Copy takeAvailableCopyInHall(long id){
//		List<Copy> copies=DaoFactory.getFactory().createCopyDao().findCopyByBook(id);
//		for(Copy c:copies){
//			if(c.getPresence()==PresenceEnum.SHELF){
//				c=DateImplementor.takeBookInHall(c);
//				c.setPresence(PresenceEnum.HALL);
//				c.setDateFactBringBack(null);
//				return c;
//			}
//		}
//		Logger.getLogger(OperationWithBooks.class.getName()).
//			info(LoggerMessage.RETURN_COPIES_NULL+OperationWithBooks.class.getName());
//		return null;
//	}
	
//	/**
//	 * Method count amount of the available Book copies
//	 * @param id - Book object id
//	 * @return int value
//	 */
//	public static int countAvailableCopy(long id){
//		List<Copy> copies=DaoFactory.getFactory().createCopyDao().findCopyByBook(id);
//		int counter=0;
//		for(Copy c:copies){
//			if(c.getPresence()==PresenceEnum.SHELF){
//				counter++;
//			}
//		}
//		return counter;
//	}

	
//	/**
//	 * Method provides operation Book bring back on the shelf 
//	 * @param copy - Book copy object
//	 * @return copy object the was put on the shelf
//	 */
//	public static Copy bookBringBack(Copy copy){
//		copy.setPresence(PresenceEnum.SHELF);
//		copy=DateImplementor.bookBringBack(copy);
//		return copy;
//	}
//	
	
//	/**
//	 * Method provides operation distributing copy of the Book to reader
//	 * @param copy
//	 * @param readerId - id of the reader
//	 * @return object Reader with new Copy in the filed "copies"
//	 */
//	public static Reader distribute(Copy copy,long readerId){
//		Reader reader=DaoFactory.getFactory().createReaderDao().find(readerId);
//		
//		DaoFactory.getFactory().createCopyDao().update(copy);
//		reader.addCopy(copy);
//		DaoFactory.getFactory().createReaderDao().updateCopyList(reader);
//		return reader;
//	}
}
