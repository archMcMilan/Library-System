package model.service;

import java.util.List;

import model.dao.CopyDao;
import model.dao.DaoFactory;
import model.entities.Copy;
import model.entities.PresenceEnum;
import model.logic.DateImplementor;
import model.logic.OperationWithBooks;

/**
 * @author Artem Pryzhkov
 * Class provides layer between database and IDE
 */
public class CopyService {
	private static CopyService instance = new CopyService();

	public static CopyService getInstance() {
		return instance;
	}
	
	private CopyDao createCopyDao(){
		return DaoFactory.getFactory().createCopyDao();
	}
	
	public List<Copy> takeBook(long id) {
		return createCopyDao().findCopyByBook(id);
	}
	
	public boolean bringBack(long id){
		return createCopyDao().update(OperationWithBooks.bookBringBack(createCopyDao().find(id)));
	}

}
