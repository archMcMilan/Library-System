package model.service;

import java.util.List;

import org.apache.log4j.Logger;

import model.dao.CopyDao;
import model.dao.DaoFactory;
import model.entities.Copy;
import model.entities.PresenceEnum;
import model.logic.DateImplementor;
import model.logic.OperationWithBooks;
import view.LoggerMessage;

/**
 * @author Artem Pryzhkov
 * Class provides layer between database and IDE
 */
public class CopyService {
	private static CopyService instance = new CopyService();

	public static CopyService getInstance() {
		return instance;
	}
	
	private CopyDao createDaoCopy(){
		return DaoFactory.getFactory().createCopyDao();
	}
	
	/**
	 * Method provides operation that bring back books's copy on the shelf
	 * @param id of book's copy
	 * @return true if bring back successfully, otherwise false
	 */
	public boolean bringBack(long id){
		Copy copy=createDaoCopy().find(id);
		copy.setPresence(PresenceEnum.SHELF);
		copy=DateImplementor.bookBringBack(copy);
		
		return createDaoCopy().update(copy);
	}

	/**
	 * Method give available Book copy to the reader in arms
	 * @param id - id of the Book object
	 * @return Copy object that will be given to the Reader. If it is no available copy it will return null
	 */
	public Copy takeAvailableCopyInArms(long id){
		List<Copy> copies=createDaoCopy().findCopyByBook(id);
		for(Copy c:copies){
			if(c.getPresence()==PresenceEnum.SHELF){
				c=DateImplementor.takeBookPerMonth(c);
				c.setPresence(PresenceEnum.ARMS);
				c.setDateFactBringBack(null);
				return c;
			}
		}
		Logger.getLogger(OperationWithBooks.class.getName()).
			info(LoggerMessage.RETURN_COPIES_NULL+OperationWithBooks.class.getName());
		return null;
	}
	
	/**
	 * Method give available Book copy to the reader in hall
	 * @param id - id of the Book object
	 * @return Copy object that will be given to the Reader. If it is no available copy it will return null
	 */
	public Copy takeAvailableCopyInHall(long id){
		List<Copy> copies=createDaoCopy().findCopyByBook(id);
		for(Copy c:copies){
			if(c.getPresence()==PresenceEnum.SHELF){
				c=DateImplementor.takeBookInHall(c);
				c.setPresence(PresenceEnum.HALL);
				c.setDateFactBringBack(null);
				return c;
			}
		}
		Logger.getLogger(OperationWithBooks.class.getName()).
			info(LoggerMessage.RETURN_COPIES_NULL+OperationWithBooks.class.getName());
		return null;
	}
	
	/**
	 * Method count amount of the available Book copies
	 * @param id - Book object id
	 * @return int value
	 */
	public int countAvailableCopy(long id){
		List<Copy> copies=createDaoCopy().findCopyByBook(id);
		int counter=0;
		for(Copy c:copies){
			if(c.getPresence()==PresenceEnum.SHELF){
				counter++;
			}
		}
		return counter;
	}
}
